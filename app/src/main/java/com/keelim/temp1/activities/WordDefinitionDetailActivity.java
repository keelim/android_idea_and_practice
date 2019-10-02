package com.keelim.temp1.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.keelim.temp1.R;

public class WordDefinitionDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_definition_detail);
		TextView wordTextView = findViewById(R.id.wordTextView);
		TextView definitionTextView = findViewById(R.id.definitionTextView);

		wordTextView.setText(getIntent().getStringExtra("word")); //인텐트 정보 받아오기
		definitionTextView.setText(getIntent().getStringExtra("definition"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.word_definition_detail, menu);
		return true;
	}
}
