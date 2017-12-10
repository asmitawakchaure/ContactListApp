package com.contactlistapplication.interfaces;

import android.telecom.Call;

import com.contactlistapplication.models.Contacts;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedString;

/**
 * Created by Asmita on 12/9/2017.
 */

public interface NetworkAPI {
    @GET("/users")
    void getContactList(Callback<JsonArray> response);



}
