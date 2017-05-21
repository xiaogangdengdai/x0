package com.ssx.spa.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.javabean.Videos;
import java.util.List;

public class DramaAdapter extends BaseAdapter {
    private Context context;
    private List<Videos> list;

    public DramaAdapter(Context context, List<Videos> videos) {
        this.context = context;
        this.list = videos;
    }

    public int getCount() {
        try {
            return this.list.size();
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
        View view = LayoutInflater.from(this.context).inflate(R.layout.textview, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setTextColor(-1);
        name.setText(((Videos) this.list.get(position)).getVoidname());
        return view;
    }
}
