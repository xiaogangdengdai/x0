package com.ssx.spa.common;

import android.app.Application;
import android.widget.EditText;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ssx.spa.javabean.Ad;
import com.ssx.spa.javabean.AllLive;
import com.ssx.spa.javabean.App;
import com.ssx.spa.javabean.AppInfo;
import com.ssx.spa.javabean.FodCategory;
import com.ssx.spa.javabean.FodDetail;
import com.ssx.spa.javabean.HeadSwitch;
import com.ssx.spa.javabean.JiShiCategory;
import com.ssx.spa.javabean.JishiDetail;
import com.ssx.spa.javabean.LiuYan;
import com.ssx.spa.javabean.LiveCategory;
import com.ssx.spa.javabean.LocalService;
import com.ssx.spa.javabean.MsgInfo;
import com.ssx.spa.javabean.RootInfo;
import com.ssx.spa.javabean.SongCategory;
import com.ssx.spa.javabean.Update;
import com.ssx.spa.javabean.Vod;
import com.ssx.spa.util.HardInfoUtil;
import com.ssx.spa.view.wearther.Bean;
import java.util.ArrayList;
import java.util.List;

public class Myapplication extends Application {
    public static String ALLSERVER_IP = "www.sznabotv.com:8794";
    public static String Bee_IP = "192.168.1.230";
    public static int Bee_PORT = 10006;
    public static String DEFAULT_IP = "192.168.1.18:8793";
    public static final String Log = "spa";
    public static String MAC = HardInfoUtil.getMacAddress();
    static RequestQueue queue;
    private List<Ad> ads = new ArrayList();
    private List<AppInfo> appInfos = new ArrayList();
    private List<App> apps = new ArrayList();
    boolean call;
    private String city;
    private List<FodCategory> fodCategories = new ArrayList();
    private List<FodDetail> fodDetails = new ArrayList();
    List<HeadSwitch> headSwitchs = new ArrayList();
    private List<JiShiCategory> jiShiCategories = new ArrayList();
    private List<JishiDetail> jishiDetails = new ArrayList();
    private List<LiuYan> liuYans = new ArrayList();
    private int liuyannum;
    private AllLive live = new AllLive();
    private List<LiveCategory> liveCategories = new ArrayList();
    private List<LocalService> localServices = new ArrayList();
    private int localtai;
    private List<MsgInfo> msg = new ArrayList();
    private int navig = 0;
    private RootInfo rootInfo;
    private boolean showbottom;
    private List<SongCategory> songs = new ArrayList();
    public EditText tmpEditText;
    private List<String> total_msg;
    private Update update = new Update();
    private String updateurl;
    private List<Vod> vods = new ArrayList();
    private Bean weatherbean;

    public static RequestQueue getQueue() {
        return queue;
    }

    public void setQueue(RequestQueue queue) {
        queue = queue;
    }

    public void onCreate() {
        queue = Volley.newRequestQueue(this);
        super.onCreate();
    }

    public boolean isShowbottom() {
        return this.showbottom;
    }

    public void setShowbottom(boolean showbottom) {
        this.showbottom = showbottom;
    }

    public List<App> getApps() {
        return this.apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    public List<LiveCategory> getLiveCategories() {
        return this.liveCategories;
    }

    public void setLiveCategories(List<LiveCategory> liveCategories) {
        this.liveCategories = liveCategories;
    }

    public String getUpdateurl() {
        return this.updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }

    public List<String> getTotal_msg() {
        return this.total_msg;
    }

    public void setTotal_msg(List<String> total_msg) {
        this.total_msg = total_msg;
    }

    public int getLiuyannum() {
        return this.liuyannum;
    }

    public void setLiuyannum(int liuyannum) {
        this.liuyannum = liuyannum;
    }

    public List<LiuYan> getLiuYans() {
        return this.liuYans;
    }

    public void setLiuYans(List<LiuYan> liuYans) {
        this.liuYans = liuYans;
    }

    public List<FodDetail> getFodDetails() {
        return this.fodDetails;
    }

    public void setFodDetails(List<FodDetail> fodDetails) {
        this.fodDetails = fodDetails;
    }

    public List<FodCategory> getFodCategories() {
        return this.fodCategories;
    }

    public void setFodCategories(List<FodCategory> fodCategories) {
        this.fodCategories = fodCategories;
    }

    public List<JishiDetail> getJishiDetails() {
        return this.jishiDetails;
    }

    public void setJishiDetails(List<JishiDetail> jishiDetails) {
        this.jishiDetails = jishiDetails;
    }

    public List<Ad> getAds() {
        return this.ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public List<MsgInfo> getMsg() {
        return this.msg;
    }

    public void setMsg(List<MsgInfo> msg) {
        this.msg = msg;
    }

    public List<JiShiCategory> getJiShiCategories() {
        return this.jiShiCategories;
    }

    public void setJiShiCategories(List<JiShiCategory> jiShiCategories) {
        this.jiShiCategories = jiShiCategories;
    }

    public List<LocalService> getLocalServices() {
        return this.localServices;
    }

    public void setLocalServices(List<LocalService> localServices) {
        this.localServices = localServices;
    }

    public Update getUpdate() {
        return this.update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public int getNavig() {
        return this.navig;
    }

    public void setNavig(int navig) {
        this.navig = navig;
    }

    public AllLive getLive() {
        return this.live;
    }

    public void setLive(AllLive live) {
        this.live = live;
    }

    public int getLocaltai() {
        return this.localtai;
    }

    public void setLocaltai(int localtai) {
        this.localtai = localtai;
    }

    public List<SongCategory> getSongs() {
        return this.songs;
    }

    public void setSongs(List<SongCategory> songs) {
        this.songs = songs;
    }

    public List<Vod> getVods() {
        return this.vods;
    }

    public void setVods(List<Vod> vods) {
        this.vods = vods;
    }

    public List<AppInfo> getAppInfos() {
        return this.appInfos;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    public RootInfo getRootInfo() {
        return this.rootInfo;
    }

    public void setRootInfo(RootInfo rootInfo) {
        this.rootInfo = rootInfo;
    }

    public Bean getWeatherbean() {
        return this.weatherbean;
    }

    public void setWeatherbean(Bean weatherbean) {
        this.weatherbean = weatherbean;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<HeadSwitch> getHeadSwitchs() {
        return this.headSwitchs;
    }

    public void setHeadSwitchs(List<HeadSwitch> headSwitchs) {
        this.headSwitchs = headSwitchs;
    }

    public boolean Iscall() {
        return this.call;
    }

    public void setcall(boolean call) {
        this.call = call;
    }
}
