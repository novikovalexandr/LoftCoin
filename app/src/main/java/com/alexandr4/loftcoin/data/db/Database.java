package com.alexandr4.loftcoin.data.db;

import com.alexandr4.loftcoin.data.db.model.CoinEntity;

import java.util.List;

public interface Database {
    void saveCoins(List<CoinEntity> coins);

    List<CoinEntity> getCoins();
}
