package com.example.hanyu.deeplinktest.main;

import com.example.hanyu.deeplinktest.base.BasePresenter;
import com.example.hanyu.deeplinktest.base.BaseView;

public class SplashContract {

    interface View extends BaseView<Presenter> {
        void jumpToMainActivity();
    }

    interface Presenter extends BasePresenter {

    }
}
