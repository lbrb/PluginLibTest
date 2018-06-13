package com.example.a11084378.testmavenlib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final String sContent = "apply plugin: 'com.android.library'\n" +
            "//add\n" +
            "apply plugin: 'maven'\n" +
            "\n" +
            "//def libVerName = \"1.7-SNAPSHOT\"\n" +
            "def libVerName = \"1.8\"\n" +
            "def libVerCode = 5\n" +
            "\n" +
            "android {\n" +
            "    compileSdkVersion 27\n" +
            "\n" +
            "    defaultConfig {\n" +
            "        minSdkVersion 15\n" +
            "        targetSdkVersion 27\n" +
            "        versionCode libVerCode\n" +
            "        versionName libVerName\n" +
            "\n" +
            "        testInstrumentationRunner \"android.support.test.runner.AndroidJUnitRunner\"\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    buildTypes {\n" +
            "        release {\n" +
            "            minifyEnabled false\n" +
            "            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "}\n" +
            "\n" +
            "dependencies {\n" +
            "    implementation fileTree(dir: 'libs', include: ['*.jar'])\n" +
            "\n" +
            "    implementation 'com.android.support:appcompat-v7:27.1.1'\n" +
            "    testImplementation 'junit:junit:4.12'\n" +
            "    androidTestImplementation 'com.android.support.test:runner:1.0.2'\n" +
            "    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'\n" +
            "}\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "uploadArchives{\n" +
            "    configuration = configurations.archives\n" +
            "    repositories {\n" +
            "        mavenDeployer {\n" +
            "\n" +
            "            snapshotRepository(url: \"http://127.0.0.1:8081/nexus/content/repositories/snapshots/\") {\n" +
            "                authentication(userName: 'admin', password: 'admin123')\n" +
            "            }\n" +
            "\n" +
            "            repository(url: 'http://127.0.0.1:8081/nexus/content/repositories/releases/') {\n" +
            "                authentication(userName: 'admin', password: 'admin123')\n" +
            "            }\n" +
            "\n" +
            "            pom.project{\n" +
            "                version '1.1.1-SNAPSHOT'\n" +
            "                artifactId 'TestPlugin'\n" +
            "                groupId 'com.vivo.plugins'\n" +
            "                packaging 'aar'\n" +
            "                description 'com.vivo.plugins.TestPlugin:1.1.1-SNAPSHOT'\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "//commit id\n" +
            "//已有uploadArchives删除\n" +
            "//插件升级（我们自己做）\n" +
            "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void btnClick(View view){
        try {
            String userHomeDir = System.getProperty("user.home");
            String path = userHomeDir + File.separator+"b"+File.separator+ "a"+".txt";
            Log.d(TAG, "btnClick: "+path);

            String str = "/b/a.txt";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
