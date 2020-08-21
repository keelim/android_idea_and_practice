package com.keelim.practice12.utils;



import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.keelim.practice12.model.StationDatas;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

//import datas.BusStationDatas;

public class StationXMLParser extends XMLParser implements Runnable {
    @Nullable
    private ArrayList<StationDatas> mDataList;
    private Handler mHandler;

    public StationXMLParser(String addr, Handler handler) {
        super(addr);
        mHandler = handler;
    }

    public void startParsing() {
        XmlPullParser parser = getXMLParser("utf-8");

        if (parser == null) {
            mDataList = null;
            Log.d("StationXMLParser", "Parser Object is null");
        } else {
            mDataList = new ArrayList<>();
            String statnNm = null;
            String subwayNm = null;
            String subX = null;
            String subY;
            String tag;
            try {
                int parserEvent = parser.getEventType();
                int tagIdentifier = 0;

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                        case XmlPullParser.END_TAG:
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            switch (tag) {
                                case "statnNm":
                                    tagIdentifier = 1;
                                    break;
                                case "subwayNm":
                                    tagIdentifier = 2;
                                    break;
                                case "subwayXcnts":
                                    tagIdentifier = 3;
                                    break;
                                case "subwayYcnts":
                                    tagIdentifier = 4;
                                    break;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if (tagIdentifier == 1) {
                                statnNm = parser.getText().trim();
                            } else if (tagIdentifier == 2) {
                                subwayNm = parser.getText().trim();
                            } else if (tagIdentifier == 3) {
                                subX = parser.getText().trim();
                            } else if (tagIdentifier == 4) {
                                subY = parser.getText().trim();
                                StationDatas data = new StationDatas(statnNm, subwayNm, subX, subY);
                                mDataList.add(data);
                            }
                            tagIdentifier = 0;
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                Log.d("StationXMLParser", e.getMessage());
            }
        }
        Log.d("StationXMLParser", Integer.toString(mDataList.size()));
    }

    @Nullable
    public ArrayList<StationDatas> getResult() {
        return mDataList;
    }

    public void run() {
        startParsing();
        mHandler.sendEmptyMessage(0);
    }
}
