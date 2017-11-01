package com.example.kolin.currencyconverterapp.data.cache;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kolin on 27.10.2017.
 */

public class FileWriterReader {

    private static final String TAG = FileWriterReader.class.getSimpleName();

    public void writeToFile(File file, String text) throws IOException {
        if (!file.exists()) {
            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                out.write(text.getBytes());
                out.flush();
                out.close();
            }
        }
    }

    public String readFromFile(File file) throws IOException {
        if (file.exists()) {
            int size = (int) file.length();
            byte[] bytes = new byte[size];

            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                in.read(bytes, 0, bytes.length);
                in.close();
            }

            return new String(bytes);
        }
        return null;
    }

    public void clearDirectory(File file){
        if (file != null && file.isDirectory()){
            for (File f: file.listFiles())
                f.delete();
        }
    }
}
