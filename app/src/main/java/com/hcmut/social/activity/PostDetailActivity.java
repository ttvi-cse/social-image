package com.hcmut.social.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.stetho.inspector.protocol.module.Network;
import com.hcmut.social.R;
import com.hcmut.social.adapter.CommentAdapter;
import com.hcmut.social.controller.controllerdata.CreateComentRequestData;
import com.hcmut.social.controller.controllerdata.GetPostDetailRequestData;
import com.hcmut.social.controller.controllerdata.ListCommentRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.controller.controllerdata.UserActionRequestData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.model.CommentModel;
import com.hcmut.social.model.PostModel;
import com.hcmut.social.utils.DialogUtil;
import com.hcmut.social.utils.UserUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by John on 10/19/2016.
 */

public class PostDetailActivity extends BaseActivity {

    public static final String EXTRA_POST_ID = "e_postid";

    ProgressBar mProgressBar;

    ImageButton mBackButton;
    ImageView mAvatarImageView;
    TextView mUsernameTextView;
    ImageView mContentImageView;
    TextView mLikeCountTextView;
    TextView mViewCountTextView;
    RatingBar mRatingBar;

    ListView mCommentListView;

    EditText mCommentEditText;
    Button mSendButton;

//    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<CommentModel> mCommentData;
    private CommentAdapter mCommentAdapter;
    private PostModel mPostData;
    private int mPostID;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAvatarImageView = (ImageView) findViewById(R.id.avatar_imageview);
        mUsernameTextView = (TextView) findViewById(R.id.username_text);

        mContentImageView = (ImageView) findViewById(R.id.content_imageview);


        mLikeCountTextView = (TextView) findViewById(R.id.like_count_text);
        mLikeCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPostData.is_liked) {
                    DialogUtil.showToastMessage(PostDetailActivity.this, getString(R.string.error_post_liked));
                    return;
                }

                UserActionRequestData requestData = new UserActionRequestData(
                        String.valueOf(1),
                        String.valueOf(mPostData.id),
                        0
                );
                DataCenter.getInstance().doRequest(requestData);

            }
        });
        mViewCountTextView = (TextView) findViewById(R.id.view_count_text);
        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                UserActionRequestData requestData = new UserActionRequestData(
                        String.valueOf(2),
                        String.valueOf(mPostData.id),
                        (int) rating
                );
                DataCenter.getInstance().doRequest(requestData);
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mCommentListView = (ListView) findViewById(R.id.comment_listview);

        mCommentEditText = (EditText) findViewById(R.id.comment_edittext);
        mSendButton = (Button) findViewById(R.id.send_button);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mCommentEditText.getText().toString();

                if (TextUtils.isEmpty(content))
                    return;

                CreateComentRequestData requestData = new CreateComentRequestData(mPostID, content);
                DataCenter.getInstance().doRequest(requestData);

            }
        });

        mPostID = getIntent().getIntExtra(EXTRA_POST_ID, -1);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    private void loadData() {
        if (mPostID == -1) {
            return;
        }

        if (mPostData == null) {
            GetPostDetailRequestData gpRequest = new GetPostDetailRequestData(mPostID);
            DataCenter.getInstance().doRequest(gpRequest);
        }

        if (mCommentData == null) {
            ListCommentRequestData lcRequest = new ListCommentRequestData(mPostID);
            DataCenter.getInstance().doRequest(lcRequest);
        }

    }

    private void newComentAdapter() {
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(PostDetailActivity.this);
            mCommentListView.setAdapter(mCommentAdapter);
        }
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {
                RequestData.TYPE_GET_POST_DETAIL,
                RequestData.TYPE_LIST_COMMENTS,
                RequestData.TYPE_CREATE_COMMENT,
                RequestData.TYPE_USER_ACTION,
        };
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {
        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
//        mSwipeRefreshLayout.setRefreshing(false);

        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        if (requestData.getType() == RequestData.TYPE_GET_POST_DETAIL) {
            ResponseData<PostModel> resData = responseData;

            PostModel model = resData.getData();

            mPostData = model;

            /**
             * avatar, name
             */
            ImageLoader.getInstance().displayImage(UserUtil.getAvatarLink(mPostData.createBy.id + ""), mAvatarImageView);
            mUsernameTextView.setText(mPostData.createBy.username);
            ImageLoader.getInstance().displayImage(mPostData.thumb, mContentImageView);

            /**
             * like, view and share
             */

            mLikeCountTextView.setText(mPostData.like_count+"");
            mViewCountTextView.setText(mPostData.view_count+"");
            mRatingBar.setRating(mPostData.rating_average);

            if (mPostData.is_liked) {
                mLikeCountTextView.setClickable(false);
            }

            if (mPostData.is_rated) {
                mRatingBar.setIsIndicator(true);
            }



        } else if (requestData.getType() == RequestData.TYPE_LIST_COMMENTS) {
            ResponseData<List<CommentModel>> resData = responseData;

            List<CommentModel> models = resData.getData();

            newComentAdapter();

            mCommentData = models;
            mCommentAdapter.setData(mCommentData);

        } else if (requestData.getType() == RequestData.TYPE_CREATE_COMMENT) {
            ResponseData<CommentModel> resData = responseData;

            CommentModel model = resData.getData();

            newComentAdapter();

            mCommentData.add(model);
            mCommentAdapter.setData(mCommentData);

            mCommentEditText.setText("");

        } else if (requestData.getType() == RequestData.TYPE_USER_ACTION) {
            String actionId = ((UserActionRequestData)requestData).getActionId();
            if (actionId.equals("1")) {
                mPostData.like_count++;
                mPostData.is_liked = true;

                mLikeCountTextView.setText(mPostData.like_count + "");
            } else if (actionId.equals("2")) {
                // disable rating again
                mPostData.is_rated = true;
                mRatingBar.setIsIndicator(true);
            }
        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
//        mSwipeRefreshLayout.setRefreshing(false);

    }
}
