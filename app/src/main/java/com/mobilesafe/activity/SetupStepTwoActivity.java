package com.mobilesafe.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.mobilesafe.R;
import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.PermissonUtil;
import com.mobilesafe.util.SpUtil;
import com.mobilesafe.view.SettingItemView;

import pub.devrel.easypermissions.AfterPermissionGranted;

public class SetupStepTwoActivity extends PermissionBaseActivity {

    private SettingItemView bindSimView;

    private final int READ_PHONT_STATE_CODE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_step_two);


        // 设置绑定sim卡
        bindSimView = findViewById(R.id.view_bindsim);
        initBindSim();
    }

    /**
     * 设置绑定sim卡
     */
    private void initBindSim(){
        // 设置初始绑定状态
        String sim_number = SpUtil.getString(getApplicationContext(),ConstantValue.MOBILE_SIM_BIND_NUMBER_KEY,"");
        if (!TextUtils.isEmpty(sim_number)){
            // 已经绑定过
            bindSimView.setChecked(true);
        }else {
            // 没有绑定过sim卡
            bindSimView.setChecked(false);
        }

        bindSimView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前绑定的sim状态
                boolean binded = bindSimView.isChecked();
                // 设置绑定状态
                bindSimView.setChecked(!binded);

                if (!binded){
                    bindSimSerialNumber();
                }else {
                    // 移除绑定的sim卡序列号
                    SpUtil.remove(getApplicationContext(),ConstantValue.MOBILE_SIM_BIND_NUMBER_KEY);
                }
            }
        });
    }

    /**
     * 读取手机sim卡序列号
     * @return
     */
    @AfterPermissionGranted(READ_PHONT_STATE_CODE)
    private void bindSimSerialNumber(){
        // 权限申请
        String simNumber = null;
        if (PermissonUtil.hasPermissions(getApplicationContext(),this,READ_PHONT_STATE_CODE,"需要读取电话卡的权限",new String[]{Manifest.permission.READ_PHONE_STATE})){
            // 已经有权限
            try {
                // 获取手机sim卡序列号
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                simNumber = telephonyManager.getSimSerialNumber();
            } catch (SecurityException e) {
                e.printStackTrace();
            } finally {
                if (TextUtils.isEmpty(simNumber)){
                    // 获取不到sim序列号或者用户拒绝了授权
                    Toast.makeText(getApplicationContext(),"获取sim卡序列号失败",Toast.LENGTH_SHORT).show();
                    bindSimView.setChecked(false);
                }else {
                    bindSimView.setChecked(true);

                    // 保存sim卡序列号
                    SpUtil.putString(getApplicationContext(),ConstantValue.MOBILE_SIM_BIND_NUMBER_KEY,simNumber);
                }
            }
        }else {
            // 没有权限
            return;
        }
    }

    // 上一页
    public void preStep(View view){
        Intent intent = new Intent(this,SetupStepOneActivity.class);
        startActivity(intent);

        finish();

        overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
    }

    // 下一页
    public void nextStep(View view){
        if (bindSimView.isChecked()){
            // 绑定过
            Intent intent = new Intent(this,SetupStepThreeActivity.class);
            startActivity(intent);

            finish();

            overridePendingTransition(R.anim.next_in,R.anim.next_out);
        }else {
            Toast.makeText(this,"请绑定sim卡",Toast.LENGTH_SHORT).show();
        }

    }
}
