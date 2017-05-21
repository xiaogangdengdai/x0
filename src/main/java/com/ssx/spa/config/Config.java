package com.ssx.spa.config;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;

public class Config {
    static AudioManager audioManager = null;
    public static final String first = "first";
    public static final String isFirst = "isFirst";
    public static final String screen = "screen";

    public static void firststart(Activity activity) {
        try {
            audioManager = (AudioManager) activity.getSystemService("audio");
            SharedPreferences preferences = activity.getSharedPreferences(first, 0);
            if (!preferences.getBoolean(isFirst, false)) {
                int w = activity.getWindowManager().getDefaultDisplay().getWidth();
                Editor editor = preferences.edit();
                editor.putBoolean(isFirst, true);
                editor.putInt(screen, w);
                editor.commit();
                VolInfo.SetDefaultVol(activity);
                VolInfo.SetDefaultMaxVol(activity);
            }
        } catch (Exception e) {
        }
    }

    private static void resetVol(Context activity) {
        audioManager.setStreamVolume(3, VolInfo.getVol(activity), 0);
    }
}
