package com.myproject.core;

import com.myproject.constant.Constant;
import com.myproject.util.FileUtils;
import com.myproject.util.HttpUtils;
import com.myproject.util.LogUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Downloader {

    public ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    public void download(String url){
        //get the file name
        String httpFileName = HttpUtils.getHttpFileName(url);

        DownloadInfoThread downloadInfoThread = null;

        //file download path
        httpFileName = Constant.PATH + httpFileName;

        //getting local file size
        long localFileLength = FileUtils.getFileContextLength(httpFileName);

        //get connection file

        HttpURLConnection httpURLConnection = null;

        try{
            httpURLConnection = HttpUtils.getHttpURLConnection(url);

            int contentLength = httpURLConnection.getContentLength();

            //check if it is download

            if (localFileLength >= contentLength){
                LogUtils.info("{}already downloaded", httpFileName);
                return;
            }

            downloadInfoThread = new DownloadInfoThread(contentLength);
            //delay 1 sec and run every sec for downloadinfothread
            scheduledExecutorService.scheduleAtFixedRate(downloadInfoThread,1,1, TimeUnit.SECONDS);
        }catch (IOException e){
            e.printStackTrace();
        }

        try(
                InputStream input = httpURLConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(input);
                FileOutputStream fos = new FileOutputStream(httpFileName);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                ){
            int len = -1;
            byte[] buffer = new byte[Constant.BYTE_SIZE];
            while ((len = bis.read(buffer)) != -1){
                downloadInfoThread.downSize += len;
                bos.write(buffer,0,len);
            }


        }catch (FileNotFoundException e){
            LogUtils.error("file does not exist{}", url);
        }catch (Exception e){
            LogUtils.error("fail to download");
        }finally {
            System.out.println("\r");
            System.out.println("finished");
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }


            //close
            scheduledExecutorService.shutdownNow();
        }

    }
}
