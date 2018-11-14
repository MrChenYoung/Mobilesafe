package com.mobilesafe.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesafe.R;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;

    // 服务器上的版本号(这里写死模拟)
    private int mVersionCodeServer = 2;
    // 在启动页总共停留的时间
    private final int STAYTIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 显示版本名称
        tv_version = findViewById(R.id.tv_version);
        tv_version.setText("版本名称" + getVersionName());

        // 检测版本
        detectorVersion();
    }


    /**
     * 检测版本
     */
    private void detectorVersion(){
        // 模拟请求服务器版本号
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    // 记录当前时间，控制启动页停留的时间
                    long startTime = System.currentTimeMillis();

                    URL url = new URL("https://www.baidu.com");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    int code = connection.getResponseCode();
                    if (code == 200){
                        // 获取并比较版本号
                        int versionCode = getVersionCode();
                        if (versionCode < mVersionCodeServer){
                            // 有新版本，提示更新
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    newVersionTip();
                                }
                            });
                        }else {
                            long endTime = System.currentTimeMillis();
                            if (endTime - startTime >= STAYTIME){
                                // 直接进入主界面
                            }else {
                                // 计算剩余停留时间
                                SystemClock.sleep(STAYTIME - (endTime - startTime));
                            }

                            toHomePage();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void newVersionTip(){
        new AlertDialog.Builder(SplashActivity.this)
                .setIcon(R.drawable.ic_launcher)
                .setTitle("版本更新")
                .setMessage("有新版本更新")
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 下载新的apk 并安装

                    }
                })
                .setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // 进入主界面
                        toHomePage();
                    }
                })
                .show();
    }

    /**
     * 进入主界面
     */
    private void toHomePage(){
        // 没有新版本，直接进入主界面
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);

        // 销毁掉启动页，防止用户点返回按钮，返回启动页
        finish();
    }

    /**
     * 获取版本名
     * @return 返回版本名
     */
    private String getVersionName(){
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取当前的版本号
     * @return
     */
    private int getVersionCode(){
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
