package com.hcmut.social.controller.dbcontroller;

import com.hcmut.social.controller.ControllerCenter;
import com.hcmut.social.controller.DataController;
import com.hcmut.social.controller.controllerdata.RequestData;

import java.io.IOException;

/**
 * Created by John on 10/6/2016.
 */

public class DBController implements DataController {
    @Override
    public void doRequest(RequestData requestData) throws IOException {

    }

    @Override
    public void registerEvent(ControllerCenter controllerCenter) {

    }

    @Override
    public boolean isFromCache() {
        return false;
    }
}
