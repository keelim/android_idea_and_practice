package com.keelim.practice10.utils;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WeatherTask extends AsyncTask<Void, Void, JSONObject>{
    @NonNull
    private String baseURL = "";
    @NonNull
    private String key = "";
    private String todayDate; //20180830
    private String todayTime; // 23:10 -> 2310
    @NonNull
    private String location = "nx=60&ny=127";
    private String receiveMsg;
    private final String[] tag = {"response", "body", "items"};

    public WeatherTask(){
        Date date = new Date();
        SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
        day.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        SimpleDateFormat time = new SimpleDateFormat("HH00");
        time.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        todayDate = day.format(date);
        todayTime = time.format(date);
        todayTime = setTime(todayTime); // 최근 관측 시간으로 시간변경
    }

    @NonNull
    @Override
    protected JSONObject doInBackground(Void... voids) {
        URL url = null;

        try {
            url = new URL(baseURL + "?ServiceKey=" + key + "&base_date=" + todayDate
                    + "&base_time=" + todayTime + "&" + location + "&_type=json");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                String str;
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                reader.close();
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 날씨 정보 가져오기
        JSONObject result = new JSONObject();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(receiveMsg);
            for(int i=0; i<tag.length; i++)
                jsonObject = jsonObject.optJSONObject(tag[i]);

            JSONArray jsonArray = new JSONArray(jsonObject.getString("item"));
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject tmp = jsonArray.getJSONObject(i);
                String category = tmp.getString("category");
                String value = tmp.getString("fcstValue");

                switch (category) {
                    case "PTY":
// 강수상태
                        result.put("강수상태", Integer.parseInt(value));
                        break;
                    case "SKY":
//하늘상태(구름양)
                        result.put("하늘상태", Integer.parseInt(value) - 1);
                        break;
                    case "T3H":
//기온
                        result.put("기온", value);
                        break;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @NonNull
    private String setTime(@NonNull String todayTime){
        int []baseTime = {2, 5, 8, 11, 14, 17, 20, 23};
        int hour = Integer.parseInt(todayTime) / 100;

        for(int i=0; i<baseTime.length; i++){
            if(hour < baseTime[i]){
                hour = baseTime[(i + baseTime.length - 1) % baseTime.length];
                break;
            }
        }

        String ret = "";
        if(hour < 10)
            ret += "0";
        ret += hour + "00";
        return ret;
    }
}
