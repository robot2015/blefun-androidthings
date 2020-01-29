package com.nilhcem.blefun.things;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

class AwesomenessCounter {

    private static final String TAG = AwesomenessCounter.class.getSimpleName();

    private static final String PREFS_NAME = "awesomeness";
    private static final String PREFS_KEY_COUNTER = "counter";

    private final SharedPreferences mPrefs;

    AwesomenessCounter(Context context) {
        Log.v(TAG, "AwesomenessCounter");
        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    int getCounterValue() {
        return mPrefs.getInt(PREFS_KEY_COUNTER, 0);
    }

    @SuppressLint("ApplySharedPref")
    int incrementCounterValue() {
        Log.v(TAG, "incrementCounterValue");
        int newValue = getCounterValue() + 1;
        mPrefs.edit().putInt(PREFS_KEY_COUNTER, newValue).commit();
        return newValue;
    }
}
