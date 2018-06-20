package com.vivo.pluginbase.uploadLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    public String readStrFromFile(File file) {
        String str = "";

        if (file == null || !file.exists() || file.isDirectory()){
            return str;
        }

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null){
                stringBuilder.append(lineStr);
            }
            str = stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return str;
    }
}
