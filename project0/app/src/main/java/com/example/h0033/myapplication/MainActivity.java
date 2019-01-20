package com.example.h0033.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.textView);

        View view = findViewById(R.id.view);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float culx = event.getX();
                float culy = event.getY();

                if (action == MotionEvent.ACTION_DOWN) {
                    println("손가락 눌렸음: " + culx + "," + culy);

                } else if (action == MotionEvent.ACTION_MOVE) {
                    println("손가락 움직임: " + culx + "," + culy);

                } else if (action == MotionEvent.ACTION_UP) {
                    println("손가락 눌렸음: " + culx + "," + culy);

                }
                return true;
            }

        });
    }

    public void println(String data) {
        textview.append(data + "\n");
    }


}
