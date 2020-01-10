package com.keelim.timechecker;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.keelim.timechecker.interfaces.IMyCounterService;
import com.keelim.timechecker.services.MyCounterService;

public class TempActivity extends AppCompatActivity {
    private TextView tVCounter;
    private Button btnPlay;
    private Button btnStop;
    private boolean running = true;

    private IMyCounterService binder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMyCounterService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        tVCounter = findViewById(R.id.tvCounter);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyCounterService.class);
            bindService(intent, connection, BIND_AUTO_CREATE);
            running = true;
            new Thread(new GetCounterThread()).start();
        });

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(v -> {
            unbindService(connection);
            running = false;
        });
    }


    private class GetCounterThread implements Runnable {
        Handler handler = new Handler();

        @Override
        public void run() {
            while (running) {
                if (binder == null) continue;

                handler.post(() -> {
                    try {
                        tVCounter.setText(binder.getCount() + " ");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
