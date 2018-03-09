package com.check.gf.gfapplication.entity;


import android.os.Parcel;
import android.os.Parcelable;

import net.nashlegend.anypref.AnyPref;
import net.nashlegend.anypref.annotations.PrefModel;

/**
 * 用户
 */
@PrefModel("User")
public class User implements Parcelable {
    // 账户 持久化
    public String username;
    // 登录密码 持久化
    public String password;
    // Token 持久化
    public String sessionToken;

    public static User getCurrentUser() {
        User user = AnyPref.get(User.class, "_CurrentUser", true);
        if (user != null) {
            return user;
        }
        return null;
    }

    /**
     * 退出登录,清空缓存数据
     */
    public static void logout() {
        //清除本地账户记录
        AnyPref.clear(User.class, "_CurrentUser");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionToken() {
        return sessionToken;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.sessionToken);
        dest.writeString(this.password);
    }

    public User() {

    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.sessionToken = in.readString();
        this.password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
