package com.check.gf.gfapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author nEdAy
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(savedInstanceState);
        View layoutView = LayoutInflater.from(this).inflate(getContentLayout(), null);
        setContentView(layoutView);
        getIntentData();
        initHeaderView();
        initContentView();
        initFooterView();
        initData();
    }

    protected abstract int getContentLayout();


    protected void getIntentData(Bundle outState) {
    }

    protected void getIntentData() {
    }

    protected void initHeaderView() {
    }

    protected void initContentView() {
    }

    protected void initFooterView() {
    }

    protected void initData() {

    }

    /**
     * RxJava调度器 有doOnSubscribe
     *
     * @param o           观察者
     * @param onSubscribe 在subscribe()前初始化数据
     * @param onNext      the {@code Action1<T>} you have designed to accept emissions from the Observable
     * @param onError     the {@code Action1<Throwable>} you have designed to accept any error notification from the
     *                    Observable
     * @param <T>         传输实体
     */
    protected <T> void toSubscribe(Observable<T> o, Action0 onSubscribe, Action1<? super T> onNext, Action1<Throwable> onError) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    /**
     * RxJava调度器 无doOnSubscribe
     *
     * @param o       观察者
     * @param onNext  the {@code Action1<T>} you have designed to accept emissions from the Observable
     * @param onError the {@code Action1<Throwable>} you have designed to accept any error notification from the
     *                Observable
     * @param <T>     传输实体
     */
    protected <T> void toSubscribe(Observable<T> o, Action1<? super T> onNext, Action1<Throwable> onError) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }
}