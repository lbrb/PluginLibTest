package com.vivo.pluginbase.uploadLog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadLog {
    private static final String TAG = UploadLog.class.getCanonicalName();
    private static final String sUrlStr = "http://pluginstat.vivo.com.cn/userOperationLog";

    public void upload(Context context) {
        uploadReal(context);
    }

    private void uploadReal(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!checkExternalStorageCanWrite()) {
                    return;
                }

                if (!isNetwrokAvailable(context)){
                    return;
                }

                JSONArray jsonArray = getLogsFromSd(context);

                uploadLogWithNet(jsonArray);

                deleteLocalLogs(context);
            }
        }).start();
    }

    private boolean checkExternalStorageCanWrite() {
        try {
            boolean mouted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
            if (mouted) {
                boolean canWrite = new File(Environment.getExternalStorageDirectory().getAbsolutePath()).canWrite();
                if (canWrite) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private JSONArray getLogsFromSd(Context context) {
        JSONArray jsonArray = new JSONArray();
        File logsDir = getLogsDir(context);
        if (logsDir == null && !logsDir.exists() && logsDir.isFile()) {
            return jsonArray;
        }

        for (File dir : logsDir.listFiles()) {
            Log.d(TAG, "getLogsFromSd: Dir:" + dir.getName());
            if (dir.getName().contains("..")) {
                continue;
            }
            String fileContent;
            JSONArray fileJsonArray;
            FileUtil fileUtil = new FileUtil();
            for (File logFile : dir.listFiles()) {
                if (logFile.isFile()) {
                    Log.d(TAG, "getLogsFromSd: File:" + logFile);
                    fileContent = fileUtil.readStrFromFile(logFile);
                    addLogs2JsonArray(fileContent, jsonArray);
                }
            }
        }

        return jsonArray;
    }

    private File getLogsDir(Context context) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "pluginInfo";
        File logsDir = new File(path);
        return logsDir;
    }

    private void addLogs2JsonArray(String content, JSONArray jsonArray) {
        String logsStr[] = content.split("##");
        for (String logStr :
                logsStr) {
            if (TextUtils.isEmpty(logStr)) {
                continue;
            }
            try {
                JSONObject jsonObject = new JSONObject(logStr);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNetwrokAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null){
            return false;
        }

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
         return true;
        }

        return false;
    }

    private void uploadLogWithNet(JSONArray jsonArray){
        if (jsonArray == null || jsonArray.length() ==0){
            return;
        }

        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(sUrlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(jsonArray.toString().getBytes());
            bufferedOutputStream.flush();

            int responseCode = httpURLConnection.getResponseCode();
            Log.d(TAG, "responseCode:"+responseCode);

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }
    }

    private void deleteLocalLogs(Context context){
        File logsDir = getLogsDir(context);
        if (logsDir == null && !logsDir.exists() && logsDir.isFile()) {
            return;
        }

        for (File dir : logsDir.listFiles()) {
            if (dir.getName().contains("..")) {
                continue;
            }
            for (File logFile : dir.listFiles()) {
                if (logFile.isFile()) {
                    boolean b = logFile.delete();
                    Log.d(TAG, "deleteLocalLogs: result:"+b+", fileName:"+logFile);
                }
            }
        }
    }
}
