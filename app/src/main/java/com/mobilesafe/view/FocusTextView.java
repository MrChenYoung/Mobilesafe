package com.mobilesafe.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 一直可以获取焦点的TextView
 */
public class FocusTextView extends AppCompatTextView {
    /**
     * 代码创建对象的时候调用
     * @param context 上下文
     */
    public FocusTextView(Context context) {
        super(context);
    }

    /**
     * 从xml加载的时候调用
     * @param context 上下文
     * @param attrs 属性列表
     */
    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 从xml加载的时候调用
     * @param context 上下文
     * @param attrs 属性列表
     * @param defStyleAttr 默认属性
     */
    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 是否获取焦点
     * @return
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}
