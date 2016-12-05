package com.hcmut.social.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John on 11/26/2016.
 */

public class LocationModel extends DataModel {
//    "place_id": "hello",
//    "name": "hello",
//    "address": "hello",
//    "phone": "hello",
//    "created_at": "",
//    "id": 1

    @SerializedName("id")
    public int id;

    @SerializedName("place_id")
    public String place_id;

    @SerializedName("name")
    public String name;

    @SerializedName("address")
    public String address;

    @SerializedName("phone")
    public String phone;

    @SerializedName("created_at")
    public String created_at;


    public LocationModel(String place_id, String name, String address, String phone) {
        this.place_id = place_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
