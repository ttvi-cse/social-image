package com.hcmut.social.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by John on 10/6/2016.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static Class[] TABLE_ARR = {
    };

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = ".db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 23;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");

            for(Class clazz : TABLE_ARR)
                TableUtils.createTable(connectionSource, clazz);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            for(Class clazz : TABLE_ARR)
                TableUtils.dropTable(connectionSource, clazz, true);

            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


}
