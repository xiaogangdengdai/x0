package com.ssx.spa.view.wearther;

import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {
    static String apikey = "eddb9c523c6f94cab8fa12e65e80a324";
    static String httpUrl = "http://apis.baidu.com/apistore/weatherservice/cityname";

    public static String request(String httpArg) {
        StringBuffer sbf = new StringBuffer();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(httpUrl + "?cityid=" + httpArg).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", apikey);
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), AsyncHttpResponseHandler.DEFAULT_CHARSET));
            while (true) {
                BufferedReader bufferedReader;
                try {
                    String strRead = reader.readLine();
                    if (strRead == null) {
                        reader.close();
                        bufferedReader = reader;
                        return sbf.toString();
                    }
                    sbf.append(strRead);
                    sbf.append("\r\n");
                } catch (Exception e) {
                    bufferedReader = reader;
                    return null;
                }
            }
        } catch (Exception e2) {
            return null;
        }
    }
}
