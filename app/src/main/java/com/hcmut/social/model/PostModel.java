package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John on 10/6/2016.
 */

public class PostModel extends DataModel{

//     "id": 8,
//    "title": "",
//    "content": "hehehh",
//    "view_count": 0,
//    "like_count": 0,
//    "comment_count": 0,
//    "rating_count": 0,
//    "rating_average": "0.0    ",
//    "created_at": "",
//    "thumb": "http://192.168.1.124:8000/system/Post/images/000/000/008/large/IMG-1463480750937-V.jpg",
//    "is_liked": false,
//    "is_rated": false
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
//    "locations": {
//        "id": 3,
//        "place_id": "ChIJDZPwOKcodTERNPN2M2CoZ_o",
//        "name": "Công Ty Cp Center Realestate Viet Nam",
//        "address": "292 Ung Văn Khiêm, 25, Bình Thạnh, Hồ Chí Minh, Việt Nam",
//        "phone": "",
//        "created_at": "2016-12-05T14:57:12+00:00"
//    }

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

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

    @SerializedName("is_liked")
    public boolean is_liked;

    @SerializedName("is_rated")
    public boolean is_rated;

    @SerializedName("created_by_user")
    public LoginModel createBy;

    @SerializedName("locations")
    public LocationModel locations;

}
