package com.hcmut.social.controller.controllerdata;

/**
 * Created by John on 11/11/2016.
 */

public class UploadPhotoRequestData extends RequestData {

    public static final String KEY_USER_ID = "userId";
    private static final String KEY_IMAGE_PATH = "img_path";

    public UploadPhotoRequestData(int userId, String path) {
        super(TYPE_UPLOAD_AVATAR);

        addParam(KEY_USER_ID, userId);
        addParam(KEY_IMAGE_PATH, path);
    }

    public int getUserId() {
        return (int) getValue(KEY_USER_ID);
    }

    public String getPath() {
        return (String) getValue(KEY_IMAGE_PATH);
    }

    @Override
    public String toJSONString() {
        return null;
    }
}