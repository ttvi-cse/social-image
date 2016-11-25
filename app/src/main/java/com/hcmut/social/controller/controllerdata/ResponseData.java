package com.hcmut.social.controller.controllerdata;

import com.hcmut.social.model.ExtraDataModel;

import java.io.Serializable;

/**
 * Created by John on 10/6/2016.
 */

public class ResponseData<T> implements Serializable {

    public static final int RETURN_OK = 200;
    public static final int ERROR_UNAUTHORIZED = 401;
    public static final int ERROR_DATA_NOT_FOUND = 404;
    public static final int ERROR_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int ERROR_METHOD_INVALID = 405;
    public static final int ERROR_SERVER_ERROR = 500;
    public static final int ERROR_CONTROLLER = -6;
    public static final int ERROR_NOT_CONNECTION = -7;
    public static final int ERROR_DATA_INVALID = -8;

//    Code 401: createBy unauthorized or token expired.
//    Code 404: data not found returnCode.
//    Code 405: all errors related to method syntax.
//    Code 500: other returnCode. This returnCode should be attached a returnCode message (ex: {"message": "returnCode message"}).

    private int returnCode = RETURN_OK;
    private Object errors;
    private ExtraDataModel extra_data;
    private T data;
    private boolean mIsFromCache = true;
    private String message;

    //public ResponseData(boolean isFromCache) {
    //    mIsFromCache = isFromCache;
    //}

    public int getReturnCode() {
        return returnCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isFromCache() {
        return mIsFromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.mIsFromCache = fromCache;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public void setErrorMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }

    public Object getError() {
        return errors;
    }

    public ExtraDataModel getExtraData() {
        return extra_data;
    }
}
