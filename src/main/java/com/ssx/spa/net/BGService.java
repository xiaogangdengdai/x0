package com.ssx.spa.net;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.config.Config;
import com.ssx.spa.javabean.LiuYan;
import com.ssx.spa.javabean.MsgInfo;
import com.ssx.spa.view.msg.IScrollState;
import com.ssx.spa.view.msg.MarqueeToast;
import com.ssx.spa.view.msg.TextSurfaceView;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class BGService extends Service implements Runnable, IScrollState {
    private static final int PORT = 9999;
    private static final long SHOW_MSG_PERIOD = 10000;
    private TextSurfaceView Text;
    String cmd = "";
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BGService.this.showMessage();
                    return;
                default:
                    return;
            }
        }
    };
    private Timer heart = new Timer();
    BufferedReader in;
    private Handler keyhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        Runtime.getRuntime().exec(BGService.this.cmd);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                        break;
                    }
            }
            super.handleMessage(msg);
        }
    };
    private List<LiuYan> liuyan = new ArrayList();
    private ExecutorService mExecutorService = null;
    private List<String> messageList = new ArrayList();
    private int messageindex;
    String msg;
    private Myapplication myapplication;
    private boolean play;
    private ServerSocket server = null;
    private Socket socket = null;
    private Timer timermin = new Timer();
    private Timer timerscoend = new Timer();
    private MarqueeToast toast;
    boolean total_msg;

    public class SocketThread extends Thread {
        private Socket socket;

        public SocketThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            IOException e;
            Throwable th;
            DataInputStream in = null;
            try {
                DataInputStream in2 = new DataInputStream(this.socket.getInputStream());
                while (true) {
                    try {
                        String msg = in2.readUTF();
                        if (msg != null) {
                            BGService.this.keyevent(msg);
                        }
                    } catch (IOException e2) {
                        e = e2;
                        in = in2;
                    } catch (Throwable th2) {
                        th = th2;
                        in = in2;
                    }
                }
            } catch (IOException e3) {
                e = e3;
                try {
                    e.printStackTrace();
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            return;
                        }
                    }
                    if (this.socket != null) {
                        this.socket.close();
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e42) {
                            e42.printStackTrace();
                            th.printStackTrace();
                        }
                    }
                    if (this.socket != null) {
                        try {
                            this.socket.close();
                        } catch (IOException e4) {
                            e3.printStackTrace();
                        }

                    }
                    th.printStackTrace();
                }
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.myapplication = (Myapplication) getApplication();
        timerscoend();
        timermin();
    }

    private void timerscoend() {
        this.timerscoend.schedule(new TimerTask() {
            public void run() {
                try {
                    BGService.this.liuyan.clear();
                    BGService.this.liuyan = JsonResult.getliuyan(BGService.this, Myapplication.MAC);
                    if (BGService.this.liuyan.size() > 0 && BGService.this.liuyan.size() > BGService.this.myapplication.getLiuyannum()) {
                        BGService.this.myapplication.setLiuYans(JsonResult.getliuyan(BGService.this, Myapplication.MAC));
                        BGService.this.myapplication.setLiuyannum(BGService.this.liuyan.size());
                        BGService.this.sendBroadcast(new Intent().setAction("liuyan"));
                    }
                } catch (Exception e) {
                }
            }
        }, 5000, 120000);
    }

    private void timermin() {
        System.out.println("timermin");
        this.timermin.schedule(new TimerTask() {
            public void run() {
                new JsonResult(null, BGService.this.myapplication, BGService.this.handler).getmsg();
            }
        }, 0, 120000);
        this.heart.schedule(new TimerTask() {
            public void run() {
                try {
                    new JsonResult(null, BGService.this.myapplication, BGService.this.handler).keepheart();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }, 0, 180000);
    }

    public void showMessage() {
        try {
            if (!this.messageList.isEmpty()) {
                this.messageList.clear();
            }
            for (int i = 0; i < this.myapplication.getMsg().size(); i++) {
                this.messageList.add(((MsgInfo) this.myapplication.getMsg().get(i)).getContent());
            }
            if (this.messageList != null && !this.messageList.isEmpty()) {
                if (this.messageList.size() <= this.messageindex) {
                    this.messageindex = 0;
                }
                if (this.toast != null) {
                    this.toast.hid();
                }
                this.toast = new MarqueeToast(this);
                this.Text = new TextSurfaceView((Context) this, (IScrollState) this);
                this.Text.setFocusable(false);
                this.Text.setOrientation(1);
                if (((String) this.messageList.get(this.messageindex)).equals("") && this.messageList.get(this.messageindex) == null) {
                    this.Text.setContent("");
                }
                this.Text.setContent((String) this.messageList.get(this.messageindex));
                this.toast.setView(this.Text);
                if (getSharedPreferences(Config.first, 0).getInt(Config.screen, 1920) >= 1920) {
                    this.Text.setFontSize(34.0f);
                    this.toast.setGravity(83, 1920, 0, 0);
                    this.toast.setHeight(48);
                } else {
                    this.toast.setHeight(30);
                    this.toast.setGravity(83, 1280, 0, 0);
                }
                this.toast.show();
                this.messageindex++;
                if (this.messageList.size() <= this.messageindex && this.total_msg) {
                    this.total_msg = false;
                } else if (this.messageList.size() <= this.messageindex && !this.total_msg) {
                    this.total_msg = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    public void run() {
        this.handler.sendEmptyMessage(0);
    }

    public void start() {
    }

    public void stop() {
        this.Text.setLoop(false);
        Looper.prepare();
        new Handler().postDelayed(this, SHOW_MSG_PERIOD);
        Looper.loop();
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            this.timermin.cancel();
            this.timerscoend.cancel();
            this.heart.cancel();
            try {
                this.toast.hid();
            } catch (Exception e) {
            }
            System.out.println("stopservices");
            stopService(new Intent(this, BGService.class));
        } catch (Exception e2) {
        }
    }

    private void createsocket() {
        new Thread(new Runnable() {
            public void run() {
                BGService.this.Main();
            }
        }).start();
    }

    public void Main() {
        try {
            this.server = new ServerSocket(PORT);
            System.out.println("服务端已经启动，监听端口：9999");
            while (true) {
                this.socket = this.server.accept();
                new SocketThread(this.socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    private void keyevent(String msg) {
        System.out.println(msg);
        if (msg.equals("power")) {
            this.cmd = "input keyevent 26";
        } else if (msg.equals("nvol")) {
            this.cmd = "input keyevent 91";
        } else if (msg.equals("one")) {
            this.cmd = "input keyevent 8";
        } else if (msg.equals("two")) {
            this.cmd = "input keyevent 9";
        } else if (msg.equals("three")) {
            this.cmd = "input keyevent 10";
        } else if (msg.equals("four")) {
            this.cmd = "input keyevent 11";
        } else if (msg.equals("five")) {
            this.cmd = "input keyevent 12";
        } else if (msg.equals("six")) {
            this.cmd = "input keyevent 13";
        } else if (msg.equals("serven")) {
            this.cmd = "input keyevent 14";
        } else if (msg.equals("eight")) {
            this.cmd = "input keyevent 15";
        } else if (msg.equals("nine")) {
            this.cmd = "input keyevent 16";
        } else if (msg.equals("zero")) {
            this.cmd = "input keyevent 7";
        } else if (msg.equals("set")) {
            this.cmd = "input keyevent 176";
        } else if (msg.equals("delete")) {
            this.cmd = "input keyevent 67";
        } else if (msg.equals("home")) {
            this.cmd = "input keyevent 3";
        } else if (msg.equals("back")) {
            this.cmd = "input keyevent 4";
        } else if (msg.equals("menu")) {
            this.cmd = "input keyevent 82";
        } else if (!msg.equals("mouse")) {
            if (msg.equals("up")) {
                this.cmd = "input keyevent 19";
            } else if (msg.equals("down")) {
                this.cmd = "input keyevent 20";
            } else if (msg.equals("left")) {
                this.cmd = "input keyevent 21";
            } else if (msg.equals("right")) {
                this.cmd = "input keyevent 22";
            } else if (msg.equals("ok")) {
                this.cmd = "input keyevent 23";
            } else if (msg.equals("volup")) {
                this.cmd = "input keyevent 24";
            } else if (msg.equals("voldown")) {
                this.cmd = "input keyevent 25";
            } else if (msg.equals("play")) {
                this.cmd = "input keyevent 85";
            } else if (msg.equals("forward")) {
                this.cmd = "input keyevent 90";
            } else if (msg.equals("rewind")) {
                this.cmd = "input keyevent 89";
            } else if (msg.equals("stop")) {
                this.cmd = "input keyevent 86";
            }
        }
        this.keyhandler.sendEmptyMessage(0);
    }
}
