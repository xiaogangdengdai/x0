package com.ssx.spa.view.wearther;

import com.ssx.spa.R;

public class WeatherImage {
    public static int parseIcon(String strIcon) {
        if (strIcon == null || "晴".equals(strIcon)) {
            return R.drawable.weather_sunny;
        }
        if ("晴转多云".equals(strIcon)) {
            return R.drawable.weather_partly;
        }
        if ("雾".equals(strIcon)) {
            return R.drawable.weather_mist;
        }
        if ("多云".equals(strIcon)) {
            return R.drawable.weather_mostly_sunny;
        }
        if ("阴".equals(strIcon)) {
            return R.drawable.weather_cloudy;
        }
        if ("小雨".equals(strIcon)) {
            return R.drawable.weather_chance_of_rain;
        }
        if ("中雨".equals(strIcon)) {
            return R.drawable.weather_rain;
        }
        if ("大雨".equals(strIcon)) {
            return R.drawable.weather_storm;
        }
        if ("雷阵雨".equals(strIcon) || "阵雨".equals(strIcon)) {
            return R.drawable.weather_chance_of_tstorm;
        }
        if ("暴雨".equals(strIcon)) {
            return R.drawable.weather_thunderstorm;
        }
        if ("小雪".equals(strIcon)) {
            return R.drawable.weather_snow;
        }
        if ("中雪".equals(strIcon)) {
            return R.drawable.weather_flurries;
        }
        if ("大雪".equals(strIcon)) {
            return R.drawable.weather_icy;
        }
        if ("雨夹雪".equals(strIcon)) {
            return R.drawable.weather_sleet;
        }
        return "冰雹 ".equals(strIcon) ? R.drawable.weather_flurries : R.drawable.weather_sunny;
    }
}
