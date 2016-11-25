package com.hcmut.social.activity;

import android.app.Dialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCallback;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.utils.DialogUtil;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends AppCompatActivity implements DataCallback {

    private int[] mEventHandleList;
    public InputMethodManager mInputMethodManager;
    private Dialog progressDialog;
    private ConnectivityManager mConMgr;
    boolean isDestroy=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mConMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        addEventHandle();
    }

    private void addEventHandle() {
        mEventHandleList = getListEventHandle();

        DataCenter dc = DataCenter.getInstance();
        if(mEventHandleList != null) {
            for (Integer type : mEventHandleList)
                dc.addCallback(type, this);
        }
        else {
//            dc.addCallback(RequestData.TYPE_POST_DEVICE_TOKEN, this);
        }

    }

    @Override
    protected void onDestroy() {
        if(EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

        isDestroy=true;
        if(mEventHandleList != null) {
            DataCenter dc = DataCenter.getInstance();

            for (Integer type : mEventHandleList)
                dc.removeCallback(type, this);
        }
        hideProgressDialog();
        super.onDestroy();
    }

    protected abstract int[] getListEventHandle();

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {

    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {

    }

    public synchronized void showProgressDialog(){
        if (progressDialog == null) {
            progressDialog = DialogUtil.createProgressDialog(this);
        }
        if(isDestroy==false)  progressDialog.show();
    }

    public void hideProgressDialog(){
        if(progressDialog!=null){
            progressDialog.hide();
            progressDialog=null;
        }
    }

    public void showErrorMessage(String error){
        DialogUtil.showToastMessage(this, error);
    }

    /**
     * Thong Nguyen
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(),
                    0);
        }
    }

    public boolean hasConnection() {
        NetworkInfo i = mConMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }
}
