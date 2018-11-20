package com.mobilesafe.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    private static SharedPreferences sp;

    /**
     * 保存boolean类型的键值对到sp中
     * @param context 上下文
     * @param key   指定的键
     * @param value 要存储的值
     */
    public static void putBoolean(Context context,String key,boolean value){
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }

        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * 获取boolean类型的值
     * @param context 上下文
     * @param key 指定的键
     * @param defValue 如果获取不到值，设置的默认值
     * @return
     */
    public static boolean getBoolean(Context context,String key,boolean defValue){
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }

        return sp.getBoolean(key,defValue);
    }

    /**
     * 保存字符串类型的键值对到sp中
     * @param context 上下文
     * @param key   指定的键
     * @param value 要存储的值
     */
    public static void putString(Context context,String key,String value){
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }

        sp.edit().putString(key,value).commit();
    }

    /**
     * 获取字符串类型的值
     * @param context 上下文
     * @param key 指定的键
     * @param defValue 如果获取不到值，设置的默认值
     * @return
     */
    public static String getString(Context context,String key,String defValue){
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }

        return sp.getString(key,defValue);
    }

    /**
     * 移除存储的键
     * @param context 上下文
     * @param key 要移除的键
     */
    public static void remove(Context context,String key){
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }

        sp.edit().remove(key).commit();
    }
}
