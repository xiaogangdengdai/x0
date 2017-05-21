package com.ssx.spa.view.vod;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.mstar.android.tv.TvCommonManager;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.Videos;
import com.ssx.spa.javabean.VoidPrograms;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.adapter.DramaAdapter;
import com.ssx.spa.view.common.VideoViewActivity;
import java.util.List;

public class DramaActivity extends BaseActivity {
    private ImageButton back;
    private TvCommonManager commonManager;
    private GridView drama_gridview;
    private TextView drama_top;
    private Myapplication myapplication;
    private ImageButton now_play;
    private String top;
    private List<Videos> videos;
    private ImageView voddetail_img;
    private VoidPrograms voidPrograms;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            DramaActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.myapplication = (Myapplication) getApplication();
        super.onCreate(savedInstanceState);
        this.voidPrograms = (VoidPrograms) getIntent().getSerializableExtra("voidPrograms");
        this.videos = this.voidPrograms.getVideos();
        this.top = getIntent().getStringExtra("top");
        setContentView(R.layout.activity_drama);
        initview();
        setvalue();
    }

    private void setvalue() {
        this.drama_gridview.setAdapter(new DramaAdapter(this, this.videos));
        this.drama_gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                DramaActivity.this.startActivity(new Intent(DramaActivity.this, VideoViewActivity.class).putExtra("url", ((Videos) DramaActivity.this.voidPrograms.getVideos().get(arg2)).getVoidpath()));
            }
        });
    }

    private void initview() {
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.drama_top = (TextView) findViewById(R.id.drama_top);
        this.drama_top.setText(this.top + "/" + this.voidPrograms.getVoidprogramname());
        this.drama_gridview = (GridView) findViewById(R.id.drama_gridview);
    }
}
