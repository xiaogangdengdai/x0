package com.ssx.spa.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;
import com.ssx.spa.R;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class WebInstaller {
    private String apkurl;
    private Context context;
    private DownLoadFileThread downLoadFileThread;
    private String filename = new StringBuilder(String.valueOf(Math.abs(this.apkurl.hashCode()))).append(".apk").toString();
    private Handler handler = new Handler();
    private ProgressDialog pBar;
    private String packageName;
    private String savePath;

    class DownLoadFileThread extends Thread {
        private DownloadCallback callback;
        private String fullFilename;
        private String url;

        public DownLoadFileThread(String url, String fullFilename, DownloadCallback callback) {
            this.url = url;
            this.fullFilename = fullFilename;
            this.callback = callback;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r17 = this;
            r3 = new org.apache.http.impl.client.DefaultHttpClient;
            r3.<init>();
            r8 = new org.apache.http.client.methods.HttpGet;
            r0 = r17;
            r15 = r0.url;
            r8.<init>(r15);
            r12 = 0;
            r14 = r3.execute(r8);	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r6 = r14.getEntity();	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r10 = r6.getContentLength();	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r9 = r6.getContent();	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r7 = new java.io.File;	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r0 = r17;
            r15 = r0.fullFilename;	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r7.<init>(r15);	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r15 = r7.getParentFile();	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r15.mkdirs();	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            if (r9 == 0) goto L_0x004f;
        L_0x0031:
            r13 = new java.io.FileOutputStream;	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r15 = new java.io.File;	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r0 = r17;
            r0 = r0.fullFilename;	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r16 = r0;
            r15.<init>(r16);	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r13.<init>(r15);	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r15 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
            r1 = new byte[r15];	 Catch:{ ClientProtocolException -> 0x0087, IOException -> 0x00c8, all -> 0x00c5 }
            r2 = -1;
            r4 = 0;
        L_0x0047:
            r2 = r9.read(r1);	 Catch:{ ClientProtocolException -> 0x0087, IOException -> 0x00c8, all -> 0x00c5 }
            r15 = -1;
            if (r2 != r15) goto L_0x005f;
        L_0x004e:
            r12 = r13;
        L_0x004f:
            r12.flush();	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r0 = r17;
            r15 = r0.callback;	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            r15.onSuccess();	 Catch:{ ClientProtocolException -> 0x00cb, IOException -> 0x009e }
            if (r12 == 0) goto L_0x005e;
        L_0x005b:
            r12.close();	 Catch:{ IOException -> 0x00c0 }
        L_0x005e:
            return;
        L_0x005f:
            r15 = r17.isInterrupted();	 Catch:{ ClientProtocolException -> 0x0087, IOException -> 0x00c8, all -> 0x00c5 }
            if (r15 == 0) goto L_0x0078;
        L_0x0065:
            r0 = r17;
            r15 = r0.callback;	 Catch:{ ClientProtocolException -> 0x0087, IOException -> 0x00c8, all -> 0x00c5 }
            r15.onCancel();	 Catch:{ ClientProtocolException -> 0x0087, IOException -> 0x00c8, all -> 0x00c5 }
            if (r13 == 0) goto L_0x0071;
        L_0x006e:
            r13.close();	 Catch:{ IOException -> 0x0073 }
        L_0x0071:
            r12 = r13;
            goto L_0x005e;
        L_0x0073:
            r5 = move-exception;
            r5.printStackTrace();
            goto L_0x0071;
        L_0x0078:
            r15 = 0;
            r13.write(r1, r15, r2);	 Catch:{ ClientProtocolException -> 0x0087, IOException -> 0x00c8, all -> 0x00c5 }
            r4 = r4 + r2;
            if (r4 == 0) goto L_0x0047;
        L_0x007f:
            r0 = r17;
            r15 = r0.callback;	 Catch:{ ClientProtocolException -> 0x0087, IOException -> 0x00c8, all -> 0x00c5 }
            r15.onPrecessing(r4, r10);	 Catch:{ ClientProtocolException -> 0x0087, IOException -> 0x00c8, all -> 0x00c5 }
            goto L_0x0047;
        L_0x0087:
            r5 = move-exception;
            r12 = r13;
        L_0x0089:
            r5.printStackTrace();	 Catch:{ all -> 0x00b4 }
            r0 = r17;
            r15 = r0.callback;	 Catch:{ all -> 0x00b4 }
            r15.onFail(r5);	 Catch:{ all -> 0x00b4 }
            if (r12 == 0) goto L_0x005e;
        L_0x0095:
            r12.close();	 Catch:{ IOException -> 0x0099 }
            goto L_0x005e;
        L_0x0099:
            r5 = move-exception;
            r5.printStackTrace();
            goto L_0x005e;
        L_0x009e:
            r5 = move-exception;
        L_0x009f:
            r5.printStackTrace();	 Catch:{ all -> 0x00b4 }
            r0 = r17;
            r15 = r0.callback;	 Catch:{ all -> 0x00b4 }
            r15.onFail(r5);	 Catch:{ all -> 0x00b4 }
            if (r12 == 0) goto L_0x005e;
        L_0x00ab:
            r12.close();	 Catch:{ IOException -> 0x00af }
            goto L_0x005e;
        L_0x00af:
            r5 = move-exception;
            r5.printStackTrace();
            goto L_0x005e;
        L_0x00b4:
            r15 = move-exception;
        L_0x00b5:
            if (r12 == 0) goto L_0x00ba;
        L_0x00b7:
            r12.close();	 Catch:{ IOException -> 0x00bb }
        L_0x00ba:
            throw r15;
        L_0x00bb:
            r5 = move-exception;
            r5.printStackTrace();
            goto L_0x00ba;
        L_0x00c0:
            r5 = move-exception;
            r5.printStackTrace();
            goto L_0x005e;
        L_0x00c5:
            r15 = move-exception;
            r12 = r13;
            goto L_0x00b5;
        L_0x00c8:
            r5 = move-exception;
            r12 = r13;
            goto L_0x009f;
        L_0x00cb:
            r5 = move-exception;
            goto L_0x0089;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ssx.spa.util.WebInstaller.DownLoadFileThread.run():void");
        }
    }

    interface DownloadCallback {
        void onCancel();

        void onFail(Throwable th);

        void onPrecessing(int i, long j);

        void onSuccess();
    }

    private class ShellExecute {
        private ShellExecute() {
        }

        public String execute(String[] cmmand, String directory) throws IOException {
            String result = "";
            try {
                ProcessBuilder builder = new ProcessBuilder(cmmand);
                if (directory != null) {
                    builder.directory(new File(directory));
                }
                builder.redirectErrorStream(true);
                InputStream is = builder.start().getInputStream();
                byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    result = new StringBuilder(String.valueOf(result)).append(new String(buffer)).toString();
                }
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public WebInstaller(Context context, String apkurl) {
        this.context = context;
        File dir = context.getDir("spadownload", 3);
        dir.mkdirs();
        this.packageName = context.getPackageName();
        this.apkurl = apkurl;
        this.savePath = dir.getAbsolutePath() + "/";
    }

    public void downloadAndInstall() {
        this.pBar = new ProgressDialog(this.context);
        this.pBar.setTitle(R.string.down);
        this.pBar.setMessage(this.context.getResources().getString(R.string.downing));
        this.pBar.setProgressStyle(1);
        this.pBar.setCancelable(false);
        this.pBar.setButton(-2, this.context.getResources().getString(R.string.cancel), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                WebInstaller.this.pBar.cancel();
                if (WebInstaller.this.downLoadFileThread != null) {
                    WebInstaller.this.downLoadFileThread.interrupt();
                }
            }
        });
        this.pBar.setButton(-3, this.context.getResources().getString(R.string.background_download), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                WebInstaller.this.pBar.cancel();
            }
        });
        this.pBar.setProgress(100);
        this.pBar.show();
        this.downLoadFileThread = new DownLoadFileThread(this.apkurl, this.savePath + this.filename, new DownloadCallback() {
            public void onSuccess() {
                WebInstaller.this.handler.post(new Runnable() {
                    public void run() {
                        WebInstaller.this.pBar.cancel();
                        Toast.makeText(WebInstaller.this.context, WebInstaller.this.context.getResources().getString(R.string.download_complete), 1).show();
                        WebInstaller.this.install(new StringBuilder(String.valueOf(WebInstaller.this.savePath)).append(WebInstaller.this.filename).toString());
                    }
                });
            }

            public void onPrecessing(int written, long total) {
                WebInstaller.this.pBar.setProgress((int) ((((long) written) * 100) / total));
            }

            public void onFail(Throwable e) {
                e.printStackTrace();
                WebInstaller.this.handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(WebInstaller.this.context, WebInstaller.this.context.getString(R.string.DownFailed), 1).show();
                    }
                });
                WebInstaller.this.pBar.cancel();
            }

            public void onCancel() {
                WebInstaller.this.handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(WebInstaller.this.context, WebInstaller.this.context.getResources().getString(R.string.download_cancel), 1).show();
                    }
                });
            }
        });
        this.downLoadFileThread.start();
    }

    private void install(String fullfilepath) {
        getFilePermission(fullfilepath);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse("file://" + fullfilepath), "application/vnd.android.package-archive");
        this.context.startActivity(intent);
    }

    private void getFilePermission(String file) {
        try {
            new ShellExecute().execute(new String[]{"chmod", "607", file}, "/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
