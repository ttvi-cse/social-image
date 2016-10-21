package com.hcmut.social;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hcmut.social.controller.controllerdata.LoginRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCallback;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.model.LoginModel;
import com.hcmut.social.model.MyProfileModel;

/**
 * Created by John on 10/6/2016.
 */

public class LoginManager implements DataCallback {

    private static final String LOGIN_PREF = "loginpref";
    private static final String LOGIN_FROM_PREF = "loginfpref";
    private static final String KEY_USER_ID = "kui";
    private static final String KEY_USERNAME = "kun";
    private static final String KEY_USER_TYPE = "kut";
    private static final String KEY_USER_TOKEN = "kt";
    private static final String KEY_TOKEN_EXPIRATION = "kte";
    private static final String KEY_LOGIN_TIME = "klt";
    private static final String KEY_PASSWORD = "kp";
    private static final String KEY_USERINFO_JSON = "kuijson";
    private static final String KEY_IS_LOGIN = "kil";

    private static final long LOGIN_AGAIN_TIME = 4*60;

    private static LoginManager mLoginManager;

    private Context mContext;
    private String mSavedUserName;
    private String mSavedPassword;
    private LoginModel mLoginModel;
    private MyProfileModel mUserInfo;
    private long mLoginTime = 0;

    private LoginManager() {

    }

    public static LoginManager getInstance() {
        if(mLoginManager == null)
            mLoginManager = new LoginManager();

        return mLoginManager;
    }

    public void init(Context context) {
        mContext = context;
        loadLoginModel();
        DataCenter.getInstance().addCallback(RequestData.TYPE_LOGIN,
                this);
        loadSavedLoginInfo();
    }

    private void saveLoginModel() {
        SharedPreferences sp = mContext.getSharedPreferences(LOGIN_PREF, 0);
        SharedPreferences.Editor e = sp.edit();
        e.putInt(KEY_USER_ID, mLoginModel.id);
        e.putString(KEY_USERNAME,mLoginModel.username);
        e.putString(KEY_PASSWORD, mLoginModel.password);
        e.putString(KEY_USER_TYPE, mLoginModel.type_id);
        e.putString(KEY_USER_TOKEN, mLoginModel.token);
        e.putLong(KEY_TOKEN_EXPIRATION,
                mLoginModel.expiration);
        e.putLong(KEY_LOGIN_TIME,
                mLoginTime);
        e.putBoolean(KEY_IS_LOGIN,
                mLoginModel.isLogin);
        e.commit();
    }

    private void saveUserInfo() {
        SharedPreferences sp = mContext.getSharedPreferences(LOGIN_PREF, 0);
        SharedPreferences.Editor e = sp.edit();
        e.putString(KEY_USERINFO_JSON,
                new Gson().toJson(mUserInfo));
        e.commit();
    }

    public static String getUserInfo(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(LOGIN_PREF, 0);
        String userInfo= sp.getString(KEY_USERINFO_JSON,
                "");
        return userInfo;
    }
    private void loadLoginModel() {
        SharedPreferences sp = mContext.getSharedPreferences(LOGIN_PREF, 0);
        mLoginTime = sp.getLong(KEY_LOGIN_TIME, 0);
        long expiration = sp.getLong(KEY_TOKEN_EXPIRATION, 0);

//        if(!isTokenValid(expiration)) {
//            SharedPreferences.Editor e = sp.edit();
//            e.clear();
//            e.commit();
//        } else {
        mLoginModel = new LoginModel();
        mLoginModel.expiration = expiration;
        mLoginModel.id = sp.getInt(KEY_USER_ID,
                -1);
        mLoginModel.username = sp.getString(KEY_USERNAME, null);
        mLoginModel.password = sp.getString(KEY_PASSWORD, null);
        mLoginModel.type_id = sp.getString(KEY_USER_TYPE, null);
        mLoginModel.token = sp.getString(KEY_USER_TOKEN,
                null);
        mLoginModel.isLogin = sp.getBoolean(KEY_IS_LOGIN, false);

        try {
            mUserInfo = new Gson().fromJson(sp.getString(KEY_USERINFO_JSON, null), MyProfileModel.class);
        } catch (JsonSyntaxException ex) {
            SharedPreferences.Editor e = sp.edit();
            e.clear();
            e.commit();

            mLoginModel = null;
        }
//        }
    }

    private void clearLoginModel() {
        SharedPreferences sp = mContext.getSharedPreferences(LOGIN_PREF, 0);
        SharedPreferences.Editor e = sp.edit();
        e.clear();
        e.commit();
    }

    public String getToken() {
        try {
            return mLoginModel.token;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public String getUserNameLogin() {
        try {
            return mLoginModel.username;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public String getPasswordLogin() {
        try {
            return mLoginModel.password;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {
        try {
            mLoginModel = (LoginModel) responseData.getData();
            mLoginTime = System.currentTimeMillis() / 1000;

            LoginRequestData lrequestData = (LoginRequestData) requestData;
            mLoginModel.password = lrequestData.getPassword();
            mLoginModel.isLogin = true;
            saveLoginFormInfo(lrequestData.getUserName(),
                    lrequestData.getPassword());

            saveLoginModel();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        try {
            Toast.makeText(mContext, "LoginManager.OnLoadFail", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearLoginInfo() {
        try {
//            mLoginTime = 0;
//            mLoginModel = null;
//            clearLoginModel();
            getLoginModel().isLogin = false;
            saveLoginModel();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void loadSavedLoginInfo() {
        SharedPreferences sp = mContext.getSharedPreferences(LOGIN_FROM_PREF, 0);

        mSavedUserName = sp.getString(KEY_USERNAME, "");
        mSavedPassword = sp.getString(KEY_PASSWORD, "");

    }

    public void saveLoginFormInfo(String userName, String password) {
        mSavedUserName = userName;
        mSavedPassword = password;

        SharedPreferences sp = mContext.getSharedPreferences(LOGIN_FROM_PREF, 0);
        SharedPreferences.Editor e = sp.edit();
        e.putString(KEY_USERNAME, userName);
        e.putString(KEY_PASSWORD, password);
        e.commit();
    }

    public String getSavedUserName() {
        return mSavedUserName;
    }

    public String getSavedPassword() {
        return mSavedPassword;
    }

    public boolean isLogin() {
//        return mLoginModel != null && isTokenValid(mLoginModel.expiration);
        return mLoginModel != null && mLoginModel.isLogin;
    }

    private boolean isTokenValid(long tokenExpiration) {
        long currentTime = System.currentTimeMillis() / 1000;

        if(mLoginTime > currentTime ||
                tokenExpiration - currentTime + mLoginTime < LOGIN_AGAIN_TIME)
            return false;
        else
            return true;
    }

    public LoginModel getLoginModel() {
        return mLoginModel;
    }

    public String getUserId() {
        return String.valueOf(mLoginModel.id);
    }

    public MyProfileModel getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(MyProfileModel contactModel) {
        mUserInfo = contactModel;

        saveUserInfo();
    }

    public void loginAsGuest(Context ctx) {
        mUserInfo = new MyProfileModel();
        mUserInfo.id = -1;
        mUserInfo.last_name = ctx.getString(R.string.guest_name);
    }
}
