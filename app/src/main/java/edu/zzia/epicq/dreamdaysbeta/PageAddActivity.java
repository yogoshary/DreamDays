package edu.zzia.epicq.dreamdaysbeta;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by EpicQ on 2016/5/9 0009.
 */
public class PageAddActivity extends Activity implements View.OnClickListener {
    //分类初始化
    private static final String[] m = {"纪念日", "生日", "节日", "学校", "生活", "其他"};
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    private Button btnDate, btnTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将布局xml文件引入到activity当中
        setContentView(R.layout.page_add);

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
        }, 3000);

        /**
         * 选择分类
         */

        spinner = (Spinner) findViewById(R.id.spinnerCategory);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);


        /**
         * 完成Button跳转实验
         */
        Button btComplete=(Button)findViewById(R.id.btComplete);
        btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageAddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



        btnDate = (Button) findViewById(R.id.btnDatePickerDialog);
        btnTime = (Button) findViewById(R.id.btnTimePickerDialog);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);



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
                        Toast.makeText(PageAddActivity.this, year + "year " + (monthOfYear + 1) + "month " + dayOfMonth + "day", Toast.LENGTH_SHORT).show();
                        btnDate.setText( year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, 2013, 7, 20);
                datePicker.show();
                break;

            case R.id.btnTimePickerDialog:
                TimePickerDialog time = new TimePickerDialog(PageAddActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        Toast.makeText(PageAddActivity.this, hourOfDay + "hour " + minute + "minute", Toast.LENGTH_SHORT).show();
                        btnTime.setText(hourOfDay + "：" + minute);
                    }
                }, 18, 25, true);
                time.show();
                break;
        }

    }


}