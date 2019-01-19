package com.example.h0033.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //Callback function
        Log.d(TAG, "onRecieve()호출됨");


        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {

        Object[] objects = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objects.length];

        for (int i = 0; i <objects.length ; i++) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                String format = bundle.getString("format");
                SmsMessage.createFromPdu((byte[]) objects[i], format);


            } else{
                SmsMessage.createFromPdu((byte[]) objects[i]);
            }

            return messages;
        }
    }
}
