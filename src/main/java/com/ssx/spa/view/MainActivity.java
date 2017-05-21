package com.ssx.spa.view;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.mstar.android.tv.TvLanguage;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.common.ProgressDiag;
import com.ssx.spa.config.Config;
import com.ssx.spa.javabean.App;
import com.ssx.spa.javabean.LiveCategory;
import com.ssx.spa.javabean.LocalService;
import com.ssx.spa.javabean.SongCategory;
import com.ssx.spa.javabean.Vod;
import com.ssx.spa.net.BGService;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.util.BitmapUtil;
import com.ssx.spa.util.ImageUtil;
import com.ssx.spa.util.WebInstaller;
import com.ssx.spa.view.common.Bottom;
import com.ssx.spa.view.common.GalleryFlow;
import com.ssx.spa.view.common.Head;
import com.ssx.spa.view.common.MyImageView;
import com.ssx.spa.view.game.GameActivity;
import com.ssx.spa.view.game.ProductActivity;
import com.ssx.spa.view.live.DoubleActivity;
import com.ssx.spa.view.live.LiveActivity;
import com.ssx.spa.view.localservice.FoodActivity;
import com.ssx.spa.view.localservice.JishiActivity;
import com.ssx.spa.view.localservice.LiuYanActivity;
import com.ssx.spa.view.localservice.OrderActivity;
import com.ssx.spa.view.localservice.ServiceOtherActivity;
import com.ssx.spa.view.music.MusicActivity;
import com.ssx.spa.view.vod.VodActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    private GalleryAdapter adapter;
    Bottom bottom;
    TextView txtAD;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -98:
                    ProgressDiag.on(MainActivity.this, "该设备尚未授权!\nMAC：" + Myapplication.MAC);
                    return;
                case 0:
                    try {
                        int temp = MainActivity.this.mGallery.getSelectedItemPosition() + 1;
                        if (temp >= MainActivity.this.mBitmaps.size()) {
                            temp = 0;
                        }
                        MainActivity.this.mGallery.setSelection(temp);
                        return;
                    } catch (Exception e) {
                        return;
                    }
                case 2:
                    new getBitMap().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
                    return;
                case 103:
                    MainActivity.this.handler.sendEmptyMessage(2);
                    return;
                case 104:
                    MainActivity.this.handler.sendEmptyMessage(2);
                    return;
                case 105:
                    MainActivity.this.handler.sendEmptyMessage(2);
                    return;
                case 106:
                    MainActivity.this.handler.sendEmptyMessage(2);
                    return;
                case 107:
                    MainActivity.this.handler.sendEmptyMessage(2);
                    return;
                case 120:
                    MainActivity.this.checkupdate();
                    return;
                default:
                    return;
            }
        }
    };
    Head head;
    private ImageButton head_call;
    private ImageButton head_liuwei;
    private ImageView imageView;
    private List<String> list = new ArrayList();
    private ArrayList<BitmapDrawable> mBitmaps = new ArrayList();
    private GalleryFlow mGallery;
    private ImageButton main_game;
    private ImageButton main_live;
    private ImageButton main_music;
    private ImageButton main_service;
    private ImageButton main_vod;
    private Myapplication myapplication;
    private Timer timer;
    int width;

    private class GalleryAdapter extends BaseAdapter {
        private int selectItem;

        private GalleryAdapter() {
        }

        public void setSelectItem(int selectItem) {
            if (this.selectItem != selectItem) {
                this.selectItem = selectItem;
            }
        }

        public int getCount() {
            return MainActivity.this.mBitmaps.size();
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new MyImageView(MainActivity.this);
                convertView.setFocusable(true);
                if (MainActivity.this.width >= 1920) {
                    convertView.setLayoutParams(new LayoutParams(TvLanguage.PAPUANAUSTRALIAN, TvLanguage.OROMO));
                } else {
                    convertView.setLayoutParams(new LayoutParams(261, 250));
                }
            }
            MainActivity.this.imageView = (ImageView) convertView;
            if (MainActivity.this.mGallery.getSelectedItemPosition() == position) {
                if (MainActivity.this.width >= 1920) {
                    MainActivity.this.imageView.setLayoutParams(new LayoutParams(TvLanguage.URDU, TvLanguage.TSIMSHIAN));
                } else {
                    MainActivity.this.imageView.setLayoutParams(new LayoutParams(TvLanguage.QUECHUA, TvLanguage.PALAUAN));
                }
            }
            if (position == MainActivity.this.mGallery.getSelectedItemPosition() - 1 || position == MainActivity.this.mGallery.getSelectedItemPosition() + 1) {
                if (MainActivity.this.width >= 1920) {
                    MainActivity.this.imageView.setLayoutParams(new LayoutParams(TvLanguage.SOGDIAN, TvLanguage.SERER));
                } else {
                    MainActivity.this.imageView.setLayoutParams(new LayoutParams(313, TvLanguage.MANIPURI));
                }
            }
            try {
                MainActivity.this.imageView.setBackground((Drawable) MainActivity.this.mBitmaps.get(position));
            } catch (Exception e) {
                System.out.println(e.toString() + "~");
                MainActivity.this.imageView.setBackgroundResource(R.drawable.touming);
            }
            MainActivity.this.imageView.setScaleType(ScaleType.FIT_XY);
            MainActivity.this.imageView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 0) {
                        MainActivity.this.sendBroadcast(new Intent("showbottomtools"));
                    }
                    return false;
                }
            });
            return MainActivity.this.imageView;
        }
    }

    class Navig implements OnClickListener {
        Navig() {
        }

        public void onClick(View v) {
            MainActivity.this.handler.removeMessages(2);
            switch (v.getId()) {
                case R.id.main_service:
                    MainActivity.this.myapplication.setNavig(0);
                    new JsonResult(MainActivity.this, MainActivity.this.myapplication, MainActivity.this.handler).getlocal();
                    return;
                case R.id.main_live:
                    MainActivity.this.myapplication.setNavig(1);
                    new JsonResult(MainActivity.this, MainActivity.this.myapplication, MainActivity.this.handler).getlivecat();
                    return;
                case R.id.main_vod:
                    MainActivity.this.myapplication.setNavig(2);
                    new JsonResult(MainActivity.this, MainActivity.this.myapplication, MainActivity.this.handler).getvod();
                    return;
                case R.id.main_music:
                    MainActivity.this.myapplication.setNavig(3);
                    new JsonResult(MainActivity.this, MainActivity.this.myapplication, MainActivity.this.handler).getSongs();
                    return;
                case R.id.main_game:
                    MainActivity.this.myapplication.setNavig(4);
                    new JsonResult(MainActivity.this, MainActivity.this.myapplication, MainActivity.this.handler).getandroid();
                    return;
                default:
                    return;
            }
        }
    }

    class getBitMap extends AsyncTask<String, String, String> {
        getBitMap() {
        }

        protected void onPostExecute(String result) {
            MainActivity.this.recover();
            super.onPostExecute(result);
        }

        protected String doInBackground(String... params) {
            try {
                int i;
                MainActivity.this.mBitmaps.removeAll(MainActivity.this.mBitmaps);
                MainActivity.this.list.clear();
                int acount = 0;
                switch (MainActivity.this.myapplication.getNavig()) {
                    case 0:
                        acount = MainActivity.this.myapplication.getLocalServices().size();
                        break;
                    case 1:
                        acount = MainActivity.this.myapplication.getLiveCategories().size();
                        break;
                    case 2:
                        acount = MainActivity.this.myapplication.getVods().size();
                        break;
                    case 3:
                        acount = MainActivity.this.myapplication.getSongs().size();
                        break;
                    case 4:
                        acount = MainActivity.this.myapplication.getApps().size();
                        break;
                }
                for (i = 0; i < acount; i++) {
                    switch (MainActivity.this.myapplication.getNavig()) {
                        case 0:
                            MainActivity.this.list.add(((LocalService) MainActivity.this.myapplication.getLocalServices().get(i)).getLogo());
                            break;
                        case 1:
                            if (((LiveCategory) MainActivity.this.myapplication.getLiveCategories().get(i)).getStatusConfig() != 1) {
                                break;
                            }
                            MainActivity.this.list.add(((LiveCategory) MainActivity.this.myapplication.getLiveCategories().get(i)).getPath());
                            break;
                        case 2:
                            MainActivity.this.list.add(((Vod) MainActivity.this.myapplication.getVods().get(i)).getImg());
                            break;
                        case 3:
                            MainActivity.this.list.add(((SongCategory) MainActivity.this.myapplication.getSongs().get(i)).getIcon());
                            break;
                        case 4:
                            MainActivity.this.list.add(((App) MainActivity.this.myapplication.getApps().get(i)).getPath());
                            break;
                        default:
                            break;
                    }
                }
                for (i = 0; i < MainActivity.this.list.size(); i++) {
                    Bitmap bitmap2;
                    Bitmap bitmap = ImageUtil.loadImage(MainActivity.this, (String) MainActivity.this.list.get(i));
                    if (bitmap != null) {
                        bitmap2 = BitmapUtil.createReflectedBitmap(bitmap);
                    } else {
                        bitmap2 = BitmapUtil.createReflectedBitmap(BitmapUtil.drawableToBitmap(MainActivity.this.getResources().getDrawable(R.drawable.ic_launcher)));
                    }
                    BitmapDrawable drawable = new BitmapDrawable(bitmap2);
                    drawable.setAntiAlias(true);
                    if (MainActivity.this.mBitmaps.size() < MainActivity.this.list.size()) {
                        MainActivity.this.mBitmaps.add(drawable);
                    }
                }
            } catch (Exception e) {
            } catch (OutOfMemoryError e2) {
                System.gc();
            }
            return null;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.myapplication = (Myapplication) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        setvalue();
        startService(new Intent(this, BGService.class));
        new JsonResult(this, this.myapplication, this.handler).getDevice();
        new JsonResult(this, this.myapplication, this.handler).getupdate();
        this.width = getSharedPreferences(Config.first, 0).getInt(Config.screen, 1920);
    }

    private void playgallery() {
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            public void run() {
                MainActivity.this.handler.sendEmptyMessage(0);
            }
        }, 5000, 5000);
    }

    private void setvalue() {
        new JsonResult(this, this.myapplication, this.handler).modelswitch();
        new JsonResult(this, this.myapplication, this.handler).getlocal();
        this.mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                MainActivity.this.adapter.setSelectItem(arg2);
                MainActivity.this.adapter.notifyDataSetChanged();
                MainActivity.this.canfcous(false);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mGallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                int i;
                switch (MainActivity.this.myapplication.getNavig()) {
                    case 0:
                        int ordertemp = -1;
                        for (i = 0; i < MainActivity.this.myapplication.getLocalServices().size(); i++) {
                            if (((LocalService) MainActivity.this.myapplication.getLocalServices().get(i)).getName().trim().equals("帐单查询")) {
                                ordertemp = i;
                            }
                        }
                        if (arg2 != 0) {
                            if (arg2 != 1) {
                                if (arg2 != ordertemp) {
                                    intent.setClass(MainActivity.this, ServiceOtherActivity.class).putExtra("current", arg2);
                                    break;
                                } else {
                                    intent.setClass(MainActivity.this, OrderActivity.class).putExtra("top", ((LocalService) MainActivity.this.myapplication.getLocalServices().get(arg2)).getName());
                                    break;
                                }
                            }
                            intent.setClass(MainActivity.this, FoodActivity.class);
                            break;
                        }
                        intent.setClass(MainActivity.this, JishiActivity.class);
                        break;
                    case 1:
                        try {
                            if (((LiveCategory) MainActivity.this.myapplication.getLiveCategories().get(0)).getStatusConfig() != 1 || ((LiveCategory) MainActivity.this.myapplication.getLiveCategories().get(1)).getStatusConfig() != 1) {
                                if (((LiveCategory) MainActivity.this.myapplication.getLiveCategories().get(0)).getStatusConfig() != 1) {
                                    if (((LiveCategory) MainActivity.this.myapplication.getLiveCategories().get(1)).getStatusConfig() == 1) {
                                        intent.setClass(MainActivity.this, LiveActivity.class);
                                        break;
                                    }
                                }
                                intent.setClass(MainActivity.this, DoubleActivity.class);
                                break;
                            } else if (((LiveCategory) MainActivity.this.myapplication.getLiveCategories().get(arg2)).getId() != 1) {
                                if (((LiveCategory) MainActivity.this.myapplication.getLiveCategories().get(arg2)).getId() == 2) {
                                    intent.setClass(MainActivity.this, LiveActivity.class);
                                    break;
                                }
                            } else {
                                intent.setClass(MainActivity.this, DoubleActivity.class);
                                break;
                            }
                        } catch (Exception e) {
                            break;
                        }
                        break;
                    case 2:
                        bundle.putSerializable("vods", (Serializable) MainActivity.this.myapplication.getVods().get(arg2));
                        intent.setClass(MainActivity.this, VodActivity.class).putExtras(bundle);
                        break;
                    case 3:
                        bundle.putSerializable("songs", (Serializable) MainActivity.this.myapplication.getSongs().get(arg2));
                        intent.setClass(MainActivity.this, MusicActivity.class).putExtras(bundle);
                        break;
                    case 4:
                        int liuyantemp = -1;
                        int hangbantemp = -1;
                        int liulanqi = -1;
                        int product = -1;
                        for (i = 0; i < MainActivity.this.myapplication.getApps().size(); i++) {
                            if (((App) MainActivity.this.myapplication.getApps().get(i)).getName().trim().equals("留言信息")) {
                                liuyantemp = i;
                            } else if (((App) MainActivity.this.myapplication.getApps().get(i)).getName().trim().equals("航班信息")) {
                                hangbantemp = i;
                            } else if (((App) MainActivity.this.myapplication.getApps().get(i)).getName().trim().equals("浏览器")) {
                                liulanqi = i;
                            } else if (((App) MainActivity.this.myapplication.getApps().get(i)).getName().trim().equals("产品介绍")) {
                                product = i;
                            }
                        }
                        if (arg2 != liulanqi) {
                            if (arg2 != hangbantemp) {
                                if (arg2 != liuyantemp) {
                                    if (arg2 != product) {
                                        intent.setClass(MainActivity.this, GameActivity.class).putExtra("position", arg2);
                                        break;
                                    } else {
                                        intent.setClass(MainActivity.this, ProductActivity.class).putExtra("position", arg2);
                                        break;
                                    }
                                }
                                intent.setClass(MainActivity.this, LiuYanActivity.class);
                                break;
                            }
                            intent.setComponent(new ComponentName("ctrip.pad.view", "ctrip.pad.splash.SplashActivity"));
                            break;
                        }
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(Uri.parse("https://www.baidu.com/"));
                        break;
                }
                try {
                    MainActivity.this.startActivity(intent);
                } catch (Exception e2) {
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.head = (Head) findViewById(R.id.head);
        switch (keyCode) {
            case 19:
                canfcous(true);
                break;
            case 20:
                canfcous(true);
                break;
        }
        if (keyCode == 4) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void generateBitmaps(final int[] ids) {
        new AsyncTask<String, String, String>() {
            protected void onPostExecute(String result) {
                MainActivity.this.adapter = new GalleryAdapter();
                MainActivity.this.mGallery.setAdapter(MainActivity.this.adapter);
                MainActivity.this.mGallery.setSpacing(-100);
                MainActivity.this.mGallery.setFadingEdgeLength(0);
                MainActivity.this.mGallery.setMaxRotationAngle(0);
                MainActivity.this.mGallery.setGravity(80);
                MainActivity.this.mGallery.setSelection((int) Math.ceil((double) (MainActivity.this.mBitmaps.size() / 2)));
            }

            protected String doInBackground(String... params) {
                MainActivity.this.mBitmaps.clear();
                for (int id : ids) {
                    Bitmap bitmap = MainActivity.this.createReflectedBitmapById(id);
                    if (bitmap != null) {
                        BitmapDrawable drawable = new BitmapDrawable(bitmap);
                        drawable.setAntiAlias(true);
                        MainActivity.this.mBitmaps.add(drawable);
                    }
                }
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
    }

    private Bitmap createReflectedBitmapById(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        if (drawable instanceof BitmapDrawable) {
            return BitmapUtil.createReflectedBitmap(((BitmapDrawable) drawable).getBitmap());
        }
        return null;
    }

    private void initview() {
        txtAD = (TextView) findViewById(R.id.txtAD);
        this.mGallery = (GalleryFlow) findViewById(R.id.main_gallery);
        this.main_service = (ImageButton) findViewById(R.id.main_service);
        this.main_live = (ImageButton) findViewById(R.id.main_live);
        this.main_vod = (ImageButton) findViewById(R.id.main_vod);
        this.main_music = (ImageButton) findViewById(R.id.main_music);
        this.main_game = (ImageButton) findViewById(R.id.main_game);
        this.main_service.setOnClickListener(new Navig());
        this.main_live.setOnClickListener(new Navig());
        this.main_vod.setOnClickListener(new Navig());
        this.main_music.setOnClickListener(new Navig());
        this.main_game.setOnClickListener(new Navig());
        this.head_call = (ImageButton) findViewById(R.id.head_call);
        this.head_liuwei = (ImageButton) findViewById(R.id.head_liuwei);

        AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.alpha);
        txtAD.startAnimation(alphaAnimation);
    }

    private void checkupdate() {
        try {
            final String updateurl = this.myapplication.getUpdateurl();
            if (updateurl != null && !updateurl.equals("")) {
                new Builder(this).setTitle("温馨提示").setMessage("检测到版本需要升级！是否下载安装？").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(new StringBuilder(String.valueOf(updateurl)).toString());
                        new WebInstaller(MainActivity.this, updateurl).downloadAndInstall();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        } catch (Exception e) {
        }
    }

    private void canfcous(boolean can) {
        if (can) {
            this.head_call.setFocusable(true);
            this.head_liuwei.setFocusable(true);
            this.main_service.setFocusable(true);
            this.main_live.setFocusable(true);
            this.main_vod.setFocusable(true);
            this.main_music.setFocusable(true);
            this.main_game.setFocusable(true);
            return;
        }
        this.head_call.setFocusable(false);
        this.head_liuwei.setFocusable(false);
        this.main_service.setFocusable(false);
        this.main_live.setFocusable(false);
        this.main_vod.setFocusable(false);
        this.main_music.setFocusable(false);
        this.main_game.setFocusable(false);
    }

    private void recover() {
        this.adapter = new GalleryAdapter();
        this.mGallery.setAdapter(this.adapter);
        this.mGallery.setSpacing(-100);
        this.mGallery.setFadingEdgeLength(0);
        this.mGallery.setMaxRotationAngle(0);
        this.mGallery.setGravity(80);
        this.mGallery.setSelection((int) Math.ceil((double) (this.mBitmaps.size() / 2)));
    }
}
