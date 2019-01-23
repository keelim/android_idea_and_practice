package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class viewFragment extends Fragment {
    ImageView image;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_view, container, false);

        image = (ImageView) rootView.findViewById(R.id.image1);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setImage(int index){
        if(index == 0){
            image.setImageResource(R.drawable.good1);
        } else if (index == 1){
            image.setImageResource(R.drawable.good2);
        } else if (index ==3 ){
            image.setImageResource(R.drawable.good3);
        }
    }

}
