package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/7/2016.
 */

public class ResetPasswordRequest extends RequestData {

    public static final String KEY_EMAIL = "email";

    public ResetPasswordRequest(String email) {
        super(TYPE_RESET_PASSWORD);

        addParam(KEY_EMAIL, email);
    }

    public String getEmail() {
        return (String) getValue(KEY_EMAIL);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return null;
    }
}
