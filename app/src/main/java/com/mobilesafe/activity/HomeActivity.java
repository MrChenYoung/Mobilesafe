package com.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesafe.R;
import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.Md5Util;
import com.mobilesafe.util.SpUtil;

public class HomeActivity extends AppCompatActivity {

    private String[] titleDatas;
    private int[] iconDatas;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 初始化数据
        initData();

        // 给GridView设置点击事件
        addItemClickListener();
    }

    /**
     * 设置9宫格数据
     */
    public void initData(){
        titleDatas = new String[]{
                "手机防盗",
                "通信卫士",
                "软件管理",
                "进程管理",
                "流量统计",
                "手机杀毒",
                "缓存清理",
                "高级工具",
                "设置中心"
        };

        iconDatas = new int[]{
                R.drawable.home_safe,
                R.drawable.home_callmsgsafe,
                R.drawable.home_apps,
                R.drawable.home_taskmanager,
                R.drawable.home_netmanager,
                R.drawable.home_trojan,
                R.drawable.home_sysoptimize,
                R.drawable.home_tools,
                R.drawable.home_settings

        };

        // 设置adapter
        gridView = findViewById(R.id.gv_home);
        gridView.setAdapter(new MyAdapter());
    }

    /**
     * 给GridView的item设置点击事件
     */
    private void addItemClickListener(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        // 手机卫士密码设置
                        String psd = SpUtil.getString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PSD_KEY,"");
                        if (!TextUtils.isEmpty(psd)){
                            // 已经设置过密码，弹出确认密码对话框
                            showConfirmDialog();
                        }else {
                            // 没有设置过密码，弹出设置密码对话框
                            showSetPsdDialog();
                        }
                        break;
                    case 8:
                        // 进入设置中心
                        Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     * 弹出设置密码对话框
     */
    private void showSetPsdDialog(){
        View view = View.inflate(this,R.layout.set_psd_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        // 设置密码逻辑
        final EditText  ed_setPsd = view.findViewById(R.id.ed_setPsd);
        final EditText ed_confirmPsd = view.findViewById(R.id.ed_confirmPsd);

        // 确认和取消按钮处理
        view.findViewById(R.id.bt_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setPsd = ed_setPsd.getText().toString();
                String confirmPsd = ed_confirmPsd.getText().toString();

                // 确认密码
                if (!TextUtils.isEmpty(setPsd) && !TextUtils.isEmpty(confirmPsd)){
                    if (setPsd.equals(confirmPsd)){
                        // 设置密码和确认密码一样
                        Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
                        startActivity(intent);

                        dialog.dismiss();

                        // 存储密码
                        String psd = Md5Util.md5Encode(setPsd);
                        SpUtil.putString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PSD_KEY,psd);
                    }else {
                        // 设置密码和确认密码不一致
                        Toast.makeText(getApplicationContext(),"确认密码错误",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    // 设置密码或者确认密码为空
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消
                dialog.dismiss();
            }
        });
    }

    /**
     * 弹出确认密码对话框
     */
    private void showConfirmDialog(){
        View view = View.inflate(this,R.layout.confirm_psd_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        // 设置密码逻辑
        final EditText ed_confirmPsd = view.findViewById(R.id.ed_confirmPsd);

        // 确认和取消按钮处理
        view.findViewById(R.id.bt_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmPsd = Md5Util.md5Encode(ed_confirmPsd.getText().toString());
                String setPsd = SpUtil.getString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PSD_KEY,"");

                // 确认密码
                if (!TextUtils.isEmpty(setPsd) && !TextUtils.isEmpty(confirmPsd)){
                    if (setPsd.equals(confirmPsd)){
                        // 输入的密码和存储的密码一样
                        Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
                        startActivity(intent);

                        dialog.dismiss();
                    }else {
                        // 输入的密码和存储的密码不一致
                        Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    // 设置密码或者确认密码为空
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消
                dialog.dismiss();
            }
        });
    }

    /**
     * 自定义适配器
     */
    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return titleDatas.length;
        }

        @Override
        public Object getItem(int position) {
            return titleDatas[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout view;
            if (convertView == null){
                view = (LinearLayout) View.inflate(getApplicationContext(),R.layout.gridview_item,null);
            }else {
                view = (LinearLayout) convertView;
            }

            ImageView iv_icon = view.findViewById(R.id.iv_icon);
            TextView tv_title = view.findViewById(R.id.tv_title);
            iv_icon.setBackgroundResource(iconDatas[position]);
            tv_title.setText(titleDatas[position]);
            return view;
        }
    }
}
