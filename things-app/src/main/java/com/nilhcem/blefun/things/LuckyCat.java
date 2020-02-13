package com.nilhcem.blefun.things;

import android.util.Log;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.pwmservo.Servo;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

class LuckyCat {

    private static final String TAG = LuckyCat.class.getSimpleName();
    private static final String SERVO_PWM = "PWM0";
    private static final String DIGITS_SPI = "SPI0.0";

    private Servo mServo;
    private AlphanumericDisplay segment;

    void onCreate() {
        Log.v(TAG, "onCreate");
        try {
            mServo = new Servo(SERVO_PWM);
            mServo.setPulseDurationRange(0.6, 2.4);
            mServo.setAngleRange(-90, 90);
            mServo.setEnabled(true);

            segment = RainbowHat.openDisplay();
            segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
        } catch (IOException e) {
            Log.e(TAG, "Error initializing lucky cat", e);
        }
    }

    void movePaw() {
        Log.v(TAG, "movePaw");
        try {
            mServo.setAngle(mServo.getMaximumAngle());
            Thread.sleep(1000);
            mServo.setAngle(mServo.getMinimumAngle());
        } catch (IOException e) {
            Log.e(TAG, "Error moving paw", e);
        } catch (InterruptedException e) {
            Log.e(TAG, "Sleep error", e);
        }
    }

    void updateCounter(int counter) {
        Log.v(TAG, "updateCounter");
        Log.v(TAG, "Counter value = " + counter);
        try {
            segment.display(counter);
            // segment.clear();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    void onDestroy() {
        Log.v(TAG, "onDestroy");
        try {
            if (mServo != null) {
                mServo.close();
            }
            if (segment != null) {
                segment.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing lucky cat resources", e);
        } finally {
            mServo = null;
            segment = null;
        }
    }
}
