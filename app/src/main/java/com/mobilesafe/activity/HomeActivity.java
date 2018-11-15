package com.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilesafe.R;

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
