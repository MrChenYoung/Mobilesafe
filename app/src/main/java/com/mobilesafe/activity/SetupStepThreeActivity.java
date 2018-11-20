package com.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesafe.R;
import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.SpUtil;

public class SetupStepThreeActivity extends AppCompatActivity {

    private EditText ed_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_step_three);

        // 初始化界面
        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        // 电话号码输入框
        ed_phone = findViewById(R.id.ed_safe_number);

        // 如果已经存储过电话号码，显示在输入框里
        String phone = SpUtil.getString(this,ConstantValue.MOBILE_PHONE_NUMBER_KEY,"");
        if (!TextUtils.isEmpty(phone)){
            ed_phone.setText(phone);
        }
    }

    /**
     * 选择联系人
     * @param view
     */
    public void selectContact(View view){
        // 跳转到显示联系人列表的界面
        Intent intent = new Intent(this,ContactsActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 选择完联系人
        if (data != null){
            String phone = data.getStringExtra("phone");
            ed_phone.setText(phone);
        }
    }

    // 上一页
    public void preStep(View view){
        Intent intent = new Intent(this,SetupStepTwoActivity.class);
        startActivity(intent);

        finish();

        overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
    }

    // 下一页
    public void nextStep(View view){
        // 判断是否输入了联系人
        if (!TextUtils.isEmpty(ed_phone.getText().toString())){
            // 保存联系人
            SpUtil.putString(this,ConstantValue.MOBILE_PHONE_NUMBER_KEY,ed_phone.getText().toString().trim());

            // 选择了联系人
            Intent intent = new Intent(this,SetupStepFourActivity.class);
            startActivity(intent);

            finish();

            overridePendingTransition(R.anim.next_in,R.anim.next_out);
        }else {
            // 没有选择联系人
            Toast.makeText(this,"请输入联系人",Toast.LENGTH_SHORT).show();
        }
    }
}
