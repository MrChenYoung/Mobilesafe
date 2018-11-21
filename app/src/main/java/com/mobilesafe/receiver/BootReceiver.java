package com.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.SpUtil;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 手机开机监听
        simDetector(context);
    }

    /**
     * 检测sim卡是否变更
     */
    private void simDetector(Context context){
        try {
            String simNumber = SpUtil.getString(context,ConstantValue.MOBILE_SIM_BIND_NUMBER_KEY,"");
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String newSimNumber = telephonyManager.getSimSerialNumber() + "11";
            if (!simNumber.equals(newSimNumber)){
                // 发送短信给安全号码
                SmsManager smsManager = SmsManager.getDefault();
                String number = SpUtil.getString(context,ConstantValue.MOBILE_PHONE_NUMBER_KEY,"");
                smsManager.sendTextMessage(number,number,"手机sim卡发生改变",null,null);
                Toast.makeText(context,"发送短信成功",Toast.LENGTH_LONG).show();
            }
        } catch (SecurityException e) {
            Log.e("==============",e.toString());
            e.printStackTrace();
            Toast.makeText(context,"发送短信失败",Toast.LENGTH_LONG).show();
        }
    }
}
