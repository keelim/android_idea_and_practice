package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MainFragment fragment;
    MenuFragment fragment2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        fragment = new MainFragment();
        fragment2 = new MenuFragment();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();


            }
        });
    }

    public void onFragmentChange(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
        }
    }
}
