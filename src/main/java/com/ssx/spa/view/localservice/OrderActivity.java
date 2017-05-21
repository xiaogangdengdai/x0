package com.ssx.spa.view.localservice;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.view.BaseActivity;

public class OrderActivity extends BaseActivity {
    private ImageButton back;
    private Myapplication myapplication;
    private TextView order_top;
    private int top;
    private String tops;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            OrderActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        this.myapplication = (Myapplication) getApplication();
        initview();
        setvalue();
    }

    private void setvalue() {
    }

    private void initview() {
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.order_top = (TextView) findViewById(R.id.order_top);
        this.tops = "首页/" + getIntent().getStringExtra("top");
        this.order_top.setText(this.tops);
    }
}
