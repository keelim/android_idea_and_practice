package com.example.myapplication;

import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView view = (ImageView) findViewById(R.id.imageview);

        Matrix matrix = new Matrix();

        matrix.postRotate(45.0f);

        view.setImageMatrix(matrix);


    }
}
