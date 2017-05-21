package com.ssx.spa.view.common;

import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.ssx.spa.R;
import com.ssx.spa.common.MyToast;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.common.SocketClient;
import com.ssx.spa.javabean.Dates;
import com.ssx.spa.javabean.HeadSwitch;
import com.ssx.spa.javabean.JsonBean;
import com.ssx.spa.net.JsonResult;
import com.ssx.spa.view.common.MyDialogBuilder.OnDialogClose;
import com.ssx.spa.view.wearther.Bean;
import com.ssx.spa.view.wearther.WeatherImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Head extends LinearLayout {
    private final int HEART = 274;
    private final int TIME = 273;
    ArrayAdapter arr_adapter;
    private Bean bean;
    OnDialogClose cc = new OnDialogClose() {
        public void close() {
        }
    };
    List<String> data_list;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 102:
                    int i = 0;
                    while (i < Head.this.myapplication.getHeadSwitchs().size()) {
                        try {
                            if (((HeadSwitch) Head.this.myapplication.getHeadSwitchs().get(i)).getId() == 1 && ((HeadSwitch) Head.this.myapplication.getHeadSwitchs().get(i)).getStatus() == 1) {
                                Head.this.head_liuwei.setVisibility(0);
                            }
                            if (((HeadSwitch) Head.this.myapplication.getHeadSwitchs().get(i)).getId() == 2 && ((HeadSwitch) Head.this.myapplication.getHeadSwitchs().get(i)).getStatus() == 1) {
                                Head.this.head_call.setVisibility(0);
                            }
                            i++;
                        } catch (Exception e) {
                            return;
                        }
                    }
                    return;
                case 273:
                    Head.this.setservertime();
                    return;
                default:
                    return;
            }
        }
    };
    private ImageButton head_call;
    private ImageButton head_liuwei;
    private Timer heart = new Timer(true);
    private TextView main_apm;
    private TextView main_date;
    private TextView main_time;
    private TextView main_weather;
    private ImageView main_weather_img;
    private TextView main_week;
    private TextView main_wendu;
    private Myapplication myapplication;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (Head.this.myapplication.Iscall()) {
                Head.this.head_call.setImageResource(R.drawable.call_cancle_vf);
            } else {
                Head.this.head_call.setImageResource(R.drawable.bottom_hujiao);
            }
        }
    };
    private Timer timer = new Timer(true);

    class Call extends AsyncTask<String, String, String> {
        Call() {
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        protected String doInBackground(String... params) {
            try {
                JsonResult.call(Head.this.getContext(), Myapplication.MAC);
            } catch (Exception e) {
            }
            return null;
        }
    }

    class HeadDate extends AsyncTask<String, String, String> {
        HeadDate() {
        }

        protected void onPostExecute(String result) {
            try {
                Head.this.bean = Head.this.myapplication.getWeatherbean();
                if (!Head.this.bean.getWeather().equals("")) {
                    String Weather = Head.this.bean.getWeather();
                    if (Weather == null) {
                        Weather = "晴";
                    }
                    Head.this.main_weather.setText(Weather);
                    String L_tmp = Head.this.bean.getL_tmp();
                    if (L_tmp == null) {
                        L_tmp = "0";
                    }
                    String H_tmp = Head.this.bean.getH_tmp();
                    if (H_tmp == null) {
                        H_tmp = "0";
                    }
                    Head.this.main_wendu.setText(new StringBuilder(String.valueOf(L_tmp)).append("~").append(H_tmp).append("°C").toString());
                    Head.this.main_weather_img.setBackgroundResource(WeatherImage.parseIcon(Head.this.bean.getWeather()));
                }
            } catch (Exception e) {
            }
            super.onPostExecute(result);
        }

        protected String doInBackground(String... params) {
            try {
                Head.this.myapplication.setCity(JsonResult.getcity(Head.this.getContext()));
            } catch (Exception e) {
            }
            return null;
        }
    }

    class headBtn implements OnClickListener {
        headBtn() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.head_liuwei:
                    try {
                        Builder dialog = new Builder(Head.this.getContext());
                        dialog.setTitle("留座");
                        dialog.setMessage("请选择留座的时间(单位：分钟)");
                        final Spinner spinner = new Spinner(Head.this.getContext());
                        Head.this.data_list = new ArrayList();
                        Head.this.data_list.add("5");
                        Head.this.data_list.add("10");
                        Head.this.data_list.add("15");
                        Head.this.arr_adapter = new ArrayAdapter(Head.this.getContext(), 17367048, Head.this.data_list);
                        Head.this.arr_adapter.setDropDownViewResource(17367049);
                        spinner.setAdapter(Head.this.arr_adapter);
                        dialog.setView(spinner);
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Head.this.getContext().startActivity(new Intent(Head.this.getContext(), LiuweiActivity.class).putExtra("time", (String) Head.this.data_list.get(spinner.getSelectedItemPosition())));
                                } catch (Exception e) {
                                    Toast.makeText(Head.this.getContext(), "请输入有效时间", 1).show();
                                }
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialog.create().show();
                        return;
                    } catch (Exception e) {
                        return;
                    }
                case R.id.head_call:
                    MyToast.makeshow(Head.this.getContext(), "呼叫成功！", 0);
                    new Call().execute(new String[0]);
                    return;
                default:
                    return;
            }
        }
    }

    public Head(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.head, this);
        this.myapplication = (Myapplication) context.getApplicationContext();
        this.head_call = (ImageButton) view.findViewById(R.id.head_call);
        this.head_liuwei = (ImageButton) view.findViewById(R.id.head_liuwei);
        this.head_call.setOnClickListener(new headBtn());
        this.head_liuwei.setOnClickListener(new headBtn());
        this.main_time = (TextView) view.findViewById(R.id.main_time);
        this.main_date = (TextView) view.findViewById(R.id.main_date);
        this.main_week = (TextView) view.findViewById(R.id.main_week);
        this.main_weather = (TextView) view.findViewById(R.id.main_weather);
        this.main_wendu = (TextView) view.findViewById(R.id.main_wendu);
        this.main_weather_img = (ImageView) view.findViewById(R.id.main_weather_img);
        initdate();
        this.handler.sendEmptyMessageDelayed(102, 1000);
        if (this.myapplication.Iscall()) {
            this.head_call.setImageResource(R.drawable.call_cancle_vf);
        } else {
            this.head_call.setImageResource(R.drawable.bottom_hujiao);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("changecall");
        context.registerReceiver(this.receiver, filter);
    }

    private void initdate() {
        this.handler.sendEmptyMessage(273);
    }

    private void setservertime() {
        Myapplication.getQueue().add(new StringRequest(JsonResult.url + "adremote/getDate.action", new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JsonBean<Dates> aj = (JsonBean) JsonResult.gson.fromJson(response, new TypeToken<JsonBean<Dates>>() {
                    }.getType());
                    try {
                        if (aj.getMsgBody() != null) {
                            Head.this.main_time.setText(((Dates) aj.getMsgBody()).getMinAndHour());
                            Head.this.main_date.setText(((Dates) aj.getMsgBody()).getYearAndDate().split("年")[1].replace("月", "-").replace("日", ""));
                            Head.this.main_week.setText(((Dates) aj.getMsgBody()).getWeekMessage());
                        }
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }

    private void connectremote() {
        try {
            SocketClient.send(Myapplication.Bee_IP, Myapplication.Bee_PORT, 10, this.myapplication.getRootInfo().getUserName(), this.myapplication.Iscall());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
