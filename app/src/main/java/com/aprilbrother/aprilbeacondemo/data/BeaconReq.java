package com.aprilbrother.aprilbeacondemo.data;

import com.aprilbrother.aprilbrothersdk.Beacon;
import com.google.gson.JsonElement;

/**
 * Created by oferschonberger on 24/12/16.
 * {"type":"noop","mode":"trkbeacon","latitude":"32.8093851","longitude":"35.1081383","fb_id":"1002"}
 */
public class BeaconReq {

    public String type, mode, latitude, longitude, fb_id, deviceId, beaconName;
    double distance;

    public BeaconReq(Beacon beacon, String deviceId) {
        distance = beacon.getDistance();
        type = "noop";
        mode = "trkbeacon";
        latitude = "32.8093851";
        longitude = "35.1081383";
        fb_id = "1002";
        this.deviceId = deviceId;
        beaconName = beacon.getName();
    }
}
