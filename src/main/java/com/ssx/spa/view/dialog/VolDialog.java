package com.ssx.spa.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.config.VolInfo;

public class VolDialog implements OnSeekBarChangeListener {
    static AlertDialog vol;
    Activity activity;
    AudioManager audioManager;
    Handler handler;
    SeekBar init_bar;
    TextView init_txt;
    SeekBar max_bar;
    TextView max_txt;

    public VolDialog(Handler handler, Activity activity) {
        this.activity = activity;
        this.handler = handler;
        this.audioManager = (AudioManager) activity.getSystemService("audio");
    }

    public void crt() {
        vol = new Builder(this.activity).create();
        if (vol == null || !vol.isShowing()) {
            vol.show();
        } else {
            vol.dismiss();
        }
        vol.setContentView(R.layout.vol_dialog);
        this.init_bar = (SeekBar) vol.findViewById(R.id.init_bar);
        this.init_bar.setMax(this.audioManager.getStreamMaxVolume(3));
        this.init_bar.setProgress(VolInfo.getVol(this.activity));
        this.max_bar = (SeekBar) vol.findViewById(R.id.max_bar);
        this.max_bar.setMax(this.audioManager.getStreamMaxVolume(3));
        this.max_bar.setProgress(VolInfo.getMaxVol(this.activity));
        this.init_txt = (TextView) vol.findViewById(R.id.init_txt);
        this.init_txt.setText(new StringBuilder(String.valueOf(this.init_bar.getProgress())).toString());
        this.max_txt = (TextView) vol.findViewById(R.id.max_txt);
        this.max_txt.setText(new StringBuilder(String.valueOf(this.max_bar.getProgress())).toString());
        this.init_bar.setOnSeekBarChangeListener(this);
        this.max_bar.setOnSeekBarChangeListener(this);
    }

    private void restart() {
        Intent intent = this.activity.getPackageManager().getLaunchIntentForPackage(this.activity.getPackageName());
        intent.addFlags(67108864);
        this.activity.startActivity(intent);
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == this.init_bar) {
            this.init_txt.setText(new StringBuilder(String.valueOf(this.init_bar.getProgress())).toString());
        }
        if (seekBar == this.max_bar) {
            this.max_txt.setText(new StringBuilder(String.valueOf(this.max_bar.getProgress())).toString());
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == this.init_bar) {
            VolInfo.SetVol(this.activity, this.init_bar.getProgress());
        }
        if (seekBar == this.max_bar) {
            VolInfo.SetMaxVol(this.activity, this.max_bar.getProgress());
        }
    }
}
