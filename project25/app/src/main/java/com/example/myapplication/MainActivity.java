package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoverFlow coverFlow = (CoverFlow) findViewById(R.id.coverflow);
        ImageAdapter adapter = new ImageAdapter();
        coverFlow.setAdapter(adapter);
    }

    class ImageAdapter extends BaseAdapter{
        int[] items = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground};
        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView view = new ImageView(getApplicationContext());


            view.setImageResource(items[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
            drawable.setAntiAlias(true);


            return view;
        }
    }
}
