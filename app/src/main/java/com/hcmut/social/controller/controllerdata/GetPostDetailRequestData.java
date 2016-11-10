package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/19/2016.
 */

public class GetPostDetailRequestData extends RequestData {

    private static final String KEY_POST_ID = "post_id";

    public GetPostDetailRequestData(int postId) {
        super(TYPE_GET_POST_DETAIL);

        addParam(KEY_POST_ID, postId);
    }

    public int getPostId() {
        return (int) getValue(KEY_POST_ID);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return null;
    }
}
