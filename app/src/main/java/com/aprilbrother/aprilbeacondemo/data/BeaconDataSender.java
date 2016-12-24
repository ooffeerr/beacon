package com.aprilbrother.aprilbeacondemo.data;

import android.util.Log;

import com.aprilbrother.aprilbeacondemo.comm.MyApiEndpointInterface;
import com.aprilbrother.aprilbrothersdk.Beacon;
import com.aprilbrother.aprilbrothersdk.BeaconManager;
import com.aprilbrother.aprilbrothersdk.Region;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Sends beacon data as he sees fit.
 */
public class BeaconDataSender implements BeaconManager.RangingListener{
    private static final String TAG = "BeaconDataSender";
    private final BeaconManager beaconManager;
    public String deviceId;
    private MyApiEndpointInterface apiService;
    private Gson gson;

    public BeaconDataSender(BeaconManager instance) {
        this.beaconManager = instance;
        gson = new Gson();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://backend.example.com")
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return retrofit.create(ApiClient.class);
//
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(MyApiEndpointInterface.class);

    }

    public static final String BASE_URL = "http://52.58.103.202:10080"; ///ws_api/main.srv/JP/";

/*/
http://52.58.103.202:10080/ws_api/main.srv/JP/remu_portals?req={%22type%22:%22noop%22,%22mode%22:%22trkbeacon%22,%22latitude%22:%2232.8093851%22,%22longitude%22:%2235.1081383%22,%22fb_id%22:%221002%22}
http://52.58.103.202:10080/ws_api/main.srv/JP/remu_portals?req={"type":"noop","mode":"trkbeacon","latitude":"32.8093851","longitude":"35.1081383","fb_id":"1002",”device_uid”:”ABC-123-EFG”}
http://52.58.103.202:10080/remu_portals/?req={%22d%22:0,%22e%22:%22fda50693-a4e2-4fb1-afcf-c6eb07647825%22,%22f%22:%22abeacon_4D83%22,%22g%22:%2212:3B:6A:1A:4D:83%22,%22h%22:10,%22i%22:19843,%22j%22:-59,%22k%22:-86}
 */
//    public void registerToBeaconCallbackAndSend() {
////        beaconManager.setRangingListener(this);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        apiService = retrofit.create(MyApiEndpointInterface.class);
//
////        apiService.setBeaconData(new );
//    }
//
//    public void connect(Context context) {
//        BeaconManagerWrapper.connect(context);
//        BeaconManagerWrapper.startRanging(context);
//    }
//
//    public void disconnect(Context context) {
//        BeaconManagerWrapper.stopRanging(context);
//        BeaconManagerWrapper.disconnect(context);
//    }

    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {
        Log.d(TAG, "onBeaconsDiscovered() called with: " + "region = [" + region + "], list = [" + list + "]");
        if (list != null && !list.isEmpty()) {
            String json = gson.toJson(new BeaconReq(list, deviceId));
            Log.d(TAG, "onBeaconsDiscovered: sending " + json);
            Call<Void> voidCall = apiService.setBeaconData(json);
            voidCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d(TAG, "onResponse() called with: " + "call = [" + call + "], response = [" + response.code() + "]");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d(TAG, "onFailure() called with: " + "call = [" + call + "], t = [" + t + "]");
                }
            });
        }
    }

    public void stopSending() {
        Log.d(TAG, "stopSending() called with: " + "");
    }


//
}
