package com.example.h0033.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "keelim"); //시스템에서 활용하지 않는다.

                setResult(Activity.RESULT_OK, intent);


                finish(); //액티비티 스택 첫번 째 화면은 깔리게 된다. menu 화면을 종료 하는 곳
            }
        });


    }
}
