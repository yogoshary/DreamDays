package edu.zzia.epicq.dreamdaysbeta;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by EpicQ on 2016/5/9 0009.
 */
public class PageAddActivity extends Activity implements View.OnClickListener {

    //事件标题初始化
    EditText editTextHeadline;

    //分类初始化
    private static final String[] categoryDegree = {"纪念日", "生日", "节日", "学校", "生活", "其他"};
    private Spinner spinnerCategory;
    private ArrayAdapter<String> adapter;

    //设置日期和时间初始化
    private Button btnDate, btnTime;

    //重复初始化
    Button btRepetition;
    private String[] repetitionDegree = new String[]{"无", "每周", "每两周", "每月", "每季度", "每半年", "每年"};
    private String repetitionChosed;


    //置顶初始化
    Switch switchSetTop;

    //通知初始化
    Switch switchNotification;

    //背景设置按钮初始化
    ImageView imageViewBackdrop;

    //添加语音初始化
    Button btRecord;

    //完成按钮初始化
    Button btComplete;

    //SharedPreferences初始化
    SharedPreferences preferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将布局xml文件引入到activity当中
        setContentView(R.layout.page_add);

        //实例化事件标题的EditText
        editTextHeadline = (EditText) findViewById(R.id.editTextHeadline);


        /**
         * 用一个定时器控制当打开这个Activity的时候就出现软键盘
         */

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 2000);

        /**
         * 选择分类
         */

        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryDegree);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinnerCategory.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinnerCategory.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinnerCategory.setVisibility(View.VISIBLE);


        /**
         * 设置日期和时间按钮 实例化
         * 监听事件
         */

        btnDate = (Button) findViewById(R.id.btnDatePickerDialog);
        btnTime = (Button) findViewById(R.id.btnTimePickerDialog);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);

        /**
         * 重复按钮 实例化
         * 监听事件
         */

        btRepetition = (Button) findViewById(R.id.btRepetition);
        btRepetition.setOnClickListener(this);


        /**
         * 置顶开关 实例化
         * 监听事件
         */

        switchSetTop = (Switch) findViewById(R.id.switchSetTop);
        switchSetTop.setOnClickListener(this);


        /**
         * 通知开关 实例化
         * 监听事件
         */

        switchNotification = (Switch) findViewById(R.id.switchNotification);
        switchNotification.setOnClickListener(this);

        /**
         * 背景图片按钮 实例化
         * 监听事件
         */

        imageViewBackdrop = (ImageView) findViewById(R.id.imageViewBackdrop);
        imageViewBackdrop.setOnClickListener(this);

        /**
         * 语音按钮 实例化
         * 监听事件
         */

        btRecord = (Button) findViewById(R.id.btRecord);
        btRecord.setOnClickListener(this);

        /**
         * 完成按钮 实例化
         * 监听事件
         */

        btComplete = (Button) findViewById(R.id.btComplete);
        btComplete.setOnClickListener(this);

        /**
         * 获取只能被本应用程序读写的SharedPreferences对象
         */

        preferences=getSharedPreferences("daysInfo",MODE_PRIVATE);
        editor=preferences.edit();


    }


    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDatePickerDialog:
                DatePickerDialog datePicker = new DatePickerDialog(PageAddActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(PageAddActivity.this, year + "year " + (monthOfYear + 1) + "month " + dayOfMonth + "day", Toast.LENGTH_SHORT).show();
                        btnDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, 2016, 5, 10);
                datePicker.show();
                break;

            case R.id.btnTimePickerDialog:
                TimePickerDialog time = new TimePickerDialog(PageAddActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(PageAddActivity.this, hourOfDay + "hour " + minute + "minute", Toast.LENGTH_SHORT).show();
                        btnTime.setText(hourOfDay + "：" + minute);
                    }
                }, 12, 25, true);
                time.show();
                break;

            case R.id.btRepetition:
                Dialog alertDialog = new AlertDialog.Builder(this).
                        //设置对话框
                                setTitle("重复设置").
                                setSingleChoiceItems(repetitionDegree, 0, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        repetitionChosed = repetitionDegree[which];
                                    }
                                }).
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        btRepetition.setText(repetitionChosed);
                                    }
                                }).
                                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).create();
                alertDialog.show();
                break;

            case R.id.switchSetTop:
                //if(switchSetTop.isChecked()==true){Toast.makeText(PageAddActivity.this, "开启了switch", Toast.LENGTH_SHORT).show();}

                break;

            case R.id.switchNotification:
                break;

            case R.id.imageViewBackdrop:
                break;

            case R.id.btRecord:
                break;

            case R.id.btComplete:
                //获取标题
                String Headline = ((EditText) findViewById(R.id.editTextHeadline)).getText().toString();
                editor.putString("Headline",Headline);




                //获取日期和时间
                String Date = ((Button) findViewById(R.id.btnDatePickerDialog)).getText().toString();
                editor.putString("Date",Date);
                String Time = ((Button) findViewById(R.id.btnTimePickerDialog)).getText().toString();
                editor.putString("Time",Time);
                //获取重复次数
                String Repetition = ((Button) findViewById(R.id.btRepetition)).getText().toString();
                editor.putString("Repetition",Repetition);
                //获取置顶Boolean
                Boolean Top=switchSetTop.isChecked();
                //获取通知Boolean
                Boolean Notification=switchNotification.isChecked();


                editor.commit();





                Toast.makeText(PageAddActivity.this,Headline+" "+Date + " " + Time + " " + Repetition+" "+Top+" "+Notification, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PageAddActivity.this, MainActivity.class);
                startActivity(intent);
                break;

        }

    }


}