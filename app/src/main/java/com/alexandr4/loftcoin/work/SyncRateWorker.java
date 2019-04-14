package com.alexandr4.loftcoin.work;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.alexandr4.loftcoin.App;
import com.alexandr4.loftcoin.R;
import com.alexandr4.loftcoin.data.api.Api;
import com.alexandr4.loftcoin.data.db.Database;
import com.alexandr4.loftcoin.data.db.model.CoinEntity;
import com.alexandr4.loftcoin.data.db.model.CoinEntityMapper;
import com.alexandr4.loftcoin.data.db.model.CoinEntityMapperImpl;
import com.alexandr4.loftcoin.data.db.model.QuoteEntity;
import com.alexandr4.loftcoin.screens.main.MainActivity;
import com.alexandr4.loftcoin.utils.CurrencyFormatter;
import com.alexandr4.loftcoin.utils.Fiat;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class SyncRateWorker extends Worker {

    public static final String EXTRA_SYMBOL = "symbol";
    private static final String NOTIFICATION_CHANNEL_RATE_CHANGED = "RATE_CHANGED";

    public SyncRateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        App app = (App) getApplicationContext();
        Api api = app.getApi();

        CoinEntityMapper mapper = new CoinEntityMapperImpl();
        AtomicBoolean success = new AtomicBoolean(false);

        Disposable disposable = api.rates(Api.CONVERT)
                .map(rateResponse -> mapper.map(rateResponse.data))
                .subscribe(
                        coinEntities -> {
                            handleCoins(coinEntities);
                            success.set(true);

                        }, throwable -> {
                            handleError(throwable);
                            success.set(false);
                        }
                );

        if (success.get()) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }

    private void handleError(Throwable error) {

    }

    private void handleCoins(List<CoinEntity> newCoins) {
        App app = (App) getApplicationContext();
        Database database = app.getDatabase();
        CurrencyFormatter formatter = new CurrencyFormatter();

        String symbol = getInputData().getString(EXTRA_SYMBOL);

        database.open();

        Fiat fiat = Fiat.USD;

        CoinEntity oldCoin = database.getCoin(symbol);
        CoinEntity newCoin = findCoin(newCoins, symbol);

        if (oldCoin != null && newCoin != null) {
            QuoteEntity oldQuote = oldCoin.getQuote(fiat);
            QuoteEntity newQuote = newCoin.getQuote(fiat);

            if (newQuote.price != (oldQuote.price + 1)) {

                Random random = new Random();
                int rangeRandomPrice = 100;
                double priceDiff = newQuote.price - (oldQuote.price + random.nextInt(rangeRandomPrice));

                String price = formatter.format(Math.abs(priceDiff), false);
                String priceDiffString;

                if (priceDiff > 0) {
                    priceDiffString = "+ " + price + " " + fiat.symbol;
                } else {
                    priceDiffString = "- " + price + " " + fiat.symbol;
                }
                showRateChangedNotification(newCoin, priceDiffString);
            }
        }
        database.saveCoins(newCoins);
        database.close();
    }

    private CoinEntity findCoin(List<CoinEntity> newCoins, String symbol) {
        for (CoinEntity coin : newCoins) {
            if (coin.symbol.equals(symbol)) {
                return coin;
            }
        }
        return null;
    }

    private void showRateChangedNotification(CoinEntity newCoin, String priceDiff) {
        Timber.d("showRateChangedNotification coin = %s, priceDiff == %s", newCoin.name, priceDiff);

        int requestCode = 0;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_RATE_CHANGED)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(newCoin.name)
                .setContentText(getApplicationContext().getString(R.string.notification_rate_changed_body, priceDiff))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_RATE_CHANGED,
                    getApplicationContext().getString(R.string.notification_channel_rate_changed),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(newCoin.id, notification);
    }
}
