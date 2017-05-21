package com.ssx.spa.view.game;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.App;
import com.ssx.spa.javabean.details;
import com.ssx.spa.util.AdGo;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.common.FULL;
import java.util.List;

public class ProductActivity extends BaseActivity {
    private ImageButton back;
    private int currt;
    private App game;
    private Myapplication myapplication;
    private int position;
    private ImageView product_img;
    private TextView product_top;
    private VideoView product_video;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            ProductActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        this.myapplication = (Myapplication) getApplication();
        this.position = getIntent().getIntExtra("position", this.position);
        initview();
        setvalue();
    }

    private void setvalue() {
        try {
            switch (this.game.getFileType()) {
                case 0:
                    new AdGo(getApplicationContext(), this.product_img).buildImage2(this.game.getDetails());
                    return;
                case 2:
                    System.out.println("视频广告开始");
                    this.product_img.setVisibility(8);
                    this.product_video.setVisibility(0);
                    adpaly(this.game.getDetails());
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
        }
    }

    private void adpaly(List<details> list) {
        if (this.currt < list.size()) {
            try {
                this.product_video.setVideoURI(Uri.parse(((details) list.get(this.currt)).getPath()));
                System.out.println(((details) list.get(this.currt)).getPath());
                this.product_video.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        ProductActivity.this.product_video.start();
                    }
                });
                this.product_video.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return true;
                    }
                });
                this.product_video.setOnCompletionListener(new OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        ProductActivity productActivity = ProductActivity.this;
                        productActivity.currt = productActivity.currt + 1;
                        ProductActivity.this.adpaly(ProductActivity.this.game.getDetails());
                    }
                });
            } catch (Exception e) {
            }
        } else if (this.currt >= list.size() && list.size() > 0) {
            this.currt = 0;
            adpaly(this.game.getDetails());
        }
    }

    private void initview() {
        this.product_top = (TextView) findViewById(R.id.product_top);
        this.product_img = (ImageView) findViewById(R.id.product_img);
        this.product_video = (VideoView) findViewById(R.id.product_video);
        FULL.star(this.product_video);
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        try {
            this.game = (App) this.myapplication.getApps().get(this.position);
        } catch (Exception e) {
        }
        this.product_top.setText("首页/安卓应用/" + this.game.getName());
    }
}
