package com.hcmut.social.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by John on 10/6/2016.
 */

public class CustomSharedPreferences {
    public static final String CUSTOM_SHAREPREFERNCES = "CUSTOM_SHAREPREFERNCES";
    private static SharedPreferences sharedPre;
    private static SharedPreferences.Editor editor;
    private static Context appContext;
    /**
     * Constructor. Please create new object before using setter and getter
     * @param context Input from Activity
     */

    public static void init (final Context context){

        appContext = context;
        //
        refresh();

    }

    private static void refresh (){

        if (editor==null) {

            if(null != appContext){
                sharedPre = appContext.getSharedPreferences(CUSTOM_SHAREPREFERNCES, Context.MODE_PRIVATE);
                editor =sharedPre.edit();
            }
            else{
                editor = null;
                sharedPre = null;
            }

        }

    }

    /**
     * Set data for String
     * @param preName Preferences name
     * @param value String input
     */
    public synchronized static void setPreferences(final String preName, final String value){
        refresh();
        if(null != editor){
            editor.putString(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for String
     * @param preName Preferences name
     * @param defaultValue
     * @return String or 0 if Name not existed
     */
    public static String getPreferences(final String preName, final String defaultValue){
        refresh();
        if(null != sharedPre){
            return sharedPre.getString(preName, defaultValue);
        }else{
            return null;
        }
    }

    /**
     * Set data for boolean
     * @param preName Preferences name
     * @param value boolean input
     */
    public synchronized static void setPreferences(final String preName, final boolean value){
        refresh();
        if(null != editor){
            editor.putBoolean(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for boolean
     * @param preName Preferences name
     * @param defaultValue
     * @return boolean or 0 if Name not existed
     */
    public static boolean getPreferences(final String preName, final boolean defaultValue){
        refresh();
        if(null != sharedPre){
            return sharedPre.getBoolean(preName, defaultValue);
        }else{
            return false;
        }
    }

    /**
     * Set data for Integer
     * @param preName Preferences name
     * @param value Integer input
     */
    public synchronized static void setPreferences(final String preName, final int value){
        refresh();
        if(null != editor){
            editor.putInt(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for Integer
     * @param preName Preferences name
     * @param defaultValue
     * @return Integer or -1 if Name not existed
     */
    public static int getPreferences(final String preName, final int defaultValue){
        refresh();
        if(null != sharedPre){
            return sharedPre.getInt(preName, defaultValue);
        }else{
            return -1;
        }
    }

    /**
     * Set data for Long
     * @param preName Preferences name
     * @param value Long input
     */
    public synchronized static void setPreferences(final String preName, final long value){
        refresh();
        if(null != editor){
            editor.putLong(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for Long
     * @param preName Preferences name
     * @param defaultValue
     * @return Long or -1 if Name not existed
     */
    public static long getPreferences(final String preName, final long defaultValue){
        refresh();
        if(null != sharedPre){
            return sharedPre.getLong(preName, defaultValue);
        }else{
            return -1;
        }
    }

    /**
     * Set data for Float
     * @param preName Preferences name
     * @param value Float input
     */
    public synchronized static void setPreferences(final String preName, final float value){
        refresh();
        if(null != editor){
            editor.putFloat(preName, value);
            editor.commit();
        }
    }

    /**
     * Get data for Float
     * @param preName Preferences name
     * @param defaultValue
     * @return Float or -1 if Name not existed
     */
    public static float getPreferences(final String preName, final float defaultValue){
        refresh();
        if(null != sharedPre){
            return sharedPre.getFloat(preName, defaultValue);
        }else{
            return -1;
        }

    }

    /**
     * remove data in preferences by key name
     * @param preName Preferences name
     */
    public synchronized static void removeKey(final String preName){
        refresh();
        if(null != editor){
            editor.remove(preName);
            editor.commit();
        }
    }
}
