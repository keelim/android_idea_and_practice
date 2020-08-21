package com.keelim.practice10.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.keelim.practice10.R;
import com.keelim.practice10.model.Constant;
import com.keelim.practice10.model.ServerImg;
import com.keelim.practice10.model.Store;
import com.keelim.practice10.utils.Camera;
import com.keelim.practice10.utils.JSONTask;
import com.keelim.practice10.view.AdminActivity;
import com.keelim.practice10.view.LoginActivity;
import com.keelim.practice10.view.OpenSourceInfoActivity;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint("ValidFragment")
public class StoreManagementFragment extends Fragment {
    @Nullable
    private Uri photoURI, resultUri;
    private ImageView imageViewStore;

    private Store store;
    private EditText edit_name, edit_tel, edit_intro, edit_info, edit_address;
    private TextView edit_admin_id, editFrom, editTo;

    private TimePickerDialog tpd;
    private String time;

    private ArrayList<EditText> editTextArrayList;

    private InputMethodManager imm; //전역변수

    private FloatingActionButton btnEdit;
    private LinearLayout linearLayout;

    private boolean uploadFlag = false;

    private Camera camera;
    public StoreManagementFragment(Store store) {
        this.store = store;
        this.camera = new Camera();
    }


    private static final String ARG_SECTION_NUMBER = "section_number";
    @NonNull
    public static StoreManagementFragment newInstance(int sectionNumber, Store store) {
        StoreManagementFragment fragment = new StoreManagementFragment(store);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString("test", "storefragment");
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_store, container, false);

        // 가로모드 고정
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        LinearLayout logout = (LinearLayout)rootView.findViewById(R.id.footer_logout);
        logout.setOnClickListener(v -> {
            LoginActivity.setLogOut();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        LinearLayout openSourceInfo = (LinearLayout)rootView.findViewById(R.id.footer_opensource);
        openSourceInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OpenSourceInfoActivity.class);
            startActivity(intent);
        });
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); //onCreate 이후,,

        editTextArrayList = new ArrayList<EditText>();

        imageViewStore = (ImageView) rootView.findViewById(R.id.imageView_store);
        edit_name = (EditText)rootView.findViewById(R.id.edit_name);
        edit_admin_id = (TextView)rootView.findViewById(R.id.edit_admin_id);
        edit_tel = (EditText)rootView.findViewById(R.id.edit_tel);
        edit_intro = (EditText)rootView.findViewById(R.id.edit_intro);
        edit_info = (EditText)rootView.findViewById(R.id.edit_info);
        edit_address = (EditText)rootView.findViewById(R.id.edit_address);

        editFrom = (TextView) rootView.findViewById(R.id.edit_from);
        editFrom.setOnClickListener(v -> {
            setTimePicker(0);
            tpd.show(getActivity().getSupportFragmentManager(), "TimePickerdialog");

        });
        editTo = (TextView) rootView.findViewById(R.id.edit_to);
        editTo.setOnClickListener(v -> {
            setTimePicker(1);
            tpd.show(getActivity().getSupportFragmentManager(), "TimePickerdialog");
        });


        final RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.rg);
        if(store.getSector()!=1 && store.getSector()!=2)
            store.setSector(1);
        final RadioButton rb1 = (RadioButton) rootView.findViewById(R.id.rb1);
        final RadioButton rb2 = (RadioButton) rootView.findViewById(R.id.rb2);
        // 저장된 정보에 따른 디폴드값 설정
        if(store.getSector()==1)
            rg.check(R.id.rb1);
        else
            rg.check(R.id.rb2);

        btnEdit = (FloatingActionButton) rootView.findViewById(R.id.fab1);
        btnEdit.setOnClickListener(v -> {
            if(rb1.isChecked())
                store.setSector(1);
            else if(rb2.isChecked())
                store.setSector(2);
            showAlert(getActivity(), store);
        });

        setEditText(store);

        editTextArrayList.add(edit_name);
        editTextArrayList.add(edit_tel);
        editTextArrayList.add(edit_intro);
        editTextArrayList.add(edit_info);
        editTextArrayList.add(edit_address);

        for (final EditText item:editTextArrayList) {
            item.setOnClickListener(v -> {
                item.setFocusableInTouchMode(true);
                item.setFocusable(true);
                showKeyboard(item);
            });
        }
        ServerImg.getStoreImageGlide(getContext(), store.getId(), imageViewStore);

        imageViewStore.setOnClickListener(v -> {
            DialogInterface.OnClickListener cameraListener = (dialog, which) -> camera.captureCamera(getActivity());
            DialogInterface.OnClickListener albumListener = (dialog, which) -> camera.getAlbum(getActivity());
            DialogInterface.OnClickListener cancelListener = (dialog, which) -> dialog.dismiss();

            new AlertDialog.Builder(getActivity())
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        });

        camera.checkPermission(getActivity());
        return rootView;
    }

    private void setTimePicker(final int selection)
    {
        tpd = TimePickerDialog.newInstance(null,
                Calendar.HOUR_OF_DAY,
                Calendar.MINUTE,
                false
        );
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setTitle("영업 시작시간");
        tpd.setOkText("변경");
        tpd.setCancelText("취소");
        if(selection==0)
            tpd.setInitialSelection(Integer.parseInt(store.getStartTime().substring(0,2)), Integer.parseInt(store.getStartTime().substring(3,5)));
        else if(selection==1)
            tpd.setInitialSelection(Integer.parseInt(store.getEndTime().substring(0,2)), Integer.parseInt(store.getEndTime().substring(3,5)));
        tpd.setTimeInterval(1, 5);
        tpd.setOnTimeSetListener((view, hourOfDay, minute, second) -> {
            time = ""+String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second);
            if(selection==0)
                editFrom.setText(time);
            else if(selection == 1)
                editTo.setText(time);
        });
        tpd.dismissOnPause(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case Constant.REQUEST_TAKE_PHOTO:
                if(resultCode == Activity.RESULT_OK){
                    try {
                        //Log.i("REQUEST_TAKE_PHOTO", "OK");
                        // 갤러리에 추가만 시킴
                        camera.galleryAddPic(getActivity());
                    } catch (Exception e){
                        //Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                } else {
                    Toast.makeText(getActivity(), "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case Constant.REQUEST_TAKE_ALBUM:
                if(resultCode == Activity.RESULT_OK){
                    if(data.getData() != null){
                        try {
                            photoURI = data.getData();
                            InputStream i = getActivity().getContentResolver().openInputStream(photoURI);

                            Bitmap bitmap = camera.resize(getContext(), photoURI, 200);

                            camera.createImageFileByBitmap(bitmap);
                            camera.copyFile(getContext(), i, camera.getmCurrentPhotoPath());
                            File f = new File(camera.getmCurrentPhotoPath());
                            resultUri = Uri.fromFile(f);
/*
                            CropImage.activity(resultUri)
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .setCropMenuCropButtonTitle("자르기")
                                    //.setActivityTitle("이미지 업로드")
                                    .setOutputUri(resultUri)
                                    .start(getActivity());
*/
                        } catch (Exception e) {
                            //Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;
/*
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result= CropImage.getActivityResult(data);
                if(resultCode == Activity.RESULT_OK) {
                    imageViewStore.setImageURI(resultUri);
                    uploadFlag = true;
                } else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                }
                break;
*/
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case Constant.MY_PERMISSION_CAMERA:
                for(int i = 0; i< grantResults.length; i++){
                    // grantResult[]: 허용된 권한은 0, 거부한 권한은 -1
                    if(grantResults[i] < 0){
                        Toast.makeText(getActivity(), "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면
                break;
        }
    }

    private void showKeyboard(EditText editText) {
        imm.showSoftInput(editText, 0);
    }

    private void hideKeyboard(@NonNull EditText editText) {
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void showAlert(Context context, @NonNull final Store store){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("매장정보를 수정하시겠습니까?");
        builder.setPositiveButton("예",
                (dialog, which) -> {
                    updateStoreByText(store);
                    JSONTask.getInstance().updateStore(store, store.getAdmin_id());
                    //setEditText(store);

                    if(uploadFlag)
                        ServerImg.uploadFileOnPath(camera.getmCurrentPhotoPath(), String.valueOf(store.getId()), String.valueOf(-1), getContext());

                    Toast.makeText(getContext(), "변경되었습니다.", Toast.LENGTH_SHORT).show();
                    for (EditText item:editTextArrayList) {
                        item.setFocusableInTouchMode(false);
                        item.setFocusable(false);
                        hideKeyboard(item);
                    }
                    refresh();
                });
        builder.setNegativeButton("아니오",
                (dialog, which) -> {
                    // 이전 데이터로 다시 셋
                    setEditText(store);
                    Toast.makeText(getActivity(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                });
        builder.show();
    }

    private void setEditText(@NonNull Store store){
        edit_admin_id.setText(store.getAdmin_id());
        edit_tel.setText(store.getTel());
        edit_name.setText(store.getName());
        edit_intro.setText(store.getIntro());
        edit_info.setText(store.getInform());
        edit_address.setText(store.getAddress());
        editFrom.setText(store.getStartTime());
        editTo.setText(store.getEndTime());
    }

    private void updateStoreByText(@NonNull Store store){
        store.setName(edit_name.getText().toString());
        store.setTel(edit_tel.getText().toString());
        store.setIntro(edit_intro.getText().toString());
        store.setInform(edit_info.getText().toString());
        store.setAddress(edit_address.getText().toString());
        store.setStartTime(editFrom.getText().toString());
        store.setEndTime(editTo.getText().toString());

        Point point = getPointFromGeoCoder(edit_address.getText().toString());
        store.setLatitude(point.latitude);
        store.setLongitude(point.longitude);

//        NaverTranslate test = new NaverTranslate();
//        store.setTransAddress(test.translatedResult(store.getAddress()));
//        store.setTransIntro(test.translatedResult(store.getIntro()));
        // 툴바 이름변경
        ((AdminActivity)getActivity()).setToolbarTitle(store.getName());
    }

    public void refresh(){
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    public void translateLan(@NonNull Store store, EditText ed) {
        String temp = store.getIntro();
//        NaverTranslate test = new NaverTranslate(ed);
//        test.execute(temp);
    }

    private class Point {
        public double latitude;
        public double longitude;
        public String addr;
        // 포인트를 받았는지 여부
        public boolean havePoint;
    }

    @NonNull
    private Point getPointFromGeoCoder(String addr) {
        Point point = new Point();
        point.addr = addr;

        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> listAddress;
        try {
            listAddress = geocoder.getFromLocationName(addr, 1);
        } catch (IOException e) {
            e.printStackTrace();
            point.havePoint = false;
            return point;
        }

        if (listAddress.isEmpty()) {
            point.havePoint = false;
            return point;
        }

        point.havePoint = true;
        point.longitude = listAddress.get(0).getLongitude();
        point.latitude = listAddress.get(0).getLatitude();
        return point;
    }
}