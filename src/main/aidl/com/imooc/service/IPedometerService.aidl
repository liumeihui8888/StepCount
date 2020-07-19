package com.imooc.service;
import com.imooc.model.PedometerChartBean;
interface IPedometerService
{
    //获取计步器步数
    int getStepsCount();
    //重置计步器步数
    void resetCount();
    //开始记步
    void startSetpsCount();
    //停止记步
    void stopSetpsCount();
    //获取消耗的卡路里
    double getCalorie();
    //获取走路的距离
    double getDistance();
    //保存数据
    void saveData();
    //设置传感器敏感度
    void setSensitivity(float sensitivity);

    //获取传感器敏感度
    double getSensitivity();
    //获取采样时间
    int getInterval();
    //设置采样时间
    void setInterval(int interval);
    //获取时间戳
    long getStartTimestmp();
    //获取运行状态
    int getServiceRunningStatus();
    //获取运动图表数据
    PedometerChartBean getChartData();
}