package com.hcmut.social.datacenter;

import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;

/**
 * Created by John on 10/6/2016.
 */

public interface DataCallback {
    void onLoadSuccessful(RequestData requestData, ResponseData responseData);
    void onLoadFail(RequestData requestData, ResponseData responseData);
}
