package com.ssx.spa.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.ssx.spa.javabean.AdDetail;
import com.ssx.spa.javabean.details;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdGo {
    private Context context;
    private Handler handler = new Handler();
    private View v;

    public AdGo(Context context, View v) {
        this.context = context;
        this.v = v;
    }

    public void buildImage(final List<AdDetail> details) {
        if (!details.isEmpty()) {
            new Thread(new Runnable() {
                public void run() {
                    final List<Bitmap> imgs = new ArrayList();
                    final Map<Bitmap, Integer> imagesIntervalMap = new HashMap();
                    for (AdDetail detail : details) {
                        Bitmap loadImage = ImageUtil.loadImage(AdGo.this.context, detail.getPath());
                        if (loadImage != null) {
                            imgs.add(loadImage);
                            imagesIntervalMap.put(loadImage, Integer.valueOf(detail.getInter() * 1000));
                        }
                    }
                    if (!imgs.isEmpty()) {
                        AdGo.this.handler.post(new Runnable() {
                            public void run() {
                                AnimationDrawable ani = new AnimationDrawable();
                                for (Bitmap bm : imgs) {
                                    ani.addFrame(new BitmapDrawable(AdGo.this.context.getResources(), bm), ((Integer) imagesIntervalMap.get(bm)).intValue());
                                }
                                ani.setOneShot(false);
                                AdGo.this.v.setBackgroundDrawable(ani);
                                ani.start();
                            }
                        });
                    }
                }
            }).start();
        }
    }

    public void buildImage2(final List<details> details) {
        if (!details.isEmpty()) {
            new Thread(new Runnable() {
                public void run() {
                    final List<Bitmap> imgs = new ArrayList();
                    final Map<Bitmap, Integer> imagesIntervalMap = new HashMap();
                    for (details detail : details) {
                        Bitmap loadImage = ImageUtil.loadImage(AdGo.this.context, detail.getPath());
                        if (loadImage != null) {
                            imgs.add(loadImage);
                            imagesIntervalMap.put(loadImage, Integer.valueOf(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT));
                        }
                    }
                    if (!imgs.isEmpty()) {
                        AdGo.this.handler.post(new Runnable() {
                            public void run() {
                                AnimationDrawable ani = new AnimationDrawable();
                                for (Bitmap bm : imgs) {
                                    ani.addFrame(new BitmapDrawable(AdGo.this.context.getResources(), bm), ((Integer) imagesIntervalMap.get(bm)).intValue());
                                }
                                ani.setOneShot(false);
                                AdGo.this.v.setBackgroundDrawable(ani);
                                ani.start();
                            }
                        });
                    }
                }
            }).start();
        }
    }
}
