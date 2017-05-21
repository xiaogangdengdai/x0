package com.ssx.spa.view.live;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.mstar.android.tv.TvLanguage;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.config.VolInfo;
import com.ssx.spa.javabean.HeadSwitch;
import com.ssx.spa.javabean.SingelLives;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.MainActivity;
import com.ssx.spa.view.common.BottomMenuView;
import com.ssx.spa.view.common.FULL;
import com.ssx.spa.view.common.LiuweiActivity;
import com.ssx.spa.view.common.VideoBars;
import com.ssx.spa.view.localservice.FoodActivity;
import com.ssx.spa.view.localservice.JishiActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LiveActivity extends BaseActivity {
    private List<SingelLives> Lives;
    private Myapplication application;
    ArrayAdapter arr_adapter;
    private ImageButton back;
    private SeekBar bar;
    private VideoBars bars;
    private BroadcastReceiver bottomMenuRev = new BroadcastReceiver() {
        public void onReceive(Context cont, Intent inte) {
        }
    };
    private BottomMenuView bottomview;
    private boolean c = false;
    private int currentVolume;
    List<String> data_list;
    private AlertDialog dilog;
    private Editor editor;
    private ImageButton fodBtn;
    Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    try {
                        if (LiveActivity.this.dilog != null && LiveActivity.this.dilog.isShowing()) {
                            LiveActivity.this.dilog.dismiss();
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        return;
                    }
                case 2:
                    LiveActivity.this.c = false;
                    LiveActivity.this.tai_num.setText("");
                    try {
                        int tmps = Integer.parseInt(LiveActivity.this.tmpNum);
                        if (tmps <= 0 || tmps >= LiveActivity.this.Lives.size()) {
                            MyToast.makeshow(LiveActivity.this, "没有找到该频道！", 0);
                            LiveActivity.this.tmpNum = "";
                            return;
                        }
                        LiveActivity.this.init(((SingelLives) LiveActivity.this.Lives.get(tmps - 1)).getLivemanagementaddress());
                        LiveActivity.this.n = tmps - 1;
                        LiveActivity.this.tmpNum = "";
                        return;
                    } catch (Exception e2) {
                    }
                    break;
                case 3:
                    LiveActivity.this.tai_num.setText("");
                    return;
                case 4:
                    try {
                        LiveActivity.this.programListView.setSelectionFromTop(LiveActivity.this.Lives.size() - 1, TvLanguage.GUARANI);
                        return;
                    } catch (Exception e3) {
                        return;
                    }
                case 5:
                    LiveActivity.this.programListView.setSelection(0);
                    return;
                case 22:
                    Toast.makeText(LiveActivity.this, new StringBuilder(String.valueOf(LiveActivity.this.player.getBufferPercentage())).append("ssssssssss").toString(), 0).show();
                    return;
                case 23:
                    LiveActivity.this.init(((SingelLives) LiveActivity.this.Lives.get(LiveActivity.this.n)).getLivemanagementaddress());
                    return;
                default:
                    return;
            }
        }
    };
    private ImageButton hujiaoBtn;
    private ImageButton jinyin;
    private ImageButton jinyins;
    private ImageButton jishiBtn;
    private ImageButton liuzuoBtn;
    private ImageButton liveback;
    private AudioManager mAudioManager;
    private int maxVolume;
    private int mode;
    private int n = 0;
    private VideoView player;
    private View popView;
    private ListView programListView;
    private ImageButton shangzhongBtn;
    private SharedPreferences sp;
    private TextView tai_num;
    int time1;
    int time2;
    private Timer timer1 = new Timer(true);
    private Timer timer2 = new Timer(true);
    int times19;
    int times20;
    private String tmpNum = "";
    private String url = "";
    private TextView vol_txt;
    private TextView volnum;
    private ImageButton xiazhongBtn;

    class Btn implements OnClickListener {
        Btn() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.jishiBtn:
                    LiveActivity.this.startActivity(new Intent(LiveActivity.this, JishiActivity.class));
                    return;
                case R.id.fodBtn:
                    LiveActivity.this.startActivity(new Intent(LiveActivity.this, FoodActivity.class));
                    return;
                case R.id.liuzuoBtn:
                    LiveActivity.this.liuzuo();
                    return;
                case R.id.hujiaoBtn:
                    MyToast.makeshow(LiveActivity.this, "呼叫成功！", 0);
                    new Call().execute(new String[0]);
                    return;
                case R.id.liveback:
                    LiveActivity.this.finish();
                    return;
                default:
                    return;
            }
        }
    }

    class Call extends AsyncTask<String, String, String> {
        Call() {
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        protected String doInBackground(String... params) {
            try {
                JsonResult.call(LiveActivity.this, Myapplication.MAC);
            } catch (Exception e) {
            }
            return null;
        }
    }

    class ListAdapter extends BaseAdapter {
        Context context;
        List<SingelLives> livemanagements;

        public ListAdapter(Context context, List<SingelLives> livemanagements) {
            this.context = context;
            this.livemanagements = livemanagements;
        }

        public int getCount() {
            try {
                return this.livemanagements.size();
            } catch (Exception e) {
                return 0;
            }
        }

        public Object getItem(int position) {
            return Integer.valueOf(this.livemanagements.size());
        }

        public long getItemId(int position) {
            return (long) this.livemanagements.size();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(this.context).inflate(R.layout.tv_list_item, null);
            LinearLayout listItem = (LinearLayout) view.findViewById(R.id.listItems);
            TextView t = (TextView) view.findViewById(R.id.txts);
            t.setSingleLine(true);
            t.setText(((SingelLives) this.livemanagements.get(position)).getLivemanagementname());
            return view;
        }
    }

    public void liuzuo() {
        Builder dialog = new Builder(this);
        dialog.setTitle("留座");
        dialog.setMessage("请选择留座的时间(单位：分钟)");
        final Spinner spinner = new Spinner(this);
        this.data_list = new ArrayList();
        this.data_list.add("5");
        this.data_list.add("10");
        this.data_list.add("15");
        this.arr_adapter = new ArrayAdapter(this, 17367048, this.data_list);
        this.arr_adapter.setDropDownViewResource(17367049);
        spinner.setAdapter(this.arr_adapter);
        dialog.setView(spinner);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    LiveActivity.this.startActivity(new Intent(LiveActivity.this, LiuweiActivity.class).putExtra("time", (String) LiveActivity.this.data_list.get(spinner.getSelectedItemPosition())));
                } catch (Exception e) {
                }
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.create().show();
    }

    private void iniview() {
        this.bottomview = (BottomMenuView) this.dilog.findViewById(R.id.bottomview);
        this.jinyin = (ImageButton) this.bottomview.findViewById(R.id.jinyin);
        this.bar = (SeekBar) this.bottomview.findViewById(R.id.bars);
        this.volnum = (TextView) this.bottomview.findViewById(R.id.volnum);
        this.jishiBtn = (ImageButton) this.bottomview.findViewById(R.id.jishiBtn);
        this.fodBtn = (ImageButton) this.bottomview.findViewById(R.id.fodBtn);
        this.liuzuoBtn = (ImageButton) this.bottomview.findViewById(R.id.liuzuoBtn);
        this.hujiaoBtn = (ImageButton) this.bottomview.findViewById(R.id.hujiaoBtn);
        int i = 0;
        while (i < this.application.getHeadSwitchs().size()) {
            try {
                if (((HeadSwitch) this.application.getHeadSwitchs().get(i)).getId() == 1 && ((HeadSwitch) this.application.getHeadSwitchs().get(i)).getStatus() == 1) {
                    this.liuzuoBtn.setVisibility(0);
                }
                if (((HeadSwitch) this.application.getHeadSwitchs().get(i)).getId() == 2 && ((HeadSwitch) this.application.getHeadSwitchs().get(i)).getStatus() == 1) {
                    this.hujiaoBtn.setVisibility(0);
                }
                i++;
            } catch (Exception e) {
            }
        }
        if (this.application.Iscall()) {
            this.hujiaoBtn.setImageResource(R.drawable.call_cancle_vf);
        } else {
            this.hujiaoBtn.setImageResource(R.drawable.bottom_hujiao);
        }
        this.shangzhongBtn = (ImageButton) this.bottomview.findViewById(R.id.shangzhongBtn);
        this.xiazhongBtn = (ImageButton) this.bottomview.findViewById(R.id.xiazhongBtn);
        this.jishiBtn.setOnClickListener(new Btn());
        this.fodBtn.setOnClickListener(new Btn());
        this.liuzuoBtn.setOnClickListener(new Btn());
        this.hujiaoBtn.setOnClickListener(new Btn());
        this.shangzhongBtn.setOnClickListener(new Btn());
        this.xiazhongBtn.setOnClickListener(new Btn());
        this.jinyin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LiveActivity.this.bar.setProgress(0);
                LiveActivity.this.mAudioManager.setStreamVolume(3, 0, 0);
            }
        });
        this.bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LiveActivity.this.mAudioManager.setStreamVolume(3, LiveActivity.this.bar.getProgress(), 0);
                LiveActivity.this.volnum.setText(new StringBuilder(String.valueOf(LiveActivity.this.bar.getProgress())).toString());
            }
        });
    }

    protected void onStart() {
        sendBroadcast(new Intent("showfloat").putExtra("flag", 1));
        super.onStart();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.application = (Myapplication) getApplication();
        sendBroadcast(new Intent("showfloat").putExtra("flag", 1));
        registerReceiver(this.bottomMenuRev, new IntentFilter("showbottom"));
        setContentView(R.layout.videoplayer);
        this.n = this.application.getLocaltai();
        this.mAudioManager = (AudioManager) getSystemService("audio");
        this.sp = getSharedPreferences("tvinfo", 0);
        this.mode = this.mAudioManager.getRingerMode();
        this.maxVolume = this.mAudioManager.getStreamMaxVolume(3);
        this.currentVolume = this.mAudioManager.getStreamVolume(3);
        Intent intent = getIntent();
        try {
            this.Lives = this.application.getLive().getSingelLives();
            this.url = ((SingelLives) this.Lives.get(this.application.getLocaltai())).getLivemanagementaddress();
            initview();
            CurrentPosition();
        } catch (Exception e) {
        }
    }

    protected void onResume() {
        super.onResume();
        this.application.setLocaltai(this.n);
        this.n = this.application.getLocaltai();
    }

    protected void onRestart() {
        try {
            sendBroadcast(new Intent("showfloat").putExtra("flag", 0));
            this.application.setLocaltai(this.n);
            this.n = this.application.getLocaltai();
            if (!this.player.isPlaying()) {
                this.player.start();
            }
        } catch (Exception e) {
        }
        super.onRestart();
    }

    protected void onPause() {
        super.onPause();
        this.application.setLocaltai(this.n);
        this.n = this.application.getLocaltai();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            if (this.c) {
                this.handler.removeMessages(2);
                this.handler.sendEmptyMessage(2);
            } else {
                this.handler.removeMessages(0);
                showProgramDailog();
            }
        }
        return super.onTouchEvent(event);
    }

    private void initview() {
        this.player = (VideoView) findViewById(R.id.player);
        this.tai_num = (TextView) findViewById(R.id.tai_num);
        FULL.star(this.player);
        String url1 = this.sp.getString("tvurl", "");
        Log.e("url1", new StringBuilder(String.valueOf(url1)).append("@@@@@@@@@@").toString());
        if (!url1.trim().equals("") && url1.trim() != null) {
            init(url1);
        } else if (url1.trim().equals("") && this.url != null) {
            init(this.url);
        }
    }

    private void CurrentPosition() {
        this.timer1.schedule(new TimerTask() {
            public void run() {
                try {
                    LiveActivity.this.time1 = LiveActivity.this.player.getCurrentPosition();
                } catch (Exception e) {
                }
            }
        }, 0, 1000);
        this.timer2.schedule(new TimerTask() {
            public void run() {
                try {
                    LiveActivity.this.time2 = LiveActivity.this.player.getCurrentPosition();
                    if (LiveActivity.this.time2 == LiveActivity.this.time1) {
                        LiveActivity.this.handler.sendEmptyMessage(23);
                    }
                } catch (Exception e) {
                }
            }
        }, 2000, 2000);
    }

    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
        super.onBackPressed();
    }

    private void showProgramDailog() {
        try {
            if (this.dilog != null && this.dilog.isShowing()) {
                this.dilog.dismiss();
            }
            this.dilog = new Builder(this).create();
            this.dilog.setTitle("节目列表");
            this.dilog.setCanceledOnTouchOutside(true);
            this.dilog.getWindow().setGravity(51);
            this.dilog.show();
            this.dilog.setContentView(R.layout.popwin_tv_programlist);
            iniview();
            this.programListView = (ListView) this.dilog.findViewById(R.id.programListView);
            this.programListView.setAdapter(new ListAdapter(this, this.application.getLive().getSingelLives()));
            this.programListView.setSelectionFromTop(this.n, TvLanguage.GUARANI);
            this.programListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    try {
                        v.setSelected(true);
                        LiveActivity.this.init(((SingelLives) LiveActivity.this.Lives.get(position)).getLivemanagementaddress());
                        LiveActivity.this.n = position;
                        LiveActivity.this.shownum(LiveActivity.this.n);
                    } catch (Exception e) {
                    }
                }
            });
            this.programListView.requestFocus();
            this.handler.sendEmptyMessageDelayed(0, 10000);
            this.dilog.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    LiveActivity.this.handler.removeMessages(0);
                    LiveActivity.this.handler.sendEmptyMessageDelayed(0, 5000);
                    if (keyCode == 19) {
                        LiveActivity.this.times20 = 0;
                        try {
                            if (LiveActivity.this.programListView.getSelectedItemPosition() == 0) {
                                LiveActivity liveActivity = LiveActivity.this;
                                liveActivity.times19++;
                                if (LiveActivity.this.times19 == 2) {
                                    LiveActivity.this.times19 = 0;
                                    LiveActivity.this.handler.sendEmptyMessage(4);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (keyCode == 20) {
                        LiveActivity.this.times19 = 0;
                        try {
                            if (LiveActivity.this.programListView.getSelectedItemPosition() == LiveActivity.this.Lives.size() - 1) {
                                        LiveActivity.this.times20++;

                                if (LiveActivity.this.times20 == 2) {
                                    LiveActivity.this.times20 = 0;
                                    LiveActivity.this.handler.sendEmptyMessage(5);
                                }
                            }
                        } catch (Exception e2) {
                        }
                    }
                    return false;
                }
            });
            this.liveback = (ImageButton) this.dilog.findViewById(R.id.liveback);
            this.liveback.setOnClickListener(new Btn());
            this.vol_txt = (TextView) this.dilog.findViewById(R.id.vol_txt);
            this.bars = (VideoBars) this.dilog.findViewById(R.id.bars);
            this.back = (ImageButton) this.dilog.findViewById(R.id.back);
            this.back.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    LiveActivity.this.finish();
                }
            });
            this.jinyins = (ImageButton) this.dilog.findViewById(R.id.jinyin);
            this.jinyins.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    LiveActivity.this.bars.setProgress(0);
                    LiveActivity.this.vol_txt.setText(new StringBuilder(String.valueOf(LiveActivity.this.bars.getProgress())).toString());
                    LiveActivity.this.mAudioManager.setStreamVolume(3, 0, 0);
                }
            });
            this.bars.setMax(VolInfo.getMaxVol(getApplicationContext()));
            this.bars.setProgress(this.mAudioManager.getStreamVolume(3));
            this.vol_txt.setText(new StringBuilder(String.valueOf(this.bars.getProgress())).toString());
            this.bars.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    LiveActivity.this.mAudioManager.setStreamVolume(3, LiveActivity.this.bars.getProgress(), 0);
                    LiveActivity.this.vol_txt.setText(new StringBuilder(String.valueOf(LiveActivity.this.bars.getProgress())).toString());
                }
            });
        } catch (Exception e) {
        }
    }

    private void init(String url) {
        try {
            System.out.println("请求视频地址" + url);
            if (url != null && !url.equals("")) {
                if (this.player.isPlaying()) {
                    this.player.stopPlayback();
                }
                this.player.setVideoURI(Uri.parse(url));
                this.player.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        LiveActivity.this.player.start();
                    }
                });
                this.player.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return true;
                    }
                });
                this.editor = this.sp.edit();
                this.editor.putString("tvurl", url.toString());
                this.editor.commit();
            }
        } catch (Exception e) {
        }
    }

    private void shownum(int num) {
        this.tai_num.setText(new StringBuilder(String.valueOf(num + 1)).toString());
        this.handler.sendEmptyMessageDelayed(3, 2000);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode >= 7 && keyCode <= 16) {
            int num = keyCode - 7;
            try {
                this.handler.removeMessages(2);
                this.tmpNum += num;
                this.tai_num.setText(this.tmpNum);
                this.n = Integer.parseInt(this.tmpNum);
                this.c = true;
                this.handler.sendEmptyMessageDelayed(2, 2000);
            } catch (Exception e) {
                this.handler.sendEmptyMessageDelayed(2, 2000);
            }
        }
        if (keyCode == 23) {
            if (this.c) {
                this.handler.removeMessages(2);
                this.handler.sendEmptyMessage(2);
            } else {
                showProgramDailog();
            }
        }
        if (keyCode == 66) {
            if (this.c) {
                this.handler.removeMessages(2);
                this.handler.sendEmptyMessage(2);
            } else {
                showProgramDailog();
            }
        }
        if (keyCode == 20) {
            try {
                if (this.n >= 1) {
                    this.n--;
                    init(((SingelLives) this.Lives.get(this.n)).getLivemanagementaddress());
                } else if (this.n < 1) {
                    this.n = this.Lives.size() - 1;
                    init(((SingelLives) this.Lives.get(this.n)).getLivemanagementaddress());
                }
                shownum(this.n);
            } catch (Exception e2) {
            }
        }
        if (keyCode == 19) {
            try {
                if (this.n < this.Lives.size() - 1) {
                    this.n++;
                    init(((SingelLives) this.Lives.get(this.n)).getLivemanagementaddress());
                } else if (this.n >= this.Lives.size() - 1) {
                    this.n = 0;
                    init(((SingelLives) this.Lives.get(this.n)).getLivemanagementaddress());
                }
                shownum(this.n);
            } catch (Exception e3) {
            }
        }
        if (keyCode == 22 && this.currentVolume < this.maxVolume) {
            this.currentVolume++;
            this.mAudioManager.adjustVolume(1, this.currentVolume);
        }
        if (keyCode == 21 && this.currentVolume <= this.maxVolume && this.currentVolume > 0) {
            this.currentVolume--;
            this.mAudioManager.adjustVolume(-1, this.currentVolume);
        }
        if (keyCode == 24 && this.currentVolume < this.maxVolume) {
            this.currentVolume++;
            this.mAudioManager.adjustVolume(1, this.currentVolume);
        }
        if (keyCode == 25 && this.currentVolume <= this.maxVolume && this.currentVolume > 0) {
            this.currentVolume--;
            this.mAudioManager.adjustVolume(-1, this.currentVolume);
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onStop() {
        super.onStop();
        sendBroadcast(new Intent("showfloat").putExtra("flag", 0));
        this.application.setLocaltai(this.n);
        System.out.println(this.n + "退出时保存的频道号。。。。");
        this.timer1.cancel();
        this.timer2.cancel();
    }
}
