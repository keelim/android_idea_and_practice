package com.keelim.temp1.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.keelim.temp1.R;
import com.keelim.temp1.db.DictionaryDatabaseHelper;
import com.keelim.temp1.db.DictionaryLoader;
import com.keelim.temp1.utils.WordDefinition;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DictionaryListActivity extends Activity {
    private AutoCompleteTextView searchEditText;
    private ArrayList<WordDefinition> allWordDefinitions;
    private DictionaryDatabaseHelper myDictionaryDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionarylist);

        allWordDefinitions = new ArrayList<>();
        searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        ListView dictionaryListView = findViewById(R.id.dictionaryListView);
        TextView userTextView = findViewById(R.id.personTextView);


        Intent intent = getIntent();
        String temp = intent.getStringExtra("user");
        userTextView.setText(temp + "'s Dictionary");

        myDictionaryDatabaseHelper = new DictionaryDatabaseHelper(this, null, 1);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_NAME_STRING, MODE_PRIVATE);

        boolean initialized = sharedPreferences.getBoolean("initialized", false);


        if (!initialized) {
            initializeDatabase();
            sharedPreferences.edit()
                    .putBoolean("initialized", true)
                    .apply();
        }

        allWordDefinitions = myDictionaryDatabaseHelper.getAllWords();

        ArrayList<String> strings = myDictionaryDatabaseHelper.getWords();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
        searchEditText.setAdapter(adapter);

        dictionaryListView.setAdapter(new BaseAdapter() { //따로 뺼수 있는 방법이 있는가?
            @Override
            public View getView(int position, View view, ViewGroup arg2) {
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.list_item, null);
                }

                TextView textView = view.findViewById(R.id.listItemTextView);
                textView.setText(allWordDefinitions.get(position).word);

                return view;
            }

            @Override
            public long getItemId(int arg0) {
                return 0;
            }

            @Override
            public Object getItem(int arg0) {
                return null;
            }

            @Override
            public int getCount() {
                return allWordDefinitions.size();
            }
        });

        dictionaryListView.setOnItemClickListener((arg0, view, position, arg3) -> {
            Intent detail = new Intent(DictionaryListActivity.this, WordDefinitionDetailActivity.class);
            detail.putExtra("word", allWordDefinitions.get(position).word);
            detail.putExtra("definition", allWordDefinitions.get(position).definition);
            startActivity(detail);
        });

        searchButton.setOnClickListener(v -> {
            String string = searchEditText.getText().toString();
            WordDefinition wordDefinition = myDictionaryDatabaseHelper.getWordDefinition(string);
            if (wordDefinition == null) {
                Toast.makeText(getApplicationContext(), "Word not found", Toast.LENGTH_LONG).show();
            } else {
                Intent intent1 = new Intent(this, WordDefinitionDetailActivity.class);
                intent1.putExtra("word", wordDefinition.word);
                intent1.putExtra("definition", wordDefinition.definition);
                startActivity(intent1);
            }
        });
    }

    private void initializeDatabase() {
        InputStream inputStream = getResources().openRawResource(R.raw.glossary);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        DictionaryLoader.loadData(bufferedReader, myDictionaryDatabaseHelper);
    }
}
