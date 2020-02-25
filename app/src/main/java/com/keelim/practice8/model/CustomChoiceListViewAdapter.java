package com.keelim.practice8.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.keelim.practice8.R;

import java.util.ArrayList;



public class CustomChoiceListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    @NonNull
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    // ListViewAdapter의 생성자
    public CustomChoiceListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        //ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView textTextView_name = (TextView) convertView.findViewById(R.id.textView4);
        TextView textTextView_cate = (TextView) convertView.findViewById(R.id.textView5);
        TextView textTextView_area = (TextView) convertView.findViewById(R.id.textView6);
        TextView textTextView_date = (TextView) convertView.findViewById(R.id.textView7);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView_name.setText(listViewItem.getName());
        textTextView_cate.setText(listViewItem.getCate());
        textTextView_area.setText(listViewItem.getArea());
        textTextView_date.setText(listViewItem.getDate());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }


    public void add_Item(String name, String cate, String area, String date) {
        ListViewItem item = new ListViewItem();
        //item.setIcon(icon);
        item.setName(name);
        item.setCate(cate);
        item.setArea(area);
        item.setDate(date);
        listViewItemList.add(item);
    }


    public void remove(int position) {
        listViewItemList.remove(position);
    }


}
