package com.example.msi.petweightcontroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Petfile extends AppCompatActivity{
    EditText name,age,height,weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petfile);
        name = (EditText) findViewById(R.id.setname);
        age = (EditText) findViewById(R.id.setage);
        height = (EditText) findViewById(R.id.setHeight);
        weight = (EditText) findViewById(R.id.setWeight);
    }
    public void previous(View v)
    {
        Intent intent = new Intent();
        intent.setClass(Petfile.this, SetBluetooth.class);
        startActivity(intent);
    }
    public void nextToTime(View v)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        float wei = Float.parseFloat(weight.getText().toString());
        float hei = Float.parseFloat(height.getText().toString());
        float BMI;
        hei = hei/100;
        hei = hei*hei;
        BMI = wei/hei;
        bundle.putString("name", name.getText().toString());
        bundle.putFloat("weight", wei);
        bundle.putFloat("BMI",BMI);
        intent.setClass(Petfile.this, SetNotificationTime.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
