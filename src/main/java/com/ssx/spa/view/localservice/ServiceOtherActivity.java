package com.ssx.spa.view.localservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.LocalService;
import com.ssx.spa.javabean.siList;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.adapter.ServiceOtherAdapter;
import java.io.Serializable;
import java.util.List;

public class ServiceOtherActivity extends BaseActivity implements OnTouchListener {
    private final int MAXSIZE = 8;
    private ImageButton back;
    private int current;
    private LocalService localServices;
    private Myapplication myapplication;
    private GridView serviceother_grid;
    private TextView serviceother_top;
    private List<siList> siLists;
    private int top;
    private String tops;
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
            ServiceOtherActivity.this.finish();
        }
    }

    class Turn implements OnClickListener {
        Turn() {
        }

        public void onClick(View v) {
            ServiceOtherActivity serviceOtherActivity;
            switch (v.getId()) {
                case R.id.turn_letf:
                    if (ServiceOtherActivity.this.current > 0) {
                        serviceOtherActivity = ServiceOtherActivity.this;
                        serviceOtherActivity.current = serviceOtherActivity.current - 1;
                        break;
                    }
                    break;
                case R.id.turn_right:
                    if (ServiceOtherActivity.this.current < ((int) Math.ceil(((double) ServiceOtherActivity.this.siLists.size()) / 8.0d)) - 1) {
                        serviceOtherActivity = ServiceOtherActivity.this;
                        serviceOtherActivity.current = serviceOtherActivity.current + 1;
                        break;
                    }
                    break;
            }
            ServiceOtherActivity.this.setvalue();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceother);
        this.myapplication = (Myapplication) getApplication();
        try {
            this.top = getIntent().getIntExtra("current", 0);
            this.localServices = (LocalService) this.myapplication.getLocalServices().get(this.top);
            this.siLists = this.localServices.getSiList();
        } catch (Exception e) {
        }
        initview();
        setvalue();
    }

    private void setListener() {
        this.serviceother_grid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("siList", (Serializable) ServiceOtherActivity.this.siLists.get((ServiceOtherActivity.this.current * 8) + position));
                ServiceOtherActivity.this.startActivity(new Intent(ServiceOtherActivity.this, ServiceOtherDetailActivity.class).putExtras(bundle).putExtra("top", ServiceOtherActivity.this.tops));
            }
        });
    }

    private void setvalue() {
        try {
            if ((this.current + 1) * 8 > this.siLists.size()) {
                this.serviceother_grid.setAdapter(new ServiceOtherAdapter(this, this.siLists.subList(this.current * 8, this.siLists.size())));
            } else {
                this.serviceother_grid.setAdapter(new ServiceOtherAdapter(this, this.siLists.subList(this.current * 8, (this.current + 1) * 8)));
            }
        } catch (Exception e) {
        }
    }

    private void initview() {
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.serviceother_top = (TextView) findViewById(R.id.serviceother_top);
        this.tops = "首页/" + this.localServices.getName();
        this.serviceother_top.setText(this.tops);
        this.serviceother_grid = (GridView) findViewById(R.id.serviceother_grid);
        this.serviceother_grid.setOnTouchListener(this);
        this.turn_letf = (ImageButton) findViewById(R.id.turn_letf);
        this.turn_right = (ImageButton) findViewById(R.id.turn_right);
        this.turn_letf.setOnClickListener(new Turn());
        this.turn_right.setOnClickListener(new Turn());
        setListener();
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
                    if (this.current < ((int) Math.ceil(((double) this.siLists.size()) / 8.0d)) - 1) {
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
