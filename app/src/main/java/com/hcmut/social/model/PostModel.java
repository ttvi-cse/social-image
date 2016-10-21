package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John on 10/6/2016.
 */

public class PostModel extends DataModel{

//    "id": 3,
//    "content": "hello",
//    "img_url": "http://localhost:8000/uploads/33466.jpg",
//    "view_count": 0,
//    "like_count": 0,
//    "rate_count": 0,
//    "user_id": 1,
//    "created_at": "2016-10-19 06:02:55",
//    "updated_at": "2016-10-19 06:02:55"

    @SerializedName("id")
    public int id;

    @SerializedName("content")
    public String content;

    @SerializedName("img_url")
    public String img_url;

    @SerializedName("view_count")
    public int view_count;

    @SerializedName("like_count")
    public int like_count;

    @SerializedName("rate_count")
    public float rate_count;

    @SerializedName("user_id")
    public int user_id;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("created_at")
    public String created_at;

}
