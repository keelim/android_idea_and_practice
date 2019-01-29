package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.listview);

        SingerAdapter adpter = new SingerAdapter();
        listView.setAdapter(adpter);
        adpter.addItem(new Singeritem("5454", "0102762840ㄴsdds09"));
        adpter.addItem(new Singeritem("ㄴㅇ", "010276824009"));
        adpter.addItem(new Singeritem("ㄴㅇㄴㅇ", "010276843009"));
        adpter.addItem(new Singeritem("ㄴㅇㄴㅇㄴㅇ", "01027684009"));
        adpter.addItem(new Singeritem("ㄴㅇㄴㅇ", "0102768404509"));

    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<Singeritem> items = new ArrayList<>();
        //Singer item을 관리를 할 수 있다.

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Singeritem item) {
            items.add(item);
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
            ItemView view = new ItemView(getApplicationContext());

            Singeritem item = items.get(position);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            return null;
        }
    }
}
