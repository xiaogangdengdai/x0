package com.ssx.spa.view.common;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.widget.EditText;

public class AlertDialogDIY {
    public static AlertDialog show(Context context, int layout) {
        AlertDialog dialog = new Builder(context).create();
        dialog.setView(new EditText(context));
        dialog.show();
        dialog.setContentView(layout);
        return dialog;
    }
}
