package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/7/2016.
 */

public class RegisterRequestData extends RequestData{

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public RegisterRequestData(String username, String email, String password) {
        super(TYPE_REGISTER);

        addParam(KEY_USERNAME, username);
        addParam(KEY_PASSWORD, password);
        addParam(KEY_EMAIL, email);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return "{\"" + KEY_USERNAME + "\":\"" + getValue(KEY_USERNAME) + "\"" +
                ", \"" + KEY_EMAIL + "\":\"" + getValue(KEY_EMAIL) + "\"" +
                ", \"" + KEY_PASSWORD + "\":\"" + getValue(KEY_PASSWORD) + "\"}";
    }
}
