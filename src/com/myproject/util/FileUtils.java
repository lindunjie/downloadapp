package com.myproject.util;

import java.io.File;

public class FileUtils {
    /**
     * getting file size
     * @param path
     * @return
     */

    public static long getFileContextLength(String path){
        File file = new File(path);
        return file.exists() && file.isFile() ? file.length() : 0;
    }
}
