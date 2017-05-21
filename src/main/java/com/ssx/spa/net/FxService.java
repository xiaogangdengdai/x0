package com.ssx.spa.net;

import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.util.DensityUtil;

public class FxService extends Service {
    BroadcastReceiver bottomMenuRev = new BroadcastReceiver() {
        public void onReceive(Context cont, Intent inte) {
            int flag = inte.getIntExtra("flag", 0);
            if (flag == 1) {
                FxService.this.handler1.removeCallbacks(FxService.this.closeRunable);
                FxService.this.handler.post(FxService.this.openRunable);
            } else if (flag == 0) {
                FxService.this.handler.removeCallbacks(FxService.this.closeRunable);
                FxService.this.handler1.post(FxService.this.closeRunable);
            }
        }
    };
    Runnable closeRunable = new Runnable() {
        public void run() {
            FxService.this.mFloatView.setVisibility(4);
        }
    };
    private Handler handler;
    private Handler handler1;
    private LinearLayout mFloatLayout;
    private ImageButton mFloatView;
    private WindowManager mWindowManager;
    private Myapplication myapplication;
    Runnable openRunable = new Runnable() {
        public void run() {
            FxService.this.mFloatView.setVisibility(0);
        }
    };
    private int screenHeight;
    private int screenWidth;
    private float upx;
    private float upy;
    private LayoutParams wmParams;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.myapplication = (Myapplication) getApplication();
        showview();
        this.handler = new Handler(Looper.getMainLooper());
        this.handler1 = new Handler(Looper.getMainLooper());
        registerReceiver(this.bottomMenuRev, new IntentFilter("showfloat"));
    }

    private synchronized void showBottomMenu() {
        sendBroadcast(new Intent("showbottom"));
    }

    private void showview() {
        this.wmParams = new LayoutParams();
        Application application = getApplication();
        getApplication();
        this.mWindowManager = (WindowManager) application.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getMetrics(dm);
        this.screenWidth = dm.widthPixels;
        this.screenHeight = dm.heightPixels;
        this.wmParams.type = 2002;
        this.wmParams.format = 1;
        this.wmParams.flags = 8;
        this.wmParams.gravity = 51;
        this.wmParams.x = DensityUtil.px2dip(this, 1190.0f);
        this.wmParams.y = DensityUtil.px2dip(this, 80.0f);
        this.wmParams.width = -2;
        this.wmParams.height = -2;
        this.mFloatLayout = (LinearLayout) LayoutInflater.from(getApplication()).inflate(R.layout.float_layout, null);
        this.mWindowManager.addView(this.mFloatLayout, this.wmParams);
        this.mFloatView = (ImageButton) this.mFloatLayout.findViewById(R.id.float_id);
        this.mFloatLayout.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        this.mFloatView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        FxService.this.upx = event.getRawX() - ((float) FxService.this.wmParams.x);
                        FxService.this.upy = event.getRawY() - ((float) FxService.this.wmParams.y);
                        break;
                    case 1:
                        float upy2 = event.getRawY() - ((float) FxService.this.wmParams.y);
                        if (Math.abs(FxService.this.upx - (event.getRawX() - ((float) FxService.this.wmParams.x))) < 10.0f && Math.abs(FxService.this.upy - upy2) < 10.0f) {
                            if (FxService.this.myapplication.isShowbottom()) {
                                FxService.this.myapplication.setShowbottom(false);
                            } else {
                                FxService.this.myapplication.setShowbottom(true);
                            }
                            FxService.this.sendBroadcast(new Intent("showbottom"));
                            break;
                        }
                    case 2:
                        FxService.this.wmParams.x = (int) (event.getRawX() - FxService.this.upx);
                        FxService.this.wmParams.y = (int) (event.getRawY() - FxService.this.upy);
                        FxService.this.mWindowManager.updateViewLayout(FxService.this.mFloatLayout, FxService.this.wmParams);
                        break;
                }
                return false;
            }
        });
    }
}
