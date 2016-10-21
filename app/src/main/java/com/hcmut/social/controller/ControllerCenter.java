package com.hcmut.social.controller;

import com.hcmut.social.SocialApplication;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.controller.dbcontroller.DBController;
import com.hcmut.social.controller.restcontroller.RESTController;
import com.hcmut.social.datacenter.DataCenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by John on 10/6/2016.
 */

public class ControllerCenter {
    private static final  DataController[] HANDLER_LIST = {
            new DBController(),
            new RESTController()
    };

    private static ControllerCenter mInstance;
    private SocialApplication mApp;
    private HashMap<Integer, ArrayList<DataController>> mEventHandlerMap = new HashMap<>();

    private ControllerCenter() {
        mEventHandlerMap = new HashMap<>();
    }

    public static ControllerCenter getInstance() {
        if(mInstance == null)
            mInstance = new ControllerCenter();

        return  mInstance;
    }

    public void init(SocialApplication application) {
        mApp = application;

        for(DataController controller : HANDLER_LIST)
            controller.registerEvent(this);
    }

    public void addEventHandler(int type, DataController controller) {
        ArrayList<DataController> list = mEventHandlerMap.get(type);

        if(list == null) {
            list = new ArrayList<>();
            mEventHandlerMap.put(type, list);
        }

        list.add(controller);
    }

    public void handleEvent(RequestData requestData) {
        ArrayList<DataController> list = mEventHandlerMap.get(requestData.getType());

        if(list != null) {
            for(DataController controller : list)
                try {
                    controller.doRequest(requestData);
                } catch (IOException e) {
                    ResponseData data = new ResponseData();
                    data.setReturnCode(ResponseData.ERROR_CONTROLLER);
                    data.setErrorMessage(e.getMessage());
                    e.printStackTrace();

                    DataCenter.getInstance().fireResponseCallback(requestData, data, controller.isFromCache());
                }
        }
    }

    public SocialApplication getApp() {
        return mApp;
    }
}
