package com.alexandr4.loftcoin.data.db.model;

import androidx.room.Embedded;

public class TransactionModel {

    @Embedded
    public Transaction transaction;

    @Embedded()
    public CoinEntity coin;
}
