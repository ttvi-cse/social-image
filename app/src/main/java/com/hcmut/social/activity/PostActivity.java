package com.hcmut.social.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmut.social.LoginManager;
import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.CreatePostRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.utils.FileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by John on 10/18/2016.
 */

public class PostActivity extends BaseActivity{

    public static final String EXTRA_IMAGE_PATH = "e_path";

    ImageButton mBackButton;
    ImageView mContentImageView;
    EditText mContentEditText;
    TextView mLocationTextView;
    TextView mPostTextView;

    private String imagePath;
    private DisplayImageOptions mOpts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPostTextView = (TextView) findViewById(R.id.tv_post);
        mPostTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mContentEditText.getText().toString();
                int userId = LoginManager.getInstance().getLoginModel().id;
//                if (!TextUtils.isEmpty(content)) {
//                    Dialog
//                };

                showProgressDialog();
                CreatePostRequestData requestData = new CreatePostRequestData(String.valueOf(userId), imagePath, content);
                DataCenter.getInstance().doRequest(requestData);
            }
        });

        mContentImageView = (ImageView) findViewById(R.id.content_imageview);
        mContentEditText = (EditText) findViewById(R.id.content_edit_text);
        mLocationTextView = (TextView) findViewById(R.id.location_text);

        mOpts = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(6))
                .showImageOnLoading(R.drawable.img_no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        if (getIntent().getStringExtra(EXTRA_IMAGE_PATH) != null) {
            Uri uri = Uri.parse(getIntent().getStringExtra(EXTRA_IMAGE_PATH));
            imagePath = FileUtils.getPath(PostActivity.this, uri);
            ImageLoader.getInstance().displayImage(uri.toString(), mContentImageView, mOpts);
        }
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {RequestData.TYPE_CREATE_POST};
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {
        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        hideProgressDialog();

        if (requestData.getType() == RequestData.TYPE_CREATE_POST) {
            finish();
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        hideProgressDialog();
    }
}
