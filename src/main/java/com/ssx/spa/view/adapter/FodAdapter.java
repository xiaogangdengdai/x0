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
import com.ssx.spa.javabean.FodDetail;
import java.util.List;

public class FodAdapter extends BaseAdapter {
    private Context context;
    private List<FodDetail> list;

    public FodAdapter(Context context, List<FodDetail> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        try {
            if (this.list.size() < 10) {
                return this.list.size();
            }
            return 10;
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
        View view = LayoutInflater.from(this.context).inflate(R.layout.adapter_service, null);
        Picasso.with(this.context).load(((FodDetail) this.list.get(position)).getImg()).into((ImageView) view.findViewById(R.id.adapterservice_img));
        ((TextView) view.findViewById(R.id.adapterservice_name)).setText(((FodDetail) this.list.get(position)).getName());
        return view;
    }
}
