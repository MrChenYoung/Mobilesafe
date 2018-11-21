package com.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.mobilesafe.R;
import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.SpUtil;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // 判断是否开启了手机防盗
        boolean open_safe = SpUtil.getBoolean(context,ConstantValue.MOBILE_OPEN_SAFE_KEY,false);
        if (open_safe){

            // 开启了手机防盗，解析短信内容
            Object[] pdus = (Object[])intent.getExtras().get("pdus");
            for (Object pdu : pdus) {
                SmsMessage message = SmsMessage.createFromPdu((byte[])pdu);
                String messageBody = message.getMessageBody();

                if (messageBody.contains("#*alarm*#")){
                    // 播放报警音乐
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            }
        }
    }
}
