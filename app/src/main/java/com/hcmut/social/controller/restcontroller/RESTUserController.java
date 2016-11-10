package com.hcmut.social.controller.restcontroller;

import com.google.gson.reflect.TypeToken;
import com.hcmut.social.controller.ControllerCenter;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.controller.controllerdata.UploadPhotoRequestData;
import com.hcmut.social.model.LoginModel;

import java.io.IOException;

/**
 * Created by John on 10/6/2016.
 */

public class RESTUserController extends RESTController {

    private static final String LOGIN_PATH = "user/login";
    private static final String LOGOUT_PATH = "user/logout";
    private static final String REGISTER_PATH = "user/register";
    private static final String UPLOAD_AVATAR_PATH = "users/%d/avatar";

    @Override
    public void doRequest(RequestData requestData) throws IOException {
        String url="";

        switch (requestData.getType()) {
            case RequestData.TYPE_LOGIN:
                url = createURL(LOGIN_PATH);
                doHTTPRequest(
                        createURLConnection(url, RESTController.METHOD_POST, "application/json"),
                        requestData,
                        new TypeToken<ResponseData<LoginModel>>(){}
                );

                break;
            case RequestData.TYPE_LOGOUT:

                break;
            case RequestData.TYPE_REGISTER:
                url = createURL(REGISTER_PATH);
                doHTTPRequest(
                        createURLConnection(url, RESTController.METHOD_POST, "application/json"),
                        requestData,
                        new TypeToken<ResponseData<Object>>(){}
                );
                break;
            case RequestData.TYPE_UPLOAD_AVATAR:
                UploadPhotoRequestData photoRequestData = (UploadPhotoRequestData) requestData;

                url = createURL(String.format(UPLOAD_AVATAR_PATH, photoRequestData.getUserId()));

                doHTTPRequestUploadPhoto(
                        createURLConnection(url, RESTController.METHOD_POST, "application/json"),
                        requestData,
                        new TypeToken<ResponseData>(){},
                        photoRequestData.getPath()
                );
                break;
            default:
                break;
        }
    }

    @Override
    public void registerEvent(ControllerCenter controllerCenter) {
        controllerCenter.addEventHandler(RequestData.TYPE_LOGIN, this);
        controllerCenter.addEventHandler(RequestData.TYPE_LOGOUT, this);
        controllerCenter.addEventHandler(RequestData.TYPE_REGISTER, this);
        controllerCenter.addEventHandler(RequestData.TYPE_UPLOAD_AVATAR, this);
//        controllerCenter.addEventHandler(RequestData.TYPE_RESET_PASSWORD, this);
    }
}
