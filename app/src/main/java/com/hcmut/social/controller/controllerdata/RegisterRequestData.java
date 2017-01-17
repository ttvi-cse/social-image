package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/7/2016.
 */

public class RegisterRequestData extends RequestData{

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CONFIRM_PASSWORD = "password_confirmation";

    public RegisterRequestData(String username, String email, String password, String confirm) {
        super(TYPE_REGISTER);

        addParam(KEY_USERNAME, username);
        addParam(KEY_PASSWORD, password);
        addParam(KEY_EMAIL, email);
        addParam(KEY_CONFIRM_PASSWORD, confirm);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return "{\"" + KEY_USERNAME + "\":\"" + getValue(KEY_USERNAME) + "\"" +
                ", \"" + KEY_EMAIL + "\":\"" + getValue(KEY_EMAIL) + "\"" +
                ", \"" + KEY_PASSWORD + "\":\"" + getValue(KEY_PASSWORD) + "\"" +
                ", \"" + KEY_CONFIRM_PASSWORD + "\":\"" + getValue(KEY_CONFIRM_PASSWORD) + "\"}";
    }
}
