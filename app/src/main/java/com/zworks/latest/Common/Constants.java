package com.zworks.latest.Common;

import android.provider.BaseColumns;

/**
 * Created by zhangqiang on 2015/7/25.
 */
public class Constants {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INT_TYPE = " INT ";
    private static final String COMMA_SEP = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private Constants() {}

    public static abstract class Program implements BaseColumns {
        public static final String TABLE_NAME = "program";
        public static final String PROGRAM_NAME = "programname";
        public static final String AIR_DAYS = "airdays";
        //提醒推迟的天数
        public static final String REMIND_DELAYED ="reminddelayed";
        public static final String BEGIN_DATE = "begindate";
        //recordtype
        //1、日期；2、期数；3、日期+期数
        public static final String RECORD_TYPE = "recordtype";



        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PROGRAM_NAME + TEXT_TYPE + "NOT NULL"+COMMA_SEP +
                RECORD_TYPE + INT_TYPE + "NOT NULL"+COMMA_SEP +
                AIR_DAYS + TEXT_TYPE + "NOT NULL"+COMMA_SEP +
                REMIND_DELAYED + INT_TYPE + "NOT NULL"+COMMA_SEP +
                BEGIN_DATE + INT_TYPE + "NOT NULL"+ " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    public static abstract class Record implements BaseColumns {
        public static final String TABLE_NAME = "record";
        public static final String PROGRAM_ID = "programid";
        public static final String RECORD_TIME = "recordtime";
        public static final String EPISODE = "episode";
        public static final String REMIND_TIME = "remindtime";
        //status:1、未看；0、已看；
        public static final String STATUS = "status";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PROGRAM_ID + INT_TYPE + "NOT NULL"+COMMA_SEP +
                RECORD_TIME + INT_TYPE + "NOT NULL"+COMMA_SEP +
                REMIND_TIME + INT_TYPE + "NOT NULL"+COMMA_SEP +
                EPISODE + INT_TYPE + "NOT NULL"+COMMA_SEP +
                STATUS + INT_TYPE + "NOT NULL"+COMMA_SEP +
                " FOREIGN KEY ("+PROGRAM_ID+") REFERENCES "+Program.TABLE_NAME+" ("+Program._ID+"));";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
