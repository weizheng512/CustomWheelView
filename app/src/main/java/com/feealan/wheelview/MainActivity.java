package com.feealan.wheelview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.feealan.wheelview.widget.CityPickerDialog;
import com.feealan.wheelview.widget.MoneyPickerDialog;
import com.feealan.wheelview.widget.SexPickerDialog;
import com.feealan.wheelview.widget.TimePickerDialog;
import com.feealan.wheelview.widget.TypePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button            btn_money_picker;
    private Button            btn_sex_picker;
    private Button            btn_time_picker;
    private MoneyPickerDialog money_dialog;
    private SexPickerDialog   sex_dialog;
    private TimePickerDialog  time_Dialog;
    private TypePickerDialog  typePickerDialog;
    private CityPickerDialog cityPickerDialog;
    private List<String> city_list = new ArrayList<>();
    private Button btn_aihao_picker;
    private Button btn_city_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_money_picker = (Button) findViewById(R.id.btn_money_picker);

        btn_money_picker.setOnClickListener(this);

        btn_sex_picker = (Button) findViewById(R.id.btn_sex_picker);
        btn_time_picker = (Button) findViewById(R.id.btn_time_picker);
        btn_sex_picker.setOnClickListener(this);
        btn_time_picker.setOnClickListener(this);


        money_dialog = new MoneyPickerDialog(this);
        money_dialog.setCallback(new MoneyPickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {
                money_dialog.dismiss();
            }

            @Override
            public void onSure(String data) {
                Log.d("aa", data);
                Toast.makeText(MainActivity.this,data,Toast.LENGTH_SHORT).show();
                money_dialog.dismiss();
            }
        });
        sex_dialog = new SexPickerDialog(this);
        sex_dialog.setCallback(new SexPickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {
                sex_dialog.dismiss();
            }

            @Override
            public void onSure(String data) {
                Log.d("aa", data);
                Toast.makeText(MainActivity.this,data,Toast.LENGTH_SHORT).show();

                sex_dialog.dismiss();
            }
        });

        time_Dialog = new TimePickerDialog(this);
        time_Dialog.setCallback(new TimePickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {
                time_Dialog.dismiss();
            }

            @Override
            public void onSure(int year, int month, int day, int hour, int minter, long time) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String           date       = dateFormat.format(time);
                Log.d("aa", date + ">>");
                Toast.makeText(MainActivity.this,date,Toast.LENGTH_SHORT).show();
                time_Dialog.dismiss();
            }
        });
        btn_aihao_picker = (Button) findViewById(R.id.btn_aihao_picker);
        btn_aihao_picker.setOnClickListener(this);
        typePickerDialog = new TypePickerDialog(this);
        typePickerDialog.setCallback(new TypePickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onSure(int f, int s, String fristType, String sencondType) {
                Toast.makeText(MainActivity.this,fristType+"--"+sencondType,Toast.LENGTH_SHORT).show();
                Log.d("aa", f + ">>" + s + ">>" + fristType + ">>" + sencondType);
            }
        });
        btn_city_picker = (Button) findViewById(R.id.btn_city_picker);
        btn_city_picker.setOnClickListener(this);
        cityPickerDialog = new CityPickerDialog(this);
        cityPickerDialog.setCallback(new CityPickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onSure(String province, String city, String county, String data) {
                Toast.makeText(MainActivity.this,province+"--"+city+"--"+county,Toast.LENGTH_SHORT).show();
                Log.d("AA", ">>" + province + ">>" + city + ">>" + county + ">>" + data);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_money_picker:
                money_dialog.show();
                break;
            case R.id.btn_sex_picker:
                sex_dialog.show();
                break;
            case R.id.btn_time_picker:
                time_Dialog.show();
                break;
            case R.id.btn_aihao_picker:
                typePickerDialog.show();
                break;
            case R.id.btn_city_picker:
                cityPickerDialog.show();
                break;


        }
    }
}
