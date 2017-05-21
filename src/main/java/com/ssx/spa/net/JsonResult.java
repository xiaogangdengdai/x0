package com.ssx.spa.net;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.Ad;
import com.ssx.spa.javabean.AllLive;
import com.ssx.spa.javabean.App;
import com.ssx.spa.javabean.AppInfo;
import com.ssx.spa.javabean.FodCategory;
import com.ssx.spa.javabean.FodDetail;
import com.ssx.spa.javabean.HeadSwitch;
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
import com.ssx.spa.view.live.DoubleActivity;
import com.ssx.spa.view.live.LiveActivity;
import com.ssx.spa.view.wearther.Bean;
import java.util.ArrayList;
import java.util.List;

public class JsonResult {
    private static String all_server_url = ("http://" + Myapplication.ALLSERVER_IP + "/spa_total/");
    public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    private static String project_name = "/newspa/";
    public static String url = ("http://" + Myapplication.DEFAULT_IP + project_name);
    Activity activity;
    boolean b;
    Handler handler;
    String mac = Myapplication.MAC;
    Myapplication myapplication;

    public JsonResult(Activity activity, Myapplication myapplication, Handler handler) {
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
            return url;
        }
        return host;
    }

    public void getDevice() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getDeviceInfo.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<RootInfo> data = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<RootInfo>>() {
                    }.getType());
                    if (data.getMsgBody() != null) {
                        System.out.println(((RootInfo) data.getMsgBody()).getUserName());
                        JsonResult.this.myapplication.setRootInfo((RootInfo) data.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(-99);
                        return;
                    }
                    JsonResult.this.handler.sendEmptyMessage(-98);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void keepheart() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/heartbeat.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    if (!response.isEmpty()) {
                        System.out.println("keepheart");
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void online() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/online.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    response.isEmpty();
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
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

    public static List<Ad> getAdInfo(Context context, String mac) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getAllAd.action?mac=" + mac), new TypeToken<JsonBean<List<Ad>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
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

    public void getjishi() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getTechnicianType.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<JiShiCategory>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<JiShiCategory>>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setJiShiCategories((List) aj.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(109);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public static List<JishiDetail> getjishidetail(Context context, String mac, int typeId) {
        try {
            return (List) ((JsonBean) gson.fromJson(HttpHelper.getStringSync(context, url + "adremote/getTechnicianById.action?mac=" + mac + "&&typeId=" + typeId), new TypeToken<JsonBean<List<JishiDetail>>>() {
            }.getType())).getMsgBody();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public void getfodcategory() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getDishStyle.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<FodCategory>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<FodCategory>>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setFodCategories((List) aj.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(108);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
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
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getAllAd.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<Ad>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<Ad>>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setAds((List) aj.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(100);
                        return;
                    }
                    JsonResult.this.handler.sendEmptyMessage(101);
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                JsonResult.this.handler.sendEmptyMessage(101);
            }
        }));
    }

    public void modelswitch() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getFinctions.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<HeadSwitch>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<HeadSwitch>>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setHeadSwitchs((List) aj.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(102);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void getmsg() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getDeviceMessage.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<MsgInfo>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<MsgInfo>>>() {
                    }.getType());
                    if (!((List) aj.getMsgBody()).isEmpty()) {
                        JsonResult.this.myapplication.setMsg((List) aj.getMsgBody());
                        if (!JsonResult.this.b) {
                            JsonResult.this.handler.sendEmptyMessage(0);
                            JsonResult.this.b = true;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void getlocal() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getAllShopIntroduce.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<LocalService>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<LocalService>>>() {
                    }.getType());
                    if (!((List) aj.getMsgBody()).isEmpty()) {
                        JsonResult.this.myapplication.setLocalServices((List) aj.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(103);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void getlivecat() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getLiveCategory.action?mac=" + Myapplication.MAC, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<LiveCategory>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<LiveCategory>>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setLiveCategories((List) aj.getMsgBody());
                        JsonResult.this.getlive(JsonResult.this.myapplication.getLiveCategories());
                        JsonResult.this.handler.sendEmptyMessage(103);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void getvod() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getAllVideos.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<Vod>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<Vod>>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setVods((List) aj.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(105);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void getSongs() {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getCategorySongs.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<SongCategory>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<SongCategory>>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setSongs((List) aj.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(106);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void getandroid() {
        String path = url + "adremote/getAppCategory.action?mac=" + this.mac;
        System.out.println(path);
        Myapplication.getQueue().add(new StringRequest(path, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<List<App>> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<List<App>>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setApps((List) aj.getMsgBody());
                        JsonResult.this.handler.sendEmptyMessage(107);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void getlive(final List<LiveCategory> list) {
        Myapplication.getQueue().add(new StringRequest(url + "adremote/getAllLiveManagement.action?mac=" + this.mac, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<AllLive> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<AllLive>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setLive((AllLive) aj.getMsgBody());
                        if (((LiveCategory) list.get(0)).getStatusConfig() == 1) {
                            JsonResult.this.activity.startActivity(new Intent(JsonResult.this.activity, DoubleActivity.class));
                        } else if (((LiveCategory) list.get(1)).getStatusConfig() == 1) {
                            JsonResult.this.activity.startActivity(new Intent(JsonResult.this.activity, LiveActivity.class));
                        } else {
                            ComponentName component = new ComponentName("com.vst.live", "com.vst.live.VstLiveActivity");
                            Intent intent = new Intent();
                            intent.setComponent(component);
                            JsonResult.this.activity.startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    MyToast.makeshow(JsonResult.this.activity, "未绑定第三方直播软件！", 0);
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    public void getupdate() {
        String path = "";
        try {
            path = url + "adremote/checkSoftUpdate.action?mac=" + this.mac + "&softVersion=" + this.activity.getPackageManager().getPackageInfo(this.activity.getPackageName(), 0).versionCode;
        } catch (Exception e) {
        }
        Myapplication.getQueue().add(new StringRequest(path, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<Update> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<Update>>() {
                    }.getType());
                    if (aj.getMsgBody() != null) {
                        JsonResult.this.myapplication.setUpdateurl(((Update) aj.getMsgBody()).getSoft().getFilePath());
                        JsonResult.this.handler.sendEmptyMessage(120);
                    }
                } catch (Exception e) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }
}
