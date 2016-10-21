package com.hcmut.social.activity;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResetPasswordRequest;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.utils.DialogUtil;

public class ResetPasswordActivity extends BaseActivity {

    EditText mEmailEditText;
    Button mSubmitButton;

    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reset_password);
        setContentView(R.layout.activity_forget_password_v1);

        mProgressDialog = DialogUtil.createProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);

        mEmailEditText = (EditText) findViewById(R.id.email_edittext);

        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEditText.getText().toString();

                if (email.length() == 0) {
                    DialogUtil.showToastMessage(ResetPasswordActivity.this,
                            getString(R.string.error_require_email));
                } else if (email.length() > 0) {
                    mProgressDialog.show();;
                    ResetPasswordRequest requestData = new ResetPasswordRequest(email);
                    DataCenter.getInstance()
                            .doRequest(requestData);
                }
            }
        });
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {RequestData.TYPE_RESET_PASSWORD};
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {
        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        if (requestData.getType() == RequestData.TYPE_RESET_PASSWORD) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {}

            finish();
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        if (requestData.getType() == RequestData.TYPE_RESET_PASSWORD) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {}

            finish();
        }
    }
}
