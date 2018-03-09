package com.check.gf.gfapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.check.gf.gfapplication.BaseActivity;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.entity.PostData;
import com.check.gf.gfapplication.entity.TeamGroupResult;
import com.check.gf.gfapplication.entity.User;
import com.check.gf.gfapplication.network.RxFactory;
import com.check.gf.gfapplication.utils.CommonUtils;
import com.check.gf.gfapplication.utils.ExtendUtils;
import com.orhanobut.logger.Logger;

import net.nashlegend.anypref.AnyPref;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via username/password.
 *
 * @author nEdAy
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private String packStation[] = {"工位一期", "工位二期", "工位三期", "工位四期"};
    private String packGroup[] = {"班组A", "班组B", "班组C", "班组D"};

    private ArrayList<TeamGroupResult.PostData> postDatas = new ArrayList<>();
    private ArrayList<String> groupDatas = new ArrayList<>();

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private TextView tv_pack_station_text;
    private TextView tv_pack_group_text;

    private OptionsPickerView optionsPickerView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        // Set up the login form.
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(view -> attemptLogin());

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        tv_pack_station_text = findViewById(R.id.tv_pack_station_text);
        tv_pack_group_text = findViewById(R.id.tv_pack_group_text);
        ExtendUtils.setOnClickListener(this, tv_pack_station_text, tv_pack_group_text);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_pack_station_text || viewId == R.id.tv_pack_group_text) {
            if (optionsPickerView != null) {
                optionsPickerView.show(); //弹出条件选择器
            }
        }
    }

    @Override
    protected void initData() {
        // 等数据加载完毕再初始化并显示Picker,以免还未加载完数据就显示,造成APP崩溃。
        toSubscribe(RxFactory.getUserServiceInstance()
                        .postQuery(),
                () -> showProgress(true),
                teamGroupResult -> {
                    if (teamGroupResult.getResult() == 0) {
                        showProgress(false);
                        List<TeamGroupResult.PostData> postDatas = teamGroupResult.getData();
                        initOptionData(postDatas);
                    } else {
                        postQueryTeamGroupError(teamGroupResult.getDesc());
                    }
                },
                throwable -> postQueryTeamGroupError(throwable.getMessage()));
    }

    /**
     * 添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口
     *
     * @param postDatas 实体数据
     */
    private void initOptionData(List<TeamGroupResult.PostData> postDatas) {
        // 选项1
        this.postDatas.addAll(postDatas);
        // 选项2
        for (int i = 0; i < postDatas.size(); i++) {
            List<TeamGroupResult.PostData.GroupData> groupDatas = postDatas.get(i).getGroups();
            for (int j = 0; j < groupDatas.size(); j++) {
                groupDatas.get(j).getGroupCode();
            }
        }
        PostData postData = new PostData();

        //this.groupDatas.add();
        initOptionPicker();
        /*--------数据源添加完毕---------*/
    }


    private void initOptionPicker() {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        optionsPickerView = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
//            //返回的分别是三个级别的选中位置
//            String tx = postDatas.get(options1).getPickerViewText()
//                    + postDatas.get(options1).getPostName()
//                    + groupDatas.get(options1).get;

            //btn_Options.setText(tx);

        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("省", "市", "区")
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .build();

        optionsPickerView.setPicker(postDatas, groupDatas);//二级选择器
    }

    private void postQueryTeamGroupError(String errorMessage) {
        showProgress(false);
        Logger.e(errorMessage);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        // Check for a valid username and password.
        // if error, don't attempt login and focus the first form field with an error.
        if (TextUtils.isEmpty(username)) {
            CommonUtils.showToast(getString(R.string.error_empty_username));
            mUsernameView.setError(getString(R.string.error_empty_username));
            mUsernameView.findFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            CommonUtils.showToast(getString(R.string.error_empty_password));
            mPasswordView.setError(getString(R.string.error_empty_password));
            mPasswordView.findFocus();
            return;
        }
        if (!isUsernameValid(username)) {
            CommonUtils.showToast(getString(R.string.error_invalid_username));
            mUsernameView.setError(getString(R.string.error_invalid_username));
            mUsernameView.findFocus();
            return;
        }
        if (!isPasswordValid(password)) {
            CommonUtils.showToast(getString(R.string.error_invalid_password));
            mPasswordView.setError(getString(R.string.error_invalid_password));
            mPasswordView.findFocus();
            return;
        }
        // 隐藏软键盘
        View peekDecorView = getWindow().peekDecorView();
        if (peekDecorView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
            }
        }
        if (!CommonUtils.isNetworkAvailable()) {
            CommonUtils.showToast(R.string.network_tips);
            return;
        }
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        userLogin(username, password);
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     *
     * @param username 用户名
     * @param password 密码
     */
    private void userLogin(String username, String password) {
        User user = new User(username, password);
        toSubscribe(RxFactory.getUserServiceInstance()
                        .login(user),
                () -> showProgress(true),
                resultObject -> {
                    if (resultObject.getResult() == 0) {
                        showProgress(false);
                        AnyPref.put(user, "_CurrentUser");// 将私有token保存
                        startActivity(new Intent(LoginActivity.this, SearchActivity.class));
                    } else {
                        userLoginError(resultObject.getDesc());
                    }
                },
                throwable -> userLoginError(throwable.getMessage()));
    }

    private void userLoginError(String errorMessage) {
        showProgress(false);
        CommonUtils.showToast("你输入的密码和账户名不匹配，请重新输入后重试");
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
        Logger.e(errorMessage);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}

