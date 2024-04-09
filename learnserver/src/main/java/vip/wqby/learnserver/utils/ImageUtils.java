package vip.wqby.learnserver.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ImageUtils {
    public static File convertFileByUrl(String url) {
        File file = null;

        URL urlfile;
        InputStream inputStream = null;
        OutputStream outputStream= null;
        try {
            file = File.createTempFile("wx_image", ".png");
            //下载
            urlfile = new URL(url);
            inputStream = urlfile.openStream();
            outputStream= new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
