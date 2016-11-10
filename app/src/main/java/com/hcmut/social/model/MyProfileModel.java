package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by John on 10/6/2016.
 */
@DatabaseTable(tableName = "profile")
public class MyProfileModel extends DataModel {

    //    "id": 1,
//    "username": "ttvi",
//    "email": "ttvicse@gmail.com",
//    "first_name": null,
//    "last_name": null,
//    "name_title": null,
//    "gender": 1,
//    "type_id": 1,
//    "job_title": null,
//    "organization": null,
//    "summary": null,
//    "token": null,
//    "expiry_in": 0,
//    "remember_token": null,
//    "created_at": "2016-10-18 04:47:42",
//    "updated_at": "2016-10-18 04:47:42"
    @DatabaseField(id = true)
    @SerializedName("id")
    public int id;

    @SerializedName("username")
    public String username;

    @SerializedName("email")
    public String email;

    @SerializedName("first_name")
    public String first_name;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("name_title")
    public String name_title;

    @SerializedName("gender")
    public int gender;

    @SerializedName("type_id")
    public String type_id;

    @SerializedName("job_title")
    public String job_title;

    @SerializedName("organization")
    public String organization;

    @SerializedName("summary")
    public String summary;

    @SerializedName("token")
    public String token;

    @SerializedName("expiry_in")
    public long expiration;

    @SerializedName("remember_token")
    public String remember_token;

    @SerializedName("created_at")
    public String created_at;

}
