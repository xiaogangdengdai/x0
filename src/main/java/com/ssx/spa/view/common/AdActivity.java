package com.ssx.spa.view.common;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.VideoView;
import com.ssx.spa.R;
import com.ssx.spa.javabean.Ad;
import com.ssx.spa.javabean.AdDetail;
import com.ssx.spa.util.AdGo;
import com.ssx.spa.view.BaseActivity;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdActivity extends BaseActivity {
    private Ad ad;
    private ImageView ad_img;
    private VideoView ad_video;
    private int adtimes;
    private int currt = 0;
    private int start;
    private Timer timer2;
    private String url;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        try {
            this.url = getIntent().getStringExtra("url");
            this.ad = (Ad) getIntent().getSerializableExtra("ad");
        } catch (Exception e) {
        }
        initview();
        setvalue();
    }

    private void adpaly(List<AdDetail> list) {
        if (this.currt < list.size()) {
            try {
                this.ad_video.setVideoURI(Uri.parse(((AdDetail) list.get(this.currt)).getPath()));
                System.out.println(((AdDetail) list.get(this.currt)).getPath());
                this.ad_video.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        AdActivity.this.ad_video.start();
                    }
                });
                this.ad_video.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return true;
                    }
                });
                this.ad_video.setOnCompletionListener(new OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        AdActivity adActivity = AdActivity.this;
                        adActivity.currt = adActivity.currt + 1;
                        AdActivity.this.adpaly(AdActivity.this.ad.getDetails());
                    }
                });
            } catch (Exception e) {
            }
        } else if (this.currt >= list.size() && list.size() > 0) {
            startActivity(new Intent(this, VideoViewActivity.class).putExtra("url", this.url));
        }
    }

    private void setad() {
        if (this.ad.getContentType() == 1) {
            this.ad_img.setVisibility(0);
            this.ad_video.setVisibility(8);
            System.out.println("图片广告开始");
            new AdGo(getApplicationContext(), this.ad_img).buildImage(this.ad.getDetails());
        } else if (this.ad.getContentType() == 2) {
            System.out.println("视频广告开始");
            this.ad_img.setVisibility(8);
            this.ad_video.setVisibility(0);
            adpaly(this.ad.getDetails());
        }
    }

    private void setvalue() {
        int i = 0;
        while (i < this.ad.getDetails().size()) {
            try {
                this.adtimes = ((AdDetail) this.ad.getDetails().get(i)).getInter() + this.adtimes;
                i++;
            } catch (Exception e) {
                return;
            }
        }
        System.out.println(this.adtimes);
        setad();
        this.timer2 = new Timer();
        this.timer2.schedule(new TimerTask() {
            public void run() {
                AdActivity adActivity = AdActivity.this;
                adActivity.start = adActivity.start + 1;
                if (AdActivity.this.adtimes == 0) {
                    AdActivity.this.startActivity(new Intent(AdActivity.this, VideoViewActivity.class).putExtra("url", AdActivity.this.url));
                } else if (AdActivity.this.adtimes > 0 && AdActivity.this.adtimes == AdActivity.this.start) {
                    AdActivity.this.startActivity(new Intent(AdActivity.this, VideoViewActivity.class).putExtra("url", AdActivity.this.url));
                }
            }
        }, 0, 1000);
    }

    private void initview() {
        this.ad_img = (ImageView) findViewById(R.id.ad_img);
        this.ad_video = (VideoView) findViewById(R.id.ad_video);
        FULL.star(this.ad_video);
    }

    protected void onStop() {
        try {
            System.out.println("onStop");
            finish();
            this.timer2.cancel();
            if (this.ad_video.isPlaying()) {
                this.ad_video.stopPlayback();
            }
        } catch (Exception e) {
        }
        super.onStop();
    }

    protected void onDestroy() {
        System.out.println("onDestroy");
        super.onDestroy();
    }
}
