package com.keelim.temp1.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.keelim.temp1.R;
import com.keelim.temp1.databinding.ActivitySettingBinding;
import com.keelim.temp1.fragments.SettingFragmnet;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //fragment 설정을 위한 activity
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.setActivity(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingFragmnet())
                .commit();
    }
}
