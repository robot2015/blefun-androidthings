package com.nilhcem.blefun.mobile.interact;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nilhcem.blefun.mobile.R;

import java.text.DecimalFormat;

public class InteractActivity extends AppCompatActivity {

    private static final String TAG = InteractActivity.class.getSimpleName();
    public static final String EXTRA_DEVICE_ADDRESS = "mAddress";

    private final GattClient mGattClient = new GattClient();
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interact_activity);
        mButton = findViewById(R.id.interact_button);
        mButton.setEnabled(false);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGattClient.writeInteractor();
            }
        });

        String address = getIntent().getStringExtra(EXTRA_DEVICE_ADDRESS);
        mGattClient.onCreate(this, address, new GattClient.OnCounterReadListener() {
            @Override
            public void onCounterRead(final int value) {
                Log.v(TAG, "onCounterRead");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DecimalFormat df = new DecimalFormat("#");
                        mButton.setText(df.format(value));
                    }
                });
            }

            @Override
            public void onConnected(final boolean success) {
                Log.v(TAG, "onConnected");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mButton.setEnabled(success);
                        if (!success) {
                            Log.v(TAG, "Connection error");
                            Toast.makeText(InteractActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
        mGattClient.onDestroy();
    }
}
