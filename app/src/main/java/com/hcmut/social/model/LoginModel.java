package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John on 10/6/2016.
 */

public class LoginModel extends DataModel {

//    "id": 1,
//    "username": "admin",
//    "email": "admin@apollo.com",
//    "first_name": "Admin",
//    "last_name": null,
//    "gender": 1,
//    "remember_token": null,
//    "created_at": "",
//    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdDo4MDAwXC9hcGlcL3VzZXJcL2xvZ2luIiwiaWF0IjoiMTQ3ODgxMTA4OSIsImV4cCI6IjE0ODAwMjA2ODkiLCJuYmYiOiIxNDc4ODExMDg5IiwianRpIjoiNTRiYTYwMWViNThhZmM4MTI5NDViNzE1OTcyYzhlN2YifQ.ZWRhMTI3ZTdlODNkNjQ5ODhkY2NhNjZmY2M3NzQ5MjcwNTljOWZiYzZiZTliMTYxNDE4YzJmZTIxN2M0NWQ2NA",
//    "expiry_in": 20160

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

    @SerializedName("gender")
    public int gender;

    @SerializedName("token")
    public String token;

    @SerializedName("expiry_in")
    public long expiration;

    @SerializedName("remamber_token")
    public String remamber_token;

    @SerializedName("created_at")
    public String created_at;

    public String password;

    public boolean isLogin = false;
}
