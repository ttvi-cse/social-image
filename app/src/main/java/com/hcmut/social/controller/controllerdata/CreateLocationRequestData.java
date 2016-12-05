package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 11/26/2016.
 */
public class CreateLocationRequestData extends RequestData {

    public static final String KEY_PLACE_ID = "place_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";


    public CreateLocationRequestData(String place_id, String name, String address, String phone) {
        super(TYPE_CREATE_LOCATION);

        addParam(KEY_PLACE_ID, place_id);
        addParam(KEY_NAME, name);
        addParam(KEY_ADDRESS, address);
        addParam(KEY_PHONE, phone);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return "{\"" + KEY_PLACE_ID + "\":\"" + getValue(KEY_PLACE_ID) + "\"" +
                ", \"" + KEY_NAME + "\":\"" + getValue(KEY_NAME) + "\"" +
                ", \"" + KEY_ADDRESS + "\":\"" + getValue(KEY_ADDRESS) + "\"" +
                ", \"" + KEY_PHONE + "\":\"" + getValue(KEY_PHONE) + "\"}";
    }
}
