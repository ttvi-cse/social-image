package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/6/2016.
 */

public class LoginRequestData extends RequestData {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    public LoginRequestData(String userName, String password) {
        super(TYPE_LOGIN);

        addParam(KEY_USERNAME, userName);
        addParam(KEY_PASSWORD, password);
    }

    public String getUserName() {
        return (String) getValue(KEY_USERNAME);
    }

    public String getPassword() {
        return (String) getValue(KEY_PASSWORD);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return "{\"" + KEY_USERNAME + "\":\"" + getValue(KEY_USERNAME) + "\"" +
                ", \"" + KEY_PASSWORD + "\":\"" + getValue(KEY_PASSWORD) + "\"}";
    }
}
