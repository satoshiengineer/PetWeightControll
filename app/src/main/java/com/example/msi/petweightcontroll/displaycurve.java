package com.example.msi.petweightcontroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class displaycurve extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaycurve);
    }
    public void previousToDisplay(View v)
    {
        Intent intent = new Intent();
        intent.setClass(displaycurve.this,display.class);
        startActivity(intent);
    }
}
