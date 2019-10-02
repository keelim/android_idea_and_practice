package com.keelim.temp1.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import androidx.databinding.DataBindingUtil;

import com.keelim.temp1.R;
import com.keelim.temp1.databinding.ActivityWordDefinitionDetailBinding;

public class WordDefinitionDetailActivity extends Activity {
    ActivityWordDefinitionDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_definition_detail);
        binding.setActivity(this);
        binding.wordTextView.setText(getStrings("word")); //인텐트 정보 받아오기
        binding.definitionTextView.setText(getStrings("definition"));
    }

    private String getStrings(String s) {
        return getIntent().getStringExtra(s);
    }

}
