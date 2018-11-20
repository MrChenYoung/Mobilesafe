package com.mobilesafe.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PermissionBaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // 授权成功

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // 授权被拒绝
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            // 用户拒绝并选择了不再询问,弹出对话框提示用户跳转到应用权限设置页面开启权限
            new AppSettingsDialog.Builder(this)
                    .setRationale("权限被拒绝，去设置页面开启?")
                    .setTitle("温馨提示")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(requestCode)
                    .build()
                    .show();
        }
    }
}
