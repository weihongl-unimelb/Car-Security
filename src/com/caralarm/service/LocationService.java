package com.caralarm.service;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.easemob.CARSECURITY.R;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class LocationService extends Service{
	
	public static LocationService instance = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	public LocationClient mLocationClient = null;

	
	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
/*	public class BaiduSDKReceiver extends BroadcastReceiver{
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			String st1 = getResources().getString(R.string.Network_error);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				
				String st2 = getResources().getString(R.string.please_check);
				Toast.makeText(instance, st2, Toast.LENGTH_SHORT).show();
			} else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(instance, st1, Toast.LENGTH_SHORT).show();
			}
		}
			
		}
		
	private BaiduSDKReceiver mBaiduReceiver;*/

	@Override
	public void onCreate() {
		super.onCreate(); 
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
	    mLocationClient.registerLocationListener( myListener );    //注册监听函数
	    //LocationClientOption option = new LocationClientOption();
	    //option.setLocationMode(LocationMode.Hight_Accuracy);
	    mLocationClient.start();
	    LocationClientOption mOption = new LocationClientOption();
	    mOption = new LocationClientOption();
		//mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
		mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
	   // mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
	   // mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
	   // mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
	    mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
	   // mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死   
	   // mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
	   // mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
	   // mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
	    mLocationClient.setLocOption(mOption); 
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);

	}



	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public class MyLocationListenner implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			if(location!=null){
				double latitude = location.getLatitude();
				double longitude = location.getLongitude();
				Intent intent=new Intent();
			    intent.putExtra("latitude", latitude);
			    intent.putExtra("longitude", longitude);
			    intent.setAction("com.caralarm.service.LocationService");
			    sendBroadcast(intent);
			}
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		} 
	}

	@Override
	public void onDestroy() {
		mLocationClient.stop();
		super.onDestroy();
	}
	
}
