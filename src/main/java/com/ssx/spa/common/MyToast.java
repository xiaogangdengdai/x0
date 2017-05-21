package com.ssx.spa.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ssx.spa.R;

public class MyToast {
    public static void makeshow(Context context, String massage, int show_length) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        ((ImageView) view.findViewById(R.id.toast_iv)).setImageResource(R.drawable.ic_launcher);
        ((TextView) view.findViewById(R.id.toast_tv)).setText(massage);
        Toast toast = new Toast(context);
        toast.setGravity(17, 0, 0);
        toast.setDuration(show_length);
        toast.setView(view);
        toast.show();
    }
}
