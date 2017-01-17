package com.hcmut.social.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hcmut.social.R;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by John on 10/19/2016.
 */

public class PostDetailActivity extends BaseActivity {

    public static final String EXTRA_POST_ID = "e_postid";


    ImageButton mBackButton;
    ImageView mAvatarImageView;
    TextView mUsernameTextView;
    ImageView mContentImageView;
    TextView mLikeCountTextView;
    TextView mViewCountTextView;
    RatingBar mRatingBar;

    TextView tvTitle;
    TextView tvContent;
    TextView tvLocation;
    TextView tvTime;

    LinearLayout mCommentLayout;

    EditText mCommentEditText;
    Button mSendButton;

//    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<CommentModel> mCommentData;
    private PostModel mPostData;
    private int mPostID;
    private boolean isLoading = false;

    private DisplayImageOptions mOpts;
    private TextView mAddressTextView;

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

        mCommentLayout = (LinearLayout) findViewById(R.id.comment_layout);

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

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        mAddressTextView = (TextView) findViewById(R.id.tv_address);
        tvTime = (TextView) findViewById(R.id.tv_time);

        mPostID = getIntent().getIntExtra(EXTRA_POST_ID, -1);

        mOpts = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(6))
                .showImageOnLoading(R.drawable.ic_avatar)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
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

//    private void newComentAdapter() {
//        if (mCommentAdapter == null) {
//            mCommentAdapter = new CommentAdapter(PostDetailActivity.this);
//            mCommentListView.setAdapter(mCommentAdapter);
//        }
//    }

    private void fetchComments(List<CommentModel> comments) {
        mCommentLayout.removeAllViews();

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < comments.size(); i++) {
            CommentModel model = comments.get(i);

            View view = inflater.inflate(R.layout.row_comment, null, false);

            ImageView img_avatar = (ImageView) view.findViewById(R.id.avatar_imageview);
            TextView tv_content = (TextView) view.findViewById(R.id.content_text);
            TextView tv_time = (TextView) view.findViewById(R.id.time_text);

            ImageLoader.getInstance().displayImage(UserUtil.getAvatarLink(model.createBy.id + ""), img_avatar, mOpts);

            tv_content.setText(String.format("%s: %s", model.createBy.username, model.content));
            tv_time.setText(model.created_at+"");

            mCommentLayout.addView(view, 0);
        }
    }

    private void addComment(CommentModel comment) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_comment, null, false);

        ImageView img_avatar = (ImageView) view.findViewById(R.id.avatar_imageview);
        TextView tv_content = (TextView) view.findViewById(R.id.content_text);
        TextView tv_time = (TextView) view.findViewById(R.id.time_text);

        ImageLoader.getInstance().displayImage(UserUtil.getAvatarLink(comment.createBy.id + ""), img_avatar, mOpts);

        tv_content.setText(String.format("%s: %s", comment.createBy.username, comment.content));
        tv_time.setText(comment.created_at+"");

        mCommentLayout.addView(view, mCommentLayout.getChildCount());
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
            if (mPostData.createBy != null) {
                ImageLoader.getInstance().displayImage(UserUtil.getAvatarLink(mPostData.createBy.id + ""), mAvatarImageView);
                mUsernameTextView.setText(mPostData.createBy.username);
            }

            ImageLoader.getInstance().displayImage(mPostData.thumb, mContentImageView);

            /**
             * like, view and share
             */

            mLikeCountTextView.setText(mPostData.like_count+"");
            mViewCountTextView.setText(mPostData.view_count+"");
            mRatingBar.setRating(mPostData.rating_average);

            if (mPostData.is_liked) {
                mLikeCountTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_fill, 0,0,0);
                mLikeCountTextView.setClickable(false);
            }

            if (mPostData.is_rated) {
                mRatingBar.setIsIndicator(true);
            }

            tvTitle.setText(String.format("Title: %s", mPostData.title));
            tvContent.setText(String.format("Content: %s", mPostData.content));
            tvLocation.setText(String.format("Location: %s", mPostData.locations.name));
            mAddressTextView.setText(String.format("Address: %s", mPostData.locations.address));
            tvTime.setText(String.format("Created at: %s", model.created_at));

        } else if (requestData.getType() == RequestData.TYPE_LIST_COMMENTS) {
            ResponseData<List<CommentModel>> resData = responseData;

            List<CommentModel> models = resData.getData();
            fetchComments(models);

            mCommentData = models;

        } else if (requestData.getType() == RequestData.TYPE_CREATE_COMMENT) {
            ResponseData<CommentModel> resData = responseData;

            CommentModel model = resData.getData();

            addComment(model);

            mCommentData.add(model);
            mCommentEditText.setText("");

        } else if (requestData.getType() == RequestData.TYPE_USER_ACTION) {
            String actionId = ((UserActionRequestData)requestData).getActionId();
            if (actionId.equals("1")) {
                mPostData.like_count++;
                mPostData.is_liked = true;

                mLikeCountTextView.setText(mPostData.like_count + "");
                mLikeCountTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_fill, 0,0,0);
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
//        mSwipeRefreshLayout.setRefreshing(false);

    }
}
