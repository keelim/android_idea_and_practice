package com.example.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.iv_like).setOnClickListener(this);
        findViewById(R.id.iv_share).setOnClickListener(this);

        findViewById(R.id.photo).setOnClickListener(this);
        findViewById(R.id.photo).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_like:
                Toast.makeText(this, "I like", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_share:
                Toast.makeText(this, "i Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.photo:
                Toast.makeText(this, "Touch", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Toast.makeText(this, "Pciture", Toast.LENGTH_SHORT).show();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;



        }
        
        return true;
    }
}
