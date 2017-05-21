package com.ssx.spa.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import com.android.volley.DefaultRetryPolicy;

public class BitmapUtil {
    public static Bitmap createReflectedBitmap(Bitmap srcBitmap) {
        if (srcBitmap == null) {
            return null;
        }
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int reflectionWidth = srcBitmap.getWidth();
        int reflectionHeight = srcBitmap.getHeight() / 2;
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.preScale(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, -1.0f);
        try {
            Bitmap reflectionBitmap = Bitmap.createBitmap(srcBitmap, 0, srcHeight / 2, srcWidth, srcHeight / 2, matrix, false);
            if (reflectionBitmap == null) {
                return null;
            }
            Bitmap bitmapWithReflection = Bitmap.createBitmap(reflectionWidth, (srcHeight + reflectionHeight) + 4, Config.ARGB_8888);
            if (bitmapWithReflection == null) {
                return null;
            }
            Canvas canvas = new Canvas(bitmapWithReflection);
            canvas.drawBitmap(srcBitmap, 0.0f, 0.0f, null);
            canvas.drawBitmap(reflectionBitmap, 0.0f, (float) (srcHeight + 4), null);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new LinearGradient(0.0f, (float) srcHeight, 0.0f, (float) (bitmapWithReflection.getHeight() + 4), 1895825407, ViewCompat.MEASURED_SIZE_MASK, TileMode.MIRROR));
            paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
            canvas.drawRect(0.0f, (float) srcHeight, (float) srcWidth, (float) (bitmapWithReflection.getHeight() + 4), paint);
            return bitmapWithReflection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        return drawableToBitmap(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        if (drawable == null || width <= 0 || height <= 0) {
            return null;
        }
        Config config;
        if (drawable.getOpacity() != -1) {
            config = Config.ARGB_8888;
        } else {
            config = Config.RGB_565;
        }
        try {
            Bitmap bitmap = Bitmap.createBitmap(width, height, config);
            if (bitmap == null) {
                return bitmap;
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
