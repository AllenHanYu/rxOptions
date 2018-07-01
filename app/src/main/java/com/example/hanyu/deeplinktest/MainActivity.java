package com.example.hanyu.deeplinktest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class MainActivity extends AppCompatActivity {

    MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Message obtain = Message.obtain();
//        obtain.what = 1;
//        handler.sendMessageDelayed(obtain, 4000);
//        rxTest();

        //lambda
//        new Thread(() -> Log.d("", "")).start();

        List<String> languages = Arrays.asList("java", "scala", "python");

//        for (String each : languages) {
//            System.out.print(each);
//        }
//        languages.forEach(x -> System.out.print(x));

//        languages.stream().map(x -> x + x * 0.5).forEach(x -> System.out.print(""));
//        Observable.interval(10000, 1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                Log.d("hanyu", "执行了  执行了 执行了");
//            }
//        });


        Intent intent = new Intent(this, RxTestActivity.class);
        startActivity(intent);

    }

    private void setCustomDensity(@NonNull Activity activity, @NonNull Application application) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        float targetDensity = displayMetrics.widthPixels / 360;
        int targetDensityDpi = (int) (160 * targetDensity);

        displayMetrics.density = displayMetrics.scaledDensity = targetDensity;
        displayMetrics.densityDpi = targetDensityDpi;


        DisplayMetrics displayMetrics1 = activity.getResources().getDisplayMetrics();
        displayMetrics1.density = displayMetrics1.scaledDensity = targetDensity;
        displayMetrics1.densityDpi = targetDensityDpi;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void handleMsg(Message message) {
        Log.d("hanyu", "mssenage === " + message.what);
    }

//    class MyHandlerThread extends Thread {
//
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                System.out.println("Thread Name  =====" + Thread.currentThread().getName() + "Msg what ==" + msg.what);
//            }
//        };
//
//        @Override
//        public void run() {
//            super.run();
//
//            Looper.prepare();
//            try {
//                sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            handler.sendEmptyMessage(3);
//            Looper.loop();
//        }
//    }

    @SuppressLint("CheckResult")
    private void rxTest() {
        //    创建一个被观察者，并发送事件，发送的事件不可以超过10个以上
        io.reactivex.Observable.just(1, 2, 3).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        //这个方法和 just() 类似，只不过 fromArray 可以传入多于10个的变量，并且可以传入一个数组。
        Integer[] array = {1, 2, 3, 4, 5, 6};
        Observable.fromArray(array).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //这里的 Callable 是 java.util.concurrent 中的 Callable，
        // Callable 和 Runnable 的用法基本一致，只是它会返回一个结果值，这个结果值就是发给观察者的。
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

//        参数中的 Future 是 java.util.concurrent 中的 Future，
// Future 的作用是增加了 cancel() 等方法操作 Callable，它可以通过 get() 方法来获取 Callable 返回的值。

        Observable.fromFuture(new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "返回结果";
            }
        })).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

            }
        }).subscribe(new Observer<String>() {
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

            }
        });
//        map 可以将被观察者发送的数据类型转变成其他的类型
        Observable.just(1, 2, 3, 4).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + "";
            }
        }).subscribe(new Observer<String>() {
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

            }
        });

    }

}
