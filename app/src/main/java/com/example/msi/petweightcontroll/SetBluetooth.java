package com.example.msi.petweightcontroll;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

public class SetBluetooth extends AppCompatActivity{
    BluetoothAdapter mBluetoothAdapter = null;
    BluetoothSocket mmSocket = null;
    BluetoothDevice mmDevice;
    Handler bluetoothIn;
    ConnectedThread mConnectedThread;
    final int handlerState = 0;
    private StringBuilder recDataString = new StringBuilder();
    TextView myLabel;
    TextView bluetoothValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bluetooth);
        ImageButton Bluetooth = (ImageButton) findViewById(R.id.dog);
        myLabel = (TextView)findViewById(R.id.myLabel);
        bluetoothValue = (TextView)findViewById(R.id.blutoothvalue);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothIn = new Handler() {
            public void handleMessage(final android.os.Message msg) {
                if (msg.what == handlerState) {
                    //if message is what we want
                    String readMessage = (String) msg.obj;// msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);//keep appending to string until ~// make sure there data before ~
                    bluetoothValue.setText("食物重量 = " + recDataString+ "g");
                }
            }
        };
    }
    public void Click(View v)throws IOException
    {
        openBT();
        findBT();
        connectBT();
    }
    void openBT() {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 0);
            Toast.makeText(getApplicationContext(), "開啟藍芽", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "已開啟", Toast.LENGTH_LONG).show();
        }
    }
    void findBT() throws IOException {
        if (mBluetoothAdapter == null) {
            myLabel.setText("No bluetooth adapter available");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-05")) //尋找HC-05
                {
                    mmDevice = device;
                }
            }
        }
        myLabel.setText(mmDevice.getName() + "已找到");
    }
    void connectBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        try {
            if (mmDevice != null) {
                mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            }
        } catch (Exception e) {
            Log.e("", "error creating socket");
        }
        try {
            mmSocket.connect();
            mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();
        } catch (IOException e) {
            Log.e("", e.getMessage());
            try {
                Log.e("", "trying fallback...");
                mmSocket = (BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mmDevice, 1);
                mmSocket.connect();
                mConnectedThread = new ConnectedThread(mmSocket);
                mConnectedThread.start();
                Log.e("", "Connected");
            } catch (Exception e2) {
                Log.e("", "Couldn't establish Bluetooth connection!");
            }
        }
    }
    public void next(View v)
    {
        Intent intent = new Intent();
        intent.setClass(SetBluetooth.this, Petfile.class);
        startActivity(intent);
    }
    public void nextToPettime(View v)
    {
        Intent intent = new Intent();
        intent.setClass(SetBluetooth.this, SetNotificationTime.class);
        startActivity(intent);
    }
    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
            }
            mmInStream = tmpIn;
        }
        public void run() {
            while (true) {
                byte[] buffer = new byte[256];
                int bytes;
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                    recDataString.setLength(bytes);
                } catch (IOException e) {
                    break;
                }
            }
        }
    }
}

