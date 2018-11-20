package com.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mobilesafe.R;
import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.SpUtil;
import com.mobilesafe.view.SettingItemView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 设置自动更新项目
        initUpdate();
    }

    /**
     * 设置自动更新项目
     */
    private void initUpdate(){
        final SettingItemView view = findViewById(R.id.view_update);
        // 设置更新状态
        boolean open_update = SpUtil.getBoolean(this,ConstantValue.MOBILE_OPEN_UPDATE_KEY,false);
        view.setChecked(open_update);

        // 设置条目点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cheched = view.isChecked();
                view.setChecked(!cheched);

                // 保存设置的更新版本状态
                SpUtil.putBoolean(getApplicationContext(),ConstantValue.MOBILE_OPEN_UPDATE_KEY,!cheched);
            }
        });
    }
}
