package com.keelim.practice10.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.keelim.practice10.R;
import com.keelim.practice10.model.Account;
import com.keelim.practice10.model.Basket;
import com.keelim.practice10.model.BasketItem;
import com.keelim.practice10.model.Clothes;
import com.keelim.practice10.model.Constant;
import com.keelim.practice10.model.Reserve;
import com.keelim.practice10.model.Store;
import com.keelim.practice10.model.adapter.BasketRecyclerAdapter;
import com.keelim.practice10.utils.JSONTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class BasketActivity extends AppCompatActivity {
    BasketRecyclerAdapter bAdapter;
    DecimalFormat decimalFormat;
    TextView priceTextView, totalClothesCnt;

    DatePickerDialog dpd;
    TimePickerDialog tpd;
    Context context;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        this.context = this;
        // 예약 목록 레이아웃 설정
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_basket_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 290, this.getResources().getDisplayMetrics());
        recyclerView.setMinimumHeight(displayMetrics.heightPixels - px);
        recyclerView.setLayoutManager(layoutManager);

        //전체 옷 개수 설정
        totalClothesCnt = findViewById(R.id.reserve_clothes_total_cnt);
        setTotalClothesCnt();

        // 전체 가격 설정
        bAdapter = new BasketRecyclerAdapter(this);
        recyclerView.setAdapter(bAdapter);

        priceTextView = findViewById(R.id.basket_reserve_clothes_total_price);
        decimalFormat = new DecimalFormat("###,###,###,###");

        // 총 대여 가격 설정
        setTotalCost();

        // 날짜 및 시간 설정
        setDateCalendar();
        setTimePicker();

        // 대여하기 버튼 설정
        LinearLayout reserveBtn = (LinearLayout) findViewById(R.id.basket_reservation);
        reserveBtn.setOnClickListener(v -> {
            if (Basket.getInstance().getClothesCnt() == 0) {
                if (Locale.getDefault().getLanguage() == "ko")
                    Toast.makeText(v.getContext(), "담은 한복이 없습니다", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(), "There is no hanbok in Basket", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<BasketItem> clothes = Basket.getInstance().getBasket();
                int mTop = 0, mBottom = 0, wTop = 0, wBottom = 0;
                for (BasketItem item : clothes) {
                    Clothes tmp = item.getClothes();
                    if (Constant.CATEGORY[tmp.getCategory()] == "상의") {
                        if (tmp.getSex() == Constant.MAN)
                            mTop++;
                        else if (tmp.getSex() == Constant.WOMAN)
                            wTop++;
                    } else if (Constant.CATEGORY[tmp.getCategory()] == "하의") {
                        if (tmp.getSex() == Constant.MAN)
                            mBottom++;
                        else if (tmp.getSex() == Constant.WOMAN)
                            wBottom++;
                    }
                }

                if (mTop == mBottom && wTop == wBottom)
                    dpd.show(getSupportFragmentManager(), "Datepickerdialog");
                else
                    showAlert();
            }
        });
    }

    public void setTotalClothesCnt() {
        totalClothesCnt.setText("" + Basket.getInstance().getTotalClothesCnt());
    }

    public void setTotalCost() {
        int price = Basket.getInstance().getTotalPrice();
        String str;
        if (Locale.getDefault().getLanguage() == "ko")
            str = decimalFormat.format(price) + " 원";
        else
            str = decimalFormat.format(price) + " won";
        priceTextView.setText(str);
    }

    private void setDateCalendar() {
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                null,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);

        String titleMsg, nextBtnMsg, cancelBtnMsg;
        if (Locale.getDefault().getLanguage() == "ko") {
            titleMsg = "대여 날짜";
            nextBtnMsg = "다음";
            cancelBtnMsg = "취소";
        } else {
            titleMsg = "Rental Date";
            nextBtnMsg = "Next";
            cancelBtnMsg = "Cancel";
        }

        dpd.setTitle(titleMsg);
        dpd.setOkText(nextBtnMsg);
        dpd.setCancelText(cancelBtnMsg);
        dpd.setMinDate(now);
        dpd.setOnDateSetListener((view, year, monthOfYear, dayOfMonth) -> {
            date = "" + year + "-" + String.format("%02d", monthOfYear + 1) + "-" + String.format("%02d", dayOfMonth);
            tpd.show(getSupportFragmentManager(), "TimePickerdialog");
        });
    }

    private void setTimePicker() {
        Calendar now = Calendar.getInstance();
        tpd = TimePickerDialog.newInstance(null,
                Calendar.HOUR_OF_DAY,
                Calendar.MINUTE,
                false
        );
        String titleMsg, nextBtnMsg, cancelBtnMsg;
        if (Locale.getDefault().getLanguage() == "ko") {
            titleMsg = "예약 시간";
            nextBtnMsg = "예약 완료";
            cancelBtnMsg = "취소";
        } else {
            titleMsg = "Rental Time";
            nextBtnMsg = "Finish";
            cancelBtnMsg = "Cancel";
        }

        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setTitle(titleMsg);
        tpd.setOkText(nextBtnMsg);
        tpd.setCancelText(cancelBtnMsg);
        tpd.setTimeInterval(1, 5);
        tpd.setOnTimeSetListener((view, hourOfDay, minute, second) -> {
            date += " " + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second);
            reservation();
        });
        tpd.dismissOnPause(true);
    }

    private void reservation() {
        Basket basket = Basket.getInstance();
        Account account = Account.getInstance();
        String adminID = JSONTask.getInstance().changeToAdminID(basket.getSelectedStoreID());
        Store store = JSONTask.getInstance().getAdminStoreAll(adminID).get(0);

        //Log.e("" + adminID, " " + basket.getSelectedStoreID());

        Reserve reserve = new Reserve(1, account.getId(),
                adminID, 0, date);
        JSONTask.getInstance().insertReserve(reserve, basket.getBasket());
        basket.clearBasket();

        String toastMsg = "";
        if (Locale.getDefault().getLanguage() == "ko")
            toastMsg = "대여 신청 완료";
        else
            toastMsg = "Complete the Rental Reservation";

        Toast toast = Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT);
        JSONTask.getInstance().sendMsgByFCM(adminID, store.getName(), "주문이 접수되었습니다.");
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        finish();
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String alertMsg = "";
        String closeBtnMsg = "";
        if (Locale.getDefault().getLanguage() == "ko") {
            alertMsg = "한복은 갖춰 입었을때 빛이 나는 법입니다.\n장바구니를 다시한번 확인해 주세요.";
            closeBtnMsg = "닫기";
        } else {
            alertMsg = "Please check the reservation list again.";
            closeBtnMsg = "Close";
        }

        builder.setMessage(alertMsg);
        builder.setPositiveButton(closeBtnMsg,
                (dialog, which) -> {

                });
        builder.show();
    }
}
