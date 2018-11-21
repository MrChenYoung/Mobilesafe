package com.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

            // 初始化界面
            initUI();
        }else {
            // 没有设置完成，进入设置第一步
            Intent intent = new Intent(this,SetupStepOneActivity.class);
            startActivity(intent);

            finish();
        }
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        TextView tv_safe_number = findViewById(R.id.tv_safe_number);
        String number = SpUtil.getString(this,ConstantValue.MOBILE_PHONE_NUMBER_KEY,"");
        tv_safe_number.setText(number);

        // 重新进入设置向导
        TextView tv_reset_setup = findViewById(R.id.tv_reset_setup);
        tv_reset_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SetupStepOneActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }
}
