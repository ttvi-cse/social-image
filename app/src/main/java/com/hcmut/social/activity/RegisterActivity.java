package com.hcmut.social.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.LoginRequestData;
import com.hcmut.social.controller.controllerdata.RegisterRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.utils.DialogUtil;

public class RegisterActivity extends BaseActivity {

    EditText mUsernameEditText;
    EditText mEmailEditText;
    EditText mPasswordEditText;
    EditText mConfirmPasswordEditText;
    Button mRegisterButton;

    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
        setContentView(R.layout.activity_signup_v1);

        mProgressDialog = DialogUtil.createProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);

        mUsernameEditText = (EditText) findViewById(R.id.username_edittext);
        mEmailEditText = (EditText) findViewById(R.id.email_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.password_confirm_edittext);

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUsernameEditText.getText()
                        .toString();
                String email = mEmailEditText.getText()
                        .toString();
                String password = mPasswordEditText.getText()
                        .toString();
                String confirm = mConfirmPasswordEditText.getText()
                        .toString();

                if (userName.length() == 0) {
                    DialogUtil.showToastMessage(RegisterActivity.this,
                            R.string.error_require_username);
                    mUsernameEditText.requestFocus();
                } else if (email.length() == 0) {
                    DialogUtil.showToastMessage(RegisterActivity.this,
                            R.string.error_require_email);
                    mEmailEditText.requestFocus();
                }else if (password.length() == 0) {
                    DialogUtil.showToastMessage(RegisterActivity.this,
                            R.string.error_require_password);
                    mPasswordEditText.requestFocus();
                } else if (confirm.length() == 0) {
                    DialogUtil.showToastMessage(RegisterActivity.this,
                            R.string.error_require_confirm_password);
                    mConfirmPasswordEditText.requestFocus();
                } else if (userName.length() > 0 && email.length() > 0 && password.length() > 0) {
                    mProgressDialog.show();
                    RegisterRequestData requestData = new RegisterRequestData(userName, email, password, confirm);
                    DataCenter.getInstance()
                            .doRequest(requestData);
                }
            }
        });
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {RequestData.TYPE_REGISTER};
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {
        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        if (requestData.getType() == RequestData.TYPE_REGISTER) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {}

            finish();
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        if (requestData.getType() == RequestData.TYPE_REGISTER) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {}

            DialogUtil.showToastMessage(RegisterActivity.this, responseData.getErrorMessage());
        }
    }
}
