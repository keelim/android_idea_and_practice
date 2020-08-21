package com.keelim.practice10.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.keelim.practice10.R;
import com.keelim.practice10.model.Account;
import com.keelim.practice10.utils.JSONTask;

public class MyPageFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private String password, name, phone;
    private EditText passwordET, nameET, phoneET;

    private Account account;

    public MyPageFragment() {
    }

    @NonNull
    public static MyPageFragment newInstance(int sectionNumber) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);

        passwordET = (EditText) rootView.findViewById(R.id.myPage_password);
        nameET = (EditText) rootView.findViewById(R.id.myPage_name);
        phoneET = (EditText) rootView.findViewById(R.id.myPage_phone);

        account = Account.getInstance();

        TextView idTextView = (TextView) rootView.findViewById(R.id.myPage_id);
        idTextView.setText(account.getId());

        password = account.getPw();
        name = account.getName();
        phone = account.getPhone();

        nameET.setText(name);
        phoneET.setText(phone);

        CardView editBtn = (CardView) rootView.findViewById(R.id.myPage_edit_btn);
        editBtn.setOnClickListener(v -> showAlert(getContext()));

        return rootView;
    }

    private void showAlert(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("정보를 수정 하시겠습니까?");
        builder.setPositiveButton("예",
                (dialog, which) -> {
                    String tmp = passwordET.getText().toString();
                    if (tmp.length() > 0)
                        password = tmp;
                    name = nameET.getText().toString();
                    phone = phoneET.getText().toString();

                    account.setName(name);
                    account.setPhone(phone);
                    account.setPw(password);

                    // 서버에 계정 정보 갱신
                    JSONTask.getInstance().updateAccount(account);

                    Toast.makeText(context, "수정 완료", Toast.LENGTH_SHORT).show();
                    refresh();
                });
        builder.setNegativeButton("아니요",
                (dialog, which) -> {

                });
        builder.show();
    }

    private void refresh() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        passwordET.setText("");
    }
}
