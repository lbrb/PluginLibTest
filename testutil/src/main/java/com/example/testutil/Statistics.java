package com.example.testutil;

import android.content.Context;
import android.content.pm.PackageManager;

public class Statistics {
    public static void uploadInfo(Context context){
        String packageName = context.getPackageName();
        try {
            String version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        

    }
}
