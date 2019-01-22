package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout page;
    Animation translateleft;
    Animation translateright;
    Button button;
    boolean ispageOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = (LinearLayout) findViewById(R.id.page);
        translateleft = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateright = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        Sliding sliding = new Sliding();
        translateleft.setAnimationListener(sliding);
        translateright.setAnimationListener(sliding);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ispageOpen) {
                    page.startAnimation(translateright);
                } else{
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateleft);
                }
            }
        });

    }

    class Sliding implements Animation.AnimationListener{ //call back 함수

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) { //애니메이션 리스터
            if(ispageOpen){
                page.setVisibility(View.INVISIBLE);
                button.setText("열");
            } else{
                button.setText("닫기");
                ispageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
