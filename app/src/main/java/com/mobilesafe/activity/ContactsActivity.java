package com.mobilesafe.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilesafe.R;
import com.mobilesafe.util.PermissonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;

public class ContactsActivity extends PermissionBaseActivity {

    // 联系人列表
    private ListView listView;
    // 联系人数据列表
    private List<HashMap<String,String>> contactsList = new ArrayList<>();
    // 读取联系人列表的权限
    private final int READ_CONTACTS_CODE = 500;
    // 刷新列表的handler
    private Handler myHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            contactsAdapter.notifyDataSetChanged();
        }
    };
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // 初始化界面
        initUI();

        // 获取所有的手机联系人
        getContacts();
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        // 获取并设置列表
        listView = findViewById(R.id.lv);
        contactsAdapter = new ContactsAdapter();
        listView.setAdapter(contactsAdapter);

        // 点击列表每一项选择联系人
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = contactsList.get(position);
                Intent intent = getIntent();
                intent.putExtra("phone",map.get("phone"));
                setResult(0,intent);

                finish();
            }
        });
    }

    /**
     * 获取所有的手机联系人
     */
    @AfterPermissionGranted(READ_CONTACTS_CODE)
    private void getContacts(){
        // 申请权限
        if (PermissonUtil.hasPermissions(this,this,READ_CONTACTS_CODE,"需要读取手机通讯录",new String[]{Manifest.permission.READ_CONTACTS})){
            // 有权限
            new Thread(){
                @Override
                public void run() {
                    contactsList.clear();

                    // 获取内容解析者
                    ContentResolver resolver = getContentResolver();
                    // 查询所有的联系人id
                    Cursor cursor = resolver.query(
                            Uri.parse("content://com.android.contacts/raw_contacts"),
                            new String[]{"contact_id"},null,null,null);
                    while (cursor.moveToNext()){
                        String contactId = cursor.getString(0);

                        HashMap<String,String> contactMap = new HashMap<>();
                        // 根据联系人id查询联系人姓名和电话号码
                        Cursor cursor1 = resolver.query(
                                Uri.parse("content://com.android.contacts/data"),
                                new String[]{"data1","mimetype"},"contact_id=?",new String[]{contactId},null);
                        while (cursor1.moveToNext()){
                            String data1 = cursor1.getString(0);
                            String mimetype = cursor1.getString(1);

                            if (mimetype.equals("vnd.android.cursor.item/name")){
                                // 姓名
                                contactMap.put("name",data1);
                            }else if (mimetype.equals("vnd.android.cursor.item/phone_v2")){
                                // 手机号码
                                contactMap.put("phone",data1);
                            }
                        }

                        contactsList.add(contactMap);
                    }

                    // 刷新列表
                    myHandle.sendEmptyMessage(0);
                }
            }.start();
        }
    }

    /**
     * 联系人数据适配器
     */
    private class ContactsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return contactsList.size();
        }

        @Override
        public Object getItem(int position) {
            return contactsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null){
                view = View.inflate(getApplicationContext(),R.layout.contacts_item,null);
            }else {
                view = convertView;
            }

            HashMap<String,String> map = contactsList.get(position);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_phone = view.findViewById(R.id.tv_phone);
            tv_name.setText(map.get("name"));
            tv_phone.setText(map.get("phone"));
            return view;
        }
    }
}
