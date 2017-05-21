package com.ssx.spa.view.msg;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.mstar.android.tv.TvLanguage;

public class MarqueeToast {
    private Context context;
    private int mGravity = 80;
    float mHorizontalMargin;
    private final LayoutParams mParams = new LayoutParams();
    float mVerticalMargin;
    View mView;
    WindowManager mWindowManager;
    private int mX;
    private int mY;

    public MarqueeToast(Context context) {
        this.context = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        LayoutParams params = this.mParams;
        params.height = -2;
        params.width = this.mWindowManager.getDefaultDisplay().getWidth();
        params.type = 2007;
        params.flags = 40;
        params.format = -3;
        params.setTitle("Toast");
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
        this.mHorizontalMargin = horizontalMargin;
        this.mVerticalMargin = verticalMargin;
    }

    public void setView(View mView) {
        this.mView = mView;
    }

    public void show() {
        if (this.mView != null) {
            int gravity = this.mGravity;
            this.mParams.gravity = gravity;
            if ((gravity & 7) == 7) {
                this.mParams.horizontalWeight = 0.0f;
            }
            if ((gravity & TvLanguage.BURMESE) == TvLanguage.BURMESE) {
                this.mParams.verticalWeight = 0.0f;
            }
            this.mParams.x = this.mX;
            this.mParams.y = this.mY;
            this.mParams.horizontalMargin = this.mHorizontalMargin;
            this.mParams.verticalMargin = this.mVerticalMargin;
            if (this.mView.getParent() != null) {
                this.mWindowManager.removeView(this.mView);
            }
            this.mWindowManager.addView(this.mView, this.mParams);
        }
    }

    public void hid() {
        if (this.mView != null && this.mView.getParent() != null) {
            this.mWindowManager.removeView(this.mView);
        }
    }

    public void setGravity(int gravity, int with, int xOffset, int yOffset) {
        this.mGravity = gravity;
        this.mX = xOffset;
        this.mY = yOffset;
        this.mParams.width = with;
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        this.mGravity = gravity;
        this.mX = xOffset;
        this.mY = yOffset;
    }

    public void setHeight(int height) {
        this.mParams.height = height;
    }

    public void setWidth(int width) {
        this.mParams.width = width;
    }
}
