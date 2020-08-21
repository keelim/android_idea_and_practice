package com.keelim.practice1.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.keelim.practice1.R;
import com.keelim.practice1.view.FindInfoActivitiy;
import com.keelim.practice1.view.InfoActivity;
import com.keelim.practice1.view.WebViewActivity;
import com.keelim.practice1.model.GuideData;

import org.jetbrains.annotations.NotNull;

/**
 * Created by kimok_000 on 2015-11-28.
 */
public class Guide_Adapter extends ArrayAdapter<GuideData> {
    // 레이아웃 XML을 읽어들이기 위한 객체
    private LayoutInflater mInflater;
    private Context context;

    public Guide_Adapter(Context context, ArrayList<GuideData> object) {
        // 상위 클래스의 초기화 과정
        // context, 0, 자료구조
        super(context, 0, object);
        this.context=context;
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
            view = mInflater.inflate(R.layout.guide_content, null);
        } else {
            view = v;
        }
        // 자료를 받는다.
        final GuideData data = this.getItem(position);
        if (data != null) {
            //화면 출력
            TextView title = view.findViewById(R.id.title);
            final TextView content= view.findViewById(R.id.content);
            RelativeLayout button = view.findViewById(R.id.button);
            final LinearLayout vis = view.findViewById(R.id.vis);
            RelativeLayout grid = view.findViewById(R.id.grid);
            TextView button_text = view.findViewById(R.id.button_text);
            final ImageView icon = view.findViewById(R.id.icon);

            grid.setOnClickListener(v12 -> {
                if(vis.getVisibility()==View.GONE){
                    icon.setImageResource(R.drawable.ic_spinner);
                    vis.setVisibility(View.VISIBLE);
                }else {
                    icon.setImageResource(R.drawable.ic_spinner_hold);
                    vis.setVisibility(View.GONE);
                }
            });

            button.setOnClickListener(v1 -> {
                switch (data.getButton()){
                    case "유실물 조회" : {
                        Intent goInfo = new Intent(context,FindInfoActivitiy.class);
                        goInfo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Log.e("class launch","com");
                        context.startActivity(goInfo);
                        break;
                    }
                    case "분실물 처리 절차 안내" : {
                        Intent goInfo = new Intent(context, InfoActivity.class);
                        goInfo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(goInfo);
                        break;
                    }
                    case "분실물 조회/ 등록" : {
                        Intent goInfo = new Intent(context, FindInfoActivitiy.class);
                        goInfo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(goInfo);
                        break;
                    }
                    case "LOST112" : {
                       Intent goWeb = new Intent(context,WebViewActivity.class);
                        goWeb.putExtra("url", "https://www.lost112.go.kr/");
                        goWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(goWeb);
                        break;
                    }
                    case "죄송합니다" : {
                        Toast.makeText(getContext(),"죄송합니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            title.setText(data.getTitle());
            content.setText(data.getContent());
            button_text.setText(data.getButton());
        }
        return view;
    }
}
