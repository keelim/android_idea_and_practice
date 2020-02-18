package com.keelim.practice4.view.fragmnets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.keelim.practice4.R;


/**
 * Created by CJR on 2015-10-26.
 */
public class MainFragment4 extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main4, container, false);

        return view;
    }
}