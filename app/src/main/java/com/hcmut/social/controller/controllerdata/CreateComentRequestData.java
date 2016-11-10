package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/19/2016.
 */
public class CreateComentRequestData extends RequestData {
    private static final String KEY_POST_ID = "post_id";
    private static final String KEY_CONTENT = "content";


    public CreateComentRequestData(int postId, String content) {
        super(TYPE_CREATE_COMMENT);

        addParam(KEY_POST_ID, postId);
        addParam(KEY_CONTENT, content);

    }

    public int getPostId() {
        return (int) getValue(KEY_POST_ID);
    }

    public String getContent() {
        return (String) getValue(KEY_CONTENT);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return "{\"" + KEY_CONTENT + "\":\"" + getValue(KEY_CONTENT) + "\"}";
    }
}
