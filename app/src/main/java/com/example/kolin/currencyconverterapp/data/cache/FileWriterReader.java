package com.example.kolin.currencyconverterapp.data.cache;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kolin on 27.10.2017.
 */

public class FileWriterReader {

    private static final String TAG = FileWriterReader.class.getSimpleName();

    public void writeToFile(File file, String text) {
        if (!file.exists()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                out.write(text.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                Log.e(TAG, "writeToFile: Failed write to file -" + file.getName(), e);
                e.printStackTrace();
            }
        }
    }

    public String readFromFile(File file) {
        if (file.exists()) {
            int size = (int) file.length();
            byte[] bytes = new byte[size];

            try {
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                in.read(bytes, 0, bytes.length);
                in.close();
            } catch (IOException e) {
                Log.e(TAG, "readFromFile: Failed read from file - " + file.getName(), e);
                e.printStackTrace();
                return null;
            }

            return new String(bytes);
        }
        return null;
    }
}
