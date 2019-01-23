package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {
    //인플레이션을 통하여 xml을 연결해야 한다.
    MainActivity activity;

    @Override
    public void onAttach(Context context) { //fragment를 activity에 올린다.
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        Button button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity.onFragmentChange(0);
            }
        });

        return root;
    }




}
