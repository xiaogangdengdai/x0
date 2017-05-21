package com.ssx.spa.view.localservice;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.FodDetail;
import com.ssx.spa.view.BaseActivity;

public class FodDetailActivity extends BaseActivity {
    private ImageButton back;
    private FodDetail fodDetail;
    private WebView fod_webview;
    private ImageView foddetail_img;
    private TextView foddetail_top;
    private Myapplication myapplication;
    private String top;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            FodDetailActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.myapplication = (Myapplication) getApplication();
        super.onCreate(savedInstanceState);
        this.fodDetail = (FodDetail) getIntent().getSerializableExtra("fodDetail");
        this.top = getIntent().getStringExtra("top");
        setContentView(R.layout.activity_foddetail);
        initview();
        setvalue();
    }

    private void setvalue() {
    }

    private void initview() {
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.foddetail_top = (TextView) findViewById(R.id.foddetail_top);
        this.foddetail_top.setText(this.top + "/" + this.fodDetail.getName());
        this.foddetail_img = (ImageView) findViewById(R.id.foddetail_img);
        Picasso.with(this).load(this.fodDetail.getImg()).into(this.foddetail_img);
        this.fod_webview = (WebView) findViewById(R.id.fod_webview);
        this.fod_webview.setBackgroundColor(0);
        try {
            this.fod_webview.loadDataWithBaseURL(null, getUrl(this.fodDetail.getDescription()), "text/html", "utf-8", null);
        } catch (Exception e) {
        }
    }

    private String getUrl(String url) {
        return "<html><head><title></title><meta http-equiv=\"content-type\"content=\"text/html;charset=utf-8\"></head><body>" + url + "</body></html>";
    }
}
