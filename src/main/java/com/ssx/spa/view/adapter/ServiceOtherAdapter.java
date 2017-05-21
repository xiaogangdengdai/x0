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
import com.ssx.spa.javabean.siList;
import java.util.List;

public class ServiceOtherAdapter extends BaseAdapter {
    private Context context;
    private List<siList> list;

    public ServiceOtherAdapter(Context context, List<siList> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        try {
            if (this.list.size() < 8) {
                return this.list.size();
            }
            return 8;
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
        View view = LayoutInflater.from(this.context).inflate(R.layout.adapter_game, null);
        Picasso.with(this.context).load(((siList) this.list.get(position)).getLogo()).into((ImageView) view.findViewById(R.id.adaptergame_img));
        ((TextView) view.findViewById(R.id.adaptergame_name)).setText(((siList) this.list.get(position)).getName());
        return view;
    }
}
