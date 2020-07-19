package com.v210.utils;

import android.content.*;

public class Settings
{
    public static final float[] SENSITIVE_ARRAY = {1.97f, 2.96f, 4.44f, 6.66f, 10.0f, 15.0f, 22.50f, 33.75f, 50.62f};
    public static final int[] INTERVAL_ARRAY = {100, 200, 300, 400, 500, 600, 700, 800};
    public static final String SENSITIVITY = "sensitivity";
    public static final String INTERVAL = "interval";
    public static final String STEP_LEN = "steplen";
    public static final String BODY_WEIGHT = "bodyweight";
    PrefsManager prefsManager = null;

    public Settings(Context context)
    {
        prefsManager = new PrefsManager(context);
    }

    public double getSensitivity()
    {
        //灵敏度
        float sensitivity = prefsManager.getFloat(SENSITIVITY);
        if (sensitivity == 0.0f)
        {
            return 10.0f;
        }
        return sensitivity;
    }

    public void setSensitivity(float sensitivity)
    {
        prefsManager.putFloat(SENSITIVITY, sensitivity);
    }

    /**
     * 时间间隔
     *
     * @return
     */
    public int getInterval()
    {
        int interval = prefsManager.getInt(INTERVAL);
        if (interval == 0)
        {
            return 200;
        }
        return interval;
    }

    public void setInterval(int interval)
    {
        prefsManager.putInt(INTERVAL, interval);
    }

    public float getSetpLength()
    {
        float stepLength = prefsManager.getFloat(STEP_LEN);
        if (stepLength == 0.0f)
        {
            return 50.0f;
        }
        return stepLength;
    }

    public void setStepLength(float stepLength)
    {
        prefsManager.putFloat(STEP_LEN, stepLength);
    }

    public float getBodyWeight()
    {
        float bodyWeight = prefsManager.getFloat(BODY_WEIGHT);
        if (bodyWeight == 0.0f)
        {
            return 60.0f;
        }
        return bodyWeight;
    }

    public void setBodyWeight(float bodyWeight)
    {
        prefsManager.putFloat(BODY_WEIGHT, bodyWeight);
    }
}
