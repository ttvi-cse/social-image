package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by John on 10/6/2016.
 */

public abstract class RequestData {

    public static enum TYPE_LOADING{LOADING_WAITING,LOADING_MORE,LOADING_PULL_TO_REFRESH};

    public static final int TYPE_LOGIN = 1;
    public static final int TYPE_REGISTER = 2;
    public static final int TYPE_LOGOUT = 3;
    public static final int TYPE_RESET_PASSWORD = 4;

    public static final int TYPE_LIST_POSTS = 6;
    public static final int TYPE_CREATE_POST = 7;
    public static final int TYPE_GET_POST_DETAIL = 12;

    public static final int TYPE_LIST_COMMENTS = 8;
    public static final int TYPE_CREATE_COMMENT = 9;

    public static final int TYPE_USER_ACTION = 10;

    public static final int TYPE_UPLOAD_AVATAR = 13;

    protected int mType;

    private HashMap<String, Object> mEventMap = new HashMap<>();

    public RequestData(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void addParam(String key, Object value) {
        mEventMap.put(key, value);
    }

    public Object getValue(String key) {
        return mEventMap.get(key);
    }

    public boolean hasKey(String key){
        return mEventMap.containsKey(key);
    }

    public abstract String toJSONString() throws UnsupportedEncodingException;
}
