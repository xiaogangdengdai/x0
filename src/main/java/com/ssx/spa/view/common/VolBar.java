package com.ssx.spa.view.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.SeekBar;
import com.android.volley.DefaultRetryPolicy;

public class VolBar extends SeekBar {
    private static final int[] SECTION_COLORS = new int[]{-16711936, -256, SupportMenu.CATEGORY_MASK};
    private float currentCount;
    private int mHeight;
    private Paint mPaint;
    private int mWidth;
    private float maxCount;

    public VolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public VolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VolBar(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        int round = this.mHeight / 2;
        System.out.println("max=" + this.maxCount + "  current=" + this.currentCount);
        this.mPaint.setColor(Color.rgb(71, 76, 80));
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight), (float) round, (float) round, this.mPaint);
        this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawRoundRect(new RectF(2.0f, 2.0f, (float) (this.mWidth - 2), (float) (this.mHeight - 2)), (float) round, (float) round, this.mPaint);
        float section = this.currentCount / this.maxCount;
        RectF rectProgressBg = new RectF(3.0f, 3.0f, ((float) (this.mWidth - 3)) * section, (float) (this.mHeight - 3));
        if (section > 0.33333334f) {
            int count = section <= 0.6666667f ? 2 : 3;
            int[] colors = new int[count];
            System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
            float[] positions = new float[count];
            if (count == 2) {
                positions[0] = 0.0f;
                positions[1] = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - positions[0];
            } else {
                positions[0] = 0.0f;
                positions[1] = (this.maxCount / 3.0f) / this.currentCount;
                positions[2] = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - (positions[0] * 2.0f);
            }
            positions[positions.length - 1] = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
            this.mPaint.setShader(new LinearGradient(3.0f, 3.0f, ((float) (this.mWidth - 3)) * section, (float) (this.mHeight - 3), colors, null, TileMode.MIRROR));
        } else if (section != 0.0f) {
            this.mPaint.setColor(SECTION_COLORS[0]);
        } else {
            this.mPaint.setColor(0);
        }
        canvas.drawRoundRect(rectProgressBg, (float) round, (float) round, this.mPaint);
    }

    private int dipToPx(int dip) {
        return (int) ((((float) (dip >= 0 ? 1 : -1)) * 0.5f) + (((float) dip) * getContext().getResources().getDisplayMetrics().density));
    }

    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    public void setCurrentCount(float currentCount) {
        if (currentCount > this.maxCount) {
            currentCount = this.maxCount;
        }
        this.currentCount = currentCount;
        invalidate();
    }

    public float getMaxCount() {
        return this.maxCount;
    }

    public float getCurrentCount() {
        return this.currentCount;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == 1073741824 || widthSpecMode == ExploreByTouchHelper.INVALID_ID) {
            this.mWidth = widthSpecSize;
        } else {
            this.mWidth = 0;
        }
        if (heightSpecMode == ExploreByTouchHelper.INVALID_ID || heightSpecMode == 0) {
            this.mHeight = dipToPx(15);
        } else {
            this.mHeight = heightSpecSize;
        }
        setMeasuredDimension(this.mWidth, this.mHeight);
    }
}
