package com.zworks.latest.Utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zworks.latest.Common.Constants;

/**
 * Created by zhangqiang on 2015/7/25.
 */
public class DatabaseHelper extends SQLiteOpenHelper  {
    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.Program.CREATE_TABLE);
        db.execSQL(Constants.Record.CREATE_TABLE);

    }

    // Method is called during an upgrade of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.Program.DELETE_TABLE);
        db.execSQL(Constants.Record.DELETE_TABLE);
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
