package com.ssx.spa.view.common;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.view.BaseActivity;
import java.util.Timer;
import java.util.TimerTask;

public class LiuweiActivity extends BaseActivity {
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LiuweiActivity liuweiActivity = LiuweiActivity.this;
                    liuweiActivity.seconds = liuweiActivity.seconds - 1;
                    if (LiuweiActivity.this.seconds / 60 < 1) {
                        LiuweiActivity.this.txtTip.setText(new StringBuilder(String.valueOf(LiuweiActivity.this.seconds)).append("秒后自动解锁 (").append(LiuweiActivity.this.seconds2Str(LiuweiActivity.this.seconds)).append(")").toString());
                    } else {
                        LiuweiActivity.this.txtTip.setText(new StringBuilder(String.valueOf(LiuweiActivity.this.seconds / 60)).append("分钟后自动解锁 (").append(LiuweiActivity.this.seconds2Str(LiuweiActivity.this.seconds)).append(")").toString());
                    }
                    if (LiuweiActivity.this.seconds == 0) {
                        LiuweiActivity.this.updateTime.cancel();
                        LiuweiActivity.this.finish();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private int seconds;
    private TextView txtTip;
    private Timer updateTime = new Timer();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liuwei);
        try {
            this.seconds = Integer.parseInt(getIntent().getStringExtra("time")) * 60;
        } catch (Exception e) {
        }
        initview();
        setvalue();
    }

    private void setvalue() {
        this.updateTime.schedule(new TimerTask() {
            public void run() {
                LiuweiActivity.this.handler.sendEmptyMessage(0);
            }
        }, 0, 1000);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    private String seconds2Str(int seconds) {
        int minute = seconds / 60;
        int second = seconds % 60;
        if (minute < 10 && second < 10) {
            return "0" + minute + ":" + "0" + second;
        }
        if (minute < 10) {
            return "0" + minute + ":" + second;
        }
        if (second < 10) {
            return new StringBuilder(String.valueOf(minute)).append(":").append("0").append(second).toString();
        }
        return new StringBuilder(String.valueOf(minute)).append(":").append(second).toString();
    }

    private void initview() {
        this.txtTip = (TextView) findViewById(R.id.tip);
    }
}
