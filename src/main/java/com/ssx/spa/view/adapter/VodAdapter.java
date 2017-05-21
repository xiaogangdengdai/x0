package com.ssx.spa.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.ssx.spa.R;
import com.ssx.spa.javabean.VoidPrograms;
import java.util.List;

public class VodAdapter extends BaseAdapter {
    private Context context;
    private List<VoidPrograms> list;

    public VodAdapter(Context context, List<VoidPrograms> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        try {
            if (this.list.size() < 12) {
                return this.list.size();
            }
            return 12;
        } catch (Exception e) {
            return 0;
        }
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.adapter_vod, null);
        Picasso.with(this.context).load(((VoidPrograms) this.list.get(position)).getVoidprogramimg()).into((ImageView) view.findViewById(R.id.adaptervod_img));
        ((TextView) view.findViewById(R.id.adaptervod_name)).setText(((VoidPrograms) this.list.get(position)).getVoidprogramname());
        return view;
    }
}
