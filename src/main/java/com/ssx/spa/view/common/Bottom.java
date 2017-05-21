package com.ssx.spa.view.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.MainActivity;

public class Bottom extends LinearLayout implements OnClickListener {
    AudioManager audioManager;
    private ImageButton backs;
    LinearLayout bottom_tools;
    Context context;
    int currentvol;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -99:
                    System.out.println("ssssssssssssss");
                    break;
                case 1:
                    try {
                        Bottom.this.main_roomno.setText(Bottom.this.myapplication.getRootInfo().getUserName());
                        break;
                    } catch (Exception e) {
                        break;
                    }
            }
            super.handleMessage(msg);
        }
    };
    private ImageButton home;
    private TextView main_roomno;
    private Myapplication myapplication;
    BroadcastReceiver showbottomtools = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        }
    };
    long th = 0;
    View view;
    private ImageButton voldown;
    private ImageButton volup;

    public Bottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.view = LayoutInflater.from(getContext()).inflate(R.layout.bottom, this);
        this.myapplication = (Myapplication) context.getApplicationContext();
        initview();
    }

    private void initview() {
        this.audioManager = (AudioManager) this.context.getSystemService("audio");
        this.main_roomno = (TextView) this.view.findViewById(R.id.main_roomno);
        this.voldown = (ImageButton) this.view.findViewById(R.id.voldown);
        this.backs = (ImageButton) this.view.findViewById(R.id.backs);
        this.home = (ImageButton) this.view.findViewById(R.id.home);
        this.volup = (ImageButton) this.view.findViewById(R.id.volup);
        this.voldown.setOnClickListener(this);
        this.backs.setOnClickListener(this);
        this.home.setOnClickListener(this);
        this.volup.setOnClickListener(this);
        this.bottom_tools = (LinearLayout) this.view.findViewById(R.id.bottom_tools);
        this.bottom_tools.setVisibility(8);
        try {
            this.handler.sendEmptyMessageDelayed(1, 500);
        } catch (Exception e) {
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                this.context.startActivity(new Intent(this.context, MainActivity.class));
                return;
            case R.id.voldown:
                this.audioManager.adjustStreamVolume(3, -1, 1);
                return;
            case R.id.backs:
                if (BaseActivity.allActivitys.size() > 1) {
                    BaseActivity.finishone();
                    return;
                }
                MyToast.makeshow(this.context, "已经是最后一页了", 0);
                this.th = System.currentTimeMillis();
                return;
            case R.id.volup:
                this.audioManager.adjustStreamVolume(3, 1, 1);
                return;
            default:
                return;
        }
    }
}
