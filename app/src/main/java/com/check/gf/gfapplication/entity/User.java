package com.check.gf.gfapplication.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户
 */
public class User implements Parcelable {
    // 账户
    private String username;
    // 登录密码
    private String password;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected User(Parcel in) {
        this.username = in.readString();
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
