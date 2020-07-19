package com.imooc.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.RemoteException;
import com.v210.utils.*;

public class PedometerService extends Service
{
    private static final long SAVE_TIME = 60000L;
    //传感器
    private SensorManager mSensorMgr;
    private static final int STATUS_NOT_RUNNING = 0;//非运行中
    private static final int STATUS_RUNNING = 1;//运行中
    private PedometerListener mPedometerListener;//监听运动状态
    private com.imooc.model.PedometerBean pedmoeterBean;//记录步数等信息
    private com.imooc.model.PedometerChartBean pedometerChartBean;//记录显示数据
    private int runStatus = 0;//当前运行状态
    private Settings settings;

    private static android.os.Handler handler = new android.os.Handler();

    private Runnable timeRunnable = new Runnable()
    {
        public void run()
        {
            if (runStatus == STATUS_RUNNING)
            {
                if (handler != null && pedometerChartBean != null)
                {
                    handler.removeCallbacks(timeRunnable);
                    updateChartData();
                    handler.postDelayed(timeRunnable, SAVE_TIME);//1分钟刷新一次记录值,然后保存
                }
            }
        }
    };

    private void updateChartData()
    {
        if (pedometerChartBean.getIndex() < 1440)
        {
            pedometerChartBean.setIndex(pedometerChartBean.getIndex() + 1);
            pedometerChartBean.getDataArray()[pedometerChartBean.getIndex()] = pedmoeterBean.getStepCount();
        }
    }

    private void saveChartData()
    {        //刷新数据
//			updateChartData();
        String json = Utils.objToJson(pedometerChartBean);
        ACache mACache = com.v210.frame.FrameApplication.getFileCache();
        mACache.put("JsonData", json);//将数据保存到文件
    }

    public void onCreate()
    {
        mSensorMgr = (SensorManager) this.getSystemService(android.content.Context.SENSOR_SERVICE);
        pedmoeterBean = new com.imooc.model.PedometerBean();
        pedmoeterBean.setCreateTime(Utils.getTimestempByDay());
        mPedometerListener = new PedometerListener(this, pedmoeterBean);
        pedometerChartBean = new com.imooc.model.PedometerChartBean();
        settings = new Settings(this);
    }

    public IBinder onBind(Intent intent)
    {
        return iPedometerService;
    }

    public void onDestroy()
    {
    }

    private IPedometerService.Stub iPedometerService = new IPedometerService.Stub()
    {
        public com.imooc.model.PedometerChartBean getChartData() throws RemoteException
        {
            return pedometerChartBean;
        }

        public int getStepsCount() throws RemoteException
        {
            if (pedmoeterBean != null)
            {
                return pedmoeterBean.getStepCount();
            }
            return 0;
        }

        public void resetCount() throws RemoteException
        {
            if (pedmoeterBean != null)
            {
                pedmoeterBean.reset();
                saveData();
            }
            if (pedometerChartBean != null)
            {
                pedometerChartBean.reset();
                saveChartData();
            }
            if (mPedometerListener != null)
            {
                mPedometerListener.resetCurrentStep();
            }
        }

        public void startSetpsCount() throws RemoteException
        {
            startStep();
        }

        public void stopSetpsCount() throws RemoteException
        {
            stopStep();
        }

        public double getCalorie() throws RemoteException
        {
            if (pedmoeterBean != null)
            {
                return getCalorieBySteps(pedmoeterBean.getStepCount());
            }
            return 0;
        }

        public double getDistance() throws RemoteException
        {
            return getDistanceVal();
        }

        public void saveData() throws RemoteException
        {
            saveDataToDb();
        }

        public void setSensitivity(float sensitivity) throws RemoteException
        {
            if (settings != null)
            {
                settings.setSensitivity(sensitivity);
            }
        }

        public double getSensitivity() throws RemoteException
        {
            return settings.getSensitivity();
        }

        public int getInterval() throws RemoteException
        {
            return settings.getInterval();
        }

        public void setInterval(int interval) throws RemoteException
        {
            if (settings != null)
            {
                settings.setInterval(interval);
            }
        }

        /**
         * 获取开始时间戳
         * @return
         * @throws RemoteException
         */
        public long getStartTimestmp() throws RemoteException
        {
            if (pedmoeterBean != null)
            {
                return pedmoeterBean.getStartTime();
            }
            return 0;
        }

        public int getServiceRunningStatus() throws RemoteException
        {
            return runStatus;
        }
    };

    /**
     * 距离,单位千米
     *
     * @return
     * @throws RemoteException
     */
    public double getDistanceVal()
    {
        if (pedmoeterBean != null)
        {
            Settings settings = new Settings(PedometerService.this);
            double distance = (pedmoeterBean.getStepCount() * (long) settings.getSetpLength()) / 100000.0f;
            return distance;
        }
        return 0;
    }

    private void saveDataToDb()
    {
        if (pedmoeterBean != null)
        {
            new Thread(new Runnable()
            {
                public void run()
                {
                    DBHelper dbHelper = new DBHelper(PedometerService.this, "PedometerDB");
                    pedmoeterBean.setDistance(getDistanceVal());//设置距离
                    pedmoeterBean.setCalorie(getCalorieBySteps(pedmoeterBean.getStepCount()));//消耗的卡路里
                    long time = pedmoeterBean.getLastStepTime() - pedmoeterBean.getStartTime() / 1000;
                    if (time == 0)
                    {
                        pedmoeterBean.setPace(0);//设置步/分
                        pedmoeterBean.setSpeed(0);
                    }
                    else
                    {
                        int pace = Math.round(60 * pedmoeterBean.getStepCount() / time);
                        pedmoeterBean.setPace(pace);//设置步/分
                        long speed = Math.round((pedmoeterBean.getDistance() / 1000) / (time / 60 * 60));
                        pedmoeterBean.setSpeed(speed);
                    }
                    dbHelper.writeToDatabase(pedmoeterBean);
                    saveChartData();
                }
            }).start();
        }
    }

    public void stopStep()
    {
        if (mSensorMgr != null && mPedometerListener != null)
        {
            Sensor sensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorMgr.unregisterListener(mPedometerListener, sensor);
            runStatus = STATUS_NOT_RUNNING;
            handler.removeCallbacks(timeRunnable);
            LogWriter.d("Stop Step Count");
        }
    }

    public void startStep()
    {
        if (mSensorMgr != null && mPedometerListener != null)
        {
            Sensor sensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorMgr.registerListener(mPedometerListener, sensor, SensorManager.SENSOR_DELAY_GAME);
            pedmoeterBean.setStartTime(System.currentTimeMillis());
            runStatus = STATUS_RUNNING;
            handler.postDelayed(timeRunnable, SAVE_TIME);//开始记录
            LogWriter.d("Start Step Count");
        }
    }

    private double getCalorieBySteps(int frequency)
    {
        Settings settings = new Settings(this);
        double METRIC_RUNNING_FACTOR = 1.02784823;//跑步
        double METRIC_WALKING_FACTOR = 0.708;//走路
        double mCalories = 0;
        // 跑步热量（kcal）＝体重（kg）×距离（公里）×1.02784823
        // 走路热量（kcal）＝体重（kg）×距离（公里）×0.708
        mCalories = (settings.getBodyWeight() * METRIC_WALKING_FACTOR) * settings.getSetpLength() * frequency / 100000.0;
        return mCalories;
    }
}
