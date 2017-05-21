package com.ssx.spa.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.javabean.FodCategory;
import java.util.List;

public class FodCategoryAdapter extends BaseAdapter {
    private Context context;
    private List<FodCategory> list;

    public FodCategoryAdapter(Context context, List<FodCategory> list) {
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
        ((TextView) view.findViewById(R.id.adapterjishi_name)).setText(((FodCategory) this.list.get(position)).getName());
        return view;
    }
}
