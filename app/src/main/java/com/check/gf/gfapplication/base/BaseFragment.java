package com.check.gf.gfapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingle.widget.LoadingView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Fragment基类
 *
 * @author nEdAy
 */
public abstract class BaseFragment extends Fragment implements IBaseComponent {

    protected Context mContext;
    protected View parentView;
    protected LoadingView mLoadingView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        // 设置渲染视图View
        parentView = inflater.inflate(bindLayout(), container, false);
        initView(savedInstanceState);
        return parentView;
    }

    protected FragmentActivity getActivityNonNull() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("null returned from getActivity()");
        }
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

    /**
     * Show/hide the loading UI .
     */
    protected void showLoading(String loadingText) {
        if (mLoadingView != null) {
            mLoadingView.setLoadingText(loadingText);
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    protected void hideLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

}
