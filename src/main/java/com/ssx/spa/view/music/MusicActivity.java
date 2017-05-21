package com.ssx.spa.view.music;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.config.VolInfo;
import com.ssx.spa.javabean.Song;
import com.ssx.spa.javabean.SongCategory;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.adapter.MusicAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MusicActivity extends BaseActivity implements OnTouchListener {
    private final int MAXSIZE = 10;
    private AudioManager audioManager;
    private ImageButton back;
    private SongCategory category;
    private int current;
    private int currentvol;
    private int endTimeStr = 0;
    private boolean isPlaying = false;
    private boolean isTimeChang = false;
    private MediaPlayer mediaPlayer;
    private TextView music_all;
    private ImageButton music_btn;
    private ImageButton music_left;
    private ListView music_list;
    private TextView music_local;
    private EditText music_names;
    private TextView music_nowname;
    private ImageButton music_play;
    private ImageButton music_right;
    private SeekBar music_sebar;
    private TextView music_top;
    private TextView music_volnum;
    private SeekBar music_volsebar;
    private Myapplication myapplication;
    private Handler myhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (MusicActivity.this.isPlaying && MusicActivity.this.mediaPlayer.isPlaying()) {
                            MusicActivity.this.staTimeStr = MusicActivity.this.mediaPlayer.getCurrentPosition();
                            MusicActivity.this.music_local.setText(MusicActivity.this.getTimeStr(MusicActivity.this.staTimeStr));
                            MusicActivity.this.music_sebar.setProgress(MusicActivity.this.staTimeStr);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        return;
                    }
                case 2:
                    try {
                        MusicActivity.this.music_all.setText(MusicActivity.this.getTimeStr(MusicActivity.this.endTimeStr));
                        MusicActivity.this.staTimeStr = MusicActivity.this.mediaPlayer.getCurrentPosition();
                        MusicActivity.this.setPlayTime();
                        MusicActivity.this.myhandler.sendEmptyMessage(3);
                        return;
                    } catch (Exception e2) {
                        return;
                    }
                case 3:
                    new Thread(new Runnable() {
                        public void run() {
                            while (MusicActivity.this.isTimeChang) {
                                try {
                                    MusicActivity.this.myhandler.sendEmptyMessage(1);
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }).start();
                    return;
                default:
                    return;
            }
        }
    };
    private int postion = -1;
    private List<Song> songs;
    private int staTimeStr = 0;
    private ImageButton turn_letf;
    private ImageButton turn_right;
    float x1 = 0.0f;
    float x2 = 0.0f;
    float y1 = 0.0f;
    float y2 = 0.0f;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            MusicActivity.this.finish();
            MusicActivity.this.stopMusic();
        }
    }

    class Search_music extends AsyncTask<String, String, String> {
        Search_music() {
        }

        protected void onPostExecute(String result) {
            if (MusicActivity.this.songs.size() == 0) {
                MyToast.makeshow(MusicActivity.this, "未找到相关歌曲信息！", 0);
            } else {
                MusicActivity.this.current = 0;
                MusicActivity.this.setvalue();
            }
            super.onPostExecute(result);
        }

        protected String doInBackground(String... params) {
            MusicActivity.this.songs = JsonResult.searchmusic(MusicActivity.this, Myapplication.MAC, MusicActivity.this.music_names.getText().toString());
            return null;
        }
    }

    public class StartMusic implements OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            MusicActivity.this.mediaPlayer.stop();
            MusicActivity.this.mediaPlayer.reset();
            MusicActivity.this.postion = (MusicActivity.this.current * 10) + arg2;
            MusicActivity.this.playerSong((MusicActivity.this.current * 10) + arg2);
        }
    }

    class Turn implements OnClickListener {
        Turn() {
        }

        public void onClick(View v) {
            MusicActivity musicActivity;
            switch (v.getId()) {
                case R.id.turn_letf:
                    if (MusicActivity.this.current > 0) {
                        musicActivity = MusicActivity.this;
                        musicActivity.current = musicActivity.current - 1;
                        break;
                    }
                    break;
                case R.id.turn_right:
                    if (MusicActivity.this.current < ((int) Math.ceil(((double) MusicActivity.this.songs.size()) / 10.0d)) - 1) {
                        musicActivity = MusicActivity.this;
                        musicActivity.current = musicActivity.current + 1;
                        break;
                    }
                    break;
            }
            MusicActivity.this.setvalue();
        }
    }

    private final class handles {
        public static final int ENDTIMESTRCHANG = 2;
        public static final int SEEKBARCHANG = 3;
        public static final int STARTIMESTRCHANG = 1;

        private handles() {
        }
    }

    protected void onStart() {
        super.onStart();
        setMediaListene();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.audioManager = (AudioManager) getSystemService("audio");
        this.currentvol = this.audioManager.getStreamVolume(3);
        this.myapplication = (Myapplication) getApplication();
        setContentView(R.layout.activity_music);
        this.category = (SongCategory) getIntent().getSerializableExtra("songs");
        this.songs = this.category.getSongs();
        initview();
        setvalue();
    }

    private void setMediaListene() {
        this.mediaPlayer = new MediaPlayer();
        this.mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                MusicActivity.this.isTimeChang = true;
                MusicActivity.this.isPlaying = true;
                mp.start();
                MusicActivity.this.music_play.setBackgroundResource(R.xml.music_play);
                MusicActivity.this.endTimeStr = mp.getDuration();
                MusicActivity.this.myhandler.sendEmptyMessage(2);
            }
        });
        this.mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                MusicActivity musicActivity = MusicActivity.this;
                int access$26 = musicActivity.postion + 1;
                musicActivity.postion = access$26;
                if (access$26 < MusicActivity.this.songs.size()) {
                    try {
                        MusicActivity.this.playerSong(MusicActivity.this.postion);
                        return;
                    } catch (Exception e) {
                        return;
                    }
                }
                MusicActivity.this.stopMusic();
            }
        });
        this.mediaPlayer.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == 2) {
            super.onTouchEvent(event);
            return true;
        }
        onTouchEvent(event);
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            this.x1 = event.getX();
            this.y1 = event.getY();
        }
        if (event.getAction() == 1) {
            this.x2 = event.getX();
            this.y2 = event.getY();
            if (this.y1 - this.y2 <= 50.0f && this.y2 - this.y1 <= 50.0f) {
                if (this.x1 - this.x2 > 50.0f) {
                    if (this.current < ((int) Math.ceil(((double) this.songs.size()) / 10.0d)) - 1) {
                        this.current++;
                    }
                    setvalue();
                } else if (this.x2 - this.x1 > 50.0f) {
                    if (this.current > 0) {
                        this.current--;
                    }
                    setvalue();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void setvalue() {
        try {
            if ((this.current + 1) * 10 > this.songs.size()) {
                this.music_list.setAdapter(new MusicAdapter(this, this.songs.subList(this.current * 10, this.songs.size())));
            } else {
                this.music_list.setAdapter(new MusicAdapter(this, this.songs.subList(this.current * 10, (this.current + 1) * 10)));
            }
        } catch (Exception e) {
        }
    }

    private void initview() {
        this.music_names = (EditText) findViewById(R.id.music_names);
        this.music_btn = (ImageButton) findViewById(R.id.music_btn);
        this.music_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MusicActivity.this.music_names.getText().toString().trim().equals("")) {
                    MyToast.makeshow(MusicActivity.this, "请输入你要搜索的歌曲名称!", 0);
                    MusicActivity.this.songs = MusicActivity.this.category.getSongs();
                    MusicActivity.this.setvalue();
                    return;
                }
                new Search_music().execute(new String[0]);
            }
        });
        this.music_top = (TextView) findViewById(R.id.music_top);
        this.music_list = (ListView) findViewById(R.id.music_list);
        this.music_top.setText("首页/音乐欣赏/" + this.category.getName());
        this.music_list.setOnItemClickListener(new StartMusic());
        this.music_left = (ImageButton) findViewById(R.id.music_left);
        this.music_play = (ImageButton) findViewById(R.id.music_play);
        this.music_right = (ImageButton) findViewById(R.id.music_right);
        this.music_sebar = (SeekBar) findViewById(R.id.music_sebar);
        this.music_volsebar = (SeekBar) findViewById(R.id.music_volsebar);
        this.music_volsebar.setMax(VolInfo.getMaxVol(getApplicationContext()));
        this.music_volnum = (TextView) findViewById(R.id.music_volnum);
        this.music_volsebar.setProgress(this.currentvol);
        this.music_volnum.setText(new StringBuilder(String.valueOf(this.currentvol)).toString());
        this.music_local = (TextView) findViewById(R.id.music_local);
        this.music_all = (TextView) findViewById(R.id.music_all);
        this.music_nowname = (TextView) findViewById(R.id.music_nowname);
        this.turn_letf = (ImageButton) findViewById(R.id.turn_letf);
        this.turn_right = (ImageButton) findViewById(R.id.turn_right);
        this.turn_letf.setOnClickListener(new Turn());
        this.turn_right.setOnClickListener(new Turn());
        this.music_left.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    if (MusicActivity.this.postion > 0) {
                        MusicActivity musicActivity = MusicActivity.this;
                        MusicActivity musicActivity2 = MusicActivity.this;
                        int access$26 = musicActivity2.postion - 1;
                        musicActivity2.postion = access$26;
                        musicActivity.playerSong(access$26);
                        return;
                    }
                    MusicActivity.this.playerSong(0);
                } catch (Exception e) {
                }
            }
        });
        this.music_right.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    if (MusicActivity.this.postion < MusicActivity.this.songs.size() - 1) {
                        MusicActivity musicActivity = MusicActivity.this;
                        MusicActivity musicActivity2 = MusicActivity.this;
                        int access$26 = musicActivity2.postion + 1;
                        musicActivity2.postion = access$26;
                        musicActivity.playerSong(access$26);
                        return;
                    }
                    MusicActivity.this.playerSong(MusicActivity.this.songs.size() - 1);
                } catch (Exception e) {
                }
            }
        });
        this.music_play.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MusicActivity.this.isTimeChang) {
                    MusicActivity.this.pouseMusic();
                } else {
                    MusicActivity.this.restMusic();
                }
            }
        });
        this.music_sebar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (MusicActivity.this.isPlaying) {
                    MusicActivity.this.mediaPlayer.seekTo(seekBar.getProgress());
                    MusicActivity.this.staTimeStr = MusicActivity.this.mediaPlayer.getCurrentPosition();
                    MusicActivity.this.music_local.setText(MusicActivity.this.getTimeStr(MusicActivity.this.staTimeStr));
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });
        this.music_volsebar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MusicActivity.this.audioManager.setStreamVolume(3, MusicActivity.this.music_volsebar.getProgress(), 0);
                MusicActivity.this.music_volnum.setText(new StringBuilder(String.valueOf(MusicActivity.this.music_volsebar.getProgress())).toString());
            }
        });
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
    }

    private String getTimeStr(int time) {
        return new SimpleDateFormat("mm:ss").format(new Date((long) time));
    }

    private void setPlayTime() {
        this.music_sebar.setMax(this.endTimeStr);
        this.music_sebar.setProgress(this.staTimeStr);
    }

    private void playerSong(int index) {
        this.mediaPlayer.stop();
        this.mediaPlayer.reset();
        try {
            System.out.println(new StringBuilder(String.valueOf(((Song) this.songs.get(index)).getSongName())).append("\n").append(((Song) this.songs.get(this.postion)).getMusicFile()).toString());
            this.music_nowname.setText("正在播放：" + ((Song) this.songs.get(index)).getSongName());
            this.music_nowname.requestFocus();
            this.mediaPlayer.setDataSource(this, Uri.parse(((Song) this.songs.get(this.postion)).getMusicFile()));
        } catch (Exception e) {
        }
        this.mediaPlayer.prepareAsync();
    }

    private void stopMusic() {
        this.isTimeChang = false;
        this.music_local.setText("00:00");
        this.music_all.setText("00:00");
        this.staTimeStr = 0;
        this.endTimeStr = 0;
        this.music_sebar.setProgress(0);
        if (this.isPlaying) {
            this.isPlaying = false;
            this.mediaPlayer.stop();
        }
        this.music_play.setBackgroundResource(R.xml.music_pause);
    }

    protected void onPause() {
        stopMusic();
        super.onPause();
    }

    private void pouseMusic() {
        this.isTimeChang = false;
        if (this.isPlaying) {
            this.mediaPlayer.pause();
        }
        this.music_play.setBackgroundResource(R.xml.music_pause);
    }

    private void restMusic() {
        this.isTimeChang = true;
        this.myhandler.sendEmptyMessage(3);
        if (this.isPlaying) {
            this.mediaPlayer.start();
        }
        this.music_play.setBackgroundResource(R.xml.music_play);
    }

    protected void onDestroy() {
        stopMusic();
        super.onDestroy();
    }
}
