package com.aprilbrother.aprilbeacondemo;

import android.content.Context;

import com.aprilbrother.aprilbrothersdk.BeaconManager;

/**
 * Wrappes the BeaconManager
 */

public class BeaconManagerWrapper {

    private static BeaconManager sBeaconManager;

    public static BeaconManager getInstance(Context context) {
        if (sBeaconManager == null) {
            sBeaconManager = new BeaconManager(context);
        }
        return sBeaconManager;
    }


}
