package com.hcmut.social.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by John on 10/6/2016.
 */

public class DatabaseManager {

    private static DatabaseManager mInstance;
    private Context mContext;
    private DatabaseHelper mDBHelper;
    private HashMap<Class, Dao<?, Integer>> mListDao = new HashMap<>();

    public static DatabaseManager getInstance() {
        if(mInstance == null)
            mInstance = new DatabaseManager();

        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        mDBHelper = new DatabaseHelper(mContext);
    }

    public DatabaseHelper getDBHelper() {
        return mDBHelper;
    }

    public Dao<?, Integer> getDao(Class clazz) {

        Dao<?, Integer> dao = mListDao.get(clazz);

        if(dao == null) {
            try {
                dao = mDBHelper.getDao(clazz);
                mListDao.put(clazz, dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dao;
    }

}
