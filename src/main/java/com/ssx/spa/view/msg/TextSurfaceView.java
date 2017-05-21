package com.ssx.spa.view.msg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

public class TextSurfaceView extends SurfaceView implements Callback, Runnable {
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 0;
    private String bgColor;
    private int bgalpha;
    private String content;
    private int fontAlpha;
    private String fontColor;
    private float fontSize;
    private boolean isMove;
    private boolean loop;
    private SurfaceHolder mSurfaceHolder;
    private int orientation;
    IScrollState scroolState;
    private long speed;
    private float x;

    public TextSurfaceView(Context context) {
        super(context);
        this.isMove = true;
        this.orientation = 0;
        this.speed = 10;
        this.bgColor = "#FF0000";
        this.bgalpha = 0;
        this.fontColor = "#FFFFFF";
        this.fontAlpha = 255;
        this.fontSize = 25.0f;
        this.loop = true;
        this.x = 0.0f;
        this.mSurfaceHolder = getHolder();
        this.mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        this.mSurfaceHolder.setFormat(-3);
        setBackgroundColor(Color.parseColor(this.bgColor));
        getBackground().setAlpha(this.bgalpha);
    }

    public TextSurfaceView(Context context, IScrollState scroolState) {
        super(context);
        this.isMove = true;
        this.orientation = 0;
        this.speed = 10;
        this.bgColor = "#FF0000";
        this.bgalpha = 0;
        this.fontColor = "#FFFFFF";
        this.fontAlpha = 255;
        this.fontSize = 25.0f;
        this.loop = true;
        this.x = 0.0f;
        this.mSurfaceHolder = getHolder();
        this.mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        this.mSurfaceHolder.setFormat(-3);
        setBackgroundColor(Color.parseColor(this.bgColor));
        getBackground().setAlpha(this.bgalpha);
        this.scroolState = scroolState;
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                System.out.println("你点击了跑马灯@@@@@@@@@@@@@@@@");
            }
        });
    }

    public TextSurfaceView(Context context, boolean move) {
        this(context);
        this.isMove = move;
        setLoop(isMove());
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("WIDTH:", getWidth()+"");
        if (this.isMove) {
            if (this.orientation == 1) {
                this.x = (float) getWidth();
            } else {
                this.x = (float) (-(this.content.length() * 10));
            }
            new Thread(this).start();
            return;
        }
        draw();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.loop = false;
    }

    private void draw() {
        Canvas canvas = this.mSurfaceHolder.lockCanvas();
        if (this.mSurfaceHolder != null && canvas != null) {
            Paint paint = new Paint();
            canvas.drawColor(0, Mode.CLEAR);
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.SANS_SERIF);
            paint.setTextSize(this.fontSize);
            paint.setColor(Color.parseColor(this.fontColor));
            paint.setAlpha(this.fontAlpha);
            canvas.drawText(this.content, this.x, (float) ((getHeight() / 2) + 5), paint);
            this.mSurfaceHolder.unlockCanvasAndPost(canvas);
            if (this.isMove) {
                float conlen = paint.measureText(this.content);
                int w = getWidth();
                if (this.orientation == 1) {
                    if (this.x < (-conlen)) {
                        this.x = (float) w;
                        if (this.scroolState != null) {
                            this.scroolState.stop();
                            return;
                        }
                        return;
                    }
                    this.x -= 2.0f;
                } else if (this.orientation != 0) {
                } else {
                    if (this.x >= ((float) w)) {
                        this.x = -conlen;
                        if (this.scroolState != null) {
                            this.scroolState.stop();
                            return;
                        }
                        return;
                    }
                    this.x += 2.0f;
                }
            }
        }
    }

    public void run() {
        while (this.loop) {
            synchronized (this.mSurfaceHolder) {
                draw();
            }
            try {
                Thread.sleep(this.speed);
            } catch (InterruptedException ex) {
                Log.e("TextSurfaceView", ex.getMessage() + "\n" + ex);
            }
        }
        this.content = null;
    }

    private int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    private long getSpeed() {
        return this.speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public boolean isMove() {
        return this.isMove;
    }

    public void setMove(boolean isMove) {
        this.isMove = isMove;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setBgalpha(int bgalpha) {
        this.bgalpha = bgalpha;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontAlpha(int fontAlpha) {
        this.fontAlpha = fontAlpha;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }
}
