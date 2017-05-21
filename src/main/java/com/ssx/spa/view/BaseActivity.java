package com.ssx.spa.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.config.VolInfo;
import com.ssx.spa.javabean.LocalService;
import com.ssx.spa.view.dialog.VolDialog;
import com.ssx.spa.view.localservice.LiuYanActivity;
import java.util.Iterator;
import java.util.LinkedList;

public class BaseActivity extends Activity {
    public static LinkedList<Activity> allActivitys = new LinkedList();
    private final String SET = "222";
    private final String SETALL = "111";
    private final String SETVOL = "333";
    private final int TOSET = 273;
    private final String TOSETCHECK = "000";
    private String TOSETno = "";
    AudioManager audioManager;
    private AlertDialog dialogs;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if (BaseActivity.this.dialogs.isShowing()) {
                        BaseActivity.this.dialogs.dismiss();
                        return;
                    }
                    return;
                case 273:
                    System.out.println(BaseActivity.this.TOSETno);
                    if (BaseActivity.this.TOSETno.equals("000")) {
                        BaseActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
                    } else if (BaseActivity.this.TOSETno.equals("222")) {
                        BaseActivity.this.setip();
                    } else if (BaseActivity.this.TOSETno.equals("111")) {
                        BaseActivity.this.setallip();
                    } else if (BaseActivity.this.TOSETno.equals("333")) {
                        new VolDialog(BaseActivity.this.handler, BaseActivity.this).crt();
                    }
                    BaseActivity.this.TOSETno.length();
                    BaseActivity.this.TOSETno = "";
                    return;
                default:
                    return;
            }
        }
    };
    private Myapplication myapplication;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            System.out.println("收到一条新留言");
            AlertDialog dialog = new Builder(BaseActivity.this).create();
            dialog.setTitle("你有一条新留言");
            dialog.setMessage("是否查看?");
            dialog.setButton("确定", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < BaseActivity.this.myapplication.getLocalServices().size(); i++) {
                        if (((LocalService) BaseActivity.this.myapplication.getLocalServices().get(i)).getName().equals("留言信息")) {
                            BaseActivity.this.startActivity(new Intent(BaseActivity.this, LiuYanActivity.class).putExtra("top", ((LocalService) BaseActivity.this.myapplication.getLocalServices().get(i)).getName()));
                        }
                    }
                }
            });
            dialog.setButton2("取消", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog.show();
        }
    };
    private boolean shoupai;
    private String sp = "刷手牌已成功";
    long time;
    private int times;

    protected void onStart() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("liuyan");
        registerReceiver(this.receiver, filter);
        super.onStart();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.myapplication = (Myapplication) getApplication();
        allActivitys.add(this);
        this.audioManager = (AudioManager) getSystemService("audio");
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            allActivitys.remove(this);
            unregisterReceiver(this.receiver);
        } catch (Exception e) {
        }
    }

    public static void finishAll() {
        Iterator it = allActivitys.iterator();
        while (it.hasNext()) {
            ((Activity) it.next()).finish();
        }
        allActivitys.clear();
    }

    public static void finishone() {
        ((Activity) allActivitys.get(allActivitys.size() - 1)).finish();
        allActivitys.remove(allActivitys.size() - 1);
    }

    private void shoupai() {
        try {
            this.times = 0;
            this.dialogs = new Builder(this).create();
            this.dialogs.setMessage(this.sp);
            this.dialogs.show();
            this.handler.sendEmptyMessageDelayed(2, 3000);
        } catch (Exception e) {
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode - 7 >= 0 && keyCode - 7 <= 9) {
            this.TOSETno += (keyCode - 7);
            this.handler.sendEmptyMessageDelayed(273, 2000);
        }
        if (keyCode != 24 || this.audioManager.getStreamVolume(3) < VolInfo.getMaxVol(this)) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!event.equals(Integer.valueOf(0)) || this.audioManager.getStreamVolume(3) < VolInfo.getMaxVol(this)) {
            return super.onTouchEvent(event);
        }
        return true;
    }

    private void setip() {
        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);
        inputServer.setText(getServer(this));
        Builder builder = new Builder(this);
        builder.setView(inputServer);
        builder.setTitle("预设服务器ip").setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (inputServer.getText() == null || inputServer.getText().toString().trim().equals("")) {
                    MyToast.makeshow(BaseActivity.this, "请输入服务器IP以及端口！", 0);
                    return;
                }
                Editor editor = BaseActivity.this.getSharedPreferences("ip", 0).edit();
                editor.putString("ip", inputServer.getText().toString());
                editor.commit();
                MyToast.makeshow(BaseActivity.this, "设置成功！", 0);
                BaseActivity.finishAll();
                BaseActivity.this.restartApplication();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void setallip() {
        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);
        inputServer.setText(getALLServer(this));
        Builder builder = new Builder(this);
        builder.setView(inputServer);
        builder.setTitle("预设总服务器ip").setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (inputServer.getText() == null || inputServer.getText().toString().trim().equals("")) {
                    MyToast.makeshow(BaseActivity.this, "请输入服务器IP以及端口！", 0);
                    return;
                }
                Editor editor = BaseActivity.this.getSharedPreferences("ip", 0).edit();
                editor.putString("allip", inputServer.getText().toString());
                editor.commit();
                MyToast.makeshow(BaseActivity.this, "设置成功！", 0);
                BaseActivity.finishAll();
                BaseActivity.this.restartApplication();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public static String getServer(Context context) {
        return context.getSharedPreferences("ip", 0).getString("ip", Myapplication.DEFAULT_IP);
    }

    public static String getALLServer(Context context) {
        return context.getSharedPreferences("ip", 0).getString("allip", Myapplication.ALLSERVER_IP);
    }

    protected void onRestart() {
        super.onRestart();
    }

    private void restartApplication() {
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(67108864);
        startActivity(intent);
    }
}
