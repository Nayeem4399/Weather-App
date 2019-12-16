package com.example.weatherapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class WeatherUtils {

    public static final String API_KEY="dfcd19c76c02e3f667646b5ce3f3dd19";
    public static final String API_KEY2="017e3086aa619bb7566c187458d5d806";


    public static class Icon{

        public static final String ICON_PREFIX="https://openweathermap.org/img/wn/";
        public static final String ICON_SUFFIX="@2x.png";

    }


    public static String convertDtToDateString(Long dt) {

        long dtMills=dt*1000;
        Date date =new Date(dtMills);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }


    public static String convertTimeToTimeString(Long time) {

        long dtMills=time*1000;
        Date date =new Date(dtMills);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }


}
