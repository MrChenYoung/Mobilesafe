package com.mobilesafe.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.mobilesafe.util.ConstantValue;
import com.mobilesafe.util.SpUtil;

public class LocationService extends Service {

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public void onCreate() {
        super.onCreate();

        // 开始定位
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // 申请权限
            Toast.makeText(getApplicationContext(),"定位权限被拒，请到设置开启权限",Toast.LENGTH_SHORT).show();
        }else {
            // 定位
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lon = location.getLongitude();
                    double lat = location.getLatitude();

                    // 发送经纬度给安全号码
                    String securityNumber = SpUtil.getString(getApplicationContext(),ConstantValue.MOBILE_PHONE_NUMBER_KEY,"");
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(securityNumber,null,"longtitude=" + lon + "," + "latitude=" + lat,null,null);

                    locationManager.removeUpdates(locationListener);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(getApplicationContext(),"定位权限被拒，请到设置开启权限",Toast.LENGTH_SHORT).show();
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0, locationListener);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
