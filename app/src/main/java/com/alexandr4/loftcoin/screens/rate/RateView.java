package com.alexandr4.loftcoin.screens.rate;

import com.alexandr4.loftcoin.data.db.model.CoinEntity;

import java.util.List;

public interface RateView {

    void setCoins(List<CoinEntity> coins);

    void setRefreshing(Boolean refreshing);

    void showCurrencyDialog();

    void invalidateRates();
}
