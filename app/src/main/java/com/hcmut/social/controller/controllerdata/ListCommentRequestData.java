package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/19/2016.
 */

public class ListCommentRequestData extends RequestData {

    private static final String KEY_POST_ID = "post_id";

    public ListCommentRequestData(int postId) {
        super(TYPE_LIST_COMMENTS);

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
