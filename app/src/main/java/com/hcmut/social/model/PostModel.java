package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John on 10/6/2016.
 */

public class PostModel extends DataModel{

//     "id": 8,
//    "content": "hehehh",
//    "view_count": 0,
//    "like_count": 0,
//    "comment_count": 0,
//    "rating_count": 0,
//    "rating_average": "0.0",
//    "created_at": "",
//    "thumb": "http://192.168.1.124:8000/system/Post/images/000/000/008/large/IMG-1463480750937-V.jpg",
//    "is_rated": false

    @SerializedName("id")
    public int id;

    @SerializedName("content")
    public String content;

    @SerializedName("view_count")
    public int view_count;

    @SerializedName("like_count")
    public int like_count;

    @SerializedName("comment_count")
    public int comment_count;

    @SerializedName("rate_count")
    public int rate_count;

    @SerializedName("rating_average")
    public float rating_average;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("thumb")
    public String thumb;

    @SerializedName("is_rated")
    public boolean is_rated;

}
