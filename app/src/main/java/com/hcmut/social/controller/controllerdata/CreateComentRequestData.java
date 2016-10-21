package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/19/2016.
 */
public class CreateComentRequestData extends RequestData {
    private static final String KEY_POST_ID = "post_id";

    public CreateComentRequestData(String postId) {
        super(TYPE_CREATE_COMMENT);

        addParam(KEY_POST_ID, postId);
    }

    public String getPostId() {
        return (String) getValue(KEY_POST_ID);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return null;
    }
}
