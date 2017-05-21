package com.ssx.spa.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ssx.spa.R;

public class ImageUtil {
    private static boolean isInit = false;
    private static DisplayImageOptions normalOptions = new Builder().showImageOnLoading((int) R.drawable.touming).showImageForEmptyUri((int) R.drawable.touming).showImageOnFail((int) R.drawable.touming).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).resetViewBeforeLoading(true).displayer(new FadeInBitmapDisplayer(100)).build();

    private ImageUtil() {
    }

    public static Bitmap loadImage(Context context, String uri) {
        initImageLoader(context);
        return ImageLoader.getInstance().loadImageSync(uri, normalOptions);
    }

    public static void displayImage(Context context, String uri, ImageView imageView) {
        initImageLoader(context);
        ImageLoader.getInstance().displayImage(uri, imageView, normalOptions);
    }

    public static void displayImage(Context context, String uri, ImageView imageView, DisplayImageOptions options) {
        initImageLoader(context);
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public static void displayImage(Context context, String uri, ImageView imageView, ImageLoadingListener listener) {
        initImageLoader(context);
        ImageLoader.getInstance().displayImage(uri, imageView, normalOptions, listener);
    }

    private static synchronized void initImageLoader(Context context) {
        synchronized (ImageUtil.class) {
            if (!isInit) {
                Context appContext = context.getApplicationContext();
                ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(appContext).memoryCacheExtraOptions(800, 600).threadPoolSize(5).threadPriority(3).denyCacheImageMultipleSizesInMemory().memoryCacheSize(10485760).diskCacheSize(209715200).tasksProcessingOrder(QueueProcessingType.FIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).imageDownloader(new BaseImageDownloader(appContext, AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT, 30000)).writeDebugLogs().build());
                isInit = true;
            }
        }
    }
}
