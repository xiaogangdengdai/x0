package com.ssx.spa.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.javabean.JiShiCategory;
import java.util.List;

public class JiShiCategoryAdapter extends BaseAdapter {
    private Context context;
    private List<JiShiCategory> list;

    public JiShiCategoryAdapter(Context context, List<JiShiCategory> list) {
        this.context = context;
        this.list = list;
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
        View view = LayoutInflater.from(this.context).inflate(R.layout.adapter_jishicategory, null);
        ((TextView) view.findViewById(R.id.adapterjishi_name)).setText(((JiShiCategory) this.list.get(position)).getName());
        return view;
    }
}
