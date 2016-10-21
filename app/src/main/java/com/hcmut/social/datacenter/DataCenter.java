package com.hcmut.social.datacenter;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;

import com.hcmut.social.SocialApplication;
import com.hcmut.social.controller.ControllerCenter;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by John on 10/6/2016.
 */

public class DataCenter extends Application {
    private static DataCenter mDataCenter;
    private SocialApplication mApp;
    private HashMap<Integer, ArrayList<DataCallback>> mCallbacks;
    private HashMap<Integer, ArrayList<ErrorCallback>> mErrorCallbacks;
    private HashMap<Integer, AsyncTask> mTasks;
    private Handler mHandler = new Handler();

    public interface ErrorCallback{
        void onError(RequestData requestData, final ResponseData responseData);
        boolean isAcceptRunAfterError(RequestData requestData, final ResponseData responseData);
    }

    private DataCenter(){
        mTasks = new HashMap<>();
        mCallbacks = new HashMap<>();
        mErrorCallbacks = new HashMap<>();
    }

    public static DataCenter getInstance() {
        if(mDataCenter == null)
            mDataCenter = new DataCenter();

        return mDataCenter;
    }

    public void init(SocialApplication application) {
        mApp = application;
    }

    public void addCallback(int type, DataCallback callback) {
        synchronized (mCallbacks) {
            ArrayList<DataCallback> list = mCallbacks.get(type);

            if (list == null) {
                list = new ArrayList<>();
                mCallbacks.put(type, list);
            }

            list.add(callback);
        }
    }

    public void addHandleError(int errorCode, ErrorCallback callback) {
        ArrayList<ErrorCallback> list = mErrorCallbacks.get(errorCode);

        if(list == null) {
            list = new ArrayList<>();
            mErrorCallbacks.put(errorCode, list);
        }

        list.add(callback);
    }

    public void removeCallback(int type, DataCallback callback) {
        synchronized (mCallbacks) {
            ArrayList<DataCallback> list = mCallbacks.get(type);

            if (list != null) {
                list.remove(callback);

                if (list.size() == 0)
                    stopRequest(type);
            }
        }
    }

    public void doRequest(RequestData requestData) {
        AsyncTask<RequestData, Void, Void> task = new AsyncTask<RequestData, Void, Void>() {
            @Override
            protected Void doInBackground(RequestData... params) {
                ControllerCenter.getInstance().handleEvent(params[0]);
                return null;
            }
        };

        mTasks.put(requestData.getType(), task);
        task.execute(requestData);
    }

    public void stopRequest(int type) {
        AsyncTask task = mTasks.get(type);

        if(task != null)
            task.cancel(true);
    }

    public void fireResponseCallback(final RequestData requestData, final ResponseData responseData, boolean isFromCache) {
        synchronized (mCallbacks) {
            responseData.setFromCache(isFromCache);

            if (responseData.getReturnCode() == ResponseData.RETURN_OK) {
                fireAllCallbackSuccessful(requestData, responseData);
            } else if (fireAllErrorCallback(requestData, responseData)) {
                fireAllCallbackFail(requestData, responseData);
            }
        }
    }

    private void fireAllCallbackSuccessful(final RequestData requestData, final ResponseData responseData) {

        synchronized (mCallbacks) {
            ArrayList<DataCallback> list = mCallbacks.get(requestData.getType());

            if (list != null) {
                for (final DataCallback callback : list) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onLoadSuccessful(requestData, responseData);
                        }
                    });
                }
            }
        }
    }

    private void fireAllCallbackFail(final RequestData requestData, final ResponseData responseData) {
        synchronized (mCallbacks) {
            ArrayList<DataCallback> list = mCallbacks.get(requestData.getType());

            if (list != null) {
                for (final DataCallback callback : list) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onLoadFail(requestData, responseData);
                        }
                    });
                }
            }
        }
    }

    private boolean fireAllErrorCallback(final RequestData requestData, final ResponseData responseData) {
        synchronized (mCallbacks) {
            ArrayList<ErrorCallback> list = mErrorCallbacks.get(responseData.getReturnCode());

            boolean f = true;

            if (list != null) {
                for (final ErrorCallback callback : list) {

                    f = callback.isAcceptRunAfterError(requestData, responseData);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(requestData, responseData);
                        }
                    });

                    if (!f) return f;
                }
            }

            return f;
        }
    }
}
