package com.alexandr4.loftcoin.screens.start;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.alexandr4.loftcoin.App;
import com.alexandr4.loftcoin.R;
import com.alexandr4.loftcoin.data.api.Api;
import com.alexandr4.loftcoin.data.db.Database;
import com.alexandr4.loftcoin.data.db.model.CoinEntityMapper;
import com.alexandr4.loftcoin.data.db.model.CoinEntityMapperImpl;
import com.alexandr4.loftcoin.data.prefs.Prefs;
import com.alexandr4.loftcoin.screens.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements StartView {

    public static void start(Context context) {
        Intent starter = new Intent(context, StartActivity.class);
        context.startActivity(starter);
    }

    public static void startInNewTask(Context context) {
        Intent starter = new Intent(context, StartActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @Override
    public void navigateToMainScreen() {
        MainActivity.start(this);
        finish();
    }

    @BindView(R.id.start_top_corner)
    ImageView topCorner;

    @BindView(R.id.start_bottom_corner)
    ImageView bottomCorner;

    private StartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        Prefs prefs = ((App) getApplication()).getPrefs();
        Api api = (((App) getApplication()).getApi());
        Database database = (((App) getApplication()).getDatabase());
        CoinEntityMapper mapper = new CoinEntityMapperImpl();

        presenter = new StartPresenterImpl(prefs, api, database, mapper);
        presenter.attachView(this);
        presenter.loadRates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startAnimations();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    private void startAnimations() {

        short angleStart = 0;
        short angleEnd = 360;
        short speedAnimation = 30000;

        ObjectAnimator innerAnimator = ObjectAnimator.ofFloat(topCorner, "rotation", angleStart, angleEnd);
        innerAnimator.setDuration(speedAnimation);
        innerAnimator.setRepeatMode(ValueAnimator.RESTART);
        innerAnimator.setRepeatCount(ValueAnimator.INFINITE);
        innerAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator outerAnimator = ObjectAnimator.ofFloat(bottomCorner, "rotation", angleStart, -angleEnd);
        outerAnimator.setDuration(speedAnimation * 2);
        outerAnimator.setRepeatMode(ValueAnimator.RESTART);
        outerAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outerAnimator.setInterpolator(new LinearInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(innerAnimator).with(outerAnimator);
        set.start();
    }
}
