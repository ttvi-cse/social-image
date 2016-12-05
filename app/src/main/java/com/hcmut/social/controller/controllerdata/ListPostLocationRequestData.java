package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 11/26/2016.
 */

public class ListPostLocationRequestData extends RequestData {
    private static final String KEY_LOCATION_ID = "location_id";

    public ListPostLocationRequestData(int locationId) {
        super(TYPE_lIST_POST_LOCATION);

        addParam(KEY_LOCATION_ID, locationId);
    }

    public int getLocationId() {
        return (int) getValue(KEY_LOCATION_ID);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return null;
    }
}
