package com.ssx.spa.view.localservice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.JiShiCategory;
import com.ssx.spa.javabean.JishiDetail;
import com.ssx.spa.javabean.LocalService;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.adapter.JiShiAdapter;
import com.ssx.spa.view.adapter.JiShiCategoryAdapter;
import java.io.Serializable;
import java.util.List;

public class JishiActivity extends BaseActivity implements OnTouchListener {
    private final int MAXSIZE = 10;
    private ImageButton back;
    private List<JiShiCategory> categories;
    private int current = 0;
    private int currentposition = 0;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 109:
                    try {
                        JishiActivity.this.categories = JishiActivity.this.myapplication.getJiShiCategories();
                        JishiActivity.this.service_list.setAdapter(new JiShiCategoryAdapter(JishiActivity.this, JishiActivity.this.categories));
                        new GetJishiDate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
                        break;
                    } catch (Exception e) {
                        break;
                    }
            }
            super.handleMessage(msg);
        }
    };
    private List<JishiDetail> jishi;
    private Myapplication myapplication;
    private GridView service_grid;
    private ListView service_list;
    private TextView service_top;
    private String top;
    private ImageButton turn_letf;
    private ImageButton turn_right;
    String url = "http://192.192.10.120/gssnart/Computer/Default3.aspx";
    WebView web_jishi;
    float x1 = 0.0f;
    float x2 = 0.0f;
    float y1 = 0.0f;
    float y2 = 0.0f;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            JishiActivity.this.finish();
        }
    }

    class GetJishiDate extends AsyncTask<String, String, String> {
        GetJishiDate() {
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JishiActivity.this.jishi = JishiActivity.this.myapplication.getJishiDetails();
            JishiActivity.this.setvalue();
        }

        protected String doInBackground(String... params) {
            try {
                JishiActivity.this.myapplication.setJishiDetails(JsonResult.getjishidetail(JishiActivity.this, Myapplication.MAC, ((JiShiCategory) JishiActivity.this.categories.get(JishiActivity.this.currentposition)).getId()));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            return null;
        }
    }

    class GridClick implements OnItemClickListener {
        GridClick() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("JishiDetail", (Serializable) JishiActivity.this.jishi.get((JishiActivity.this.current * 10) + arg2));
            JishiActivity.this.startActivity(new Intent(JishiActivity.this, JishiDetailActivity.class).putExtras(bundle).putExtra("top", JishiActivity.this.top));
        }
    }

    class Jishilist implements OnItemClickListener {
        Jishilist() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            JishiActivity.this.currentposition = arg2;
            new GetJishiDate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
        }
    }

    class Turn implements OnClickListener {
        Turn() {
        }

        public void onClick(View v) {
            JishiActivity jishiActivity;
            switch (v.getId()) {
                case R.id.turn_letf:
                    if (JishiActivity.this.current > 0) {
                        jishiActivity = JishiActivity.this;
                        jishiActivity.current = jishiActivity.current - 1;
                        break;
                    }
                    break;
                case R.id.turn_right:
                    if (JishiActivity.this.current < ((int) Math.ceil(((double) JishiActivity.this.jishi.size()) / 10.0d)) - 1) {
                        jishiActivity = JishiActivity.this;
                        jishiActivity.current = jishiActivity.current + 1;
                        break;
                    }
                    break;
            }
            JishiActivity.this.setvalue();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.myapplication = (Myapplication) getApplication();
        setContentView(R.layout.activity_jishi);
        initview();
        new JsonResult(this, this.myapplication, this.handler).getjishi();
    }

    private void date() {
        this.web_jishi = (WebView) findViewById(R.id.web_jishi);
        try {
            WebSettings websettings = this.web_jishi.getSettings();
            websettings.setJavaScriptEnabled(true);
            websettings.setBuiltInZoomControls(true);
            this.web_jishi.loadUrl(this.url);
            this.web_jishi.setBackgroundColor(0);
            this.web_jishi.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }

    private void setvalue() {
        try {
            if ((this.current + 1) * 10 > this.jishi.size()) {
                this.service_grid.setAdapter(new JiShiAdapter(this, this.jishi.subList(this.current * 10, this.jishi.size())));
            } else {
                this.service_grid.setAdapter(new JiShiAdapter(this, this.jishi.subList(this.current * 10, (this.current + 1) * 10)));
            }
        } catch (Exception e) {
        }
    }

    private void initview() {
        this.service_top = (TextView) findViewById(R.id.service_top);
        this.top = "首页/" + ((LocalService) this.myapplication.getLocalServices().get(0)).getName();
        this.service_top.setText(this.top);
        this.service_grid = (GridView) findViewById(R.id.service_grid);
        this.service_grid.setOnItemClickListener(new GridClick());
        this.service_grid.setOnTouchListener(this);
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.turn_letf = (ImageButton) findViewById(R.id.turn_letf);
        this.turn_right = (ImageButton) findViewById(R.id.turn_right);
        this.turn_letf.setOnClickListener(new Turn());
        this.turn_right.setOnClickListener(new Turn());
        this.service_list = (ListView) findViewById(R.id.service_list);
        this.service_list.setOnItemClickListener(new Jishilist());
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
                    if (this.current < ((int) Math.ceil(((double) this.jishi.size()) / 10.0d)) - 1) {
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
}
