package com.ssx.spa.view.localservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.LiuYan;
import com.ssx.spa.view.adapter.LiuYanAdapter;
import java.util.List;

public class LiuYanActivity extends Activity {
    private ImageButton back;
    private List<LiuYan> list;
    private ListView liuyan_list;
    private TextView liuyan_top;
    private WebView liuyan_webview;
    private Myapplication myapplication;
    private String top;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            LiuYanActivity.this.finish();
        }
    }

    class OtherLiuyan implements OnItemClickListener {
        OtherLiuyan() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            LiuYanActivity.this.liuyan_webview.loadDataWithBaseURL(null, new StringBuilder(String.valueOf(LiuYanActivity.this.center(((LiuYan) LiuYanActivity.this.list.get(arg2)).getTime().replace("T", " ")))).append("<p>&nbsp&nbsp").append(LiuYanActivity.this.getUrl(((LiuYan) LiuYanActivity.this.list.get(arg2)).getContent())).toString(), "text/html", "utf-8", null);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.myapplication = (Myapplication) getApplication();
        setContentView(R.layout.activity_liuyan);
        initview();
        setvalue();
    }

    private void setvalue() {
        try {
            this.liuyan_list.setAdapter(new LiuYanAdapter(this, this.list));
        } catch (Exception e) {
        }
    }

    private void initview() {
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.liuyan_top = (TextView) findViewById(R.id.liuyan_top);
        this.liuyan_top.setText("首页/留言信息");
        this.liuyan_list = (ListView) findViewById(R.id.liuyan_list);
        this.liuyan_list.setOnItemClickListener(new OtherLiuyan());
        this.liuyan_webview = (WebView) findViewById(R.id.liuyan_webview);
        this.liuyan_webview.setBackgroundColor(0);
        try {
            this.list = this.myapplication.getLiuYans();
            this.liuyan_webview.loadDataWithBaseURL(null, center(((LiuYan) this.list.get(0)).getTime().replace("T", " ")) + "<p>&nbsp&nbsp" + getUrl(((LiuYan) this.list.get(0)).getContent()), "text/html", "utf-8", null);
        } catch (Exception e) {
        }
    }

    private String getUrl(String url) {
        return "<html><head><title></title><meta http-equiv=\"content-type\"content=\"text/html;charset=utf-8\"></head><body><font color='white' size='6px'>" + url + "</font></body></html>";
    }

    private String center(String content) {
        return "<div align='center'><font  color='white' size='6px'>" + content + "</font></div>";
    }

    protected void onStop() {
        finish();
        super.onStop();
    }
}
