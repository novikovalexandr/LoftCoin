package com.alexandr4.loftcoin.screens.currencies;

import com.alexandr4.loftcoin.data.db.model.CoinEntity;

public interface CurrenciesBottomSheetListener {
    void onCurrencySelected(CoinEntity coin);
}