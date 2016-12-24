package com.aprilbrother.aprilbeacondemo.data;

import android.util.Log;

import com.aprilbrother.aprilbrothersdk.Beacon;
import com.aprilbrother.aprilbrothersdk.BeaconManager;
import com.aprilbrother.aprilbrothersdk.Region;

import java.util.List;

/**
 * Created by oferschonberger on 23/12/16.
 */
public class BeaconRangingListener implements BeaconManager.RangingListener {

    private static final String TAG = "BeaconRangingListener";

    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {
        Log.d(TAG, "onBeaconsDiscovered() called with: " + "region = [" + region + "], list = [" + list + "]");
    }
}
