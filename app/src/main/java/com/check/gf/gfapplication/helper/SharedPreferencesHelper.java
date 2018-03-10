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
    private static final String SETTING_FIRST = "setting_first";
    private static final String SETTING_NOTIFY = "setting_notify";
    private static final String SETTING_VOICE = "setting_voice";
    private static final String SETTING_VIBRATE = "setting_vibrate";
    private static final String SETTING_QUIET = "setting_quiet";
    private static final String SETTING_PROVINCE_FLOW_MODEL = "setting_province_flow_model ";
    private static final String USERNAME = "username";
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

    /**
     * 是否首次导航
     */
    public boolean isAllowFirst() {
        return mSharedPreferences.getBoolean(SETTING_FIRST, true);
    }

    public void setAllowFirstEnable(boolean isFirst) {
        editor.putBoolean(SETTING_FIRST, isFirst);
        editor.commit();
    }


    /**
     * 是否允许推送通知
     */
    public boolean isAllowPushNotify() {
        return mSharedPreferences.getBoolean(SETTING_NOTIFY, true);
    }

    public void setPushNotifyEnable(boolean isChecked) {
        editor.putBoolean(SETTING_NOTIFY, isChecked);
        editor.commit();
    }

    /**
     * 是否允许声音
     */
    public boolean isAllowVoice() {
        return mSharedPreferences.getBoolean(SETTING_VOICE, true);
    }

    public void setAllowVoiceEnable(boolean isChecked) {
        editor.putBoolean(SETTING_VOICE, isChecked);
        editor.commit();
    }

    /**
     * 是否允许震动
     */
    public boolean isAllowVibrate() {
        return mSharedPreferences.getBoolean(SETTING_VIBRATE, true);
    }

    public void setAllowVibrateEnable(boolean isChecked) {
        editor.putBoolean(SETTING_VIBRATE, isChecked);
        editor.commit();
    }

    /**
     * 允许靜音
     */
    public boolean isAllowQuiet() {
        return mSharedPreferences.getBoolean(SETTING_QUIET, true);
    }

    public void setAllowQuietEnable(boolean isChecked) {
        editor.putBoolean(SETTING_QUIET, isChecked);
        editor.commit();
    }

    /**
     * 是否开启省流模式
     */
    public boolean isAllowProvinceFlowModel() {
        return mSharedPreferences.getBoolean(SETTING_PROVINCE_FLOW_MODEL, false);
    }

    public void setAllowProvinceFlowModelEnable(boolean isProvinceFlowModel) {
        editor.putBoolean(SETTING_PROVINCE_FLOW_MODEL, isProvinceFlowModel);
        editor.commit();
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
