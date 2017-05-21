package com.ssx.spa.common;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDiag {
    private static ProgressDialog dialog;

    public static void on(Context context, String msg) {
        try {
            dialog = new ProgressDialog(context);
            off();
            dialog.setMessage(msg);
            dialog.show();
        } catch (Exception e) {
        }
    }

    public static void off() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
        }
    }
}
