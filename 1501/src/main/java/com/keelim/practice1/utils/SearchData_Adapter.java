package com.keelim.practice1.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.keelim.practice1.R;
import com.keelim.practice1.model.SearchData;

import org.jetbrains.annotations.NotNull;

/**
 * Copyright by 2015 Sunrin Internet High School EDCAN
 * Created by kimok_000 on 2015-10-13.
 */
public class SearchData_Adapter extends ArrayAdapter<SearchData> {
    // 레이아웃 XML을 읽어들이기 위한 객체
    private LayoutInflater mInflater;

    public SearchData_Adapter(Context context, ArrayList<SearchData> object) {
        // 상위 클래스의 초기화 과정
        // context, 0, 자료구조
        super(context, 0, object);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 보여지는 스타일을 자신이 만든 xml로 보이기 위한 구문
    @NotNull
    @Override
    public View getView(int position, View v, @NotNull ViewGroup parent) {
        View view;
        // 현재 리스트의 하나의 항목에 보일 컨트롤 얻기
        if (v == null) {
            // XML 레이아웃을 직접 읽어서 리스트뷰에 넣음
            view = mInflater.inflate(R.layout.searchlist_listview_content, null);
        } else {
            view = v;
        }
        // 자료를 받는다.
        final SearchData data = this.getItem(position);
        if (data != null) {
            //화면 출력
            TextView title = view.findViewById(R.id.lostlist_listview_title);
            TextView content= view.findViewById(R.id.lostlist_listview_content);
            TextView type = view.findViewById(R.id.lostlist_listview_type);
            TextView id = view.findViewById(R.id.lostlist_listview_id);
            TextView url = view.findViewById(R.id.lostlist_listview_url);
            TextView date = view.findViewById(R.id.lostlist_listview_date);
            TextView take_place = view.findViewById(R.id.lostlist_listview_take_plcae);
            TextView contact = view.findViewById(R.id.lostlist_listview_contact);
            TextView position0 = view.findViewById(R.id.lostlist_listview_position);
            TextView plcae = view.findViewById(R.id.lostlist_listview_place);
            TextView thing = view.findViewById(R.id.lostlist_listview_thing);
            TextView image_url = view.findViewById(R.id.lostlist_listview_image_url);

            title.setText(data.getLost_name());
            content.setText(data.getLost_date()+" "+data.getLost_position());
            id.setText(data.getLost_id());
            date.setText(data.getLost_date());
            take_place.setText(data.getLost_take_place());
            position0.setText(data.getLost_position());

        }
        return view;
    }
}
