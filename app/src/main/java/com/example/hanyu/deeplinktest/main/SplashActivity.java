package com.example.hanyu.deeplinktest.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hanyu.deeplinktest.MainActivity;
import com.example.hanyu.deeplinktest.R;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new SplashPresenter(this);
    }

    @Override
    public void jumpToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
