package com.keelim.practice12.utils;



import android.util.Log;

import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

public class XMLParser {
    private String mAddr;

    public XMLParser(String addr) {
        mAddr = addr;
    }

    @Nullable
    public XmlPullParser getXMLParser(String type) {
        try {
            URL targetURL = new URL(mAddr);
            InputStream is = targetURL.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, type);
            return parser;
        } catch (Exception e) {
            Log.d("XMLParser", e.getMessage());
            return null;
        }
    }
}
