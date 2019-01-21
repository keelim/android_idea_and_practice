package com.example.h0033.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textview;

    GestureDetector detector;

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

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                println("onDown 호출됨");

                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                println("onShowpress 호출됨");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                println("onSingleTapUp 호출됨");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                println("onScroll 호출됨" + distanceX +"," + distanceY);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                println("onLongPress 호출됨");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                println("onFling 호출됨" + velocityX + "," + velocityY);
                return true;
            }
        });
        View view2 = findViewById(R.id.view2);
        view2.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void println(String data) {
        textview.append(data + "\n");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(this, "System back Button call", Toast.LENGTH_LONG).show();

            return true;
        }
        return false;
    }
}
