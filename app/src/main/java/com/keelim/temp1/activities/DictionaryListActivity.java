package com.keelim.temp1.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.keelim.temp1.R;
import com.keelim.temp1.databinding.ActivityDictionarylistBinding;
import com.keelim.temp1.db.DictionaryDatabaseHelper;
import com.keelim.temp1.db.DictionaryLoader;
import com.keelim.temp1.utils.DictionaryAdapter;
import com.keelim.temp1.utils.WordDefinition;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DictionaryListActivity extends Activity {
    private ActivityDictionarylistBinding binding;
    private ArrayList<WordDefinition> allWordDefinitions;
    private DictionaryDatabaseHelper myDictionaryDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dictionarylist);
        binding.setActivity(this);

        binding.personTextView.setText(getIntent().getStringExtra("user") + "'s Dictionary");
        myDictionaryDatabaseHelper = new DictionaryDatabaseHelper(this, null, 1);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_NAME_STRING, MODE_PRIVATE);

        boolean initialized = sharedPreferences.getBoolean("initialized", false);
        if (!initialized) {
            InputStream inputStream = getResources().openRawResource(R.raw.glossary);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            DictionaryLoader.loadData(bufferedReader, myDictionaryDatabaseHelper);
            sharedPreferences.edit()
                    .putBoolean("initialized", true)
                    .apply();
        }

        allWordDefinitions = myDictionaryDatabaseHelper.getAllWords();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myDictionaryDatabaseHelper.getWords());
        binding.searchEditText.setAdapter(adapter);

        binding.dictionaryListView.setAdapter(new DictionaryAdapter(this, allWordDefinitions));
        binding.dictionaryListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent detail = new Intent(DictionaryListActivity.this, WordDefinitionDetailActivity.class);
            detail.putExtra("word", allWordDefinitions.get(i).word);
            detail.putExtra("definition", allWordDefinitions.get(i).definition);
            startActivity(detail);
        });

        binding.searchButton.setOnClickListener(v -> {
            WordDefinition wordDefinition = myDictionaryDatabaseHelper.getWordDefinition(binding.searchEditText.getText().toString());
            if (wordDefinition == null) {
                Toast.makeText(getApplicationContext(), "Word not found", Toast.LENGTH_LONG).show();
            } else {
                Intent intent_detail = new Intent(this, WordDefinitionDetailActivity.class);
                intent_detail.putExtra("word", wordDefinition.word);
                intent_detail.putExtra("definition", wordDefinition.definition);
                startActivity(intent_detail);
            }
        });
    }
}
