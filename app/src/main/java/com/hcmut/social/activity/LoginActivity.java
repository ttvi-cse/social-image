package com.hcmut.social.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hcmut.social.LoginManager;
import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.LoginRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.utils.DialogUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by John on 10/6/2016.
 */
public class LoginActivity extends BaseActivity {

    private EditText mUsernameEditText, mPasswordEditText;
    private TextView mForgotPasswordTextView;
    private TextView mSignupTextView;
    private Button mLoginButton;

    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_login_layout);

        mProgressDialog = DialogUtil.createProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);

        mUsernameEditText = (EditText) findViewById(R.id.username_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);

        final String user = LoginManager.getInstance().getUserNameLogin();
        final String pass = LoginManager.getInstance().getPasswordLogin();
        if(!TextUtils.isEmpty(user)){
            mUsernameEditText.setText(user);
        }
        if(!TextUtils.isEmpty(pass)){
            mPasswordEditText.setText(pass);
        }

        mForgotPasswordTextView = (TextView) findViewById(R.id.forgot_password_text);
        mSignupTextView = (TextView) findViewById(R.id.signup_text);
        mLoginButton = (Button) findViewById(R.id.login_button);

        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TrackingUtil.forgetPassword(LoginActivity.this);

                Intent it = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(it);
            }
        });

        mSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = mUsernameEditText.getText()
                        .toString();
                String password = mPasswordEditText.getText()
                        .toString();

                if (userName.length() == 0) {
                    DialogUtil.showToastMessage(LoginActivity.this,
                            R.string.error_require_username);
                    mUsernameEditText.requestFocus();
                } else if (password.length() == 0) {
                    DialogUtil.showToastMessage(LoginActivity.this,
                            R.string.error_require_password);
                    mPasswordEditText.requestFocus();
                } else if (userName.length() > 0 && password.length() > 0) {
                    if (!hasConnection()) {
                        if (LoginManager.getInstance()
                                .getLoginModel() != null
                                && userName.equals(LoginManager.getInstance()
                                .getUserNameLogin())
                                && password.equals(LoginManager.getInstance()
                                .getPasswordLogin())) {
                            openMainActivity(false);
                            return;
                        } else {
                            DialogUtil.showToastMessage(LoginActivity.this, R.string.error_login_failed);
//                            DialogUtil.showAlertDialog(LoginActivity.this,
//                                                       getString(R.string.error_no_internet));
                            return;
                        }
                    }
                    mProgressDialog.show();
                    LoginRequestData requestData = new LoginRequestData(userName,
                            password);
                    DataCenter.getInstance()
                            .doRequest(requestData);
                }
            }
        });

        if(LoginManager.getInstance().isLogin()) {
            openMainActivity(true);
        }

    }

    private void openMainActivity(boolean b) {
        Intent it = new Intent(this, MainActivity.class);
        Bundle bundleNotification = getIntent().getExtras();
        startActivity( it );
        finish();
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {RequestData.TYPE_LOGIN};
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {

        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        mProgressDialog.dismiss();

        if (requestData.getType() == RequestData.TYPE_LOGIN) {
//            LoginManager.getInstance().setUserInfo();
            openMainActivity(false);
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        mProgressDialog.dismiss();
    }
}
