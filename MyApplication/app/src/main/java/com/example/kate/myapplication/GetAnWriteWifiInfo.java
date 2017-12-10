package com.example.kate.myapplication;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by Kate on 11.12.2017.
 */

public class GetAnWriteWifiInfo implements Runnable {
    private boolean isReading = false;
    private WifiManager wifiManager;
    final String LOG_TAG = "myLogs";
    public GetAnWriteWifiInfo(WifiManager wifiM) {
        wifiManager = wifiM;
    }
    @Override
    public void run() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        List<ScanResult> list = wifiManager.getScanResults();
        for (ScanResult result : list) {
            Log.d(LOG_TAG, String.valueOf(result.SSID));
            Log.d(LOG_TAG, String.valueOf(result.level));
        }

    }
}
