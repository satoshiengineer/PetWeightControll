package com.example.msi.petweightcontroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class display extends AppCompatActivity {
    TextView nameTx,WeightTx,BMITx,mealTx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        nameTx = (TextView)findViewById(R.id.textView11);
        mealTx = (TextView) findViewById(R.id.textView10);
        WeightTx = (TextView) findViewById(R.id.textView5);
        BMITx = (TextView) findViewById(R.id.textView6);
        Bundle getBundle = getIntent().getExtras();
        String name = getBundle.getString("name");
        String meal = getBundle.getString("meal");
        float wei = getBundle.getFloat("weight");
        float BMI = getBundle.getFloat("BMI");
        nameTx.setText(name);
        mealTx.setText(meal);
        BMITx.setText(String.valueOf(BMI));
        WeightTx.setText(String.valueOf(wei));
    }
    public void nextToCurvepic(View v)
    {
        Intent intent = new Intent();
        intent.setClass(display.this,displaycurve.class);
        startActivity(intent);
    }

}
