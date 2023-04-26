import com.myproject.core.Downloader;
import com.myproject.util.LogUtils;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = null;
        if (args == null || args.length ==0){
            for (;;){
                LogUtils.info("please enter the download url ");
                Scanner scanner = new Scanner(System.in);
                url = scanner.next();
                if (url != null){
                    break;
                }
            }
        }else {
            url = args[0];
        }

        Downloader downloader = new Downloader();
        downloader.download(url);
    }
}