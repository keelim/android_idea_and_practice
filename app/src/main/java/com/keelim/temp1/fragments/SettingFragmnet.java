package com.keelim.temp1.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.keelim.temp1.R;

public class SettingFragmnet extends PreferenceFragmentCompat { //setting
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preference);
    }
}
