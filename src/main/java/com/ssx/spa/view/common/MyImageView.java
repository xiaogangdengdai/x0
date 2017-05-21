package com.ssx.spa.view.common;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
