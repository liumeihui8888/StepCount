package com.imooc.service;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.v210.utils.LogWriter;

public class PedometerListener implements SensorEventListener
{

	public static int CURRENT_SETP = 0;
	public static float SENSITIVITY = 10; // SENSITIVITY灵敏度
	private static long mLimit = 300;//采样时间
	private float mLastValues[] = new float[3 * 2];//最后保存的数据
	private float mScale[] = new float[2];

	private float mYOffset;
	private static long end = 0;
	private static long start = 0;
	/**
	 * 最后加速度方向
	 */
	private float mLastDirections[] = new float[3 * 2];
	private float mLastExtremes[][] = {new float[3 * 2], new float[3 * 2]};
	private float mLastDiff[] = new float[3 * 2];
	private int mLastMatch = -1;
	com.imooc.model.PedometerBean pedmoeterBean;

	public void  resetCurrentStep()
	{
		CURRENT_SETP = 0;
	}
	/**
	 * 传入上下文的构造函数
	 *
	 * @param context
	 */
	public PedometerListener(Context context, com.imooc.model.PedometerBean pedmoeterBean)
	{
		super();
		int h = 480;
		mYOffset = h * 0.5f;//240
		mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
		mScale[1] = -(h * 0.5f *  (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
		this.pedmoeterBean = pedmoeterBean;
	}

	//当传感器检测到的数值发生变化时就会调用这个方法
	public void onSensorChanged(SensorEvent event)
	{
		Sensor sensor = event.sensor;
		synchronized (this)
		{
			if (sensor.getType() == Sensor.TYPE_ACCELEROMETER)//加速传感器
			{
				float vSum = 0;
				for (int i = 0; i < 3; i++)
				{
					final float v = mYOffset + event.values[i] * mScale[1];
					vSum += v;
				}
				int k = 0;
				float v = vSum / 3;//记录三个轴向,传感器的平均值

				float direction = (v > mLastValues[k] ? 1
						: (v < mLastValues[k] ? -1 : 0));
				if (direction == -mLastDirections[k])
				{
					// Direction changed
					int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
					mLastExtremes[extType][k] = mLastValues[k];
					float diff = Math.abs(mLastExtremes[extType][k]
							- mLastExtremes[1 - extType][k]);

					if (diff > SENSITIVITY)
					{
						boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
						boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
						boolean isNotContra = (mLastMatch != 1 - extType);

						if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough
								&& isNotContra)
						{
							end = System.currentTimeMillis();
							if (end - start > mLimit)
							{// 此时判断为走了一步
								CURRENT_SETP++;
								pedmoeterBean.setStepCount(CURRENT_SETP);
								pedmoeterBean.setLastStepTime(System.currentTimeMillis());
								mLastMatch = extType;
								start = end;
								LogWriter.d("Current count = "+ CURRENT_SETP);
							}
						} else
						{
							mLastMatch = -1;
						}
					}
					mLastDiff[k] = diff;
				}
				mLastDirections[k] = direction;
				mLastValues[k] = v;
			}
		}
	}

	public void onAccuracyChanged(Sensor sensor, int i)
	{

	}

}
