package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 10/19/2016.
 */
public class UserActionRequestData extends RequestData {

    private static final  String KEY_ACTION_ID = "action_id";
    private static final  String KEY_TARGET_ID = "target_id";
    private static final  String KEY_TARGET_TYPE_ID = "target_type_id";
    private static final  String KEY_ACTION_VALUE = "action_value";

    public UserActionRequestData(String actionId, String targetId, int value) {
        super(TYPE_USER_ACTION);

        addParam(KEY_ACTION_ID, actionId);
        addParam(KEY_TARGET_ID, targetId);
        addParam(KEY_ACTION_VALUE, value);
    }

    public String getActionId() {
        return (String) getValue(KEY_ACTION_ID);
    }

    public String getTargetId() {
        return (String) getValue(KEY_TARGET_ID);
    }

    public String getTargetTypeId() {
        return String.valueOf(1);
    }

    public int getActionValue(){
        return (int) getValue(KEY_ACTION_VALUE);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return "{\"" + KEY_ACTION_ID + "\":\"" + getActionId() + "\"" +
                ", \"" + KEY_TARGET_ID + "\":\"" + getTargetId() + "\"" +
                ", \"" + KEY_TARGET_TYPE_ID + "\":\"" + getTargetTypeId() + "\"" +
                ", \"" + KEY_ACTION_VALUE + "\":\"" + getActionValue() + "\"}";
    }
}
