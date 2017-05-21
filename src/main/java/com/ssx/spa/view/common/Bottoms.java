package com.ssx.spa.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.HeadSwitch;

public class Bottoms extends LinearLayout {
    private Context context;
    ImageButton hujiaoBtn;
    ImageButton liuzuoBtn;
    private Myapplication myapplication;

    public Bottoms(Context context, int resId, String name) {
        super(context);
        this.context = context;
        initView();
    }

    public Bottoms(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.myapplication = (Myapplication) context.getApplicationContext();
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottoms, this);
        this.liuzuoBtn = (ImageButton) view.findViewById(R.id.liuzuoBtn);
        this.hujiaoBtn = (ImageButton) view.findViewById(R.id.hujiaoBtn);
        int i = 0;
        while (i < this.myapplication.getHeadSwitchs().size()) {
            try {
                if (((HeadSwitch) this.myapplication.getHeadSwitchs().get(i)).getId() == 1 && ((HeadSwitch) this.myapplication.getHeadSwitchs().get(i)).getStatus() == 1) {
                    this.liuzuoBtn.setVisibility(0);
                }
                if (((HeadSwitch) this.myapplication.getHeadSwitchs().get(i)).getId() == 2 && ((HeadSwitch) this.myapplication.getHeadSwitchs().get(i)).getStatus() == 1) {
                    this.hujiaoBtn.setVisibility(0);
                }
                i++;
            } catch (Exception e) {
                return;
            }
        }
        this.hujiaoBtn.setVisibility(0);
    }
}
