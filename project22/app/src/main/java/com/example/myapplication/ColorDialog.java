package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class ColorDialog extends AppCompatActivity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_dialog);

        gridView = (GridView) findViewById(R.id.gridView);
        ColorAdapter adapter = new ColorAdapter();

        adapter.addItem(0x0000000);
        adapter.addItem(0xfff0000);
        adapter.addItem(0xff00ff0);

        gridView.setAdapter(adapter);

    }

    class ColorAdapter extends BaseAdapter {
        ArrayList<Integer> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ColorItemView view = null;
            if (convertView == null) {
                view = new ColorItemView(getApplicationContext());
            } else {
                view = (ColorItemView) convertView;
            }

            int color = (Integer) items.get(position);

            view.setColor(color);

            return view;
        }

        public void addItem(int color) {
            items.add(color);
        }


    }


}
