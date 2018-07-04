package com.example.administrator.todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    Calendar c = Calendar.getInstance();
    TextView txDate,tv_title,tv_time,tv_list,tv_color;
    Button btn_remind,btn_ok,btn_can;
    Bundle bundle;
    DbAdapter dba;
    int index;
    String new_time,new_list,new_color,new_remind;
    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        View();
        txDate = (TextView) findViewById(R.id.timepick);
        txDate.setOnClickListener(this);
        bundle = this.getIntent().getExtras();
        dba = new DbAdapter(this);

        if(bundle.getString("type").equals("edit")){
            tv_title.setText("修改便利貼");
            index = bundle.getInt("item_id");
            Cursor cursor = dba.queryById(index);
            tv_time.setText(cursor.getString(1));
            tv_list.setText(cursor.getString(2));
            tv_color.setText(cursor.getString(3));
            btn_remind.setText(cursor.getString(4));
        }
    }

    private void View(){
        tv_title = findViewById(R.id.text);
        tv_time = findViewById(R.id.timepick);
        tv_list = findViewById(R.id.list);
//        tv_color = findViewById(R.id.spinner);
        btn_remind = findViewById(R.id.remind);
        btn_ok = findViewById(R.id.ok);
        btn_can = findViewById(R.id.cancel);

        tv_time.setOnClickListener(this);
        tv_list.setOnClickListener(this);
        btn_remind.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_can.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.timepick:
                if(bundle.getString("type").equals("add")) tv_time.setText("");
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //將選定日期設定至edt_birth
                        tv_time.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, mYear,mMonth,mDay);
                datePickerDialog.show();
                break;
            case R.id.list:
                if(bundle.getString("type").equals("add"))  tv_list.setText("");
                break;
//            case R.id.spinner:
//                if(bData.getString("type").equals("add"))  tv_time.setText("");
//                break;
//            case R.id.remind:
//                if(bundle.getString("type").equals("add"))  tv_time.setText("");
//                break;
            case R.id.ok:
                new_time = tv_time.getText().toString();
                new_list = tv_list.getText().toString();

//                dba = new DbAdapter(EditActivity.this);
                if(bundle.getString("type").equals("add")){
                    try{
                        dba.updateContacts(index, new_time, new_list,new_color,new_remind);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        //回到列表
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    }
                }else{
                    try{
                        dba.createContact( new_time, new_list,new_color,new_remind);
                    }catch(Exception e){
                        e.printStackTrace();
                    }finally {
                        Intent i = new Intent(this, ShowActivity.class);
                        startActivity(i);
                    }
                }
                break;
        }

    }
}
