package com.keelim.temp1.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.keelim.temp1.R;
import com.keelim.temp1.activities.DictionaryListActivity;

import java.util.ArrayList;

public class DictionaryAdapter extends BaseAdapter {
    private DictionaryListActivity activity;
    private ArrayList<WordDefinition> list;

    public DictionaryAdapter(DictionaryListActivity activity, ArrayList<WordDefinition> allWordDefinitions) {
        this.activity = activity;
        this.list = allWordDefinitions;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.list_item, null);
        }
        TextView textView = view.findViewById(R.id.listItemTextView);
        textView.setText(list.get(i).word);
        return view;
    }
}
