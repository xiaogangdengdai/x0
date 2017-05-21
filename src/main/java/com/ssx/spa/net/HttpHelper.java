package com.ssx.spa.net;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mstar.android.tv.TvLanguage;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import org.apache.http.Header;

public class HttpHelper {
    private static AsyncHttpClient asynClient = new AsyncHttpClient();
    private static SyncHttpClient syncHttpClient = new SyncHttpClient();

    static class RequestResult {
        public String content;
        public Throwable e;
        public int status = 0;

        RequestResult() {
        }
    }

    static class AnonymousClass1 extends TextHttpResponseHandler {
        private final /* synthetic */ RequestResult val$r;

        AnonymousClass1(RequestResult requestResult) {
            this.val$r = requestResult;
        }

        @Override
        public void onSuccess(int status, Header[] arg1, String responseString) {
            this.val$r.content = responseString;
            this.val$r.status = status;
        }
        @Override
        public void onFailure(int status, Header[] arg1, String arg2, Throwable e) {
            e.printStackTrace();
            this.val$r.content = arg2;
            this.val$r.status = status;
            this.val$r.e = e;
        }
    }

    static {
        asynClient.setTimeout(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT);
        syncHttpClient.setTimeout(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT);
        syncHttpClient.setUserAgent("Mozilla/5.0 (Windows NT 5.1; rc:30.0) Gccko/20100101 Firefox/30.0");
        asynClient.setUserAgent("Mozilla/5.0 (Windows NT 5.1; rc:30.0) Gccko/20100101 Firefox/30.0");
    }

    public static void getStringAsync(Context context, String urlString, RequestParams params, TextHttpResponseHandler handler) {
        asynClient.get(context, urlString, params, handler);
    }

    public static RequestHandle getBinary(Context context, String url, AsyncHttpResponseHandler hander) {
        return asynClient.get(context, url, (ResponseHandlerInterface) hander);
    }

    public static void postAsync(Context context, String url, RequestParams params, TextHttpResponseHandler handler) {
        asynClient.post(context, url, params, handler);
    }

    public static String getStringSync(Context context, String url) throws Exception {
        RequestResult r = new RequestResult();
        syncHttpClient.get(url, new AnonymousClass1(r));
        if (r.status == TvLanguage.GUARANI && r.e == null) {
            return r.content;
        }
        throw new Exception(r.e);
    }
}
