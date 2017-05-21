package com.ssx.spa.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.ssx.spa.R;

public class MediaCtrolls extends LinearLayout {
    public MediaCtrolls(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.mediac, this);
    }
}
