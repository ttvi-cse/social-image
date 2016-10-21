package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/7/2016.
 */

public class ListPostRequestData extends RequestData {

    private static final String KEY_USER_ID = "user_id";

    public ListPostRequestData(String userId) {
        super(TYPE_LIST_POSTS);

        addParam(KEY_USER_ID, userId);
    }

    public String getUserId() {
        return (String) getValue(KEY_USER_ID);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return null;
    }
}
