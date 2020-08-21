package com.keelim.temp1.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.keelim.temp1.R;
import com.keelim.temp1.databinding.ActivityMainBinding;

public class MainActivity extends Activity {
    private ActivityMainBinding binding;
    final static String SHARED_NAME_STRING = "sharedp";
    final static String USER_NAME_STRING = "user";


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        sharedPreferences = getSharedPreferences(SHARED_NAME_STRING, MODE_PRIVATE);
        binding.userNameEditText.setText(sharedPreferences.getString(USER_NAME_STRING, ""));

        binding.enterButton.setOnClickListener(v -> {
            Intent intent_user = new Intent(MainActivity.this, DictionaryListActivity.class);
            intent_user.putExtra("user", binding.userNameEditText.getText().toString());
            startActivity(intent_user);
            sharedPreferences.edit().putString(USER_NAME_STRING, binding.userNameEditText.getText().toString()).apply();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); //menu 를 load 한다.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent_setting = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent_setting);
                break;
            case R.id.action_developer:
                Intent intent_developer = new Intent(getApplicationContext(), DeveloperPageActivity.class);
                startActivity(intent_developer);
                break;
        }
        return true;
    }
}
