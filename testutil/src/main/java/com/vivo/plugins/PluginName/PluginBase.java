/*
Automatic generated, Please do not modify.
Any questions please contact liangbinhlw@bbktel.com. tks.

please add "PluginBase.init(context)" in your init method.
 */
package com.vivo.plugins.PluginName;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class PluginBase {
    private final static String TAG = PluginBase.class.getCanonicalName();
    private static final String PLUGIN_NAME = "PluginName";
    private static final String PLUGIN_VERSION_NAME = "1.1.2-SNAPSHOT";
    private static final int PLUGIN_VERSION_CODE = 112;
    private static final String PLUGIN_BUILD_DATE = "2018-06-20 14:42:51";
    private static final String PLUGIN_BUILD_COMMIT_ID = "ed20077";
    public static void init(final Context context){

        new Thread(new Runnable() {
            @Override
            public void run() {
                saveLog(context.getApplicationContext());
            }
        }).start();
    }

    private static void saveLog(Context context){
        JSONObject jsonObject = getJsonObject(context);
        save2sd(context, jsonObject);
    }

    private static JSONObject getJsonObject(Context context){
        JSONObject jsonObject = null;
        String packageName = context.getPackageName();
        try {
            //app info
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
            String appVersionName = packageInfo.versionName;
            String appVersionCode = String.valueOf(packageInfo.versionCode);
            jsonObject = new JSONObject();
            jsonObject.put("pluginName", PLUGIN_NAME);
            jsonObject.put("pluginVersionName", PLUGIN_VERSION_NAME);
            jsonObject.put("pluginVersionCode", PLUGIN_VERSION_CODE);
            jsonObject.put("pluginBuildDate", PLUGIN_BUILD_DATE);
            jsonObject.put("pluginBuildCommitId", PLUGIN_BUILD_COMMIT_ID);
            jsonObject.put("appPackageName", packageName);
            jsonObject.put("appVersionName", appVersionName);
            jsonObject.put("appVersionCode", appVersionCode);
            jsonObject.put("clientDate", getToday());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static void save2sd(Context context, JSONObject jsonObject){
        if (!canWrite(context)) {
            return;
        }

        if (isAlreadySavedWeek(context)){
            return;
        }

        File file = findFile2Write(context);
        try {
            boolean result = writeStr2File(file, jsonObject);
            if (result) {
                setSavedWeekTag(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File findFile2Write(Context context){
        String parentFilePath = getParentFilePath(context);
        String filePath = parentFilePath + File.separator + PLUGIN_NAME+".txt";
        File file = new File(filePath);
        return file;
    }

    private static boolean writeStr2File(File file, JSONObject jsonObject) throws IOException {
        if (file == null){
            return false;
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = null;
        FileLock fileLock = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            fileLock = fileOutputStream.getChannel().tryLock();

            if (fileLock != null){
                String content = "##"+jsonObject.toString();
                fileOutputStream.write(content.getBytes());
                fileOutputStream.flush();
                Log.d(TAG, "writeStr2File: "+content);
                return true;
            }
            return false;
        } finally {
            if (fileLock != null) {
                fileLock.release();
            }

            if (fileOutputStream != null){
                fileOutputStream.close();
            }
        }
    }

    private static boolean canWrite(Context context){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            String parentPath = getParentFilePath(context);
            File file = new File(parentPath);
            if (!file.exists()) {
                boolean result = file.mkdirs();
                if (!result) {
                    return false;
                }
            }
            if (file.canWrite()){
                return true;
            }
        }
        return false;
    }

    private static String getParentFilePath(Context context){
        return Environment.getExternalStorageDirectory()+File.separator+"pluginInfo"+File.separator+context.getPackageName();
    }

    private static String getToday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private static String getWeek(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-ww");
        String week = simpleDateFormat.format(new Date());
        return week;
    }

    private static boolean isAlreadySavedWeek(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("PluginBase", Context.MODE_PRIVATE);
        boolean alreadySaved = sharedPreferences.getBoolean(getWeek(), false);
        if (alreadySaved){
            return true;
        }
        return false;
    }
    
    private static void setSavedWeekTag(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("PluginBase", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getWeek(), true);
        editor.apply();
    }
}