package com.ssx.spa.view.vod;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.mstar.android.tv.TvCommonManager;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.Videos;
import com.ssx.spa.javabean.Vod;
import com.ssx.spa.javabean.VoidPrograms;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.adapter.VodAdapter;
import com.ssx.spa.view.common.AdActivity;
import com.ssx.spa.view.common.VideoViewActivity;
import java.io.Serializable;
import java.util.List;

public class VodActivity extends BaseActivity implements OnTouchListener {
    private final int MAXSIZE = 12;
    private ImageButton back;
    private TvCommonManager commonManager;
    private int current;
    private Myapplication myapplication;
    private ImageButton turn_letf;
    private ImageButton turn_right;
    private Vod vod;
    private ImageButton vod_btn;
    private GridView vod_grid;
    private EditText vod_names;
    private TextView vod_top;
    private List<VoidPrograms> voidPrograms;
    float x1 = 0.0f;
    float x2 = 0.0f;
    float y1 = 0.0f;
    float y2 = 0.0f;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            VodActivity.this.finish();
        }
    }

    class Search_vod extends AsyncTask<String, String, String> {
        Search_vod() {
        }

        protected void onPostExecute(String result) {
            if (VodActivity.this.voidPrograms.size() == 0) {
                MyToast.makeshow(VodActivity.this, "未找到相关影片信息！", 0);
            } else {
                VodActivity.this.current = 0;
                VodActivity.this.setvalue();
            }
            super.onPostExecute(result);
        }

        protected String doInBackground(String... params) {
            VodActivity.this.voidPrograms = JsonResult.searchvod(VodActivity.this, Myapplication.MAC, VodActivity.this.vod_names.getText().toString());
            return null;
        }
    }

    public class StartVod implements OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("voidPrograms", (Serializable) VodActivity.this.voidPrograms.get((VodActivity.this.current * 12) + arg2));
            switch (((VoidPrograms) VodActivity.this.voidPrograms.get((VodActivity.this.current * 12) + arg2)).getTypes()) {
                case 0:
                    VodActivity.this.startActivity(new Intent(VodActivity.this, DramaActivity.class).putExtras(bundle).putExtra("top", VodActivity.this.vod_top.getText().toString()));
                    return;
                case 1:
                    VodActivity.this.vodplay((VoidPrograms) VodActivity.this.voidPrograms.get((VodActivity.this.current * 12) + arg2));
                    return;
                default:
                    return;
            }
        }
    }

    class Turn implements OnClickListener {
        Turn() {
        }

        public void onClick(View v) {
            VodActivity vodActivity;
            switch (v.getId()) {
                case R.id.turn_letf:
                    if (VodActivity.this.current > 0) {
                        vodActivity = VodActivity.this;
                        vodActivity.current = vodActivity.current - 1;
                        break;
                    }
                    break;
                case R.id.turn_right:
                    if (VodActivity.this.current < ((int) Math.ceil(((double) VodActivity.this.voidPrograms.size()) / 12.0d)) - 1) {
                        vodActivity = VodActivity.this;
                        vodActivity.current = vodActivity.current + 1;
                        break;
                    }
                    break;
            }
            VodActivity.this.setvalue();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.myapplication = (Myapplication) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);
        this.vod = (Vod) getIntent().getSerializableExtra("vods");
        this.voidPrograms = this.vod.getVoidPrograms();
        initview();
        setvalue();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
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
                    if (this.current < ((int) Math.ceil(((double) this.voidPrograms.size()) / 12.0d)) - 1) {
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

    private void vodplay(VoidPrograms voidPrograms2) {
        Videos videos = null;
        try {
            this.commonManager = TvCommonManager.getInstance();
            this.commonManager.setInputSource(34);
        } catch (Exception e) {
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable("ad", voidPrograms2.getAd());
            videos = (Videos) voidPrograms2.getVideos().get(0);
            if (voidPrograms2.getAd().equals("") || voidPrograms2.getAd() == null) {
                startActivity(new Intent(this, VideoViewActivity.class).putExtra("url", videos.getVoidpath()));
            } else {
                startActivity(new Intent(this, AdActivity.class).putExtras(bundle).putExtra("url", videos.getVoidpath()));
            }
        } catch (Exception e2) {
            startActivity(new Intent(this, VideoViewActivity.class).putExtra("url", videos.getVoidpath()));
        }
    }

    private void setvalue() {
        try {
            if ((this.current + 1) * 12 > this.voidPrograms.size()) {
                this.vod_grid.setAdapter(new VodAdapter(this, this.voidPrograms.subList(this.current * 12, this.voidPrograms.size())));
            } else {
                this.vod_grid.setAdapter(new VodAdapter(this, this.voidPrograms.subList(this.current * 12, (this.current + 1) * 12)));
            }
        } catch (Exception e) {
        }
    }

    private void initview() {
        this.vod_names = (EditText) findViewById(R.id.vod_names);
        this.vod_btn = (ImageButton) findViewById(R.id.vod_btn);
        this.vod_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (VodActivity.this.vod_names.getText().toString().trim().equals("")) {
                    MyToast.makeshow(VodActivity.this, "请输入你要搜索的影片名称!", 0);
                    VodActivity.this.voidPrograms = VodActivity.this.vod.getVoidPrograms();
                    VodActivity.this.setvalue();
                    return;
                }
                new Search_vod().execute(new String[0]);
            }
        });
        this.vod_top = (TextView) findViewById(R.id.vod_top);
        this.vod_grid = (GridView) findViewById(R.id.vod_grid);
        this.vod_top.setText("首页/点播/" + this.vod.getVodpartname());
        this.vod_grid.setOnItemClickListener(new StartVod());
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.turn_letf = (ImageButton) findViewById(R.id.turn_letf);
        this.turn_right = (ImageButton) findViewById(R.id.turn_right);
        this.turn_letf.setOnClickListener(new Turn());
        this.turn_right.setOnClickListener(new Turn());
    }
}
