package com.mobilesafe.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesafe.R;
import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.SpUtil;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;

    // 服务器上的版本号(这里写死模拟)
    private int mVersionCodeServer = 1;
    // 在启动页总共停留的时间
    private final int STAYTIME = 1000;
    // 消息处理
    private MyHandler handler = new MyHandler();
    private final int UPDATENEWVERSION = 100;
    private final int ENTERHOME = 200;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 显示版本名称
        tv_version = findViewById(R.id.tv_version);
        tv_version.setText("版本名称" + getVersionName());
        progressBar = findViewById(R.id.progress);

        // 检测版本
        if (SpUtil.getBoolean(this,ConstantValue.OPEN_UPDATE_KEY,false)){
            // 开启了自动更新，版本对比
            progressBar.setVisibility(View.VISIBLE);
            detectorVersion();
        }else{
            // 没有开启，直接进入主界面
            progressBar.setVisibility(View.GONE);
            handler.sendEmptyMessageAtTime(ENTERHOME,STAYTIME);
        }
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
                            Message msg = Message.obtain();
                            msg.what = UPDATENEWVERSION;
                            handler.sendMessage(msg);
                        }else {
                            long endTime = System.currentTimeMillis();
                            if (endTime - startTime >= STAYTIME){
                                // 直接进入主界面
                            }else {
                                // 计算剩余停留时间
                                SystemClock.sleep(STAYTIME - (endTime - startTime));
                            }

                            Message msg = Message.obtain();
                            msg.what = ENTERHOME;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 有新版本更新提示框
     */
    private void newVersionTip(){
        new AlertDialog.Builder(SplashActivity.this)
                .setIcon(R.drawable.ic_launcher)
                .setTitle("版本更新")
                .setMessage("有新版本更新")
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 下载新的apk 并安装
                        Toast.makeText(getApplicationContext(),"模拟下载",Toast.LENGTH_SHORT).show();
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
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // 点击返回按钮，取消掉弹出框，进入主界面
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

        // 启动页淡出动画
        AlphaAnimation animation = new AlphaAnimation(1,0);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        RelativeLayout rl_root = findViewById(R.id.rl_root);
        rl_root.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束，进入主界面
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);

                // 销毁掉启动页，防止用户点返回按钮，返回启动页
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    /**
     * 自定义handle
     */
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATENEWVERSION:
                    // 有新版本
                    newVersionTip();
                    break;
                case ENTERHOME:
                    // 没有新版本
                    toHomePage();
                    break;
            }
        }
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
