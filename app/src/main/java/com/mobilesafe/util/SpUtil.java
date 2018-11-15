package com.mobilesafe.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    private static SharedPreferences sp;

    /**
     * 保存指定的键值对到sp中
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
     * 获取指定键的值
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
}
