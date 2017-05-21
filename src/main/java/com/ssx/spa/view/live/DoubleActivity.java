package com.ssx.spa.view.live;

import android.content.ComponentName;
import android.content.Intent;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramCtrl;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.DoubleLives;
import com.ssx.spa.view.BaseActivity;
import java.util.List;

public class DoubleActivity extends BaseActivity {
    private String TAG = "TVTAG";
    private List<DoubleLives> list;
    private TvChannelManager mTvChannelManager = null;
    private Myapplication myapplication;

    protected void onStart() {
        this.myapplication = (Myapplication) getApplication();
        try {
            setJiemu();
            startActivity(new Intent().setComponent(new ComponentName("com.mstar.tv.tvplayer.ui", "com.mstar.tv.tvplayer.ui.RootActivity")));
        } catch (Exception e) {
            MyToast.makeshow(this, "不适用于此系统！", 0);
        }
        finish();
        super.onStart();
    }

    private void setJiemu() {
        MyToast.makeshow(this, "正在同步直播列表！请稍后...", 0);
        try {
            this.mTvChannelManager = TvChannelManager.getInstance();
            this.mTvChannelManager.setProgramCtrl(EnumGetProgramCtrl.E_GET_CURRENT_PROGRAM_NUMBER.ordinal(), 0, 0);
            this.list = this.myapplication.getLive().getLives();
            for (int i = 0; i < this.list.size(); i++) {
                int mColorSystemIndex = 0;
                int mSoundSystemIndex = 0;
                int mTvSaveChannel = ((DoubleLives) this.list.get(i)).getChannelcode() + 1;
                int mFreq = (int) (Float.valueOf(((DoubleLives) this.list.get(i)).getFreq()).floatValue() * 1000.0f);
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("PAL")) {
                    mColorSystemIndex = 0;
                }
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("NTSC_M")) {
                    mColorSystemIndex = 1;
                }
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("SECAM")) {
                    mColorSystemIndex = 2;
                }
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("NTSC_44")) {
                    mColorSystemIndex = 3;
                }
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("PAL_M")) {
                    mColorSystemIndex = 4;
                }
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("PAL_N")) {
                    mColorSystemIndex = 5;
                }
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("PAL_60")) {
                    mColorSystemIndex = 6;
                }
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("NO_STAND")) {
                    mColorSystemIndex = 7;
                }
                if (((DoubleLives) this.list.get(i)).getZhishi().equals("AUTO")) {
                    mColorSystemIndex = 8;
                }
                if (((DoubleLives) this.list.get(i)).getRadiozhishi().equals("BG")) {
                    mSoundSystemIndex = 0;
                }
                if (((DoubleLives) this.list.get(i)).getRadiozhishi().equals("I")) {
                    mSoundSystemIndex = 2;
                }
                if (((DoubleLives) this.list.get(i)).getRadiozhishi().equals("L")) {
                    mSoundSystemIndex = 3;
                }
                if (((DoubleLives) this.list.get(i)).getRadiozhishi().equals("M")) {
                    mSoundSystemIndex = 4;
                }
                if (((DoubleLives) this.list.get(i)).getRadiozhishi().equals("DK")) {
                    mSoundSystemIndex = 1;
                }
                this.mTvChannelManager.startAtvManualTuning(3000, mFreq, 5);
                this.mTvChannelManager.setAtvAudioStandard(mSoundSystemIndex);
                this.mTvChannelManager.setAtvVideoStandard(mColorSystemIndex);
                this.mTvChannelManager.saveAtvProgram(mTvSaveChannel - 1);
                this.mTvChannelManager.setProgramName(mTvSaveChannel - 1, (short) 0, ((DoubleLives) this.list.get(i)).getName());
            }
            if (this.mTvChannelManager.getProgramCount(1) > 0) {
                this.mTvChannelManager.programUp();
            }
        } catch (Exception e) {
            MyToast.makeshow(this, "同步直播列表失败!", 0);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean SetChannelCopyFromTextFile() {
        /*
        r31 = this;
        r27 = java.lang.System.out;
        r28 = "-SetChannelCopyFromTextFile-";
        r27.println(r28);
        r27 = com.mstar.android.tv.TvChannelManager.getInstance();
        r0 = r27;
        r1 = r31;
        r1.mTvChannelManager = r0;
        r13 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x0144 }
        r27 = "/mnt/usb/sda4/vsS28xx/tvlist.txt";
        r0 = r27;
        r13.<init>(r0);	 Catch:{ FileNotFoundException -> 0x0144 }
        r12 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x0144 }
        r27 = "/mnt/usb/sda1/vsS28xx/tvlist.txt";
        r0 = r27;
        r12.<init>(r0);	 Catch:{ FileNotFoundException -> 0x0144 }
        r27 = r13.exists();	 Catch:{ FileNotFoundException -> 0x0144 }
        if (r27 != 0) goto L_0x0043;
    L_0x0029:
        r27 = r12.exists();	 Catch:{ FileNotFoundException -> 0x0144 }
        if (r27 != 0) goto L_0x0043;
    L_0x002f:
        r27 = "频道列表文件不存在！";
        r28 = 0;
        r0 = r31;
        r1 = r27;
        r2 = r28;
        r27 = android.widget.Toast.makeText(r0, r1, r2);	 Catch:{ FileNotFoundException -> 0x0144 }
        r27.show();	 Catch:{ FileNotFoundException -> 0x0144 }
        r17 = 0;
    L_0x0042:
        return r17;
    L_0x0043:
        r27 = "开始读取文件数据！";
        r28 = 0;
        r0 = r31;
        r1 = r27;
        r2 = r28;
        r27 = android.widget.Toast.makeText(r0, r1, r2);	 Catch:{ FileNotFoundException -> 0x0144 }
        r27.show();	 Catch:{ FileNotFoundException -> 0x0144 }
        r27 = r12.exists();	 Catch:{ FileNotFoundException -> 0x0144 }
        if (r27 != 0) goto L_0x0075;
    L_0x005a:
        r11 = r13;
    L_0x005b:
        r27 = r11.exists();	 Catch:{ FileNotFoundException -> 0x0144 }
        if (r27 != 0) goto L_0x0077;
    L_0x0061:
        r27 = "频道列表文件不存在！！！！";
        r28 = 0;
        r0 = r31;
        r1 = r27;
        r2 = r28;
        r27 = android.widget.Toast.makeText(r0, r1, r2);	 Catch:{ FileNotFoundException -> 0x0144 }
        r27.show();	 Catch:{ FileNotFoundException -> 0x0144 }
        r17 = 0;
        goto L_0x0042;
    L_0x0075:
        r11 = r12;
        goto L_0x005b;
    L_0x0077:
        r9 = new java.io.BufferedReader;	 Catch:{ FileNotFoundException -> 0x0144 }
        r27 = new java.io.FileReader;	 Catch:{ FileNotFoundException -> 0x0144 }
        r0 = r27;
        r0.<init>(r11);	 Catch:{ FileNotFoundException -> 0x0144 }
        r0 = r27;
        r9.<init>(r0);	 Catch:{ FileNotFoundException -> 0x0144 }
        r8 = "";
        r17 = 0;
        r21 = 0;
        r22 = "---VS_TvList---";
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r28 = com.mstar.android.tvapi.atv.vo.EnumGetProgramCtrl.E_GET_CURRENT_PROGRAM_NUMBER;	 Catch:{ IOException -> 0x00fd }
        r28 = r28.ordinal();	 Catch:{ IOException -> 0x00fd }
        r29 = 0;
        r30 = 0;
        r27.setProgramCtrl(r28, r29, r30);	 Catch:{ IOException -> 0x00fd }
        r27 = "清除已存在频道列表";
        r28 = 0;
        r0 = r31;
        r1 = r27;
        r2 = r28;
        r27 = android.widget.Toast.makeText(r0, r1, r2);	 Catch:{ IOException -> 0x00fd }
        r27.show();	 Catch:{ IOException -> 0x00fd }
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r28 = 1;
        r20 = r27.getProgramCount(r28);	 Catch:{ IOException -> 0x00fd }
        if (r20 <= 0) goto L_0x00c8;
    L_0x00bf:
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r27.programUp();	 Catch:{ IOException -> 0x00fd }
    L_0x00c8:
        r19 = 1;
        r10 = 0;
        r18 = 0;
        r14 = 55250; // 0xd7d2 float:7.7422E-41 double:2.7297E-319;
        r7 = 1;
        r6 = 5250; // 0x1482 float:7.357E-42 double:2.594E-320;
        r4 = 0;
        r5 = 0;
        r15 = 860000; // 0xd1f60 float:1.205117E-39 double:4.248965E-318;
        r16 = 43000; // 0xa7f8 float:6.0256E-41 double:2.1245E-319;
    L_0x00db:
        r8 = r9.readLine();	 Catch:{ IOException -> 0x00fd }
        if (r8 != 0) goto L_0x0105;
    L_0x00e1:
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r28 = 1;
        r20 = r27.getProgramCount(r28);	 Catch:{ IOException -> 0x00fd }
        if (r20 <= 0) goto L_0x00f8;
    L_0x00ef:
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r27.programUp();	 Catch:{ IOException -> 0x00fd }
    L_0x00f8:
        r9.close();	 Catch:{ IOException -> 0x00fd }
        goto L_0x0042;
    L_0x00fd:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ FileNotFoundException -> 0x0144 }
    L_0x0101:
        r17 = 0;
        goto L_0x0042;
    L_0x0105:
        r0 = r22;
        r27 = r0.equals(r8);	 Catch:{ IOException -> 0x00fd }
        if (r27 != 0) goto L_0x0149;
    L_0x010d:
        if (r21 != 0) goto L_0x0149;
    L_0x010f:
        r27 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00fd }
        r28 = "获取文件错误！文件必须为UTF-8无BOM格式";
        r27.<init>(r28);	 Catch:{ IOException -> 0x00fd }
        r0 = r27;
        r27 = r0.append(r8);	 Catch:{ IOException -> 0x00fd }
        r28 = "第";
        r27 = r27.append(r28);	 Catch:{ IOException -> 0x00fd }
        r0 = r27;
        r1 = r21;
        r27 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r28 = "行";
        r27 = r27.append(r28);	 Catch:{ IOException -> 0x00fd }
        r27 = r27.toString();	 Catch:{ IOException -> 0x00fd }
        r28 = 0;
        r0 = r31;
        r1 = r27;
        r2 = r28;
        r27 = android.widget.Toast.makeText(r0, r1, r2);	 Catch:{ IOException -> 0x00fd }
        r27.show();	 Catch:{ IOException -> 0x00fd }
        goto L_0x00e1;
    L_0x0144:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x0101;
    L_0x0149:
        r17 = 0;
        r27 = 44;
        r0 = r27;
        r7 = r8.indexOf(r0);	 Catch:{ IOException -> 0x00fd }
        r27 = 44;
        r28 = r7 + 1;
        r0 = r27;
        r1 = r28;
        r6 = r8.indexOf(r0, r1);	 Catch:{ IOException -> 0x00fd }
        r27 = 44;
        r28 = r6 + 1;
        r0 = r27;
        r1 = r28;
        r4 = r8.indexOf(r0, r1);	 Catch:{ IOException -> 0x00fd }
        r27 = 44;
        r28 = r4 + 1;
        r0 = r27;
        r1 = r28;
        r5 = r8.indexOf(r0, r1);	 Catch:{ IOException -> 0x00fd }
        r25 = "";
        r24 = "";
        r23 = "";
        r26 = "";
        r27 = -1;
        r0 = r27;
        if (r7 == r0) goto L_0x018d;
    L_0x0185:
        r27 = 0;
        r0 = r27;
        r25 = r8.substring(r0, r7);	 Catch:{ IOException -> 0x00fd }
    L_0x018d:
        r27 = -1;
        r0 = r27;
        if (r6 == r0) goto L_0x019b;
    L_0x0193:
        r27 = r7 + 1;
        r0 = r27;
        r24 = r8.substring(r0, r6);	 Catch:{ IOException -> 0x00fd }
    L_0x019b:
        r27 = -1;
        r0 = r27;
        if (r4 == r0) goto L_0x01a9;
    L_0x01a1:
        r27 = r6 + 1;
        r0 = r27;
        r23 = r8.substring(r0, r4);	 Catch:{ IOException -> 0x00fd }
    L_0x01a9:
        r27 = -1;
        r0 = r27;
        if (r5 == r0) goto L_0x01b7;
    L_0x01af:
        r27 = r4 + 1;
        r0 = r27;
        r26 = r8.substring(r0, r5);	 Catch:{ IOException -> 0x00fd }
    L_0x01b7:
        r27 = -1;
        r0 = r27;
        if (r4 == r0) goto L_0x01cf;
    L_0x01bd:
        r27 = -1;
        r0 = r27;
        if (r7 == r0) goto L_0x01cf;
    L_0x01c3:
        r27 = -1;
        r0 = r27;
        if (r6 == r0) goto L_0x01cf;
    L_0x01c9:
        r27 = -1;
        r0 = r27;
        if (r5 != r0) goto L_0x0227;
    L_0x01cf:
        r17 = 0;
    L_0x01d1:
        if (r21 <= 0) goto L_0x0223;
    L_0x01d3:
        if (r17 != 0) goto L_0x022a;
    L_0x01d5:
        r27 = java.lang.System.out;	 Catch:{ IOException -> 0x00fd }
        r28 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00fd }
        r29 = "文件错误！第";
        r28.<init>(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r1 = r21;
        r28 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r29 = "行\n";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv台号为=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r28 = r0.append(r7);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv频点=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r28 = r0.append(r6);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv彩色制式=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r28 = r0.append(r4);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv伴音制式=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r28 = r0.append(r5);	 Catch:{ IOException -> 0x00fd }
        r28 = r28.toString();	 Catch:{ IOException -> 0x00fd }
        r27.println(r28);	 Catch:{ IOException -> 0x00fd }
    L_0x0223:
        r21 = r21 + 1;
        goto L_0x00db;
    L_0x0227:
        r17 = 1;
        goto L_0x01d1;
    L_0x022a:
        r27 = " ";
        r28 = "";
        r0 = r25;
        r1 = r27;
        r2 = r28;
        r27 = r0.replace(r1, r2);	 Catch:{ IOException -> 0x00fd }
        r27 = java.lang.Integer.valueOf(r27);	 Catch:{ IOException -> 0x00fd }
        r19 = r27.intValue();	 Catch:{ IOException -> 0x00fd }
        r27 = java.lang.Integer.valueOf(r23);	 Catch:{ IOException -> 0x00fd }
        r10 = r27.intValue();	 Catch:{ IOException -> 0x00fd }
        r27 = java.lang.Integer.valueOf(r26);	 Catch:{ IOException -> 0x00fd }
        r18 = r27.intValue();	 Catch:{ IOException -> 0x00fd }
        r27 = java.lang.Float.valueOf(r24);	 Catch:{ IOException -> 0x00fd }
        r27 = r27.floatValue();	 Catch:{ IOException -> 0x00fd }
        r28 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r27 = r27 * r28;
        r0 = r27;
        r14 = (int) r0;	 Catch:{ IOException -> 0x00fd }
        r27 = 860000; // 0xd1f60 float:1.205117E-39 double:4.248965E-318;
        r0 = r27;
        if (r14 >= r0) goto L_0x030b;
    L_0x0266:
        r27 = 43000; // 0xa7f8 float:6.0256E-41 double:2.1245E-319;
        r0 = r27;
        if (r14 <= r0) goto L_0x030b;
    L_0x026d:
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r28 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        r29 = 5;
        r0 = r27;
        r1 = r28;
        r2 = r29;
        r0.startAtvManualTuning(r1, r14, r2);	 Catch:{ IOException -> 0x00fd }
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r0 = r27;
        r1 = r18;
        r0.setAtvAudioStandard(r1);	 Catch:{ IOException -> 0x00fd }
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r0 = r27;
        r0.setAtvVideoStandard(r10);	 Catch:{ IOException -> 0x00fd }
        r0 = r31;
        r0 = r0.mTvChannelManager;	 Catch:{ IOException -> 0x00fd }
        r27 = r0;
        r28 = r19 + -1;
        r27.saveAtvProgram(r28);	 Catch:{ IOException -> 0x00fd }
        r27 = java.lang.System.out;	 Catch:{ IOException -> 0x00fd }
        r28 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00fd }
        r29 = "正在保存！文件第";
        r28.<init>(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r1 = r21;
        r28 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r29 = "行\n";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r29 = "第";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r1 = r19;
        r28 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r29 = "台\n";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv台号为=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r1 = r19;
        r28 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv频点=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r28 = r0.append(r14);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv彩色制式=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r28 = r0.append(r10);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv伴音制式=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r1 = r18;
        r28 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r28 = r28.toString();	 Catch:{ IOException -> 0x00fd }
        r27.println(r28);	 Catch:{ IOException -> 0x00fd }
        goto L_0x0223;
    L_0x030b:
        r27 = java.lang.System.out;	 Catch:{ IOException -> 0x00fd }
        r28 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00fd }
        r29 = "不保存！第";
        r28.<init>(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r1 = r19;
        r28 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r29 = "台\n";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv台号为=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r1 = r19;
        r28 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv频点=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r28 = r0.append(r14);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv彩色制式=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r28 = r0.append(r10);	 Catch:{ IOException -> 0x00fd }
        r29 = "Tv伴音制式=";
        r28 = r28.append(r29);	 Catch:{ IOException -> 0x00fd }
        r0 = r28;
        r1 = r18;
        r28 = r0.append(r1);	 Catch:{ IOException -> 0x00fd }
        r28 = r28.toString();	 Catch:{ IOException -> 0x00fd }
        r27.println(r28);	 Catch:{ IOException -> 0x00fd }
        goto L_0x0223;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ssx.spa.view.live.DoubleActivity.SetChannelCopyFromTextFile():boolean");
    }

    protected void onStop() {
        super.onStop();
    }
}
