package com.ssx.spa.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.config.Config;
import com.ssx.spa.javabean.Ad;
import com.ssx.spa.javabean.AdDetail;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.util.AdGo;
import com.ssx.spa.util.NetworkUtil;
import com.ssx.spa.view.common.FULL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private Ad ad;
    private int adtimes = 0;
    private boolean c;
    private int connecttimes = 10;
    private int currt;
    private ProgressDialog dialog;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WelcomeActivity.this.getad();
                    return;
                case 2:
                    System.out.println("网络连接失败");
                    MyToast.makeshow(WelcomeActivity.this, "网络连接失败！", 0);
                    WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    return;
                case 100:
                    int i = 0;
                    while (i < ((Ad) WelcomeActivity.this.myapplication.getAds().get(0)).getDetails().size()) {
                        try {
                            WelcomeActivity welcomeActivity = WelcomeActivity.this;
                            welcomeActivity.adtimes = ((AdDetail) ((Ad) WelcomeActivity.this.myapplication.getAds().get(0)).getDetails().get(i)).getInter() + welcomeActivity.adtimes;
                            i++;
                        } catch (Exception e) {
                            return;
                        }
                    }
                    WelcomeActivity.this.setad();
                    System.out.println("广告时间：" + WelcomeActivity.this.adtimes);
                    WelcomeActivity.this.handler.sendEmptyMessageDelayed(101, (long) (WelcomeActivity.this.adtimes * 1000));
                    return;
                case 101:
                    WelcomeActivity.this.finish();
                    WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    return;
                default:
                    return;
            }
        }
    };
    private Myapplication myapplication;
    private Timer timer = new Timer();
    private LinearLayout welcomeLayout;
    private RelativeLayout welcome_ad;
    private VideoView welcome_video;

    protected void onStart() {
        super.onStart();
        JsonResult.setHost(getServer(this));
        JsonResult.setAllHost(getALLServer(this));
    }

    public static String getServer(Context context) {
        return context.getSharedPreferences("ip", 0).getString("ip", Myapplication.DEFAULT_IP);
    }

    public static String getALLServer(Context context) {
        return context.getSharedPreferences("ip", 0).getString("allip", Myapplication.ALLSERVER_IP);
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.myapplication = (Myapplication) getApplication();
        super.onCreate(savedInstanceState);
        Config.firststart(this);
        setContentView(R.layout.activity_welcome);
        initview();
        netconnect();
    }

    public boolean isNetworkConnected() {
        if (NetworkUtil.isConnected(this)) {
            return true;
        }
        return false;
    }

    private void netconnect() {
        this.dialog = new ProgressDialog(this);
        this.dialog.setCancelable(false);
        this.dialog.setMessage("网络连接中...");
        this.dialog.show();
        this.timer.schedule(new TimerTask() {
            public void run() {
                try {
                    WelcomeActivity welcomeActivity = WelcomeActivity.this;
                    welcomeActivity.connecttimes = welcomeActivity.connecttimes - 1;
                    if (WelcomeActivity.this.isNetworkConnected()) {
                        WelcomeActivity.this.timer.cancel();
                        WelcomeActivity.this.dialog.dismiss();
                        WelcomeActivity.this.handler.sendEmptyMessage(1);
                        MyToast.makeshow(WelcomeActivity.this, "网络已连接！", 0);
                    } else if (WelcomeActivity.this.connecttimes == 0) {
                        WelcomeActivity.this.timer.cancel();
                        WelcomeActivity.this.dialog.dismiss();
                        WelcomeActivity.this.handler.sendEmptyMessage(2);
                    }
                } catch (Exception e) {
                }
            }
        }, 0, 2000);
    }

    public void getad() {
        new JsonResult(this, this.myapplication, this.handler).getad();
        new JsonResult(this, this.myapplication, this.handler).online();
    }

    private void initview() {
        this.welcome_ad = (RelativeLayout) findViewById(R.id.welcome_ad);
        this.welcome_video = (VideoView) findViewById(R.id.welcome_video);
        FULL.star(this.welcome_video);
    }

    private void adpaly(List<AdDetail> list) {
        if (this.currt < list.size()) {
            try {
                this.welcome_video.setVideoURI(Uri.parse(((AdDetail) list.get(this.currt)).getPath()));
                System.out.println(((AdDetail) list.get(this.currt)).getPath());
                this.welcome_video.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        WelcomeActivity.this.welcome_video.start();
                    }
                });
                this.welcome_video.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        MyToast.makeshow(WelcomeActivity.this, "视频广告无法播放,请联系管理人员！", 0);
                        return true;
                    }
                });
                this.welcome_video.setOnCompletionListener(new OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        WelcomeActivity welcomeActivity = WelcomeActivity.this;
                        welcomeActivity.currt = welcomeActivity.currt + 1;
                        WelcomeActivity.this.adpaly(WelcomeActivity.this.ad.getDetails());
                    }
                });
            } catch (Exception e) {
            }
        } else if (this.currt >= list.size() && list.size() > 0) {
            this.currt = 0;
            adpaly(this.ad.getDetails());
        }
    }

    private void setad() {
        try {
            this.currt = 0;
            this.ad = (Ad) this.myapplication.getAds().get(0);
            switch (this.ad.getContentType()) {
                case 1:
                    System.out.println("图片广告开始");
                    new AdGo(getApplicationContext(), this.welcome_ad).buildImage(this.ad.getDetails());
                    return;
                case 2:
                    System.out.println("视频广告开始");
                    this.welcome_video.setVisibility(0);
                    adpaly(this.ad.getDetails());
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
