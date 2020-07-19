package com.imooc.stepapp;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.imooc.model.PedometerChartBean;
import com.imooc.service.IPedometerService;
import com.imooc.service.PedometerService;
import com.v210.frame.BaseActivity;
import com.v210.utils.LogWriter;
import com.v210.utils.Utils;
import com.v210.widgets.CircleProgressBar;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private Intent myServiceIntent = null;
    private boolean bindService = false;
    private IPedometerService mRemoteService;
    private Button startBtn;
    private Button resetBtn;
    private CircleProgressBar circleProgressBar;
    private View stepCountView;
    private TextView stepCount;
    private TextView calorieCount;
    private TextView time;
    private TextView distance;
    protected BarChart mChart;
    private static final int STATUS_NOT_RUNNING = 0;//not running
    private static final int STATUS_RUNNING = 1;//运行中
    private static final long SLEEP_TIME = 60000L;
    private static final int MESSAGE_UPDATE_STEP_COUNT = 1;
    private static final int MESSAGE_UPDATE_CHART = 2;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_UPDATE_STEP_COUNT: {
                    handler.removeMessages(MESSAGE_UPDATE_STEP_COUNT);
                    updateStepCount();
                }
                break;
                case MESSAGE_UPDATE_CHART: {
                    handler.removeMessages(MESSAGE_UPDATE_CHART);
                    if (chartBean != null) {
                        setData(chartBean);
                    }
                }
                break;
                default:
            }
            super.handleMessage(msg);
        }
    };

    public void setData(PedometerChartBean pChartBean) {
        java.util.ArrayList<String> xVals = new java.util.ArrayList<String>();
        java.util.ArrayList<BarEntry> yVals = new java.util.ArrayList<BarEntry>();
        if (pChartBean != null) {
            for (int i = 0; i <= pChartBean.getIndex(); i++) {
                xVals.add(String.valueOf(i) + "分");
                int valY = pChartBean.getDataArray()[i];
                yVals.add(new BarEntry(valY, i));
            }
            time.setText(String.valueOf(pChartBean.getIndex()) + "分");
            BarDataSet set1 = new BarDataSet(yVals, "所走步数");
            set1.setBarSpacePercent(2f);
            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);
            mChart.setData(data);
            mChart.invalidate();
        }
    }


    private volatile boolean isRunning = false;
    private PedometerChartBean chartBean;
    private volatile boolean isChartUpdate = false;

    private class ChartRunnable implements Runnable {
        public void run() {
            while (isChartUpdate) {
                try {
                    chartBean = mRemoteService.getChartData();
                    handler.sendEmptyMessage(MESSAGE_UPDATE_CHART);
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    LogWriter.d(e.toString());
                } catch (RemoteException e) {
                    LogWriter.d(e.toString());
                }
            }
        }
    }

    private class StepRunnable implements Runnable {
        public void run() {
            while (isRunning) {
                try {
                    //刷新一下服务的运行状态
                    status = mRemoteService.getServiceRunningStatus();
                    // 清理一下以前的消息
                    handler.removeMessages(MESSAGE_UPDATE_STEP_COUNT);
                    //更新一下UI显示
                    handler.sendEmptyMessage(MESSAGE_UPDATE_STEP_COUNT);
                    Thread.sleep(200);
                } catch (RemoteException e) {
                    LogWriter.d(e.toString());
                } catch (InterruptedException e) {
                    LogWriter.d(e.toString());
                }
            }
        }
    }

    public void updateStepCount() {
        if (mRemoteService != null) {
            //服务正在运行
            int stepCountVal = 0;
            double calorieVal = 0;
            double distanceVal = 0;
            try {
                stepCountVal = mRemoteService.getStepsCount();
                calorieVal = mRemoteService.getCalorie();
                distanceVal = mRemoteService.getDistance();
                LogWriter.d("distance =" + distanceVal);
            } catch (RemoteException e) {
                LogWriter.d(e.toString());
            }
            stepCount.setText(String.valueOf(stepCountVal) + "步");
            calorieCount.setText(Utils.getFormatVal(calorieVal, "0.00") + "卡");
            distance.setText(Utils.getFormatVal(distanceVal, "0.00"));
            circleProgressBar.setProgress(stepCountVal);
        }
    }

    protected void onInitVariable() {
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); //这一句必须的，否则Intent无法获得最新的数据
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mRemoteService = IPedometerService.Stub.asInterface(iBinder);
            try {
                status = mRemoteService.getServiceRunningStatus();
                if (status == STATUS_RUNNING) {
                    startBtn.setText("停止");
                    isRunning = true;
                    isChartUpdate = true;
                    new Thread(new StepRunnable()).start();
                    new Thread(new ChartRunnable()).start();
                    chartBean = mRemoteService.getChartData();
                    LogWriter.d("chart index" + chartBean.getIndex());
                    setData(chartBean);
                } else {
                    startBtn.setText("启动");
                }
            } catch (RemoteException e) {
                LogWriter.d(e.toString());
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteService = null;
        }
    };

    protected void onInitView(Bundle savedInstanceState) {
        setContentView(R.layout.act_main);
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText("iMooc计步器");
        stepCountView = findViewById(R.id.stepCountView);
        circleProgressBar = (CircleProgressBar) stepCountView.findViewById(R.id.circleProgressBar);
        circleProgressBar.setMax(10000);
        circleProgressBar.setProgress(0);
        stepCount = (TextView) findViewById(R.id.stepCount);
        time = (TextView) findViewById(R.id.time);
        distance = (TextView) findViewById(R.id.distance);
        calorieCount = (TextView) findViewById(R.id.textCalorie);
        startBtn = (Button) findViewById(R.id.btnStart);
        resetBtn = (Button) findViewById(R.id.reset);
        mChart = (com.github.mikephil.charting.charts.BarChart) findViewById(com.imooc.stepapp.R.id.chart1);
        mChart.setDrawBarShadow(false);//取消阴影
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        mChart.setMaxVisibleValueCount(288);
        mChart.setPinchZoom(true);//取消缩放
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setDrawGridBackground(false);
        ImageView rightImg = (ImageView) findViewById(R.id.rightImg);
        rightImg.setImageResource(R.drawable.setting_icon);
        rightImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(mIntent);
            }
        });
        time.setText("0");
        stepCount.setText("0步");
        calorieCount.setText("0.00卡");
        distance.setText("0.00");
        resetBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("确认重置");
                mBuilder.setMessage("您的记录将会被清零,确定吗?");
                mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mRemoteService != null) {
                            try {
                                mRemoteService.stopSetpsCount();
                                mRemoteService.resetCount();
                                chartBean = mRemoteService.getChartData();
                                setData(chartBean);
                                status = mRemoteService.getServiceRunningStatus();
                                if (status == STATUS_RUNNING) {
                                    startBtn.setText("停止");
                                } else if (status == STATUS_NOT_RUNNING) {
                                    startBtn.setText("启动");
                                }
                            } catch (RemoteException e) {
                                LogWriter.d(e.toString());
                            }
                        }
                        dialog.dismiss();
                    }
                });
                mBuilder.setNegativeButton("取消", null);
                AlertDialog dlg = mBuilder.create();
                dlg.show();
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    status = mRemoteService.getServiceRunningStatus();
                } catch (RemoteException e) {
                    LogWriter.d(e.toString());
                }

                if (status == STATUS_RUNNING) {
                    if (mRemoteService != null) {
                        try {
                            mRemoteService.stopSetpsCount();
                            startBtn.setText("启动");
                            isRunning = false;
                            isChartUpdate = false;
                        } catch (RemoteException e) {
                            LogWriter.d(e.toString());
                        }
                    }
                } else if (status == STATUS_NOT_RUNNING) {
                    if (mRemoteService != null) {
                        try {
                            mRemoteService.startSetpsCount();
                            startBtn.setText("停止");
                            isRunning = true;
                            isChartUpdate = true;
                            new Thread(new StepRunnable()).start();
                            new Thread(new ChartRunnable()).start();
                            chartBean = mRemoteService.getChartData();
                            setData(chartBean);
                        } catch (RemoteException e) {
                            LogWriter.d(e.toString());
                        }
                    }
                }
            }
        });
    }

    protected void onRequestData() {
        if (!Utils.isServiceRunning(this, "com.imooc.service.PedometerService")) {
            myServiceIntent = new Intent(this, PedometerService.class);
            startService(myServiceIntent);
        }

        if (myServiceIntent == null) {
            myServiceIntent = new Intent(this.getApplicationContext(), PedometerService.class);
        }
        // 设置新TASK的方式
        myServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 以bindService方法连接绑定服务
        bindService = bindService(myServiceIntent, serviceConnection, BIND_AUTO_CREATE);

        if (bindService && mRemoteService != null) {
            try {
                status = mRemoteService.getServiceRunningStatus();
                if (status == 0) {
                    startBtn.setText("启动");
                } else {
                    startBtn.setText("停止");
                    mRemoteService.startSetpsCount();
                    isRunning = true;
                    isChartUpdate = true;
                    new Thread(new StepRunnable()).start();
                    new Thread(new ChartRunnable()).start();
                }
            } catch (RemoteException e) {
                LogWriter.d(e.toString());
            }
        } else {
            startBtn.setText("启动");
        }
    }

    private int status = -1;

    protected void onLoadData() {
        saveData();
    }

    protected void onUnLoadData() {
        saveData();
    }

    private void saveData() {
        if (mRemoteService != null) {
            try {
                mRemoteService.saveData();
            } catch (RemoteException e) {
                LogWriter.d(e.toString());
            }
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        if (bindService) {
            bindService = false;
            isRunning = false;
            isChartUpdate = false;
            unbindService(serviceConnection);
        }

    }
}
