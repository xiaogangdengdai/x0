package com.ssx.spa.view.common;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

class Backs extends Activity implements OnClickListener {
    Backs() {
    }

    public void onClick(View v) {
        finish();
    }
}
