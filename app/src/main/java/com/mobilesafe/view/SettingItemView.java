package com.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilesafe.R;

import org.w3c.dom.Text;

public class SettingItemView extends RelativeLayout {

    // 控件
    private CheckBox checkBox;
    private TextView tv_title;
    private TextView tv_description;

    // 命名空间
    private final String NAMESPACE = "http://schemas.android.com/apk/settingItem";

    private String desoff;
    private String deson;

    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 最后一个参数表示把inflate生成的view添加到this上
        inflate(context, R.layout.setting_item_view,this);

        // 获取指定控件
        checkBox = findViewById(R.id.cb_select);
        tv_title = findViewById(R.id.tv_title);
        tv_description = findViewById(R.id.tv_description);

        // 初始化属性
        initAttrs(attrs);
    }

    /**
     * 初始化属性
     * @param attributeSet 属性列表
     */
    private void initAttrs(AttributeSet attributeSet){
        String title = attributeSet.getAttributeValue(NAMESPACE,"title");
        desoff = attributeSet.getAttributeValue(NAMESPACE,"desoff");
        deson = attributeSet.getAttributeValue(NAMESPACE,"deson");
        tv_title.setText(title);
    }

    /**
     * 是否处于勾选状态
     * @return 被勾选返回true 没有被勾选返回false
     */
    public boolean isChecked(){
        return checkBox.isChecked();
    }

    /**
     * 设置条目的选中状态
     * @param isChecked 是否被选中
     */
    public void setChecked(boolean isChecked){
        checkBox.setChecked(isChecked);

        if (isChecked){
            tv_description.setText(deson);
        }else {
            tv_description.setText(desoff);
        }
    }

}
