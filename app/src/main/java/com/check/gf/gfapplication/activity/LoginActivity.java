package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.check.gf.gfapplication.CustomApplication;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.entity.PostData;
import com.check.gf.gfapplication.helper.SharedPreferencesHelper;
import com.check.gf.gfapplication.network.RxFactory;
import com.check.gf.gfapplication.utils.CommonUtils;
import com.check.gf.gfapplication.utils.ExtendUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via username/password.
 *
 * @author nEdAy
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<PostData> postDatas = new ArrayList<>();
    private ArrayList<ArrayList<String>> groupNameDatas = new ArrayList<>();
    private ArrayList<ArrayList<String>> groupCodeDatas = new ArrayList<>();

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;

    private TextView tv_pack_station_text;
    private TextView tv_pack_group_text;

    private OptionsPickerView optionsPickerView;
    private SharedPreferencesHelper sharedPreferencesHelper;


    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initTopBarForLeft("登录", getString(R.string.tx_exit));
        sharedPreferencesHelper = CustomApplication.getInstance().getSpHelper();
        sharedPreferencesHelper.setUserPostCode("");
        sharedPreferencesHelper.setUserGroupCode("");
        sharedPreferencesHelper.setRealname("");
        sharedPreferencesHelper.clear();
        // Set up the login form.
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        String lastUsername = sharedPreferencesHelper.getUsername();
        if (!TextUtils.isEmpty(lastUsername)) {
            mUsernameView.setText(lastUsername);
            mPasswordView.setFocusable(true);
            mPasswordView.setFocusableInTouchMode(true);
            mPasswordView.requestFocus();
            mPasswordView.requestFocusFromTouch();
        }
        mPasswordView.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });
        Button mSignInButton = findViewById(R.id.btn_search);
        mSignInButton.setOnClickListener(view -> attemptLogin());

        mLoadingView = findViewById(R.id.loadView);

        tv_pack_station_text = findViewById(R.id.tv_pack_station_text);
        tv_pack_group_text = findViewById(R.id.tv_pack_group_text);

        ExtendUtils.setOnClickListener(this, tv_pack_station_text, tv_pack_group_text);

        initData();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_pack_station_text || viewId == R.id.tv_pack_group_text) {
            hideSoftInputFromWindow();
            if (optionsPickerView != null) {
                optionsPickerView.show(); //弹出条件选择器
            } else {
                showLoading("条件选择器配置异常");
            }
        }
    }

    private void initData() {
        // 等数据加载完毕再初始化并显示Picker,以免还未加载完数据就显示,造成APP崩溃。
        toSubscribe(RxFactory.getUserServiceInstance()
                        .postQuery(),
                () -> showLoading("初始化数据..."),
                teamGroupResult -> {
                    if (teamGroupResult.getResult() == 0) {
                        hideLoading();
                        List<PostData> postDatas = teamGroupResult.getData();
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
    private void initOptionData(List<PostData> postDatas) {
        // 选项1
        this.postDatas.addAll(postDatas);
        // 选项2
        for (int i = 0; i < postDatas.size(); i++) {
            List<PostData.GroupData> groupDatas = postDatas.get(i).getGroups();
            ArrayList<String> options2ItemNames = new ArrayList<>();
            ArrayList<String> options2ItemCodes = new ArrayList<>();
            for (int j = 0; j < groupDatas.size(); j++) {
                options2ItemNames.add(groupDatas.get(j).getGroupName());
                options2ItemCodes.add(groupDatas.get(j).getGroupCode());
            }
            this.groupNameDatas.add(options2ItemNames);
            this.groupCodeDatas.add(options2ItemCodes);
        }
        initOptionPicker();
        /*--------数据源添加完毕---------*/
    }


    /**
     * 条件选择器初始化
     */
    private void initOptionPicker() {
        optionsPickerView = new OptionsPickerView.Builder(this, (options1, options2, options3, v) -> {
            sharedPreferencesHelper.setUserPostCode(postDatas.get(options1).getPostCode());
            sharedPreferencesHelper.setUserGroupCode(groupCodeDatas.get(options1).get(options2));
            tv_pack_station_text.setText(postDatas.get(options1).getPostName());
            tv_pack_group_text.setText(groupNameDatas.get(options1).get(options2));
        })
                .setTitleText("工位/班组")
                .build();
        // 二级选择器
        if (postDatas.size() > 0 && groupNameDatas.size() > 0) {
            optionsPickerView.setPicker(this.postDatas, this.groupNameDatas);
        } else {
            initData(); // 数据异常，重新请求
        }
    }

    /**
     * 班组数据请求失败 （该数据必须存在）
     *
     * @param errorMessage errorMessage
     */
    private void postQueryTeamGroupError(String errorMessage) {
        // showProgress(false);
        Logger.e(errorMessage);
        initData();
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
//        if (!isUsernameValid(username)) {
//            CommonUtils.showToast(getString(R.string.error_invalid_username));
//            mUsernameView.setError(getString(R.string.error_invalid_username));
//            mUsernameView.findFocus();
//            return;
//        }
//        if (!isPasswordValid(password)) {
//            CommonUtils.showToast(getString(R.string.error_invalid_password));
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            mPasswordView.findFocus();
//            return;
//        }
        if (TextUtils.isEmpty(sharedPreferencesHelper.getUserPostCode())
                || TextUtils.isEmpty(sharedPreferencesHelper.getUserGroupCode())) {
            CommonUtils.showToast(getString(R.string.error_empty_team_group));
            return;
        }
        hideSoftInputFromWindow();
        if (!CommonUtils.isNetworkAvailable()) {
            CommonUtils.showToast(R.string.network_tips);
            return;
        }
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        userLogin(username, password);
    }

//    private boolean isUsernameValid(String username) {
//        return true;// username.length() > 4;
//    }
//
//    private boolean isPasswordValid(String password) {
//        return true;// password.length() > 4;
//    }


    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     *
     * @param username 用户名
     * @param password 密码
     */
    private void userLogin(String username, String password) {
        toSubscribe(RxFactory.getUserServiceInstance()
                        .login(username, password),
                () -> showLoading("登录中..."),
                loginResult -> {
                    if (loginResult.getResult() == 0) {
                        hideLoading();
                        sharedPreferencesHelper.setUsername(username);
                        String realname = loginResult.getData().getRealName();
                        sharedPreferencesHelper.setRealname(realname != null ? realname : "");
                        startActivity(new Intent(
                                LoginActivity.this, SearchActivity.class));
                        finish();
                    } else {
                        userLoginError(loginResult.getDesc());
                    }
                },
                throwable -> userLoginError(throwable.getMessage()));
    }

    private void userLoginError(String errorMessage) {
        hideLoading();
        CommonUtils.showToast("你输入的密码和账户名不匹配，请重新输入后重试");
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
        Logger.e(errorMessage);
    }


    // 连续按两次返回键就退出标记位
    private long firstTime;

    /**
     * 截获Back键动作
     */
    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            CommonUtils.showToast("再按一次退出程序");
        }
        firstTime = System.currentTimeMillis();
    }
}

