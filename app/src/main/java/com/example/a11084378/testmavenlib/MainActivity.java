package com.example.a11084378.testmavenlib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.vivo.pluginbase.uploadLog.UploadLog;
import com.vivo.plugins.PluginName.PluginBase;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final String sContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLog(View view) {
        UploadLog uploadLog = new UploadLog();
        uploadLog.upload(MainActivity.this);
    }

    public void addLog(View view) {
        new PluginBase().init(MainActivity.this);
    }

    public void clearSp(View view) {
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("PluginBase", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear();
        sharedPreferences.edit().commit();
        Log.d(TAG, "clearSp: ");
    }
}
