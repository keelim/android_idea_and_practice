package com.keelim.DatatoObject.AsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.layout.R;

public class Calculator extends AppCompatActivity {


    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tvDisplay = findViewById(R.id.tv_display);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncCalculation();
            }
        });
    }

    private void startAsyncCalculation() {

        AsyncCalculateTask asyncCalculateTask = new AsyncCalculateTask();
        asyncCalculateTask.execute(1, 100000000);
    }


    class AsyncCalculateTask extends AsyncTask<Integer, Integer, Integer> {


        /**
         * Async Thread Method
         */
        @Override
        protected Integer doInBackground(Integer... integers) { //가변형 매개 변수

            int number = integers[0];
            int count = integers[1];
            int result = 0;

            int percent = count / 100;

            for (int i = 0; i < count; i++) {
                result += number;


                if (result % percent == 0) {
                    //1%
                    publishProgress(result / percent);
                }
            }

            return result;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvDisplay.setText(values[0] + "Percent");
        }

        /**
         * UI Thread
         */
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            tvDisplay.setText("Result: " + integer);
        }
    }
}
