package com.ssx.spa.view.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.mstar.android.tv.TvLanguage;

public class GalleryAdapter extends BaseAdapter {
    private Context context;
    private ImageView imageView;
    private int[] images;
    private int selectItem;

    private class MyImageView extends ImageView {
        public MyImageView(GalleryAdapter galleryAdapter, Context context) {
            this(context, null);
        }

        public MyImageView(Context context, AttributeSet attrs) {
            super(context, attrs, 0);
        }

        public MyImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }
    }

    public GalleryAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    public void setSelectItem(int selectItem) {
        if (this.selectItem != selectItem) {
            this.selectItem = selectItem;
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        return this.images.length;
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new MyImageView(this, this.context);
            convertView.setLayoutParams(new LayoutParams(TvLanguage.BUGINESE, TvLanguage.GA));
        }
        if (this.selectItem == position) {
            this.imageView.setLayoutParams(new ViewGroup.LayoutParams(TvLanguage.QUECHUA, TvLanguage.PALAUAN));
        }
        if (position == this.selectItem - 1 || position == this.selectItem + 1) {
            this.imageView.setLayoutParams(new ViewGroup.LayoutParams(313, TvLanguage.MANIPURI));
        }
        ImageView imageView = (ImageView) convertView;
        imageView.setBackgroundResource(this.images[position]);
        imageView.setScaleType(ScaleType.FIT_XY);
        return imageView;
    }
}
