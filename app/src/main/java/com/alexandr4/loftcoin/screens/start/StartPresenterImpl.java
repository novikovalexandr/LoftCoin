package com.alexandr4.loftcoin.screens.start;

import com.alexandr4.loftcoin.data.api.Api;
import com.alexandr4.loftcoin.data.api.model.Coin;
import com.alexandr4.loftcoin.data.api.model.RateResponse;
import com.alexandr4.loftcoin.data.db.Database;
import com.alexandr4.loftcoin.data.db.model.CoinEntity;
import com.alexandr4.loftcoin.data.db.model.CoinEntityMapper;
import com.alexandr4.loftcoin.data.prefs.Prefs;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class StartPresenterImpl implements StartPresenter {

    private Prefs prefs;
    private Api api;
    private Database database;
    private CoinEntityMapper coinEntityMapper;

    public StartPresenterImpl(Prefs prefs, Api api, Database database, CoinEntityMapper coinEntityMapper) {
        this.prefs = prefs;
        this.api = api;
        this.database = database;
        this.coinEntityMapper = coinEntityMapper;
    }

    @Nullable
    private StartView view;

    @Override
    public void attachView(StartView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadRates() {

        Call<RateResponse> call = api.rates(Api.CONVERT);

        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(@NonNull Call<RateResponse> call, @NonNull Response<RateResponse> response) {

                if (response.body() != null) {
                    List<Coin> coins = response.body().data;
                    List<CoinEntity> coinEntities = coinEntityMapper.map(coins);
                    database.saveCoins(coinEntities);
                }

                if (view != null) {
                    view.navigateToMainScreen();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RateResponse> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
    }
}
