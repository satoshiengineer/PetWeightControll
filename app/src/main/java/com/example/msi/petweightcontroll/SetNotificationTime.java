package com.example.msi.petweightcontroll;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetNotificationTime extends AppCompatActivity {
    private int mHour;
    private int mMinute;
    private int mmHour;
    private int mmMinute;
    private int mmmHour;
    private int mmmMinute;
    AlarmManager am;
    PendingIntent pi;
    PendingIntent pi2;
    PendingIntent pi3;
    Calendar mon,after,even;
    TextView morning,afternoon,evening;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification_time);
        morning =(TextView) findViewById(R.id.morning);
        afternoon = (TextView) findViewById(R.id.afternoon);
        evening = (TextView) findViewById(R.id.evening);
        mon = Calendar.getInstance();
        mon.setTimeInMillis(System.currentTimeMillis());
        mHour = mon.get(Calendar.HOUR_OF_DAY);
        mMinute = mon.get(Calendar.MINUTE);
        after = Calendar.getInstance();
        after.setTimeInMillis(System.currentTimeMillis());
        mmHour = after.get(Calendar.HOUR_OF_DAY);
        mmMinute = after.get(Calendar.MINUTE);
        even = Calendar.getInstance();
        even.setTimeInMillis(System.currentTimeMillis());
        mmmHour = even.get(Calendar.HOUR_OF_DAY);
        mmMinute = even.get(Calendar.MINUTE);
    }
    public void SetMorning(View v) {
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        morning.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        tpd.show();
    }
    public void SetAfternoon(View v) {
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        mmHour = hourOfDay;
                        mmMinute = minute;
                        afternoon.setText(hourOfDay + ":" + minute);
                    }
                }, mmHour, mmMinute, false);
        tpd.show();
    }
    public void SetEvening(View v) {
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        mmmHour = hourOfDay;
                        mmmMinute = minute;
                        evening.setText(hourOfDay + ":" + minute);
                    }
                }, mmmHour, mmmMinute, false);
        tpd.show();
    }
    public void previousPetfile(View v)
    {
        Intent intent = new Intent();
        intent.setClass(SetNotificationTime.this, Petfile.class);
        startActivity(intent);
    }
    public void previousSetBluetooth(View v)
    {
        Intent intent = new Intent();
        intent.setClass(SetNotificationTime.this, SetBluetooth.class);
        startActivity(intent);
    }
    public void finish(View v)
    {
        //上午
        mon.set(Calendar.HOUR_OF_DAY, mHour);
        mon.set(Calendar.MINUTE, mMinute);
        Intent intent2 = new Intent(this, AlarmReceive.class);
        am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        pi = PendingIntent.getBroadcast(this, 1, intent2, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, mon.getTimeInMillis(), pi);
        //下午
        after.set(Calendar.HOUR_OF_DAY, mmHour);
        after.set(Calendar.MINUTE, mmMinute);
        pi2 = PendingIntent.getBroadcast(this,2,intent2, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP,after.getTimeInMillis(),pi2);
        //晚上
        even.set(Calendar.HOUR_OF_DAY,mmmHour);
        even.set(Calendar.MINUTE,mmmHour);
        pi3 = PendingIntent.getBroadcast(this,3,intent2, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP,even.getTimeInMillis(),pi3);
        //傳遞資料
        Bundle getbundle = getIntent().getExtras();
        String name = getbundle.getString("name");
        String meal = getbundle.getString("meal");
        float wei = getbundle.getFloat("weight");
        float BMI = getbundle.getFloat("BMI");
        Intent intent = new Intent();
        Bundle setbundle = new Bundle();
        setbundle.putString("meal",meal);
        setbundle.putString("name",name);
        setbundle.putFloat("weight", wei);
        setbundle.putFloat("BMI", BMI);
        intent.putExtras(setbundle);
        intent.setClass(SetNotificationTime.this, display.class);
        startActivity(intent);
    }
}
