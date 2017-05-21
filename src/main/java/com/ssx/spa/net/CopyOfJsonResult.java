package com.ssx.spa.net;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.Ad;
import com.ssx.spa.javabean.AllLive;
import com.ssx.spa.javabean.App;
import com.ssx.spa.javabean.AppInfo;
import com.ssx.spa.javabean.FodCategory;
import com.ssx.spa.javabean.FodDetail;
import com.ssx.spa.javabean.JiShiCategory;
import com.ssx.spa.javabean.JishiDetail;
import com.ssx.spa.javabean.JsonBean;
import com.ssx.spa.javabean.LiuYan;
import com.ssx.spa.javabean.LiveCategory;
import com.ssx.spa.javabean.LocalService;
import com.ssx.spa.javabean.MsgInfo;
import com.ssx.spa.javabean.RootInfo;
import com.ssx.spa.javabean.Song;
import com.ssx.spa.javabean.SongCategory;
import com.ssx.spa.javabean.TotalUpdate;
import com.ssx.spa.javabean.Update;
import com.ssx.spa.javabean.Vod;
import com.ssx.spa.javabean.VoidPrograms;
import com.ssx.spa.view.wearther.Bean;
import java.util.ArrayList;
import java.util.List;

public class CopyOfJsonResult {
    private static String all_server_url = ("http://" + Myapplication.ALLSERVER_IP + "/spa_total/");
    public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    private static String project_name = "/newspa/";
    public static String url = ("http://" + Myapplication.DEFAULT_IP + project_name);
    Activity activity;
    Handler handler;
    String mac = Myapplication.MAC;
    Myapplication myapplication;

    public CopyOfJsonResult(Activity activity, Myapplication myapplication, Handler handler) {
        this.activity = activity;
        this.myapplication = myapplication;
        this.handler = handler;
    }

    public static void setHost(String host) {
        if (host != null) {
            url = "http://" + host + "/newspa/";
        }
    }

    public static void setAllHost(String host) {
        if (host != null) {
            all_server_url = "http://" + host + "/spa_total/";
        }
    }

    public static String getHost(String host) {
        if (host == null) {
            return Myapplication.MAC;
        }
        return host;
    }

    public void getDevice() {
        try {
            JsonBean<RootInfo> data = (JsonBean) gson.fromJson(HttpHelper.getStringSync(this.activity, url + "adremote/getDeviceInfo.action?mac=" + this.mac), new TypeToken<JsonBean<RootInfo>>() {
            }.getType());
            if (data.getMsgBody() != null) {
                System.out.println(((RootInfo) data.getMsgBody()).getUserName());
                this.myapplication.setRootInfo((RootInfo) data.getMsgBody());
                this.handler.sendEmptyMessage(-99);
                return;
            }
            this.handler.sendEmptyMessage(-98);
        } catch (Exception e) {
        }
    }

    public static String keepheart(Context context, String mac) {
        try {
            return (String) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/heartbeat.action?mac=" + mac), new TypeToken<JsonBean<String>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return "";
        }
    }

    public static String online(Context context, String mac) {
        try {
            return (String) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/online.action?mac=" + mac), new TypeToken<JsonBean<String>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return "";
        }
    }

    public static String update(Context context, String mac, int softVersion) {
        try {
            return ((Update) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/checkSoftUpdate.action?mac=" + mac + "&softVersion=" + softVersion), new TypeToken<JsonBean<Update>>() {
            }.getType())).getMsgBody()).getSoft().getFilePath();
        } catch (Exception e) {
            return "";
        }
    }

    public static List<MsgInfo> getmsg(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getDeviceMessage.action?mac=" + mac), new TypeToken<JsonBean<List<MsgInfo>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<AppInfo> getapp(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getAppInfos.action?mac=" + mac), new TypeToken<JsonBean<List<AppInfo>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<Vod> getvod(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getAllVideos.action?mac=" + mac), new TypeToken<JsonBean<List<Vod>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<Ad> getAdInfo(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getAllAd.action?mac=" + mac), new TypeToken<JsonBean<List<Ad>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<LocalService> getlocal(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getAllShopIntroduce.action?mac=" + mac), new TypeToken<JsonBean<List<LocalService>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static AllLive getlive(Context context, String mac) {
        try {
            return (AllLive) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getAllLiveManagement.action?mac=" + mac), new TypeToken<JsonBean<AllLive>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new AllLive();
        }
    }

    public static List<LiuYan> getliuyan(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getServerMsgList.action?mac=" + mac), new TypeToken<JsonBean<List<LiuYan>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static String call(Context context, String mac) {
        try {
            JsonBean jsonBean = (JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getCalling.action?mac=" + mac), new TypeToken<JsonBean<String>>() {
            }.getType());
        } catch (Exception e) {
        }
        return "";
    }

    public static String getcity(Context context) {
        try {
            return (String) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/updateCity.action"), new TypeToken<JsonBean<String>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return "";
        }
    }

    public static List<JiShiCategory> getjishi(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getTechnicianType.action?mac=" + mac), new TypeToken<JsonBean<List<JiShiCategory>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<JishiDetail> getjishidetail(Context context, String mac, int typeId) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getTechnicianById.action?mac=" + mac + "&&typeId=" + typeId), new TypeToken<JsonBean<List<JishiDetail>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<FodCategory> getfodcategory(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getDishStyle.action?mac=" + mac), new TypeToken<JsonBean<List<FodCategory>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<FodDetail> getfod(Context context, String mac, int styleId) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getDishStringByStyleId.action?mac=" + mac + "&&styleId=" + styleId), new TypeToken<JsonBean<List<FodDetail>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<VoidPrograms> searchvod(Context context, String mac, String vodName) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getvideoByname.action?mac=" + mac + "&vodName=" + vodName), new TypeToken<JsonBean<List<VoidPrograms>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<Song> searchmusic(Context context, String mac, String songName) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/findSongByName.action?mac=" + mac + "&songName=" + songName), new TypeToken<JsonBean<List<Song>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<SongCategory> getSongs(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getCategorySongs.action?mac=" + mac), new TypeToken<JsonBean<List<SongCategory>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<App> getandroid(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getAppCategory.action?mac=" + mac), new TypeToken<JsonBean<List<App>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static List<LiveCategory> getlivecategory(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getLiveCategory.action?mac=" + mac), new TypeToken<JsonBean<List<LiveCategory>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static Bean getweather(String json) {
        try {
            return (Bean) ((JsonBean) gson.fromJson(json, new TypeToken<JsonBean<Bean>>() {
            }.getType())).getRetData();
        } catch (Exception e) {
            System.err.println("JsonResult_getweather():" + e.toString());
            return new Bean();
        }
    }

    public static List<String> totalserver_msg(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, all_server_url + "box/getMessageAction_doGetMessage.action?mac=" + mac + "&nodeName=one"), new TypeToken<JsonBean<List<String>>>() {
            }.getType())).getData();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static String totalserver_update(Context context, String mac, int version) {
        try {
            JsonBean<TotalUpdate> data = (JsonBean) gson.fromJson(HttpHelper.getStringSync(context, all_server_url + "box/getMessageAction_checkUpdate.action?mac=" + mac + "&nodeName=one" + "&version=" + version), new TypeToken<JsonBean<TotalUpdate>>() {
            }.getType());
            System.out.println(((TotalUpdate) data.getData()).getApkUrl());
            return ((TotalUpdate) data.getData()).getApkUrl();
        } catch (Exception e) {
            System.err.println("JsonResult_totalserver_update():" + e.toString());
            return "";
        }
    }

    public static List<Ad> totalserver_ads(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, all_server_url + "box/getMessageAction_getAd.action?mac=" + mac + "&nodeName=one"), new TypeToken<JsonBean<List<Ad>>>() {
            }.getType())).getData();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public void getad() {
        String path = url + "adremote/getAllAd.action?mac=" + this.mac;
        System.out.println(path);
        Myapplication.getQueue().add(new StringRequest(path, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    if (((List) ((JsonBean) CopyOfJsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<Ad>>>() {
                    }.getType())).getMsgBody()).isEmpty()) {
                        CopyOfJsonResult.this.handler.sendEmptyMessage(101);
                    } else {
                        CopyOfJsonResult.this.handler.sendEmptyMessage(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }
}
