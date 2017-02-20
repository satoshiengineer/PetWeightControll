package com.example.msi.petweightcontroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onclick(View v)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SetBluetooth.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
}
