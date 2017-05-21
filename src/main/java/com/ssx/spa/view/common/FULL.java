package com.ssx.spa.view.common;

import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

public class FULL {
    public static void star(View view) {
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(12);
        layoutParams.addRule(10);
        layoutParams.addRule(9);
        layoutParams.addRule(11);
        view.setLayoutParams(layoutParams);
    }
}
