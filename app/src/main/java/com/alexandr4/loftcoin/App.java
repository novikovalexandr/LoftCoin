package com.alexandr4.loftcoin;

import android.app.Application;

import com.alexandr4.loftcoin.data.api.Api;
import com.alexandr4.loftcoin.data.api.ApiInitializer;
import com.alexandr4.loftcoin.data.db.Database;
import com.alexandr4.loftcoin.data.db.DatabaseInitializer;
import com.alexandr4.loftcoin.data.db.realm.DatabaseImplRealm;
import com.alexandr4.loftcoin.data.prefs.Prefs;
import com.alexandr4.loftcoin.data.prefs.PrefsImpl;

import timber.log.Timber;

public class App extends Application {

    private Prefs prefs;
    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        prefs = new PrefsImpl(this);
        api = new ApiInitializer().init();
        new DatabaseInitializer().init(this);
    }

    public Prefs getPrefs() {
        return prefs;
    }

    public Api getApi() {
        return api;
    }

    public Database getDatabase() {
        return new DatabaseImplRealm();
    }
}
