package com.nilhcem.blefun.things;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

public class MainActivity2 extends Activity {
    private static final String TAG = MainActivity2.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        // Light up the Red LED.
        // lightRedLed();

        // Display a string on the segment display.
        // displayLetters();

        // Clear the segment display.
        clearDisplay();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }

    void lightRedLed() {
        try {
            // Light up the Red LED.
            Gpio led = RainbowHat.openLedRed();
            led.setValue(false);
            led.close();
            Log.v(TAG, "lightRedLed");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    void displayLetters() {
        try {
            // Display a string on the segment display.
            AlphanumericDisplay segment = RainbowHat.openDisplay();
            segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
            segment.display("ABCD");
            segment.setEnabled(true);
            // Close the device when done.
            segment.close();
            Log.v(TAG, "displayLetters");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    void clearDisplay() {
        try {
            // Clear the segment display.
            AlphanumericDisplay segment = RainbowHat.openDisplay();
            segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
            segment.clear();
            // Close the device when done.
            segment.close();
            Log.v(TAG, "displayLetters");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
