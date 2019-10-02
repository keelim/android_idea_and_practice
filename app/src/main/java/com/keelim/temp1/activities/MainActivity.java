package com.keelim.temp1.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import com.keelim.temp1.R;

public class MainActivity extends Activity {
    final static String SHARED_NAME_STRING = "sharedp";
    final static String USER_NAME_STRING = "user";

    private EditText editText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.userNameEditText);
        Button button = findViewById(R.id.enterButton);

        sharedPreferences = getSharedPreferences(SHARED_NAME_STRING, MODE_PRIVATE);
        String userNameString = sharedPreferences.getString(USER_NAME_STRING, "");
        editText.setText(userNameString);

        button.setOnClickListener(v -> {
            String string = editText.getText().toString();
            Intent intent = new Intent(MainActivity.this, DictionaryListActivity.class);
            intent.putExtra("user", string);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_NAME_STRING, string).apply();
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu); //menu 를 load 한다.
        return true;
    }

}
