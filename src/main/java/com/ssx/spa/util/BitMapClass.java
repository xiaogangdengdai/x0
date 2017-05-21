package com.ssx.spa.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import com.loopj.android.http.AsyncHttpClient;
import com.mstar.android.tv.TvLanguage;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitMapClass {
    private String ALBUM_PATH;

    public static Bitmap getBitmap(Context context, String path) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == TvLanguage.GUARANI) {
                return BitmapFactory.decodeStream(conn.getInputStream());
            }
        } catch (Exception e) {
        }
        return null;
    }

    public InputStream getImageStream(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setReadTimeout(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
        conn.setConnectTimeout(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == TvLanguage.GUARANI) {
            return conn.getInputStream();
        }
        return null;
    }

    public void saveFile(Bitmap bm, String fileName, String modifyTime) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(this.ALBUM_PATH + fileName)));
        bm.compress(CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
}
