package com.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobilesafe.R;
import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.SpUtil;

public class SetupOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean setupOver = SpUtil.getBoolean(this,ConstantValue.MOBILE_SAFE_SETUP_OVER_KEY,false);
        if (setupOver){
            // 设置完成进入设置完成界面
            setContentView(R.layout.activity_setup_over);
        }else {
            // 没有设置完成，进入设置第一步
            Intent intent = new Intent(this,SetupStepOneActivity.class);
            startActivity(intent);

            finish();
        }
    }
}
