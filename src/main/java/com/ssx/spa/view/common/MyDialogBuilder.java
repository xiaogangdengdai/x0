package com.ssx.spa.view.common;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;

public class MyDialogBuilder {
    private static EditText text;
    private BroadcastReceiver DataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("7777")) {
                String msg2 = intent.getStringExtra("123");
                System.out.println(new StringBuilder(String.valueOf(msg2)).append("msg2msg2msg2msg2msg2msg2msg2msg2msg2msg2msg2").toString());
                MyDialogBuilder.this.app.tmpEditText.setText(msg2);
                if (MyDialogBuilder.this.completeHandler != null) {
                    MyDialogBuilder.this.completeHandler.onComplete(msg2, MyDialogBuilder.this.dialog);
                }
            }
        }
    };
    Myapplication app;
    private Context c;
    private OnCompleteHandler completeHandler;
    private Dialog dialog;
    private String msg;
    private OnDialogClose onDialogClose;
    private long startInputTime = 0;

    public interface OnCompleteHandler {
        void onComplete(String str, Dialog dialog);
    }

    public interface OnDialogClose {
        void close();
    }

    public MyDialogBuilder(Context context) {
        this.c = context;
    }

    public MyDialogBuilder(String msg, Context context, OnCompleteHandler completeHandler, OnDialogClose onDialogClose) {
        this.msg = msg;
        this.c = context;
        this.completeHandler = completeHandler;
        this.onDialogClose = onDialogClose;
        this.app = (Myapplication) context.getApplicationContext();
        IntentFilter filter = new IntentFilter();
        filter.addAction("7777");
        context.registerReceiver(this.DataReceiver, filter);
    }

    public void setCompleteHandler(OnCompleteHandler c) {
        this.completeHandler = c;
    }

    public AlertDialog buildInputIdDialog() {
        Builder builder = new Builder(this.c);
        builder.setView(createContent());
        builder.setNegativeButton("取消", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Log.d("TAG", "取消刷手牌");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialog) {
                MyDialogBuilder.text.requestFocus();
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                if (MyDialogBuilder.this.onDialogClose != null) {
                    MyDialogBuilder.this.onDialogClose.close();
                }
                MyDialogBuilder.this.c.unregisterReceiver(MyDialogBuilder.this.DataReceiver);
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        this.dialog = dialog;
        return dialog;
    }

    private View createContent() {
        View view = LayoutInflater.from(this.c).inflate(R.layout.swipe_card_content, null);
        ((TextView) view.findViewById(R.id.txt)).setText(this.msg);
        text = (EditText) view.findViewById(R.id.cardNoTxt);
        this.app.tmpEditText = text;
        this.app.tmpEditText.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getKeyCode() == 66) {
                    String s = v.getText().toString();
                    if (s.length() >= 10) {
                        s = s.substring(s.length() - 10, s.length());
                    }
                    v.setText(s);
                    String id = v.getText().toString();
                    if (MyDialogBuilder.this.completeHandler != null) {
                        MyDialogBuilder.this.completeHandler.onComplete(id, MyDialogBuilder.this.dialog);
                    }
                }
                return true;
            }
        });
        text.setInputType(0);
        return view;
    }
}
