package com.ssx.spa.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;

public class VolInfo {
    private static int DefaultVol = 30;
    private static int MaxVol = 100;
    public static final String maxvol = "maxvol";
    public static final String maxvolinfo = "maxvolinfo";
    static SharedPreferences preferences = null;
    public static final String vol = "vol";
    public static final String volinfo = "volinfo";

    public VolInfo(Context context) {
        preferences = context.getSharedPreferences(volinfo, 0);
    }

    public static int getVol(Context context) {
        try {
            preferences = context.getSharedPreferences(volinfo, 0);
            return preferences.getInt(vol, 0);
        } catch (Exception e) {
            return DefaultVol;
        }
    }

    public static void SetDefaultVol(Context context) {
        try {
            preferences = context.getSharedPreferences(volinfo, 0);
            Editor editor = preferences.edit();
            editor.putInt(vol, DefaultVol);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public static void SetVol(Context context, int vols) {
        try {
            preferences = context.getSharedPreferences(volinfo, 0);
            Editor editor = preferences.edit();
            editor.putInt(vol, vols);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public static int getMaxVol(Context context) {
        try {
            preferences = context.getSharedPreferences(maxvolinfo, 0);
            return preferences.getInt(maxvol, MaxVol);
        } catch (Exception e) {
            return MaxVol;
        }
    }

    public static void SetDefaultMaxVol(Context context) {
        try {
            MaxVol = ((AudioManager) context.getSystemService("audio")).getStreamMaxVolume(3);
            preferences = context.getSharedPreferences(maxvolinfo, 0);
            Editor editor = preferences.edit();
            editor.putInt(maxvol, MaxVol);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public static void SetMaxVol(Context context, int maxvols) {
        try {
            preferences = context.getSharedPreferences(maxvolinfo, 0);
            Editor editor = preferences.edit();
            editor.putInt(maxvol, maxvols);
            editor.commit();
        } catch (Exception e) {
        }
    }
}
