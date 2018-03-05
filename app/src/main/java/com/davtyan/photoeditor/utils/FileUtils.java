package com.davtyan.photoeditor.utils;

import android.os.Environment;

import java.io.File;


public class FileUtils {

    public static final String FOLDER_NAME = "xinlanedit";
    private static String string = "tietu" + System.currentTimeMillis() + ".jpg";

    public static File genEditFile(){
        File folder = Environment.getExternalStorageDirectory();
        if (folder != null) {
            if (folder.exists()) {
                File file = new File(folder, string);
                return file;
            }
        }
        return null;
    }


    public static File createFolders() {
        File baseDir;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            baseDir = Environment.getExternalStorageDirectory();
        } else {
            baseDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        }
        if (baseDir == null)
            return Environment.getExternalStorageDirectory();

        File aviaryFolder = new File(baseDir, FOLDER_NAME);
        if (aviaryFolder.exists())
            return aviaryFolder;

        if (aviaryFolder.isFile())
            aviaryFolder.delete();

        if (aviaryFolder.mkdirs())
            return aviaryFolder;
        return Environment.getExternalStorageDirectory();
    }
}
