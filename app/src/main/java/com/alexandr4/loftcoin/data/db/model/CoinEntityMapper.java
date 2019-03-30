package com.alexandr4.loftcoin.data.db.model;

import com.alexandr4.loftcoin.data.api.model.Coin;

import java.util.List;

public interface CoinEntityMapper {
    List<CoinEntity> map(List<Coin> coins);
}
