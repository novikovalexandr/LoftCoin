package com.alexandr4.loftcoin.work;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class WorkHelperImpl implements WorkHelper {

    @Override
    public void startSyncRateWorker(String symbol) {

        int requestRepeatInterval = 15;

        WorkManager workManager = WorkManager.getInstance();
        workManager.cancelAllWorkByTag(symbol);

        Data data = new Data.Builder()
                .putString(SyncRateWorker.EXTRA_SYMBOL, symbol)
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(SyncRateWorker.class, requestRepeatInterval, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(symbol)
                .setInputData(data)
                .build();

        workManager.enqueue(request);
    }
}
