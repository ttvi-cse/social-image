package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 11/26/2016.
 */

public class ListAllPostRequestData extends RequestData {
    private static final String KEY_USER_ID = "user_id";

    public ListAllPostRequestData(int userId) {
        super(TYPE_lIST_ALL_POST);

        addParam(KEY_USER_ID, userId);
    }

    public int getUserId() {
        return (int) getValue(KEY_USER_ID);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return null;
    }
}
