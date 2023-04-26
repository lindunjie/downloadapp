package com.myproject.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    /**
     * getting the HttpURLConnection object
     * @param url  file url address
     * @return
     */

    public  static HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL httpUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        ////// sent to file server  info

        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        return httpURLConnection;
    }

    /**
     * get the file name from url
     * @param url
     * @return
     */

    public static String getHttpFileName(String url){
        int index = url.lastIndexOf("/");
        return url.substring(index +1);
    }
}
