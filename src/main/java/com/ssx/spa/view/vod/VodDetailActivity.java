package com.ssx.spa.view.vod;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.mstar.android.tv.TvCommonManager;
import com.squareup.picasso.Picasso;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.Videos;
import com.ssx.spa.javabean.VoidPrograms;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.common.AdActivity;
import com.ssx.spa.view.common.VideoViewActivity;

public class VodDetailActivity extends BaseActivity {
    private ImageButton back;
    private TvCommonManager commonManager;
    private Myapplication myapplication;
    private ImageButton now_play;
    private String top;
    private Videos videos;
    private TextView voddetail_area;
    private TextView voddetail_description;
    private TextView voddetail_director;
    private TextView voddetail_filmtype;
    private ImageView voddetail_img;
    private TextView voddetail_name;
    private TextView voddetail_star;
    private TextView voddetail_top;
    private VoidPrograms voidPrograms;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            VodDetailActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.myapplication = (Myapplication) getApplication();
        super.onCreate(savedInstanceState);
        this.voidPrograms = (VoidPrograms) getIntent().getSerializableExtra("voidPrograms");
        this.videos = (Videos) this.voidPrograms.getVideos().get(0);
        this.top = getIntent().getStringExtra("top");
        setContentView(R.layout.activity_voddetail);
        initview();
        setvalue();
    }

    private void setvalue() {
        this.now_play.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    VodDetailActivity.this.commonManager = TvCommonManager.getInstance();
                    VodDetailActivity.this.commonManager.setInputSource(34);
                } catch (Exception e) {
                }
                try {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ad", VodDetailActivity.this.voidPrograms.getAd());
                    if (VodDetailActivity.this.voidPrograms.getAd().equals("") || VodDetailActivity.this.voidPrograms.getAd() == null) {
                        VodDetailActivity.this.startActivity(new Intent(VodDetailActivity.this, VideoViewActivity.class).putExtra("url", VodDetailActivity.this.videos.getVoidpath()));
                    } else {
                        VodDetailActivity.this.startActivity(new Intent(VodDetailActivity.this, AdActivity.class).putExtras(bundle).putExtra("url", VodDetailActivity.this.videos.getVoidpath()));
                    }
                } catch (Exception e2) {
                    VodDetailActivity.this.startActivity(new Intent(VodDetailActivity.this, VideoViewActivity.class).putExtra("url", VodDetailActivity.this.videos.getVoidpath()));
                }
            }
        });
    }

    private void initview() {
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.voddetail_top = (TextView) findViewById(R.id.voddetail_top);
        this.voddetail_top.setText(this.top + "/" + this.voidPrograms.getVoidprogramname());
        this.now_play = (ImageButton) findViewById(R.id.now_play);
        this.voddetail_img = (ImageView) findViewById(R.id.voddetail_img);
        Picasso.with(this).load(this.voidPrograms.getVoidprogramimg()).into(this.voddetail_img);
        this.voddetail_name = (TextView) findViewById(R.id.voddetail_name);
        this.voddetail_star = (TextView) findViewById(R.id.voddetail_star);
        this.voddetail_director = (TextView) findViewById(R.id.voddetail_director);
        this.voddetail_area = (TextView) findViewById(R.id.voddetail_area);
        this.voddetail_filmtype = (TextView) findViewById(R.id.voddetail_filmtype);
        this.voddetail_description = (TextView) findViewById(R.id.voddetail_description);
        try {
            this.voddetail_name.setText(this.voidPrograms.getVoidprogramname());
            this.voddetail_star.setText("主演：" + this.voidPrograms.getVoidprogramstarring());
            this.voddetail_director.setText("导演：" + this.voidPrograms.getVoidprogramdirector());
            this.voddetail_area.setText("地区：" + this.voidPrograms.getArea());
            this.voddetail_filmtype.setText("类型：" + this.voidPrograms.getFilmtype());
            this.voddetail_description.setText("简介：" + this.voidPrograms.getVoidprogramdescription());
        } catch (Exception e) {
        }
    }
}
