package com.hcmut.social;

import android.app.Application;
import android.content.Intent;

import com.crittercism.app.Crittercism;
import com.facebook.stetho.Stetho;
import com.hcmut.social.activity.LoginActivity;
import com.hcmut.social.controller.ControllerCenter;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.database.DatabaseManager;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.utils.CustomSharedPreferences;
import com.hcmut.social.utils.ErrorUtil;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import de.greenrobot.event.EventBus;

/**
 * Created by John on 10/6/2016.
 */

public class SocialApplication extends Application implements DataCenter.ErrorCallback {

    public static int mSelectedClassId = -1;



    @Override
    public void onCreate() {
        super.onCreate();

        Crittercism.initialize(getApplicationContext(), "54fed8d9e0697fa4496374dd");

        DatabaseManager.getInstance().init(this);
        DataCenter.getInstance().init(this);
        ControllerCenter.getInstance().init(this);
        LoginManager.getInstance().init(this);
        DataCenter.getInstance().addHandleError(ResponseData.ERROR_UNAUTHORIZED, this);
        DataCenter.getInstance().addHandleError(ResponseData.ERROR_DATA_NOT_FOUND, this);
        DataCenter.getInstance().addHandleError(ResponseData.ERROR_UNSUPPORTED_MEDIA_TYPE, this);
        DataCenter.getInstance().addHandleError(ResponseData.ERROR_DATA_INVALID, this);
        DataCenter.getInstance().addHandleError(ResponseData.ERROR_SERVER_ERROR, this);
        DataCenter.getInstance().addHandleError(ResponseData.ERROR_CONTROLLER, this);
        DataCenter.getInstance().addHandleError(ResponseData.ERROR_NOT_CONNECTION, this);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheExtraOptions(800, 1280)
                .diskCacheExtraOptions(800, 1280, null)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .memoryCacheSize(5 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(200)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);

        CustomSharedPreferences.init(this);

        /** Setup debug with Stetho facebook, chrome://inspect/#devices*/
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public void onError(RequestData requestData, ResponseData responseData) {
        if(responseData.getReturnCode() == ResponseData.ERROR_UNAUTHORIZED) {
//            LoginManager.getInstance().clearLoginInfo();
            if (null == responseData.getErrorMessage() ||
                    ! responseData.getErrorMessage().contains("Your account was expired")) {
                ErrorUtil.showErrorMessage(this,
                        requestData,
                        responseData);
            }
        } else {
            if( !(ResponseData.ERROR_DATA_NOT_FOUND == responseData.getReturnCode()) )
                ErrorUtil.showErrorMessage(this, requestData, responseData);
        }

        EventBus.getDefault().post(responseData.getErrorMessage() + "");
    }

    @Override
    public boolean isAcceptRunAfterError(RequestData requestData, ResponseData responseData) {
        if(responseData.getReturnCode() == ResponseData.ERROR_UNAUTHORIZED)
            return false;
        else
            return true;
    }

    public void logout() {
        LoginManager.getInstance().clearLoginInfo();
//        Common.deleteFileFromInternal(this,
//                                      Constant.CACHE_CLASS_FILE_NAME);
        startLoginActivity();
    }

    private void startLoginActivity() {
        Intent it = new Intent(this, LoginActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
    }
}
