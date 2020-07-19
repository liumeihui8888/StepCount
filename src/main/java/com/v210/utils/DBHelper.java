package com.v210.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
//    public static final String CREATE_PEDOMETER="create table pedometer("
//            +"id,"
//            +"stepCount,"
//            +"calorie,"
//            +"distance,"
//            +"pace,"
//            + "speed,"
//            +"startTime,"
//            +"lastStepTime,"
//            +"createTime)";

      public static final String TABLE_NAME = "pedometer";
      public static final String[] COLUMNS =
            {
                    "id",
                    "stepCount",
                    "calorie",
                    "distance",
                    "pace",
                    "speed",
                    "startTime",
                    "lastStepTime",
                    "createTime"
            };

    /**
     * 在SQLiteOpenHelper的子类当中，必须有该构造函数
     *
     * @param context 上下文对象
     * @param name    数据库名称
     * @param factory
     * @param version 当前数据库的版本，值必须是整数并且是递增的状态
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DBHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    //该函数是在第一次创建的时候执行，实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
    public void onCreate(SQLiteDatabase db) {
        //execSQL用于执行SQL语句
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id integer  PRIMARY KEY AUTOINCREMENT DEFAULT NULL," +
                "stepCount integer," +
                "calorie Double," +
                "distance Double DEFAULT NULL," +
                "pace INTEGER," +
                "speed Double," +
                "startTime Timestamp DEFAULT NULL," +
                "lastStepTime Timestamp  DEFAULT NULL," +
                "createTime Timestamp   DEFAULT NULL)");
    }

    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }


    public void writeToDatabase(com.imooc.model.PedometerBean bean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stepCount", bean.getStepCount());
        values.put("calorie", bean.getCalorie());
        values.put("distance", bean.getDistance());
        values.put("pace", bean.getPace());
        values.put("speed", bean.getSpeed());
        values.put("startTime", bean.getStartTime());
        values.put("lastStepTime", bean.getLastStepTime());
        values.put("createTime", bean.getCreateTime());
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }


    public com.imooc.model.PedometerBean getByCreateTime(long createTime) {
        Cursor cursor = null;
        com.imooc.model.PedometerBean bean = new com.imooc.model.PedometerBean();
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery("select * from " + DBHelper.TABLE_NAME + " where createTime=" + String.valueOf(createTime), null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMNS[0]));
                int stepCount = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMNS[1]));
                double calorie = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMNS[2]));
                double distance = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMNS[3]));
                int pace = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMNS[4]));
                double speed = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMNS[5]));
                long startTime = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMNS[6]));
                long lastStepTime = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMNS[7]));
                long cTime = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMNS[8]));
                bean.setId(id);
                bean.setStepCount(stepCount);
                bean.setCalorie(calorie);
                bean.setDistance(distance);

                bean.setPace(pace);
                bean.setSpeed(speed);
                bean.setStartTime(startTime);
                bean.setLastStepTime(lastStepTime);
                bean.setCreateTime(cTime);

            }
        }
        cursor.close();
        db.close();
        return bean;
    }

    public ArrayList<com.imooc.model.PedometerBean> getFromDatabase() {
        int pageSize = 20;
        int offVal = 0;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null,
                "createTime desc limit " + String.valueOf(pageSize) + " offset " + String.valueOf(offVal),
                null);
        if (cursor != null && cursor.getCount() > 0) {
            ArrayList<com.imooc.model.PedometerBean> data = new ArrayList<com.imooc.model.PedometerBean>();
            while (cursor.moveToNext()) {
                com.imooc.model.PedometerBean bean = new com.imooc.model.PedometerBean();
                int id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMNS[0]));
                int stepCount = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMNS[1]));
                double calorie = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMNS[2]));
                double distance = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMNS[3]));
                int pace = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMNS[4]));
                double speed = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMNS[5]));
                long startTime = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMNS[6]));
                long lastStepTime = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMNS[7]));
                long createTime = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMNS[8]));
                bean.setId(id);
                bean.setStepCount(stepCount);
                bean.setCalorie(calorie);
                bean.setDistance(distance);
                bean.setPace(pace);
                bean.setSpeed(speed);
                bean.setStartTime(startTime);
                bean.setLastStepTime(lastStepTime);
                bean.setCreateTime(createTime);
                data.add(bean);
            }
            cursor.close();
            db.close();
            return data;
        }
        return null;
    }

    public void updateToDatabase(ContentValues values, long createTime) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_NAME, values, "createTime=?", new String[]{String.valueOf(createTime)});
        database.close();
    }
}
