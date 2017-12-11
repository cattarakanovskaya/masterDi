package com.example.kate.myapplication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.net.wifi.ScanResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button listen;
    private  boolean isPower;
    private WifiManager wifiMgr;
    final String LOG_TAG = "myLogs";
    private GetAnWriteWifiInfo runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        runnable = new GetAnWriteWifiInfo(wifiMgr);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listen = (Button) findViewById(R.id.button);
        isPower = false;
        listen.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "Click!");
            if(isPower == false){
                listen.setText("Stop");
                isPower = true;
                new Thread(runnable).start();
            }
            else{
                runnable.StopActive();
                listen.setText("Start");
                isPower = false;
            }
        }
    };
}
