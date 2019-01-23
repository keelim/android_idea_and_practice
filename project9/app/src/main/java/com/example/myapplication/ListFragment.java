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

public class ListFragment extends Fragment {
    MainActivity mainActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.viewer, container, false);
        Button button = (Button) rootView.findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onImageChange(0);
            }
        });
        Button button1 = (Button) rootView.findViewById(R.id.button6);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onImageChange(1);
            }
        });
        Button button2 = (Button) rootView.findViewById(R.id.button7);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onImageChange(2);
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
