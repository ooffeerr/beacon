package com.aprilbrother.aprilbeacondemo.comm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by oferschonberger on 22/12/16.
 */

public interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("/ws_api/main.srv/JP/remu_portals")
    Call<Void> setBeaconData(@Query("req") String requestJson);


}