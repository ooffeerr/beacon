package com.aprilbrother.aprilbeacondemo;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.aprilbrother.aprilbrothersdk.BeaconManager;
import com.aprilbrother.aprilbrothersdk.Region;

/**
 * Wrappes the BeaconManager
 */

public class BeaconManagerWrapper {

    private static final String TAG = "BeaconManagerWrapper";
    private static BeaconManager sBeaconManager;

    private static final Region ALL_BEACONS_REGION = new Region("customRegionName", null,
            null, null);

    public static BeaconManager getInstance(Context context) {
        if (sBeaconManager == null) {
            sBeaconManager = new BeaconManager(context);
        }
        return sBeaconManager;
    }


    public static void connect(final Context applicationContext) {
        Log.d(TAG, "connect() called with: " + "applicationContext = [" + applicationContext + "]");
        getInstance(applicationContext).connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d(TAG, "onServiceReady() called with: " + "");
                new BeaconManager.ServiceReadyCallback() {
                    @Override
                    public void onServiceReady() {
                        try {
                            Log.i(TAG, "connectToService");
                            BeaconManagerWrapper.getInstance(applicationContext).startRanging(ALL_BEACONS_REGION);
                            // beaconManager.startMonitoring(ALL_BEACONS_REGION);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                };
            }
        });
    }

    public static void stopRanging(Context applicationContext) {
        Log.d(TAG, "stopRanging() called with: " + "applicationContext = [" + applicationContext + "]");
        try {
            getInstance(applicationContext).stopRanging(ALL_BEACONS_REGION);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(Context applicationContext) {
        Log.d(TAG, "disconnect() called with: " + "applicationContext = [" + applicationContext + "]");
        getInstance(applicationContext).disconnect();
    }

    public static void startRanging(Context applicationContext) {
        Log.d(TAG, "startRanging() called with: " + "applicationContext = [" + applicationContext + "]");
        try {
            getInstance(applicationContext).startRanging(ALL_BEACONS_REGION);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
