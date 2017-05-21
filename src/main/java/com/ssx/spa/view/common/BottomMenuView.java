package com.ssx.spa.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.ssx.spa.R;

public class BottomMenuView extends FrameLayout {
    private Context context;

    public BottomMenuView(Context context) {
        this(context, null);
    }

    public BottomMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_bottom_menu, this);
    }
}
