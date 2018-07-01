package com.example.hanyu.deeplinktest;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;


/**
 * 解决Handler内存泄漏的问题
 */
public class MyHandler extends Handler {

    private WeakReference<MainActivity> mWeakReference;

    public MyHandler(MainActivity mainActivity) {
        this.mWeakReference = new WeakReference<>(mainActivity);
    }

    @Override
    public void handleMessage(Message msg) {
        int what = msg.what;
        if (what == 1) {
            mWeakReference.get().handleMsg(msg);
        }
    }
}
