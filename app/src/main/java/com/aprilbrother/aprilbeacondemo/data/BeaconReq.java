package com.aprilbrother.aprilbeacondemo.data;

import com.aprilbrother.aprilbrothersdk.Beacon;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oferschonberger on 24/12/16.
 * {"type":"noop","mode":"trkbeacon","latitude":"32.8093851","longitude":"35.1081383","fb_id":"1002"}
 */
public class BeaconReq {

    private final List<com.aprilbrother.aprilbeacondemo.data.Beacon> beacon_list;
    public String type, mode, latitude, longitude, fb_id, deviceId;

    public BeaconReq(List<Beacon> beaconList, String deviceId) {
        //distance = beacon.getDistance();
        type = "noop";
        mode = "trkbeacon";
        latitude = "32.8093851";
        longitude = "35.1081383";
        fb_id = "1002";
        this.deviceId = deviceId;
        beacon_list = createList(beaconList);
    }

    private List<com.aprilbrother.aprilbeacondemo.data.Beacon> createList(List<Beacon> beaconList) {
        ArrayList beaconsConverted = new ArrayList(beaconList.size());
        for (Beacon beacon : beaconList) {
            beaconsConverted.add(new com.aprilbrother.aprilbeacondemo.data.Beacon(beacon));
        }
        return beaconsConverted;
    }
}
