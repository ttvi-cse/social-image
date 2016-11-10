package com.hcmut.social.controller.restcontroller;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hcmut.social.controller.ControllerCenter;
import com.hcmut.social.controller.controllerdata.CreateComentRequestData;
import com.hcmut.social.controller.controllerdata.CreatePostRequestData;
import com.hcmut.social.controller.controllerdata.GetPostDetailRequestData;
import com.hcmut.social.controller.controllerdata.LikePostRequestData;
import com.hcmut.social.controller.controllerdata.ListCommentRequestData;
import com.hcmut.social.controller.controllerdata.ListPostRequestData;
import com.hcmut.social.controller.controllerdata.RatePostRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.model.CommentModel;
import com.hcmut.social.model.PostModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by John on 10/6/2016.
 */

public class RESTPostController extends RESTController{

    private static final String LIST_POST_PATH = "posts";
    private static final String CREATE_POST_PATH = "posts";
    private static final String GET_POST_DETAIL_PATH = "posts/%s";

    private static final String LIST_COMMENT_PATH = "posts/%s/comments";
    private static final String CREATE_COMMENT_PATH = "posts/%s/comments";


    @Override
    public void doRequest(RequestData requestData) throws IOException {
        String url="";
        switch (requestData.getType()) {
            case RequestData.TYPE_LIST_POSTS:
                url = createURL(LIST_POST_PATH);

                doHTTPRequest(
                        createURLConnection(url, RESTController.METHOD_GET, "application/json"),
                        requestData,
                        new TypeToken<ResponseData<List<PostModel>>>(){}
                );
                break;
            case RequestData.TYPE_CREATE_POST:
                CreatePostRequestData cpRequest = (CreatePostRequestData) requestData;
                String path = cpRequest.getImageUri();
                String content  = cpRequest.getContent();

                url = createURL(CREATE_POST_PATH);
                doHTTPRequestUploadFile(
                        createURLConnection(url, RESTController.METHOD_POST, "application/json"),
                        requestData,
                        new TypeToken<ResponseData<Object>>(){},
                        path,
                        content
                );
                break;
            case RequestData.TYPE_GET_POST_DETAIL:
                GetPostDetailRequestData gpdRequest = (GetPostDetailRequestData) requestData;

                String post_id = gpdRequest.getPostId();

                url = createURL(String.format(GET_POST_DETAIL_PATH, post_id));
                doHTTPRequest(
                        createURLConnection(url, RESTController.METHOD_GET, "application/json"),
                        requestData,
                        new TypeToken<ResponseData<PostModel>>(){}
                );
                break;
            case RequestData.TYPE_LIST_COMMENTS:
                ListCommentRequestData lcRequest = (ListCommentRequestData) requestData;

                post_id = lcRequest.getPostId();

                url = createURL(String.format(LIST_COMMENT_PATH, post_id));
                doHTTPRequest(
                        createURLConnection(url, RESTController.METHOD_GET, "application/json"),
                        requestData,
                        new TypeToken<ResponseData<List<CommentModel>>>(){}
                );
                break;
            case RequestData.TYPE_CREATE_COMMENT:
                CreateComentRequestData ccRequest = (CreateComentRequestData) requestData;

                post_id = ccRequest.getPostId();

                url = createURL(String.format(CREATE_COMMENT_PATH, post_id));
                doHTTPRequest(
                        createURLConnection(url, RESTController.METHOD_GET, "application/json"),
                        requestData,
                        new TypeToken<ResponseData<CommentModel>>(){}
                );
                break;

            default:
                break;
        }
    }

    @Override
    public void registerEvent(ControllerCenter controllerCenter) {
        controllerCenter.addEventHandler(RequestData.TYPE_LIST_POSTS, this);
        controllerCenter.addEventHandler(RequestData.TYPE_CREATE_POST, this);
        controllerCenter.addEventHandler(RequestData.TYPE_GET_POST_DETAIL, this);
        controllerCenter.addEventHandler(RequestData.TYPE_LIST_COMMENTS, this);
        controllerCenter.addEventHandler(RequestData.TYPE_CREATE_COMMENT, this);
        controllerCenter.addEventHandler(RequestData.TYPE_LIKE_POST, this);
        controllerCenter.addEventHandler(RequestData.TYPE_RATE_POST, this);
    }
}
