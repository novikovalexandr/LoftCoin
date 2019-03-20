package com.alexandr4.loftcoin.screens.launch;

import android.os.Bundle;

import com.alexandr4.loftcoin.App;
import com.alexandr4.loftcoin.data.prefs.Prefs;
import com.alexandr4.loftcoin.screens.start.StartActivity;
import com.alexandr4.loftcoin.screens.welcome.WelcomeActivity;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Prefs prefs = ((App) getApplication()).getPrefs();
        if (prefs.isFirstLaunch()) {
            WelcomeActivity.start(this);
        } else {
            StartActivity.start(this);
        }
        finish();
    }
}
