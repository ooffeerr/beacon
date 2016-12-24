package com.aprilbrother.aprilbeacondemo.data;

/**
 * Created by oferschonberger on 24/12/16.
 */

public class Beacon {
    private final double distance;
    private final String macAddress;
    private final int measuredPower;
    private final String name;
    private final int proximity;
    private final String proximityUUID;
    private final int power;

    public Beacon(com.aprilbrother.aprilbrothersdk.Beacon other) {
        this.distance = other.getDistance();
        this.macAddress = other.getMacAddress();
        this.measuredPower = other.getMeasuredPower();
        this.name = other.getName();
        this.proximity = other.getProximity();
        this.proximityUUID = other.getProximityUUID();
        this.power = other.getPower();
    }
}
