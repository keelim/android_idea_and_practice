package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView monthText;
    Button monthPrevious, monthNext;
    GridView monthView;
    int firstDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthText = (TextView) findViewById(R.id.monthText);
        monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthNext = (Button) findViewById(R.id.monthNext);
        monthView = (GridView) findViewById(R.id.monthView);
    }

    class MonthAdapter extends BaseAdapter {
        Monthitem[] items;
        Calendar calendar;

        public MonthAdapter() {
            items = new Monthitem[7 * 6];
            Date date = new Date();

            calendar = Calendar.getInstance();
            calendar.setTime(date);

            recalculate();
            resetDayNumbers();
        }

        public void recalculate() {
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            firstDay = getFirstDay(dayOfWeek);

            int firstdayofweek = calendar.getFirstDayOfWeek();
            getFirstDayOfWeek(firstdayofweek);
        }

        public int getFirstDay(int dayOfWeek) {
            int result = 0;
            if (dayOfWeek == Calendar.SUNDAY) {
                result = 0;
            } else if (dayOfWeek == Calendar.MONDAY) {
                result = 1;
            } else if (dayOfWeek == Calendar.TUESDAY) {
                result = 2;
            } else if (dayOfWeek == Calendar.WEDNESDAY) {
                result = 3;
            } else if (dayOfWeek == Calendar.THURSDAY) {
                result = 4;
            } else if (dayOfWeek == Calendar.FRIDAY) {
                result = 5;
            } else if (dayOfWeek == Calendar.SATURDAY) {
                result = 6;
            }

            return result;
        }

        public void resetDayNumbers() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private int getFirstDayOfWeek(int firstdayofweek) {
    }


}
