package com.example.androidmonitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView batteryLevelTxt;
	private String Battery_test;
//	 Thread t1 = new testThread();
	testThread t1 ;
	private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
		@Override
		// public void onReceive(Context context,Intent intent){
		// int lv=intent.getIntExtra("level",0);
		// batteryLevelTxt.setText(String.valueOf(lv)+"%");
		// }
		// };
		public void onReceive(Context arg0, Intent info) {
			// TODO Auto-generated method stubtestbench_Main tMain = new
			// testbench_Main();
			if (Intent.ACTION_BATTERY_CHANGED.equals(info.getAction())) {
				int level = info.getIntExtra("level", 0);
				int scale = info.getIntExtra("scale", 0);
				int status = info.getIntExtra("status", 0);
				int health = info.getIntExtra("health", 0);
				int temperature = info.getIntExtra("temperature", 0);
				String technology = info.getStringExtra("technology");

				StringBuilder information = new StringBuilder();
				information.append("Level:" + Integer.toString(level) + "\n");
				information.append("Scale:" + Integer.toString(scale) + "\n");
				Battery_test = Integer.toString(scale);
				System.out.println("NOW 電量是 "+Battery_test);
				information
						.append("Battery:" + Integer.toString(level) + "%\n");

				switch (status) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					information.append("Status:" + "CHARGING" + "\n");
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					information.append("Status:" + "DISCHARGING" + "\n");
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					information.append("Status:" + "FULL" + "\n");
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					information.append("Status:" + "NOT_CHARGING" + "\n");
					break;
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					information.append("Status:" + "UNKNOWN" + "\n");
					break;
				}
				switch (health) {
				case BatteryManager.BATTERY_HEALTH_DEAD:
					information.append("Health:" + "DEAD" + "\n");
					break;
				case BatteryManager.BATTERY_HEALTH_GOOD:
					information.append("Health:" + "GOOD" + "\n");
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					information.append("Health:" + "OVER_VOLTAGE" + "\n");
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					information.append("Health:" + "OVERHEAT" + "\n");
					break;
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					information.append("Health:" + "UNKNOWN" + "\n");
					break;
				case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
					information
							.append("Health:" + "UNSPECIFIED_FAILURE" + "\n");
					break;
				}
				information.append("Temperature:"
						+ Double.toString(temperature * 0.1) + "\n");
				information.append("Technology:" + technology + "\n");

				// CPU抓取

				// -----
				batteryLevelTxt.setTextSize(25);
				batteryLevelTxt.setText(information);
			}
		}
	};

	public String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" };
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return cpuInfo;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		batteryLevelTxt = (TextView) findViewById(R.id.LevelTxt);
		this.registerReceiver(batteryInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		System.out.println("NOW 電量111 "+Battery_test);
		t1 = new testThread();
		t1.start();
		t1 = null;
		System.gc();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class testThread extends Thread{
		 public void run() { // implements Runnable run()
		        System.out.println("Here is the starting point of Thread.");
		        int i = 0;
		        while(true){
		        i = i+1 ;
		    	System.out.println( i +" IN thread NOW 電量是 "+Battery_test);
		    	try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        }
		        }
		 }
	}


