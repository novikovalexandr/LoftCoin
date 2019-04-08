package com.alexandr4.loftcoin.data.db.room;

import com.alexandr4.loftcoin.data.db.model.CoinEntity;
import com.alexandr4.loftcoin.data.db.model.Wallet;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CoinEntity.class, Wallet.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();

    public abstract WalletDao walletDao();
}