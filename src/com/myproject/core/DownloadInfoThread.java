package com.myproject.core;

import com.myproject.constant.Constant;

public class DownloadInfoThread implements Runnable{
    //total file size
    private long httpFileContextLength;
    //local file size which already download
    public double finishedSize;
    //current accumulated download size
    public volatile double downSize;
    //last time download file size
    public double preSize;


    public DownloadInfoThread(long httpFileContextLength) {
        this.httpFileContextLength = httpFileContextLength;
    }

    @Override
    public void run() {
        //COMPUTE total file size mb

        String httpFileSize = String.format("%.2f",httpFileContextLength / Constant.MB);

        int speed = (int) ( (downSize - preSize) / 1024d);
        preSize = downSize;

        //remaining file size
        double remainSize = httpFileContextLength - finishedSize - downSize;

        String remainTime = String.format("%.1f", remainSize /1024d / speed);

        String currentFileSize = String.format("%.2f", (downSize - finishedSize) /Constant.MB);

        String downInfo = String.format("downloaded %smb/%smb, speed %skb/s, remainning time %ss",
                currentFileSize, httpFileSize, speed, remainTime);

        System.out.println("\r");
        System.out.println(downInfo);
    }
}
