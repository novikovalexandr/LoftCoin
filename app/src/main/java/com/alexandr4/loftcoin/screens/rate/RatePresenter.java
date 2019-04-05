package com.alexandr4.loftcoin.screens.rate;

import com.alexandr4.loftcoin.utils.Fiat;

public interface RatePresenter {

    void attachView(RateView view);

    void detachView();

    void getRate();

    void onRefresh();

    void onMenuItemCurrencyClick();

    void onFiatCurrencySelected(Fiat currency);
}