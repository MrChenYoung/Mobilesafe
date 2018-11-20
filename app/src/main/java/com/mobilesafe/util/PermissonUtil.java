package com.mobilesafe.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissonUtil {
    // 相机权限
    public static final int CAMERA = 100;
    // 录音权限
    public static final int RECORD = 200;

    /**
     * 检查是否有权限
     * @param context      山下文
     * @param host         请求授权的页面
     * @param requestCode  请求码(和AfterPermissionGranted(requestCode)里面的请求码一致)
     * @param rationale    请求权限描述
     * @param permissions   要请求的权限
     * @return             是否已经授权,已经授权返回true,没有授权返回false
     */
    public static boolean hasPermissions(Context context, @NonNull Activity host, int requestCode, @NonNull String rationale, @NonNull String...permissions){
        if (EasyPermissions.hasPermissions(context,permissions)){
            return true;
        }else {
            // 申请权限
            EasyPermissions.requestPermissions(host,rationale,requestCode,permissions);
            return false;
        }
    }
}
