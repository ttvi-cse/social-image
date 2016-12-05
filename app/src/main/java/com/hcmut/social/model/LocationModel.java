package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John on 11/26/2016.
 */

public class LocationModel extends DataModel {
//    "id": 1,
//    "title": "hello",
//    "lat": 19.123313,
//    "lng": 29.242434,
//    "created_at": ""

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("lat")
    public double lat;

    @SerializedName("lng")
    public double lng;

    @SerializedName("created_at")
    public String created_at;

}
