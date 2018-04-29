package com.check.gf.gfapplication.helper;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * APP个性化配置管理工具
 *
 * @author nEdAy
 */
public final class SharedPreferencesHelper {
    private static final String PREFERENCE_SETTINGS = "_Settings";
    private static final String USERNAME = "username";
    private static final String REALNAME = "realname";
    private static final String USER_POST_CODE = "user_post_code";
    private static final String USER_GROUP_CODE = "user_group_code";
    private static SharedPreferences.Editor editor;
    private final SharedPreferences mSharedPreferences;

    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文
     */
    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_SETTINGS,
                Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

    /**
     * 上一次登录用户的用户名
     */
    public String getUsername() {
        return mSharedPreferences.getString(USERNAME, "");
    }

    public void setUsername(String userPhone) {
        editor.putString(USERNAME, userPhone);
        editor.commit();
    }

    /**
     * 用户真名
     */
    public String getRealname() {
        return mSharedPreferences.getString(REALNAME, "");
    }

    public void setRealname(String realname) {
        editor.putString(REALNAME, realname);
        editor.commit();
    }

    /**
     * 登录用户的工位编码
     */
    public String getUserPostCode() {
        return mSharedPreferences.getString(USER_POST_CODE, "");
    }

    public void setUserPostCode(String userPostCode) {
        editor.putString(USER_POST_CODE, userPostCode);
        editor.commit();
    }

    /**
     * 登录用户的班组编码
     */
    public String getUserGroupCode() {
        return mSharedPreferences.getString(USER_GROUP_CODE, "");
    }

    public void setUserGroupCode(String userGroupCode) {
        editor.putString(USER_GROUP_CODE, userGroupCode);
        editor.commit();
    }
}
