package com.example.kate.myapplication;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Kate on 11.12.2017.
 */

public class GetAnWriteWifiInfo implements Runnable {
    private boolean isReading = false;
    private WifiManager wifiManager;
    private boolean isActive;
    private File sdPath;
    final String LOG_TAG = "myLogs";
    final String DIR_SD = "MyFiles2";
    final String FILENAME_SD = "fileSD.csv";
    public GetAnWriteWifiInfo(WifiManager wifiM) {
        wifiManager = wifiM;
        isActive = false;
        // проверяем доступность SD
        Log.d(LOG_TAG, "Start!!");
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        sdPath = Environment.getExternalStorageDirectory();
        Log.d(LOG_TAG, String.valueOf(sdPath));
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        Log.d(LOG_TAG, String.valueOf(sdPath));
        // создаем каталог
        if (sdPath.mkdirs() == true ) Log.d(LOG_TAG, "It's ok");
        else Log.d(LOG_TAG, "It's not ok");
        // формируем объект File, который содержит путь к файлу

    }
    public void StopActive(){
        isActive = false;
    }
   /* private void writeFileSD() {
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
    }*/
    @Override
    public void run() {
        isActive = true;
        File sdFile = new File(sdPath, FILENAME_SD);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            long moment = 0;
            while (isActive == true) {
                Long tsLong = System.currentTimeMillis();
                String ts = tsLong.toString();
                List<ScanResult> list = wifiManager.getScanResults();
                for (ScanResult result : list) {
                    String WifiRresult = ts + ";" + String.valueOf(moment) + ";" + String.valueOf(result.SSID) + ";" + String.valueOf(result.level) + "\n";
                    bw.write(WifiRresult);


                    // закрываем поток

                    Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());

                    Log.d(LOG_TAG, String.valueOf(result.SSID));
                    Log.d(LOG_TAG, String.valueOf(result.level));
                }
                SystemClock.sleep(500);
                moment = moment + 500;
            }
            bw.close();
        }
        catch(IOException e){
            Log.d(LOG_TAG, "Block exception");
            e.printStackTrace();
        }


    }
}
