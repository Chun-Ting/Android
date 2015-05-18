package com.example.receive;
/*author CT*/
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.security.auth.PrivateCredentialPermission;

import android.R.string;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private TextView tMessage = null ;
	private TextView tReceive = null;
	private EditText eip , ePort , eKeyword ;
	private Button bSend , bStartSrecive ;
	public static String localIP ;	//get IP address for IPv4(localIP)
	private String[] request_test_array = new String[4];
	private String request_test;
	private long StatrTime ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		eip = (EditText)findViewById(R.id.eip);
		ePort = (EditText)findViewById(R.id.ePort);
		eKeyword = (EditText)findViewById(R.id.eKeyword);
//		tMessage = (TextView)findViewById(R.id.tMessage);
		tReceive = (TextView)findViewById(R.id.tReceive);
		bSend = (Button)findViewById(R.id.bSend);
		bStartSrecive = (Button)findViewById(R.id.bStartSrecive);
	    localIP = getLocalIpAddress();
		
		
//		bSend	function 
	bSend.setOnClickListener(new OnClickListener(){

		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(eip.getText().toString().equals("")||ePort.getText().toString().equals("")){
				Toast.makeText(v.getContext(), "IP or Port 不可為null", Toast.LENGTH_SHORT).show();
			}
			else{
//			tMessage.setText("send IP : "+eip.getText().toString()+"\n"+"send Port : "+ePort.getText().toString()+"\n"
//					+"Keyword : "+eKeyword.getText().toString()+"\n"+"localIP : "+localIP);
			String showsString = "send IP : "+eip.getText().toString()+"\n"+"send Port : "+ePort.getText().toString()+"\n"
					+"Keyword : "+eKeyword.getText().toString()+"\n"+"localIP : "+localIP;
			String ip = eip.getText().toString();
			int port = Integer.valueOf(ePort.getText().toString()).intValue();
			socketClient sc = new socketClient(ip, port);
			StatrTime = System.currentTimeMillis();
			request_test_array[0] = String.valueOf(localIP);
			request_test_array[1] = String.valueOf(port);
			request_test_array[2] = String.valueOf(eKeyword.getText().toString());
			request_test_array[3] = String.valueOf(StatrTime);
			request_test = request_test_array[0]+","+request_test_array[1]+","
					+request_test_array[2]+","+request_test_array[3];
			Toast.makeText(v.getContext(), showsString, Toast.LENGTH_SHORT).show();
			sc.str(request_test);
			sc.start();
			}
//			tMessage.setText(request_test);
		
		}
	});

//	bStartSrecive function 
	bStartSrecive.setOnClickListener(new OnClickListener() {
		socketServer ss = new socketServer();
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(bStartSrecive.getText().equals("StartSercive"))
			{
				Toast.makeText(v.getContext(), "ON Server", Toast.LENGTH_SHORT).show();
				ss.running(false);
				ss.SetHandler(sHandler);//連結
				ss.start();
//				ss.SetHanlder(HandlerTest);
				bStartSrecive.setText("StopService");
			}
			else 
			{
				Toast.makeText(v.getContext(), "OFF Server", Toast.LENGTH_SHORT).show();
				bStartSrecive.setText("StartSercive");
				ss.running(true);
//				mHandler.removeCallbacks(ss);
			}
		}
	});
	
	}
	
//	Handler!!
	private Handler sHandler = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			String MsgString = (String)msg.obj;
			String[] arr = MsgString.split(",");
			System.out.println(arr[2]);
			tReceive.setText(arr[2]);
		}
	};

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
	
	//========get_local_ip_IP-V4=======================
    public static String getLocalIpAddress() {  
        try {  
            for (Enumeration<NetworkInterface> en = NetworkInterface  
                            .getNetworkInterfaces(); en.hasMoreElements();) {  
                        NetworkInterface intf = en.nextElement();  
                       for (Enumeration<InetAddress> enumIpAddr = intf  
                                .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                            InetAddress inetAddress = enumIpAddr.nextElement();  
                            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {  
                            return inetAddress.getHostAddress().toString();  
                            }  
                       }  
                    }  
                } catch (SocketException ex) {  
                    
                }  
             return null;  
}   
}
