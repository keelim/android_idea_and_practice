package com.example.myapplication;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListFragment fragment1;
    viewFragment fragment2;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //Life Cycle > onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        fragment1 =(ListFragment) manager.findFragmentById(R.id.listfragment);
        fragment2 =(viewFragment) manager.findFragmentById(R.id.viewerfragment);


    }

    public void onImageChange(int index){
            fragment2.setImage(index);
    }
}
