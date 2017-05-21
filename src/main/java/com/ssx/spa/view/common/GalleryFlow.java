package com.ssx.spa.view.common;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class GalleryFlow extends Gallery {
    private Camera mCamera;
    private int mCoveflowCenter;
    private int mMaxRotationAngle;
    private int mMaxZoom;

    public GalleryFlow(Context context) {
        this(context, null);
    }

    public GalleryFlow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCamera = new Camera();
        this.mMaxRotationAngle = 60;
        this.mMaxZoom = -120;
        this.mCoveflowCenter = 0;
        setStaticTransformationsEnabled(true);
        setChildrenDrawingOrderEnabled(true);
    }

    public int getMaxRotationAngle() {
        return this.mMaxRotationAngle;
    }

    public void setMaxRotationAngle(int maxRotationAngle) {
        this.mMaxRotationAngle = maxRotationAngle;
    }

    public int getMaxZoom() {
        return this.mMaxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        this.mMaxZoom = maxZoom;
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        int selectedIndex = getSelectedItemPosition() - getFirstVisiblePosition();
        if (selectedIndex >= 0 && i >= selectedIndex && i >= selectedIndex) {
            return ((childCount - 1) - i) + selectedIndex;
        }
        return i;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int getCenterOfView(View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }

    protected boolean getChildStaticTransformation(View child, Transformation t) {
        super.getChildStaticTransformation(child, t);
        int childCenter = getCenterOfView(child);
        int childWidth = child.getWidth();
        t.clear();
        t.setTransformationType(2);
        if (childCenter == this.mCoveflowCenter) {
            transformImageBitmap(child, t, 0);
        } else {
            int rotationAngle = (int) ((((float) (this.mCoveflowCenter - childCenter)) / ((float) childWidth)) * ((float) this.mMaxRotationAngle));
            if (Math.abs(rotationAngle) > this.mMaxRotationAngle) {
                if (rotationAngle < 0) {
                    rotationAngle = -this.mMaxRotationAngle;
                } else {
                    rotationAngle = this.mMaxRotationAngle;
                }
            }
            transformImageBitmap(child, t, rotationAngle);
        }
        return true;
    }

    private int getCenterOfCoverflow() {
        return (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
    }

    private void transformImageBitmap(View child, Transformation t, int rotationAngle) {
        this.mCamera.save();
        Matrix imageMatrix = t.getMatrix();
        int imageHeight = child.getHeight();
        int imageWidth = child.getWidth();
        int rotation = Math.abs(rotationAngle);
        this.mCamera.translate(0.0f, 0.0f, (float) this.mMaxZoom);
        if (rotation < this.mMaxRotationAngle) {
            this.mCamera.translate(0.0f, 0.0f, ((float) this.mMaxZoom) + (((float) rotation) * 1.5f));
        }
        this.mCamera.rotateY((float) rotationAngle);
        this.mCamera.getMatrix(imageMatrix);
        imageMatrix.postTranslate((float) (imageWidth / 2), (float) (imageHeight / 2));
        imageMatrix.preTranslate((float) (-(imageWidth / 2)), (float) (-(imageHeight / 2)));
        this.mCamera.restore();
    }
}
