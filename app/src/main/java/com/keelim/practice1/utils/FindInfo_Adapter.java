package com.keelim.practice1.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.keelim.practice1.R;
import com.keelim.practice1.view.WebViewActivity;
import com.keelim.practice1.model.FindInfoData;

import org.jetbrains.annotations.NotNull;

/**
 * Created by kimok_000 on 2015-11-28.
 */
public class FindInfo_Adapter extends ArrayAdapter<FindInfoData> {
    // 레이아웃 XML을 읽어들이기 위한 객체
    private LayoutInflater mInflater;
    private Context context;

    public FindInfo_Adapter(Context context, ArrayList<FindInfoData> object) {
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
            view = mInflater.inflate(R.layout.findinfo_content, null);
        } else {
            view = v;
        }
        // 자료를 받는다.
        final FindInfoData data = this.getItem(position);
        if (data != null) {
            //화면 출력
            TextView title = view.findViewById(R.id.title);
            final TextView content= view.findViewById(R.id.content);
            RelativeLayout button0 = view.findViewById(R.id.button0);
            RelativeLayout button1 = view.findViewById(R.id.button1);
            final LinearLayout vis = view.findViewById(R.id.vis);
            RelativeLayout grid = view.findViewById(R.id.grid);
            TextView button_text = view.findViewById(R.id.button_text);
            final ImageView icon = view.findViewById(R.id.icon);

            grid.setOnClickListener(v14 -> {
                if(vis.getVisibility()==View.GONE){
                    icon.setImageResource(R.drawable.ic_spinner);
                    vis.setVisibility(View.VISIBLE);
                }else {
                    icon.setImageResource(R.drawable.ic_spinner_hold);
                    vis.setVisibility(View.GONE);
                }
            });

            button0.setOnClickListener(v13 -> {
                Intent goWeb = new Intent(getContext(), WebViewActivity.class);
                goWeb.putExtra("url",data.getButton0());
                goWeb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(goWeb);
            });

            button1.setOnClickListener(v12 -> {
                Intent goWeb = new Intent(getContext(), WebViewActivity.class);
                goWeb.putExtra("url",data.getButton1());
                goWeb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(goWeb);
            });

            content.setOnClickListener(v1 -> {
//                    new MaterialDialog.Builder(getContext())
//                            .title("연락처로 전화")
//                            .content(data.getContent()+"번 으로 전화를 겁니다.")
//                            .positiveText("확인")
//                            .negativeText("취소")
//                            .backgroundColorRes(R.color.met)
//                            .titleColorRes(R.color.white)
//                            .contentColorRes(R.color.white)
//                            .positiveColorRes(R.color.white)
//                            .negativeColorRes(R.color.white)
//                            .callback(new MaterialDialog.ButtonCallback() {
//                                @Override
//                                public void onPositive(MaterialDialog dialog) {
//                                    super.onPositive(dialog);
                                Intent goTell = new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + data.getContent()));
                                goTell.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               context.startActivity(goTell);
//                                }
//                            }).show();
            });
            title.setText(data.getTitle());
            content.setText(data.getContent());
        }
        return view;
    }
}
