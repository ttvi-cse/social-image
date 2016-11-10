package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John on 10/6/2016.
 */

public class CommentModel extends DataModel {
//    "content": "hello",
//    "created_at": "",
//    "id": 4,
//    "post": {
//        "id": 10,
//        "content": "jejej",
//        "view_count": 11,
//        "like_count": 0,
//        "comment_count": 1,
//        "rating_count": 0,
//        "rating_average": "0.0",
//        "created_at": "",
//        "thumb": "http://192.168.1.124:8000/system/Post/images/000/000/010/large/IMG-1463480750937-V.jpg",
//        "is_rated": false
//    },
//    "created_by_user": {
//        "id": 1,
//        "username": "admin",
//        "email": "admin@apollo.com",
//        "first_name": "Admin",
//        "last_name": null,
//        "gender": 1,
//        "remember_token": null,
//        "created_at": ""
//    },
//    "parent_id": null

    @SerializedName("id")
    public int id;

    @SerializedName("content")
    public String content;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("post")
    public PostModel post;

    @SerializedName("created_by_user")
    MyProfileModel user;
}
