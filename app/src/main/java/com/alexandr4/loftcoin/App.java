package com.alexandr4.loftcoin;

import android.app.Application;

import com.alexandr4.loftcoin.data.prefs.Prefs;
import com.alexandr4.loftcoin.data.prefs.PrefsImpl;

public class App extends Application {

    private Prefs prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = new PrefsImpl(this);
    }

    public Prefs getPrefs() {
        return prefs;
    }
}
