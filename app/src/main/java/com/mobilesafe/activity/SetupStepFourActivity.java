package com.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mobilesafe.R;
import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.SpUtil;

public class SetupStepFourActivity extends AppCompatActivity {

    private CheckBox cb_open_safe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_step_four);

        // 初始化界面
        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        // 获取防盗开关显示控件
        cb_open_safe = findViewById(R.id.cb_openSafe);
        boolean openSafe = SpUtil.getBoolean(this,ConstantValue.MOBILE_OPEN_SAFE_KEY,false);
        cb_open_safe.setChecked(openSafe);
        if (openSafe){
            cb_open_safe.setText("安全防盗已开启");
        }else {
            cb_open_safe.setText("您还没有开启安全防盗");
        }

        // checkBox点击事件
        cb_open_safe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cb_open_safe.setText("安全防盗已开启");
                }else {
                    cb_open_safe.setText("您还没有开启安全防盗");
                }

                // 保存状态
                SpUtil.putBoolean(getApplicationContext(),ConstantValue.MOBILE_OPEN_SAFE_KEY,isChecked);
            }
        });
    }

    // 上一页
    public void preStep(View view){
        Intent intent = new Intent(this,SetupStepThreeActivity.class);
        startActivity(intent);

        finish();

        overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
    }

    // 下一页
    public void nextStep(View view){
        // 判断是否开启了安全防盗
        if (cb_open_safe.isChecked()){
            Intent intent = new Intent(this,SetupOverActivity.class);
            startActivity(intent);

            finish();

            // 设置完成状态保存
            SpUtil.putBoolean(this,ConstantValue.MOBILE_SAFE_SETUP_OVER_KEY,true);

            overridePendingTransition(R.anim.next_in,R.anim.next_out);
        }else {
            Toast.makeText(this,"请开启安全防盗",Toast.LENGTH_SHORT).show();
        }
    }
}
