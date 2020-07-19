/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\Android\\studiospace\\MyApplication\\iMoocStep\\src\\main\\aidl\\com\\imooc\\service\\IPedometerService.aidl
 */
package com.imooc.service;
public interface IPedometerService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.imooc.service.IPedometerService
{
private static final java.lang.String DESCRIPTOR = "com.imooc.service.IPedometerService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.imooc.service.IPedometerService interface,
 * generating a proxy if needed.
 */
public static com.imooc.service.IPedometerService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.imooc.service.IPedometerService))) {
return ((com.imooc.service.IPedometerService)iin);
}
return new com.imooc.service.IPedometerService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getStepsCount:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getStepsCount();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_resetCount:
{
data.enforceInterface(DESCRIPTOR);
this.resetCount();
reply.writeNoException();
return true;
}
case TRANSACTION_startSetpsCount:
{
data.enforceInterface(DESCRIPTOR);
this.startSetpsCount();
reply.writeNoException();
return true;
}
case TRANSACTION_stopSetpsCount:
{
data.enforceInterface(DESCRIPTOR);
this.stopSetpsCount();
reply.writeNoException();
return true;
}
case TRANSACTION_getCalorie:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getCalorie();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_getDistance:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getDistance();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_saveData:
{
data.enforceInterface(DESCRIPTOR);
this.saveData();
reply.writeNoException();
return true;
}
case TRANSACTION_setSensitivity:
{
data.enforceInterface(DESCRIPTOR);
float _arg0;
_arg0 = data.readFloat();
this.setSensitivity(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getSensitivity:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getSensitivity();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_getInterval:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getInterval();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setInterval:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setInterval(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getStartTimestmp:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.getStartTimestmp();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getServiceRunningStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getServiceRunningStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getChartData:
{
data.enforceInterface(DESCRIPTOR);
com.imooc.model.PedometerChartBean _result = this.getChartData();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.imooc.service.IPedometerService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
//获取计步器步数

@Override public int getStepsCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getStepsCount, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//重置计步器步数

@Override public void resetCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resetCount, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//开始记步

@Override public void startSetpsCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startSetpsCount, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//停止记步

@Override public void stopSetpsCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopSetpsCount, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//获取消耗的卡路里

@Override public double getCalorie() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCalorie, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获取走路的距离

@Override public double getDistance() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDistance, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//保存数据

@Override public void saveData() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_saveData, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//设置传感器敏感度

@Override public void setSensitivity(float sensitivity) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeFloat(sensitivity);
mRemote.transact(Stub.TRANSACTION_setSensitivity, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//获取传感器敏感度

@Override public double getSensitivity() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSensitivity, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获取采样时间

@Override public int getInterval() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getInterval, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//设置采样时间

@Override public void setInterval(int interval) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(interval);
mRemote.transact(Stub.TRANSACTION_setInterval, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//获取时间戳

@Override public long getStartTimestmp() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getStartTimestmp, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获取运行状态

@Override public int getServiceRunningStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getServiceRunningStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获取运动图表数据

@Override public com.imooc.model.PedometerChartBean getChartData() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.imooc.model.PedometerChartBean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getChartData, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.imooc.model.PedometerChartBean.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getStepsCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_resetCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_startSetpsCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_stopSetpsCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getCalorie = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getDistance = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_saveData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setSensitivity = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getSensitivity = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getInterval = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_setInterval = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getStartTimestmp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getServiceRunningStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getChartData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
}
//获取计步器步数

public int getStepsCount() throws android.os.RemoteException;
//重置计步器步数

public void resetCount() throws android.os.RemoteException;
//开始记步

public void startSetpsCount() throws android.os.RemoteException;
//停止记步

public void stopSetpsCount() throws android.os.RemoteException;
//获取消耗的卡路里

public double getCalorie() throws android.os.RemoteException;
//获取走路的距离

public double getDistance() throws android.os.RemoteException;
//保存数据

public void saveData() throws android.os.RemoteException;
//设置传感器敏感度

public void setSensitivity(float sensitivity) throws android.os.RemoteException;
//获取传感器敏感度

public double getSensitivity() throws android.os.RemoteException;
//获取采样时间

public int getInterval() throws android.os.RemoteException;
//设置采样时间

public void setInterval(int interval) throws android.os.RemoteException;
//获取时间戳

public long getStartTimestmp() throws android.os.RemoteException;
//获取运行状态

public int getServiceRunningStatus() throws android.os.RemoteException;
//获取运动图表数据

public com.imooc.model.PedometerChartBean getChartData() throws android.os.RemoteException;
}
