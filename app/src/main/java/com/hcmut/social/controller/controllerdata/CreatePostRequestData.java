package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/7/2016.
 */

public class CreatePostRequestData extends RequestData{

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_IMAGE_PATH = "img_path";
    private static final String KEY_CONTENT = "content";

    public CreatePostRequestData(String userId, String path, String content) {
        super(TYPE_CREATE_POST);

        addParam(KEY_USER_ID, userId);
        addParam(KEY_IMAGE_PATH, path);
        addParam(KEY_CONTENT, content);
    }

    public String getUserId() {
        return (String) getValue(KEY_USER_ID);
    }

    public String getImageUri() {
        return (String) getValue(KEY_IMAGE_PATH);
    }

    public String getContent() {
        return (String) getValue(KEY_CONTENT);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return "{\"" + KEY_CONTENT + "\":\"" + getValue(KEY_CONTENT) + "\"" +
                ", \"" + KEY_IMAGE_PATH + "\":\"" + getValue(KEY_IMAGE_PATH) + "\"" +
                ", \"" + KEY_USER_ID + "\":\"" + getValue(KEY_USER_ID) + "\"}";
    }
}
