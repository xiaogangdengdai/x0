package com.ssx.spa.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.ssx.spa.R;
import java.util.ArrayList;
import java.util.List;

public class AppUtil {
    public static List<AndroidAppInfo> getInstalledAppInfos(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(1);
        List<AndroidAppInfo> androidAppInfos = new ArrayList();
        for (PackageInfo p : installedPackages) {
            AndroidAppInfo aai = new AndroidAppInfo();
            aai.setAppName(p.applicationInfo.loadLabel(packageManager).toString());
            aai.setIcon(p.applicationInfo.loadIcon(packageManager));
            aai.setPackageName(p.packageName);
            aai.setFlags(Integer.valueOf(p.applicationInfo.flags));
            if (!(aai.getPackageName() == null || aai.getPackageName().trim().equals(""))) {
                androidAppInfos.add(aai);
            }
        }
        return androidAppInfos;
    }

    public static void startApp(Context context, String appPackageName) {
        try {
            context.startActivity(context.getPackageManager().getLaunchIntentForPackage(appPackageName));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "启动失败", 1).show();
        }
    }

    public static boolean checkAppIsExist(Context context, String packageName) {
        try {
            if (context.getPackageManager().getPackageInfo(packageName, 0) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void showToastPosition(Context context, String str) {
        View view = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.remindtext, null);
        ((TextView) view.findViewById(R.id.text)).setText(str);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(1);
        toast.show();
    }

    public static int getVersion(Context context, String packageName) {
        try {
            PackageInfo p = context.getPackageManager().getPackageInfo(packageName, 0);
            if (p != null) {
                return p.versionCode;
            }
        } catch (Exception e) {
        }
        return -1;
    }
}
