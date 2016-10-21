package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John on 10/6/2016.
 */

public class CommentModel extends DataModel {
//    "id": 3,
//    "content": "Content goes here",
//    "post_id": 14,
//    "user_id": 1,
//    "created_at": "2016-10-19 09:59:56",
//    "updated_at": "2016-10-19 09:59:56"
    @SerializedName("id")
    public int id;

    @SerializedName("content")
    public String content;

    @SerializedName("post_id")
    public int post_id;

    @SerializedName("user_id")
    public int user_id;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;
}
