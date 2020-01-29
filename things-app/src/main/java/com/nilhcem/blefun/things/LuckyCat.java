package com.nilhcem.blefun.things;

import android.util.Log;

import com.google.android.things.contrib.driver.pwmservo.Servo;
import com.nilhcem.ledcontrol.LedControl;

import java.io.IOException;

class LuckyCat {

    private static final String TAG = LuckyCat.class.getSimpleName();
    private static final String SERVO_PWM = "PWM0";
    private static final String DIGITS_SPI = "SPI0.0";

    private Servo mServo;
    private LedControl mLedControl;

    void onCreate() {
        Log.v(TAG, "onCreate");
        try {
            mServo = new Servo(SERVO_PWM);
            mServo.setPulseDurationRange(0.6, 2.4);
            mServo.setAngleRange(-90, 90);
            mServo.setEnabled(true);

            mLedControl = new LedControl(DIGITS_SPI);
            mLedControl.setIntensity(4);
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
        int curValue = counter;
        for (int i = 0; i < 8; i++) {
            byte value = (byte) ((i != 0 && curValue == 0) ? 16 : (curValue % 10));
            try {
                mLedControl.setDigit(i, value, false);
                Log.v(TAG, "Set digit = " + i);
            } catch (IOException e) {
                Log.e(TAG, "Error setting counter", e);
            }
            curValue /= 10;
        }
    }

    void onDestroy() {
        Log.v(TAG, "onDestroy");
        try {
            if (mServo != null) {
                mServo.close();
            }
            if (mLedControl != null) {
                mLedControl.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing lucky cat resources", e);
        } finally {
            mServo = null;
            mLedControl = null;
        }
    }
}
