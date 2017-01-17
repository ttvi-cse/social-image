package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/7/2016.
 */

public class CreatePostRequestData extends RequestData{

    private static final String KEY_IMAGE_PATH = "img_path";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LOCATION_ID = "location_id";

    public CreatePostRequestData(String path, String title, String content, int locationId) {
        super(TYPE_CREATE_POST);

        addParam(KEY_IMAGE_PATH, path);
        addParam(KEY_TITLE, title);
        addParam(KEY_CONTENT, content);
        addParam(KEY_LOCATION_ID, locationId);
    }

    public String getImageUri() {
        return (String) getValue(KEY_IMAGE_PATH);
    }

    public String getTitle() {
        return (String) getValue(KEY_TITLE);
    }

    public String getContent() {
        return (String) getValue(KEY_CONTENT);
    }

    public int getLocationId() {
        if (getValue(KEY_LOCATION_ID) == null)
            return  -1;

        return (int) getValue(KEY_LOCATION_ID);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {

        return "{\"" + KEY_TITLE + "\":\"" + getValue(KEY_TITLE) + "\"" +
                ", \"" + KEY_CONTENT + "\":\"" + getValue(KEY_CONTENT) + "\"" +
                ", \"" + KEY_IMAGE_PATH + "\":\"" + getValue(KEY_IMAGE_PATH) + "\"" +
                ", \"" + KEY_LOCATION_ID + "\":\"" + getValue(KEY_LOCATION_ID) + "\"}";
    }
}
