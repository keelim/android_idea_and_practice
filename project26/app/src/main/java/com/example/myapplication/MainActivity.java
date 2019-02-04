package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    //    int value = 0;
    ValueHandler handler = new ValueHandler();

    Handler handler2 = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Background background = new Background();
//                background.start();

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        int value = 0;
                        boolean running = true;
                        while (running) {
                            value += 1;

                            handler2.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("현재 값:" + value);
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                textView.setText("현재 값 : " + value);
            }
        });
    }

    class Background extends Thread {
        int value = 0;
        boolean running = false;

        @Override
        public void run() {
            running = true;
            while (running) {
                value += 1;
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);

                msg.setData(bundle);
                handler.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ValueHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            textView.setText("현재값: " + value);
        }
    }
}
