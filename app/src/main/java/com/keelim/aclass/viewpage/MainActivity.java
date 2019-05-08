package com.keelim.aclass.viewpage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.layout.R;

public class MainActivity extends AppCompatActivity {
//    user = 1
//    최초 = -1
    public static final String SHARED_PREF_FIRST_USER_KEY = "1000";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_main);
        TextView textView = findViewById(R.id.hello);
        SharedPreferences share = getPreferences(Context.MODE_PRIVATE);
        int first_user = share.getInt(SHARED_PREF_FIRST_USER_KEY, -1);

        if(first_user == 1){
            //기존 유저

            textView.setText("//오늘도 감사합니다.");
        } else if(first_user == -1){
            //최초 유조
            textView.setText("//만나서 반갑습니다. "); //저장을 해주어야 한다.
            saveUserIsNotFirst();

        }
    }

    private void saveUserIsNotFirst() {
        SharedPreferences share = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putInt(SHARED_PREF_FIRST_USER_KEY, 1);
        editor.commit();
    }
}
