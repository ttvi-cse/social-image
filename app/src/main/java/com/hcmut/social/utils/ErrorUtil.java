package com.hcmut.social.utils;

import android.content.Context;
import android.text.TextUtils;

import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;

/**
 * Created by John on 10/6/2016.
 */

public class ErrorUtil {
    public static void showErrorMessage(Context ctx, RequestData requestData, ResponseData responseData) {

        String errorMessage = getErrorMessage(ctx, requestData, responseData);
        if(!TextUtils.isEmpty(errorMessage)){
            DialogUtil.showToastMessage(
                    ctx,
                    errorMessage
            );
        }
    }

    public static String getErrorMessage(Context ctx, RequestData requestData, ResponseData responseData) {

//        if(requestData.getType() == RequestData.TYPE_POST_DEVICE_TOKEN
//                || requestData.getType() == RequestData.TYPE_CHECK_VERSION){
//            return "";
//        }

        String message="";

        switch (responseData.getReturnCode()) {
            case ResponseData.ERROR_UNAUTHORIZED:
                if(requestData.getType() == RequestData.TYPE_LOGIN) {
                    if (null != responseData.getErrorMessage() && responseData.getErrorMessage()
                            .contains("Your account was expired")) {
                        message = responseData.getErrorMessage();
                    } else {
                        message = ctx.getString(R.string.error_login_failed);
                    }
                }
                else
                    message = ctx.getString(R.string.error_unauthorized);
                break;
            case ResponseData.ERROR_DATA_NOT_FOUND:
                message = ctx.getString(R.string.error_data_not_found);
                break;
            case ResponseData.ERROR_UNSUPPORTED_MEDIA_TYPE:
                message = ctx.getString(R.string.error_unsupported_media_type);
                break;
            case ResponseData.ERROR_DATA_INVALID:
                message = ctx.getString(R.string.error_data_invalid);
                break;
            case ResponseData.ERROR_SERVER_ERROR:
                message = ctx.getString(R.string.error_server_error);
                break;
            case ResponseData.ERROR_CONTROLLER:
                message = ctx.getString(R.string.error_controller, responseData.getErrorMessage());
                break;
            case ResponseData.ERROR_NOT_CONNECTION:
                message = ctx.getString(R.string.error_not_connection);
                break;

        }

        return message;
    }
}
