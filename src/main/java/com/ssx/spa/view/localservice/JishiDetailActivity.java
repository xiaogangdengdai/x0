package com.ssx.spa.view.localservice;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.JishiDetail;
import com.ssx.spa.view.BaseActivity;

public class JishiDetailActivity extends BaseActivity {
    private ImageButton anpai;
    private ImageButton back;
    private JishiDetail jishiDetail;
    private TextView jishidetail_age;
    private TextView jishidetail_area;
    private TextView jishidetail_height;
    private ImageView jishidetail_img;
    private TextView jishidetail_iswork;
    private TextView jishidetail_no;
    private TextView jishidetail_sex;
    private TextView jishidetail_status;
    private TextView jishidetail_top;
    private TextView jishidetail_type;
    private Myapplication myapplication;
    private String top;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            JishiDetailActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.myapplication = (Myapplication) getApplication();
        super.onCreate(savedInstanceState);
        this.jishiDetail = (JishiDetail) getIntent().getSerializableExtra("JishiDetail");
        this.top = getIntent().getStringExtra("top");
        setContentView(R.layout.activity_jishidetail);
        initview();
        setvalue();
    }

    private void setvalue() {
        this.anpai.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyToast.makeshow(JishiDetailActivity.this, "安排成功！", 0);
            }
        });
    }

    private void initview() {
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.jishidetail_top = (TextView) findViewById(R.id.jishidetail_top);
        this.jishidetail_top.setText(this.top + "/" + this.jishiDetail.getName());
        this.anpai = (ImageButton) findViewById(R.id.anpai);
        this.jishidetail_img = (ImageView) findViewById(R.id.jishidetail_img);
        Picasso.with(this).load(this.jishiDetail.getImg()).into(this.jishidetail_img);
        this.jishidetail_no = (TextView) findViewById(R.id.jishidetail_no);
        this.jishidetail_sex = (TextView) findViewById(R.id.jishidetail_sex);
        this.jishidetail_age = (TextView) findViewById(R.id.jishidetail_age);
        this.jishidetail_height = (TextView) findViewById(R.id.jishidetail_height);
        this.jishidetail_type = (TextView) findViewById(R.id.jishidetail_type);
        this.jishidetail_area = (TextView) findViewById(R.id.jishidetail_area);
        this.jishidetail_iswork = (TextView) findViewById(R.id.jishidetail_iswork);
        this.jishidetail_status = (TextView) findViewById(R.id.jishidetail_status);
        try {
            this.jishidetail_no.setText("技师号：" + this.jishiDetail.getNumber());
            this.jishidetail_sex.setText("性别：" + this.jishiDetail.getSex());
            this.jishidetail_age.setText("年龄：" + this.jishiDetail.getAge());
            this.jishidetail_height.setText("身高：" + this.jishiDetail.getHeigh());
            this.jishidetail_area.setText("籍贯：" + this.jishiDetail.getNativePlace());
            this.jishidetail_type.setText("技师类型：" + this.jishiDetail.getLevel().getName());
            if (this.jishiDetail.getOnDuty() == 0) {
                this.jishidetail_iswork.setText("是否当值：否");
            } else {
                this.jishidetail_iswork.setText("是否当值：是");
            }
            if (this.jishiDetail.getStatus() == 0) {
                this.jishidetail_status.setText("状态：空闲");
            } else {
                this.jishidetail_status.setText("状态：忙碌");
            }
        } catch (Exception e) {
        }
    }
}
