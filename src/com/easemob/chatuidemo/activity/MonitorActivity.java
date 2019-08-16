package com.easemob.chatuidemo.activity;



import com.caralarm.service.VibrateService;
import com.easemob.CARSECURITY.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;



public class MonitorActivity extends Activity{
	private float X,Y,Z,V=0;
	private String userId;
	private boolean isComingCall;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		MyReceiver receiver=new MyReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction("com.caralarm.service.VibrateService");
		MonitorActivity.this.registerReceiver(receiver,filter);
		userId = getIntent().getStringExtra("userId");
		isComingCall = getIntent().getBooleanExtra("isComingCall", false); 
		
		startService(new Intent (MonitorActivity.this,VibrateService.class));
	}
	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			      
			      Bundle bundle=intent.getExtras();
			      X=bundle.getFloat("X");
			      Y=bundle.getFloat("Y");
			      Z=bundle.getFloat("Z");
			      V =(float) Math.sqrt(X * X + Y * Y + Z * Z);
			      
				  if(V>10.5){
					  	stopService(new Intent (MonitorActivity.this,VibrateService.class));
						startActivity(new Intent(MonitorActivity.this,VideoCallActivity.class).putExtra("userId", userId).putExtra("isComingCall", isComingCall));
					}
		
		}	
		}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



}
