package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 11/26/2016.
 */
public class CreateLocationRequestData extends RequestData {

    public static final String KEY_TITLE = "title";
    public static final String KEY_LATITUDE = "lat";
    public static final String KEY_LONGTITUDE = "lng";


    public CreateLocationRequestData(String title, double lat, double lng) {
        super(TYPE_CREATE_LOCATION);

        addParam(KEY_TITLE, title);
        addParam(KEY_LATITUDE, lat);
        addParam(KEY_LONGTITUDE, lng);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return "{\"" + KEY_TITLE + "\":\"" + getValue(KEY_TITLE) + "\"" +
                ", \"" + KEY_LATITUDE + "\":\"" + getValue(KEY_LATITUDE) + "\"" +
                ", \"" + KEY_LONGTITUDE + "\":\"" + getValue(KEY_LONGTITUDE) + "\"}";
    }
}
