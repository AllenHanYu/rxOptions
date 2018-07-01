package com.example.hanyu.deeplinktest;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

public class MyApplication extends Application {

    private AppsFlyerConversionListener listener = new AppsFlyerConversionListener() {
        @Override
        public void onInstallConversionDataLoaded(Map<String, String> map) {
            for (String attrName : map.keySet()) {
                Log.d("hanyu", "attrName = " + attrName);
            }
        }

        @Override
        public void onInstallConversionFailure(String s) {

        }

        @Override
        public void onAppOpenAttribution(Map<String, String> map) {

        }

        @Override
        public void onAttributionFailure(String s) {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        AppsFlyerLib.getInstance().init("", listener, this);
        AppsFlyerLib.getInstance().startTracking(this);

        AppsFlyerLib.getInstance().registerConversionListener(this, listener);

    }
}
