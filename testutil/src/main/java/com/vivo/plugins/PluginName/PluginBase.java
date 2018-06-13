/*
Automatic generated, Please do not modify.
Any questions please contact liangbinhlw@bbktel.com. tks.

please add "PluginBase.init(context)" in your init method.
 */
package com.vivo.plugins.PluginName;

import android.content.Context;

public class PluginBase {
    private static final String PLUGIN_NAME = "PluginName";
    private static final String PLUGIN_VERSION = "1.1.1-SNAPSHOT";
    public static final String PLUGIN_BUILD_DATE = "2018-06-13 11:03:09";
    public static final String PLUGIN_BUILD_COMMIT_ID = "3633ef7";
    public static void init(final Context context){

        new Thread(new Runnable() {
            @Override
            public void run() {
                saveLog(context.getApplicationContext());
            }
        }).start();
    }

    private static void saveLog(Context context){
        //TODO
    }
}