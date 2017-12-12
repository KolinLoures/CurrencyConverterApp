package com.example.kolin.currencyconverterapp.data.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class for work with files
 */

public class FileWriterReader {

    private static final String TAG = FileWriterReader.class.getSimpleName();

    /**
     * Write content to file
     *
     * @param file Where you should write
     * @param text Content that to write
     * @throws IOException Vse ochen ploho
     */
    public void writeToFile(File file, String text) throws IOException {
        if (!file.exists()) {
            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                out.write(text.getBytes());
                out.flush();
                out.close();
            }
        }
    }

    /**
     * Read content of file
     *
     * @param file Where you should read
     * @return Content of file
     * @throws IOException Vse ochen ploho
     */
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

    /**
     * Clear file in directory
     *
     * @param file file directory
     */
    public void clearDirectory(File file){
        if (file != null && file.isDirectory()){
            for (File f: file.listFiles())
                f.delete();
        }
    }
}
