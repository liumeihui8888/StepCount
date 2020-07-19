package com.imooc.model;


public class PedometerBean
{
	public int getStepCount()
	{
		return stepCount;
	}

	public void setStepCount(int stepCount)
	{
		this.stepCount = stepCount;
	}

	public double getCalorie()
	{
		return calorie;
	}

	public void setCalorie(double calorie)
	{
		this.calorie = calorie;
	}

	public double getDistance()
	{
		return distance;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public int getPace()
	{
		return pace;
	}

	public void setPace(int pace)
	{
		this.pace = pace;
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	public long getStartTime()
	{
		return startTime;
	}

	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}

	public long getLastStepTime()
	{
		return lastStepTime;
	}

	public void setLastStepTime(long lastStepTime)
	{
		this.lastStepTime = lastStepTime;
	}

	public long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(long createTime)
	{
		this.createTime = createTime;
	}

	private int stepCount;//所走步数
	private double calorie;//消耗卡路里
	private double distance;//所走距离
	private int pace;//步/每分钟
	private double speed;//速度
	private long startTime;//开始记录时间
	private long lastStepTime;//最后一步的时间
	private long createTime;//当天的时间

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	private int id;

	public void reset()
	{
		stepCount = 0;
		calorie = 0;
		distance = 0;
	}

}
