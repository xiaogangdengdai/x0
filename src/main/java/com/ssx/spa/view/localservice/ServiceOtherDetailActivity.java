package com.ssx.spa.view.localservice;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.Ad;
import com.ssx.spa.javabean.AdDetail;
import com.ssx.spa.javabean.siList;
import com.ssx.spa.util.AdGo;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.common.FULL;
import java.util.List;

public class ServiceOtherDetailActivity extends BaseActivity {
    private Ad ad;
    private ImageButton back;
    private boolean c;
    private int currt;
    private Myapplication myapplication;
    private ImageView serviceotherdetail_img;
    private TextView serviceotherdetail_top;
    private VideoView serviceotherdetail_video;
    private WebView serviceotherdetail_web;
    private siList siList;
    private String top;
    private TextView webserviceotherdetail_txt;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            ServiceOtherDetailActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceotherdetail);
        this.myapplication = (Myapplication) getApplication();
        this.siList = (siList) getIntent().getSerializableExtra("siList");
        this.top = getIntent().getStringExtra("top");
        initview();
        setvalue();
    }

    private void setvalue() {
        try {
            setad();
            this.webserviceotherdetail_txt.setText(this.siList.getName());
            this.serviceotherdetail_web.loadDataWithBaseURL(null, getUrl(this.siList.getDescription()), "text/html", "utf-8", null);
        } catch (Exception e) {
        }
    }

    private void initview() {
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.serviceotherdetail_top = (TextView) findViewById(R.id.serviceotherdetail_top);
        this.serviceotherdetail_top.setText(this.top + "/" + this.siList.getName());
        this.serviceotherdetail_img = (ImageView) findViewById(R.id.serviceotherdetail_img);
        this.serviceotherdetail_video = (VideoView) findViewById(R.id.serviceotherdetail_video);
        FULL.star(this.serviceotherdetail_video);
        this.webserviceotherdetail_txt = (TextView) findViewById(R.id.webserviceotherdetail_txt);
        this.serviceotherdetail_web = (WebView) findViewById(R.id.serviceotherdetail_web);
        this.serviceotherdetail_web.setBackgroundColor(0);
    }

    private String getUrl(String url) {
        return "<html><head><title></title><meta http-equiv=\"content-type\"content=\"text/html;charset=utf-8\"></head><body>" + url + "</body></html>";
    }

    private void setad() {
        try {
            this.currt = 0;
            this.ad = this.siList.getAd();
            switch (this.ad.getContentType()) {
                case 1:
                    System.out.println("图片广告开始");
                    new AdGo(getApplicationContext(), this.serviceotherdetail_img).buildImage(this.ad.getDetails());
                    return;
                case 2:
                    System.out.println("视频广告开始");
                    this.serviceotherdetail_img.setVisibility(8);
                    this.serviceotherdetail_video.setVisibility(0);
                    adpaly(this.ad.getDetails());
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            if (!this.c) {
                this.c = true;
                setad();
            }
        }
        if (!this.c) {
            this.c = true;
            setad();
        }
    }

    private void adpaly(List<AdDetail> list) {
        if (this.currt < list.size()) {
            try {
                this.serviceotherdetail_video.setVideoURI(Uri.parse(((AdDetail) list.get(this.currt)).getPath()));
                System.out.println(((AdDetail) list.get(this.currt)).getPath());
                this.serviceotherdetail_video.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        ServiceOtherDetailActivity.this.serviceotherdetail_video.start();
                    }
                });
            } catch (Exception e) {
            }
            this.serviceotherdetail_video.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    MyToast.makeshow(ServiceOtherDetailActivity.this, "视频广告无法播放！", 0);
                    return true;
                }
            });
            this.serviceotherdetail_video.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    ServiceOtherDetailActivity serviceOtherDetailActivity = ServiceOtherDetailActivity.this;
                    serviceOtherDetailActivity.currt = serviceOtherDetailActivity.currt + 1;
                    ServiceOtherDetailActivity.this.adpaly(ServiceOtherDetailActivity.this.ad.getDetails());
                }
            });
        } else if (this.currt >= list.size() && list.size() > 0) {
            this.currt = 0;
            adpaly(this.ad.getDetails());
        }
    }
}
