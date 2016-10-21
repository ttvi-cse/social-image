package com.hcmut.social.controller;

import com.hcmut.social.controller.controllerdata.RequestData;

import java.io.IOException;

/**
 * Created by John on 10/6/2016.
 */

public interface DataController {
    void doRequest(RequestData requestData) throws IOException;
    void registerEvent(ControllerCenter controllerCenter);
    boolean isFromCache();
}
