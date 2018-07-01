package com.example.hanyu.deeplinktest;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxTestActivity extends AppCompatActivity {

    public final String TAG = RxTestActivity.this.getClass().getName() + "====>HanYu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_test);
//        createOption();
//        MapOption();
//        zipOption();
//        concatOption();
//        flatMapOption();
//        concatMapOption();
//        distinctOption();
//        FilterOption();
//        bufferOption();
//        timerOption();
//        intervalOption();
//        doOnNextOption();
//        skipOption();
//        takeOption();
//        justOption();
//        singleOption();
//        debounceOption();
//        deferOption();
//        lastOption();
//        mergeOption();
//        reduceOption();
//        scanOption();
        backpressure();

    }

    /**
     * 背压是指在异步场景中，被观察者发送事件速度远快于观察者的处理速度的情况下，一种告诉上游的被观察者降低发送速度的策略。
     */
    private void backpressure() {
        Flowable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出
     */
    private void scanOption() {
        Observable.just(1, 2, 3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "The result is ====>" + integer);
            }
        });
    }

    /**
     * reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。
     */
    private void reduceOption() {
        Observable.just(1, 3, 2, 5).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(s -> {
            Log.d(TAG, "The result is ====>" + s);
        });
    }


    /**
     * merge
     * merge 顾名思义，熟悉版本控制工具的你一定不会不知道 merge 命令，而在 Rx 操作符中，merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。
     * 注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
     */
    private void mergeOption() {
        Observable.merge(Observable.just(1, 2, 3), Observable.just(4, 5))
                .subscribe(s -> {
                    Log.d(TAG, "The result is ====>" + s);
                });
    }


    /**
     * last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
     */
    private void lastOption() {
//        DataBean bean = new DataBean("wanghanyu", "山东省济南市", 26);
//
//        Observable
//                .create((ObservableOnSubscribe<DataBean>) emitter -> {
//                    emitter.onNext(new DataBean("wanghanyu", "山东省济南", 26));
//                    emitter.onNext(new DataBean("wanghanyu", "山东省济南市槐荫区", 26));
//                    emitter.onNext(new DataBean("wanghanyu", "山东省济南市", 26));
//                })
//                .last(bean)
//                .subscribe(dataBean -> Log.d(TAG, "name =" + dataBean.getName() + "\n address = " + dataBean.getAddress() + "\n age = " + dataBean.getAge()));

        Observable.empty()
                .last(1)
                .subscribe(integer -> Log.d(TAG, "The result is =====>" + integer));

    }

    /**
     * 简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable。
     */
    @SuppressLint("CheckResult")
    private void deferOption() {
        Observable.defer((Callable<ObservableSource<Integer>>) () -> Observable.just(1, 2, 3, 4, 5)).subscribe(integer -> Log.d(TAG, "The result ===>" + integer));
    }

    /**
     * debounce
     * 去除发送频率过快的项，看起来好像没啥用处，但你信我，后面绝对有地方很有用武之地。
     */
    private void debounceOption() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(400);

                emitter.onNext(2);
                Thread.sleep(450);

                emitter.onNext(3);
                Thread.sleep(600);

                emitter.onNext(4);
                Thread.sleep(800);

                emitter.onNext(5);
            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "The result =====>" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
     */
    private void singleOption() {
        Single.just(1)
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "The result is=====>" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * just，没什么好说的，其实在前面各种例子都说明了，就是一个简单的发射器依次调用 onNext() 方法。
     */
    private void justOption() {
        Observable.just(1, 23, 4, 5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "The result is " + integer);
                    }
                });
    }

    /**
     * take，接受一个 long 型参数 count ，代表至多接收 count 个数据。
     */
    private void takeOption() {
        Observable.just(1, 2, 3, 4, 5)
                .take(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "The result ====>" + integer);
                    }
                });
    }

    /**
     * skip 很有意思，其实作用就和字面意思一样，接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。
     */
    private void skipOption() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "The result is ===>" + integer);
                    }
                });
    }

    /**
     * 其实觉得 doOnNext 应该不算一个操作符，但考虑到其常用性，我们还是咬咬牙将它放在了这里。
     * 它的作用是让订阅者在接收到数据之前干点有意思的事情。假如我们在获取到数据之前想先保存一下它，无疑我们可以这样实现。
     */
    private void doOnNextOption() {
        Observable.just(1, 2, 3, 4)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "The result is = " + integer);
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "The result is ====" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * interval 操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     * 采用 interval 操作符实现心跳间隔任务
     */
    private void intervalOption() {
        Log.d(TAG, "当前的时间是");
        Observable.interval(3, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "The result  === " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * timer 很有意思，相当于一个定时任务。在 1.x 中它还可以执行间隔逻辑，
     * 但在 2.x 中此功能被交给了 interval，下一个会介绍。但需要注意的是，timer 和 interval 均默认在新线程。
     */
    private void timerOption() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Toast.makeText(RxTestActivity.this, "包钟时间到了哦  亲", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * buffer
     * buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，
     * 然后生成一个  Observable 。也许你还不太理解，我们可以通过我们的示例图和示例代码来进一步深化它。
     */
    private void bufferOption() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .buffer(3, 2)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG, "buffer size ===>" + integers.size());
                        for (Integer integer : integers) {
                            Log.d(TAG, integer + "");
                        }

                        Log.d(TAG, "======================");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * Filter
     * 过滤器嘛。可以接受一个参数，让其过滤掉不符合我们条件的值
     */
    private void FilterOption() {
        Observable.just(1, 2, 3, 7, 8, 9, 10)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 5;
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "The result is ===>" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    /**
     * distinct
     * 这个操作符非常的简单、通俗、易懂，就是简单的去重嘛
     */
    private void distinctOption() {
        Observable.just(1, 1, 1, 1, 3, 2, 2, 4, 6, 8)
                .distinct()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "The result is ====>" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * concatMap
     * oncatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序
     */
    private void concatMapOption() {

    }

    /**
     * FlatMap
     * FlatMap 是一个很有趣的东西，我坚信你在实际开发中会经常用到。它可以把一个发射器 Observable 通过某种方法转换为多个 Observables，
     * 然后再把这些分散的 Observables装进一个单一的发射器 Observable。
     * 但有个需要注意的是，flatMap 并不能保证事件的顺序，如果需要保证，需要用到我们下面要讲的 ConcatMap。
     * <p>
     * flatMap 实现多个网络请求依次依赖
     */
    private void flatMapOption() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {

                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("The result is " + integer);
                    }
                });
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "HAN_YU === Result" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * Concat
     * 对于单一的把两个发射器连接成一个发射器，虽然 zip 不能完成，
     * 但我们还是可以自力更生，官方提供的 concat 让我们的问题得到了完美解决
     * concat 可以做到不交错的发射两个甚至多个 Observable 的发射事件，并且只有前一个 Observable 终止(onComplete) 后才会订阅下一个 Observable
     * <p>
     * 采用 concat 操作符先读取缓存再通过网络请求获取数据
     */
    private void concatOption() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "The result ===>" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * zip 专用于合并事件，该合并不是连接（连接操作符后面会说），
     * 而是两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
     * <p>
     * 善用 zip 操作符，实现多个接口数据共同更新 UI
     */
    private void zipOption() {
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
            }
        });

        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("====>1");
                emitter.onNext("====>2");
                emitter.onNext("====>3");
            }
        });

        Observable.zip(integerObservable, stringObservable, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s + "====>The Result";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * Map 基本算是 RxJava 中一个最简单的操作符了，熟悉 RxJava 1.x 的知道，
     * 它的作用是对发射时间发送的每一个事件应用一个函数，是的每一个事件都按照指定的函数去变化，而在 2.x 中它的作用几乎一致。
     */
    private void MapOption() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "Han Yu ===>" + integer + "<===Han Yu ";
            }
        }).subscribe(new Observer<String>() {
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: The Result =" + s);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * Create
     * <p>
     * create 操作符应该是最常见的操作符了，主要用于产生一个 Obserable 被观察者对象，为了方便大家的认知，
     * 以后的教程中统一把被观察者 Observable 称为发射器（上游事件），观察者 Observer 称为接收器（下游事件）。
     */
    private void createOption() {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "Observable emitter 1");
                emitter.onNext("1");

                Log.d(TAG, "Observable emitter 2");
                emitter.onNext("2");

                Log.d(TAG, "Observable emitter 3");
                emitter.onNext("3");

                emitter.onComplete();
                Log.d(TAG, "Observable emitter 4");
                emitter.onNext("4");
            }
        }).subscribe(new Observer<String>() {

            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n");
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext : value ====>" + s);

                if ("3".equals(s)) {
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError : e.getMessage ===>" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }
}
