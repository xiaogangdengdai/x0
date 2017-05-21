package com.ssx.spa.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.javabean.Song;
import java.util.List;

public class MusicAdapter extends BaseAdapter {
    private Context context;
    private List<Song> list;

    public MusicAdapter(Context context, List<Song> list) {
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
        View view = LayoutInflater.from(this.context).inflate(R.layout.adapter_music, null);
        TextView music_author = (TextView) view.findViewById(R.id.music_author);
        TextView music_info = (TextView) view.findViewById(R.id.music_info);
        ((TextView) view.findViewById(R.id.music_name)).setText(((Song) this.list.get(position)).getSongName());
        music_author.setText(((Song) this.list.get(position)).getSinger());
        music_info.setText(((Song) this.list.get(position)).getSongYinpin());
        return view;
    }
}
