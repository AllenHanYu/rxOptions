package com.example.hanyu.deeplinktest.main;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;

    public SplashPresenter(SplashContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        Observable.just("")
                .delay(4000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.jumpToMainActivity();
                    }
                });
    }

    @Override
    public void unsubscribe() {

    }
}
