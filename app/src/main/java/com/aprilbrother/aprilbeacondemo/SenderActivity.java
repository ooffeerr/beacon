package com.aprilbrother.aprilbeacondemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.aprilbrother.aprilbeacondemo.data.BeaconDataSender;
import com.aprilbrother.aprilbrothersdk.Beacon;
import com.aprilbrother.aprilbrothersdk.BeaconManager;
import com.aprilbrother.aprilbrothersdk.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by oferschonberger on 22/12/16.
 */
public class SenderActivity extends Activity {

    private static final String TAG = "SenderActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String identifierString;
    private BeaconDataSender sender;
    private List<Beacon> myBeacons = new ArrayList<>();

    @Override
    protected void onPause() {
        super.onPause();
        if (identifierStringDialog != null) {
            identifierStringDialog.dismiss();
        }
    }

    private AlertDialog identifierStringDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(new String[]{"a", "b", "c", "d"});
        mRecyclerView.setAdapter(mAdapter);

        sender = new BeaconDataSender(BeaconManagerWrapper.getInstance(SenderActivity.this));

    }

    @Override
    protected void onStart() {
        super.onStart();
//        sender.connect(getApplicationContext());
        BeaconManagerWrapper.getInstance(getApplicationContext()).connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    BeaconManagerWrapper.getInstance(getApplicationContext()).startRanging(BeaconList.ALL_BEACONS_REGION);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        BeaconManagerWrapper.getInstance(getApplicationContext()).setRangingListener(new BeaconManager.RangingListener() {

            @Override
            public void onBeaconsDiscovered(Region region,
                                            final List<Beacon> beacons) {
                if (beacons != null && beacons.size() > 0) {
                    Log.i(TAG, "onBeaconsDiscovered, size = " + beacons.size());
                    Log.i(TAG, beacons.get(0).getName());
                }

                myBeacons.clear();
                myBeacons.addAll(beacons);
                getActionBar().setSubtitle("Found beacons: " + beacons.size());
                ComparatorBeaconByRssi com = new ComparatorBeaconByRssi();
                Collections.sort(myBeacons, com);
//                adapter.replaceWith(myBeacons);
            }
        });

        BeaconManagerWrapper.getInstance(getApplicationContext()).setMonitoringListener(new BeaconManager.MonitoringListener() {

            @Override
            public void onExitedRegion(Region region) {
                Toast.makeText(SenderActivity.this, "Notify in", 0).show();

            }

            @Override
            public void onEnteredRegion(Region region, List<Beacon> beacons) {
                Toast.makeText(SenderActivity.this, "Notify out", 0).show();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
//        sender.disconnect(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTextDialog();
    }


    private void showTextDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter an identifier String");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                identifierString = input.getText().toString();
                Log.d(TAG, "deviceId =  " + identifierString);
                //sendBeaconData(deviceId, );
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });

        identifierStringDialog = builder.show();
    }

    private void sendBeaconData(String identifierString) {
        sender.deviceId = identifierString;
//        sender.registerToBeaconCallbackAndSend();
    }
}
