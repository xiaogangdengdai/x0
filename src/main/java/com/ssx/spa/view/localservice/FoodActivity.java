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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.FodCategory;
import com.ssx.spa.javabean.FodDetail;
import com.ssx.spa.javabean.LocalService;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.adapter.FodAdapter;
import com.ssx.spa.view.adapter.FodCategoryAdapter;
import java.io.Serializable;
import java.util.List;

public class FoodActivity extends BaseActivity implements OnTouchListener {
    private final int MAXSIZE = 10;
    private ImageButton back;
    private List<FodCategory> categories;
    private int current = 0;
    private int currentposition = 0;
    private List<FodDetail> fod;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 108:
                    try {
                        FoodActivity.this.categories = FoodActivity.this.myapplication.getFodCategories();
                        FoodActivity.this.service_list.setAdapter(new FodCategoryAdapter(FoodActivity.this, FoodActivity.this.categories));
                        FoodActivity.this.service_list.setOnItemClickListener(new Jishilist());
                        new GetFodDate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
                        break;
                    } catch (Exception e) {
                        break;
                    }
            }
            super.handleMessage(msg);
        }
    };
    private Myapplication myapplication;
    private GridView service_grid;
    private ListView service_list;
    private TextView service_top;
    private String top;
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
            FoodActivity.this.finish();
        }
    }

    class GetFodDate extends AsyncTask<String, String, String> {
        GetFodDate() {
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            FoodActivity.this.fod = FoodActivity.this.myapplication.getFodDetails();
            FoodActivity.this.setvalue();
        }

        protected String doInBackground(String... params) {
            try {
                FoodActivity.this.myapplication.setFodDetails(JsonResult.getfod(FoodActivity.this, Myapplication.MAC, ((FodCategory) FoodActivity.this.categories.get(FoodActivity.this.currentposition)).getId()));
            } catch (Exception e) {
            }
            return null;
        }
    }

    class GridClick implements OnItemClickListener {
        GridClick() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("fodDetail", (Serializable) FoodActivity.this.fod.get((FoodActivity.this.current * 10) + arg2));
            FoodActivity.this.startActivity(new Intent(FoodActivity.this, FodDetailActivity.class).putExtras(bundle).putExtra("top", FoodActivity.this.top));
        }
    }

    class Jishilist implements OnItemClickListener {
        Jishilist() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            FoodActivity.this.currentposition = arg2;
            new GetFodDate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
        }
    }

    class Turn implements OnClickListener {
        Turn() {
        }

        public void onClick(View v) {
            FoodActivity foodActivity;
            switch (v.getId()) {
                case R.id.turn_letf:
                    if (FoodActivity.this.current > 0) {
                        foodActivity = FoodActivity.this;
                        foodActivity.current = foodActivity.current - 1;
                        break;
                    }
                    break;
                case R.id.turn_right:
                    if (FoodActivity.this.current < ((int) Math.ceil(((double) FoodActivity.this.fod.size()) / 10.0d)) - 1) {
                        foodActivity = FoodActivity.this;
                        foodActivity.current = foodActivity.current + 1;
                        break;
                    }
                    break;
            }
            FoodActivity.this.setvalue();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.myapplication = (Myapplication) getApplication();
        setContentView(R.layout.activity_jishi);
        initview();
    }

    private void setvalue() {
        try {
            if ((this.current + 1) * 10 > this.fod.size()) {
                this.service_grid.setAdapter(new FodAdapter(this, this.fod.subList(this.current * 10, this.fod.size())));
            } else {
                this.service_grid.setAdapter(new FodAdapter(this, this.fod.subList(this.current * 10, (this.current + 1) * 10)));
            }
        } catch (Exception e) {
        }
    }

    private void initview() {
        this.service_top = (TextView) findViewById(R.id.service_top);
        this.top = "首页/" + ((LocalService) this.myapplication.getLocalServices().get(1)).getName();
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
        new JsonResult(this, this.myapplication, this.handler).getfodcategory();
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
                    if (this.current < ((int) Math.ceil(((double) this.fod.size()) / 10.0d)) - 1) {
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
