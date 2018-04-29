package com.check.gf.gfapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.view.HeaderLayout;
import com.mingle.widget.LoadingView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author nEdAy
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseComponent {
    protected Context mContext;
    private HeaderLayout mHeaderLayout;
    protected LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        View layoutView = LayoutInflater.from(this).inflate(bindLayout(), null);
        getIntentData();
        setContentView(layoutView);
        // 初始化控件
        initView(savedInstanceState);
    }

    /**
     * back+title
     */
    protected void initTopBarForLeft(String titleName, String leftText) {
        mHeaderLayout = findViewById(R.id.top_title_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.TITLE_LIFT_IMAGE_BUTTON);
        mHeaderLayout.setTitleAndLeftImageButton(titleName,
                R.drawable.ic_back, leftText,
                this::finish);
    }

    /**
     * back+title+右文字
     */
    protected void initTopBarForBoth(String titleName, String leftText, String rightText,
                                     HeaderLayout.onRightButtonClickListener onRightButtonClickListener) {
        mHeaderLayout = findViewById(R.id.top_title_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.TITLE_DOUBLE_IMAGE_BUTTON);
        mHeaderLayout.setTitleAndLeftImageButton(titleName,
                R.drawable.ic_back, leftText,
                this::finish);
        mHeaderLayout.setTitleAndRightTextButton(titleName, rightText,
                onRightButtonClickListener);
    }

    /**
     * back**+title+右文字
     */
    protected void initTopBarForBoth(String titleName, String leftText, HeaderLayout.onLeftButtonClickListener onLeftButtonClickListener, String rightText,
                                     HeaderLayout.onRightButtonClickListener onRightButtonClickListener) {
        mHeaderLayout = findViewById(R.id.top_title_bar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.TITLE_DOUBLE_IMAGE_BUTTON);
        mHeaderLayout.setTitleAndLeftImageButton(titleName,
                R.drawable.ic_back, leftText, onLeftButtonClickListener);
        mHeaderLayout.setTitleAndRightTextButton(titleName, rightText,
                onRightButtonClickListener);

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

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInputFromWindow() {
        View peekDecorView = getWindow().peekDecorView();
        if (peekDecorView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
            }
        }
    }


}
