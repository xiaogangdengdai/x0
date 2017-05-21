package com.ssx.spa.view.common;

import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;
import com.mstar.android.tv.TvLanguage;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.config.VolInfo;
import com.ssx.spa.javabean.HeadSwitch;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.localservice.FoodActivity;
import com.ssx.spa.view.localservice.JishiActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VideoViewActivity extends BaseActivity {
    ArrayAdapter arr_adapter;
    private final int audio = 20;
    private ImageButton back;
    private SeekBar bar;
    private VideoBars bars;
    private BroadcastReceiver bottomMenuRev = new BroadcastReceiver() {
        public void onReceive(Context cont, Intent inte) {
        }
    };
    private BottomMenuView bottomview;
    private MediaCtrolls c;
    private SeekBar cbar;
    private Timer cbart;
    private ImageButton cforward;
    private MediaController controller;
    private ImageButton cpause;
    private ImageButton crewind;
    private TextView ct1;
    private TextView ct2;
    private int currentVolume;
    List<String> data_list;
    private ImageButton fodBtn;
    private GestureDetector gestureDetector;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    VideoViewActivity.this.cbar.setMax(VideoViewActivity.this.videoview.getDuration());
                    VideoViewActivity.this.cbar.setProgress(VideoViewActivity.this.videoview.getCurrentPosition());
                    VideoViewActivity.this.ct1.setText(VideoViewActivity.this.cmin(VideoViewActivity.this.videoview.getCurrentPosition()));
                    VideoViewActivity.this.ct2.setText(VideoViewActivity.this.cmin(VideoViewActivity.this.videoview.getDuration()));
                    break;
                case 18:
                    try {
                        VideoViewActivity.this.cbart = new Timer();
                        VideoViewActivity.this.cbart.schedule(new TimerTask() {
                            public void run() {
                                VideoViewActivity.this.handler.sendEmptyMessage(10);
                            }
                        }, 0, 1000);
                        VideoViewActivity.this.bars.setProgress(VideoViewActivity.this.mAudioManager.getStreamVolume(3));
                        VideoViewActivity.this.popupWindow.showAtLocation(VideoViewActivity.this.videoview, 80, 0, 0);
                        VideoViewActivity.this.handler.sendEmptyMessageDelayed(19, 10000);
                        break;
                    } catch (Exception e) {
                        break;
                    }
                case 19:
                    try {
                        if (VideoViewActivity.this.cbart != null) {
                            VideoViewActivity.this.cbart.cancel();
                        }
                        if (VideoViewActivity.this.popupWindow != null) {
                            VideoViewActivity.this.popupWindow.dismiss();
                            break;
                        }
                    } catch (Exception e2) {
                        break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private final int hide = 19;
    private ImageButton hujiaoBtn;
    private ImageButton jinyin;
    private ImageButton jishiBtn;
    private ImageButton liuzuoBtn;
    private AudioManager mAudioManager;
    private int maxVolume;
    private Myapplication myapplication;
    private OnGestureListener onGestureListener = new SimpleOnGestureListener() {
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();
            int current = VideoViewActivity.this.mAudioManager.getStreamVolume(3);
            if (x > 0.0f) {
                System.out.println("快进--");
                if (VideoViewActivity.this.videoview != null) {
                    if (VideoViewActivity.this.videoview.getCurrentPosition() >= 0) {
                        VideoViewActivity.this.videoview.seekTo(VideoViewActivity.this.videoview.getCurrentPosition() + ((int) (((double) ((float) VideoViewActivity.this.videoview.getDuration())) * 0.005d)));
                        System.out.println(VideoViewActivity.this.videoview.getCurrentPosition() + ((int) (((double) ((float) VideoViewActivity.this.videoview.getDuration())) * 0.005d)));
                    }
                    if (VideoViewActivity.this.videoview.getCurrentPosition() >= VideoViewActivity.this.videoview.getDuration()) {
                        VideoViewActivity.this.videoview.seekTo(VideoViewActivity.this.videoview.getDuration());
                    }
                }
            } else if (x < 0.0f) {
                System.out.println("快退--");
                if (VideoViewActivity.this.videoview != null) {
                    if (VideoViewActivity.this.videoview.getCurrentPosition() >= 0) {
                        VideoViewActivity.this.videoview.seekTo(VideoViewActivity.this.videoview.getCurrentPosition() - ((int) (((double) ((float) VideoViewActivity.this.videoview.getDuration())) * 0.005d)));
                        System.out.println(VideoViewActivity.this.videoview.getCurrentPosition() - ((int) (((double) ((float) VideoViewActivity.this.videoview.getDuration())) * 0.005d)));
                    }
                    if (VideoViewActivity.this.videoview.getCurrentPosition() <= 0) {
                        VideoViewActivity.this.videoview.start();
                    }
                }
            }
            return true;
        }
    };
    private View popView;
    private PopupWindow popupWindow;
    private SharedPreferences preferences;
    private ImageButton shangzhongBtn;
    private final int show = 18;
    private VideoView videoview;
    private TextView vol_txt;
    private TextView volnum;
    private ImageButton xiazhongBtn;

    class Btn implements OnClickListener {
        Btn() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.jishiBtn:
                    VideoViewActivity.this.startActivity(new Intent(VideoViewActivity.this, JishiActivity.class));
                    return;
                case R.id.fodBtn:
                    VideoViewActivity.this.startActivity(new Intent(VideoViewActivity.this, FoodActivity.class));
                    return;
                case R.id.liuzuoBtn:
                    VideoViewActivity.this.liuzuo();
                    return;
                case R.id.hujiaoBtn:
                    MyToast.makeshow(VideoViewActivity.this, "呼叫成功！", 0);
                    new Call().execute(new String[0]);
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
                JsonResult.call(VideoViewActivity.this, Myapplication.MAC);
            } catch (Exception e) {
            }
            return null;
        }
    }

    protected void onRestart() {
        System.out.println("location:" + this.preferences.getInt("time", 0));
        if (!(this.videoview == null || this.preferences.getInt("time", 0) == 0)) {
            this.videoview.seekTo(this.preferences.getInt("time", 0));
        }
        super.onRestart();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.myapplication = (Myapplication) getApplication();
        registerReceiver(this.bottomMenuRev, new IntentFilter("showbottom"));
        this.mAudioManager = (AudioManager) getSystemService("audio");
        this.gestureDetector = new GestureDetector(this, this.onGestureListener);
        setContentView(R.layout.activity_videoview);
        initview();
        setvalue();
        OnPopwindows();
    }

    private void iniview() {
        this.bottomview = (BottomMenuView) this.popView.findViewById(R.id.bottomview);
        this.jinyin = (ImageButton) this.bottomview.findViewById(R.id.jinyin);
        this.bar = (SeekBar) this.bottomview.findViewById(R.id.bars);
        this.back = (ImageButton) this.bottomview.findViewById(R.id.back);
        this.volnum = (TextView) this.bottomview.findViewById(R.id.volnum);
        this.jishiBtn = (ImageButton) this.bottomview.findViewById(R.id.jishiBtn);
        this.fodBtn = (ImageButton) this.bottomview.findViewById(R.id.fodBtn);
        this.liuzuoBtn = (ImageButton) this.bottomview.findViewById(R.id.liuzuoBtn);
        this.hujiaoBtn = (ImageButton) this.bottomview.findViewById(R.id.hujiaoBtn);
        int i = 0;
        while (i < this.myapplication.getHeadSwitchs().size()) {
            try {
                if (((HeadSwitch) this.myapplication.getHeadSwitchs().get(i)).getId() == 1 && ((HeadSwitch) this.myapplication.getHeadSwitchs().get(i)).getStatus() == 1) {
                    this.liuzuoBtn.setVisibility(0);
                }
                if (((HeadSwitch) this.myapplication.getHeadSwitchs().get(i)).getId() == 2 && ((HeadSwitch) this.myapplication.getHeadSwitchs().get(i)).getStatus() == 1) {
                    this.hujiaoBtn.setVisibility(0);
                }
                i++;
            } catch (Exception e) {
            }
        }
        if (this.myapplication.Iscall()) {
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
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                BaseActivity.finishone();
            }
        });
        this.jinyin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VideoViewActivity.this.bar.setProgress(0);
                VideoViewActivity.this.mAudioManager.setStreamVolume(3, 0, 0);
            }
        });
        this.bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                VideoViewActivity.this.mAudioManager.setStreamVolume(3, VideoViewActivity.this.bar.getProgress(), 0);
                VideoViewActivity.this.volnum.setText(new StringBuilder(String.valueOf(VideoViewActivity.this.bar.getProgress())).toString());
            }
        });
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
                    VideoViewActivity.this.startActivity(new Intent(VideoViewActivity.this, LiuweiActivity.class).putExtra("time", (String) VideoViewActivity.this.data_list.get(spinner.getSelectedItemPosition())));
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

    private void initview() {
        this.videoview = (VideoView) findViewById(R.id.videoview);
        FULL.star(this.videoview);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1 && !this.popupWindow.isShowing()) {
            this.handler.sendEmptyMessage(18);
        }
        if (event.getAction() == 0) {
            sendBroadcast(new Intent("showbottomtools"));
        }
        return false;
    }

    private void OnPopwindows() {
        this.popView = getLayoutInflater().inflate(R.layout.ctrols, null);
        this.popupWindow = new PopupWindow(this.popView, -1, -1);
        iniview();
        ciniview();
        this.vol_txt = (TextView) this.popView.findViewById(R.id.vol_txt);
        this.bars = (VideoBars) this.popView.findViewById(R.id.bars);
        this.back = (ImageButton) this.popView.findViewById(R.id.back);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VideoViewActivity.this.finish();
            }
        });
        this.jinyin = (ImageButton) this.popView.findViewById(R.id.jinyin);
        this.jinyin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VideoViewActivity.this.bars.setProgress(0);
                VideoViewActivity.this.vol_txt.setText(new StringBuilder(String.valueOf(VideoViewActivity.this.bars.getProgress())).toString());
                VideoViewActivity.this.mAudioManager.setStreamVolume(3, 0, 0);
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
                VideoViewActivity.this.mAudioManager.setStreamVolume(3, VideoViewActivity.this.bars.getProgress(), 0);
                VideoViewActivity.this.vol_txt.setText(new StringBuilder(String.valueOf(VideoViewActivity.this.bars.getProgress())).toString());
            }
        });
    }

    private String cmin(int d) {
        int hour = 0;
        int minute = 0;
        int second = d / 1000;
        if (second > 60) {
            minute = second / 60;
            second %= 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute %= 60;
        }
        return getTwoLength(hour) + ":" + getTwoLength(minute) + ":" + getTwoLength(second);
    }

    private static String getTwoLength(int data) {
        if (data < 10) {
            return "0" + data;
        }
        return data+"";
    }

    private void ciniview() {
        this.c = (MediaCtrolls) this.popView.findViewById(R.id.c);
        this.cbar = (SeekBar) this.c.findViewById(R.id.cbar);
        this.ct1 = (TextView) this.c.findViewById(R.id.ct1);
        this.ct2 = (TextView) this.c.findViewById(R.id.ct2);
        this.cbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                VideoViewActivity.this.videoview.seekTo(VideoViewActivity.this.cbar.getProgress());
                if (VideoViewActivity.this.videoview != null && !VideoViewActivity.this.videoview.isPlaying()) {
                    VideoViewActivity.this.cpause.setBackgroundResource(R.xml.control_pause);
                    VideoViewActivity.this.videoview.start();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });
        this.crewind = (ImageButton) this.c.findViewById(R.id.crewind);
        this.crewind.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VideoViewActivity.this.onkey(90);
            }
        });
        this.cpause = (ImageButton) this.c.findViewById(R.id.cpause);
        this.cpause.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (VideoViewActivity.this.videoview == null) {
                    return;
                }
                if (VideoViewActivity.this.videoview.isPlaying()) {
                    VideoViewActivity.this.cpause.setBackgroundResource(R.xml.control_play);
                    VideoViewActivity.this.videoview.pause();
                    return;
                }
                VideoViewActivity.this.cpause.setBackgroundResource(R.xml.control_pause);
                VideoViewActivity.this.videoview.start();
            }
        });
        this.cforward = (ImageButton) this.c.findViewById(R.id.cforward);
        this.cforward.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VideoViewActivity.this.onkey(89);
            }
        });
    }

    private void onkey(int keyCode) {
        switch (keyCode) {
            case 4:
                finish();
                return;
            case 21:
            case TvLanguage.BALUCHI /*90*/:
                System.out.println("快退--");
                if (this.videoview != null) {
                    if (this.videoview.getCurrentPosition() >= 0) {
                        this.videoview.seekTo(this.videoview.getCurrentPosition() - ((int) (((double) ((float) this.videoview.getDuration())) * 0.05d)));
                        System.out.println(this.videoview.getCurrentPosition() - ((int) (((double) ((float) this.videoview.getDuration())) * 0.05d)));
                    }
                    if (this.videoview.getCurrentPosition() <= 0) {
                        this.videoview.start();
                        return;
                    }
                    return;
                }
                return;
            case 22:
            case TvLanguage.BALTIC /*89*/:
                System.out.println("快进--");
                if (this.videoview != null) {
                    if (this.videoview.getCurrentPosition() >= 0) {
                        this.videoview.seekTo(this.videoview.getCurrentPosition() + ((int) (((double) ((float) this.videoview.getDuration())) * 0.05d)));
                        System.out.println(this.videoview.getCurrentPosition() + ((int) (((double) ((float) this.videoview.getDuration())) * 0.05d)));
                    }
                    if (this.videoview.getCurrentPosition() >= this.videoview.getDuration()) {
                        this.videoview.seekTo(this.videoview.getDuration());
                        return;
                    }
                    return;
                }
                return;
            case 85:
                if (this.videoview == null) {
                    return;
                }
                if (this.videoview.isPlaying()) {
                    this.cpause.setBackgroundResource(R.xml.control_play);
                    this.videoview.pause();
                    return;
                }
                this.cpause.setBackgroundResource(R.xml.control_pause);
                this.videoview.start();
                return;
            default:
                return;
        }
    }

    private void setvalue() {
        try {
            this.videoview.setVideoURI(Uri.parse(getIntent().getStringExtra("url")));
        } catch (Exception e) {
            MyToast.makeshow(this, "发生未知错误！请联系管人员更换片源！", 1);
        }
        this.videoview.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                VideoViewActivity.this.videoview.start();
            }
        });
        this.videoview.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                VideoViewActivity.this.finish();
            }
        });
        this.videoview.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        onkey(keyCode);
        return super.onKeyUp(keyCode, event);
    }

    protected void onPause() {
        if (this.videoview != null) {
            this.preferences = getSharedPreferences("CurrentTime", 0);
            this.preferences.edit().putInt("time", this.videoview.getCurrentPosition()).commit();
        }
        super.onPause();
    }

    protected void onStop() {
        this.handler.sendEmptyMessage(19);
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.preferences.edit().clear();
        unregisterReceiver(this.bottomMenuRev);
    }
}
