package com.alexandr4.loftcoin.data.db.room;

import com.alexandr4.loftcoin.data.db.Database;
import com.alexandr4.loftcoin.data.db.model.CoinEntity;

import java.util.List;

import io.reactivex.Flowable;

public class DatabaseImplRoom implements Database {

    private AppDatabase appDatabase;

    public DatabaseImplRoom(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void saveCoins(List<CoinEntity> coins) {
        appDatabase.coinDao().saveCoins(coins);
    }

    @Override
    public Flowable<List<CoinEntity>> getCoins() {
        return appDatabase.coinDao().getCoins();
    }
}