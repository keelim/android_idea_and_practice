package com.keelim.practice12.utils;



import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.keelim.practice12.model.CNMDatas;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;



public class CNM_XMLParser extends XMLParser implements Runnable {
    @Nullable
    private ArrayList<CNMDatas> mDataList;
    private Handler mHandler;

    public CNM_XMLParser(String addr, Handler handler) {
        super(addr);
        mHandler = handler;
    }

    public void startParsing() {
        XmlPullParser parser = getXMLParser("utf-8");

        if (parser == null) {
            mDataList = null;
            Log.d("CNM_XMLParser", "Parser Object is null");
        } else {
            mDataList = new ArrayList<>();
            String x;
            String y;
            String tag;
            try {
                int parserEvent = parser.getEventType();
                int tagIdentifier = 0;

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                        case XmlPullParser.TEXT:
                        case XmlPullParser.END_TAG:
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if (tag.equals("result")) {
                                x = parser.getAttributeValue(null, "x").trim();
                                y = parser.getAttributeValue(null, "y").trim();
                                CNMDatas data = new CNMDatas(x, y);
                                mDataList.add(data);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                Log.d("CNM_XMLParser", e.getMessage());
            }
            //CNMDatas tmp=new CNMDatas("1.0","1.0");
            //mDataList.add(tmp);
        }
        //Log.d("CNM_XMLParser", Integer.toString(mDataList.size()));
    }

    @Nullable
    public ArrayList<CNMDatas> getResult() {
        return mDataList;
    }

    public void run() {
        startParsing();
        mHandler.sendEmptyMessage(0);
    }
}
