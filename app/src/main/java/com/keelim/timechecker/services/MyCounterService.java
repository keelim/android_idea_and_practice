package com.keelim.timechecker.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.keelim.timechecker.interfaces.IMyCounterService;

public class MyCounterService extends Service {
    private int count;
    private boolean isStop;

    public MyCounterService() {
    }


    IMyCounterService.Stub binder = new IMyCounterService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getCount() throws RemoteException {
            return count;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Thread counter = new Thread(new Counter());
        counter.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isStop = true;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

    private class Counter implements Runnable {
        private Handler handler = new Handler();


        @Override
        public void run() {
            for (count = 0; count < 50; count++) {
                if (isStop) break;

                handler.post(() -> {
                    Toast.makeText(getApplicationContext(), count + "", Toast.LENGTH_SHORT).show();

                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            handler.post(() -> {
                Toast.makeText(getApplicationContext(), "서비스 종료", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
