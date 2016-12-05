package com.hcmut.social.controller.controllerdata;

import java.io.UnsupportedEncodingException;

/**
 * Created by John on 11/26/2016.
 */

public class ListLocationRequestData extends RequestData {

    public ListLocationRequestData() {
        super(TYPE_LIST_LOCATION);
    }

    @Override
    public String toJSONString() throws UnsupportedEncodingException {
        return null;
    }
}
