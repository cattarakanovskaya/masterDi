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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        writeFileSD();


        wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);






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
                GetAnWriteWifiInfo runnable = new GetAnWriteWifiInfo(wifiMgr);
                new Thread(runnable).start();
            }
            else{
                listen.setText("Start");
                isPower = false;
            }

        }
    };

    final String LOG_TAG = "myLogs";
    final String DIR_SD = "MyFiles2";
    final String FILENAME_SD = "fileSD.txt";

    private void writeFileSD() {
        // проверяем доступность SD
        Log.d(LOG_TAG, "Start!!");
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        Log.d(LOG_TAG, String.valueOf(sdPath));
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        Log.d(LOG_TAG, String.valueOf(sdPath));
        // создаем каталог
        if (sdPath.mkdirs() == true ) Log.d(LOG_TAG, "It's ok");
        else Log.d(LOG_TAG, "It's not ok");
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);
        try {
            // открываем поток для записи
            Log.d(LOG_TAG, "Block try1");
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            Log.d(LOG_TAG, "Block try2");
            // пишем данные
            bw.write("Содержимое файла на SD");
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            Log.d(LOG_TAG, "Block exception");
            e.printStackTrace();
        }
    }
}
