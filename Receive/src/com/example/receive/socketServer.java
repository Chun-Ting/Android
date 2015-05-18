package com.example.receive;
/*author CT*/
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;


public class socketServer extends Thread {

	private boolean OutServer = true;
	private ServerSocket server;
	private final int ServerPort = ??;
	
	private Handler mHandler = null;
	public socketServer(){
		try {
			server = new ServerSocket(ServerPort);
			
		} catch (java.io.IOException e) {
			// TODO: handle exception
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
		}
	}
	public void run(){
		Socket socket ;
		java.io.BufferedInputStream in;
		
		System.out.println("伺服器啟動");
		while (!OutServer) {
			try {
				socket = null;
//				測試看看有沒有問題
//				 Message message;
//	                String hh ="h2555689877";
//	                String obj = hh.toString();
//	                message = this.mHandler.obtainMessage(1,obj);
//	                this.mHandler.sendMessage(message);
//	                Thread.sleep(1000);
//	                測試END
	            try {
	                synchronized (server) {
	                    socket = server.accept();
	                }
	                System.out.println("取得連線 : InetAddress = "
	                        + socket.getInetAddress());
	                // TimeOut時間
	                socket.setSoTimeout(15000);
	 
	                in = new java.io.BufferedInputStream(socket.getInputStream());
	                byte[] b = new byte[1024];
	                String data = "";
	                int length;
	                while ((length = in.read(b)) > 0)// <=0的話就是結束了
	                {
	                    data += new String(b, 0, length);
	                }
	 
	                System.out.println("我取得的值:" + data);
//	                mainact.sbuf(data);//把data放入mainActivity
	                in.close();
	                in = null;
	                socket.close();
//	                Handler & Message 處
	                Message message;
	                String obj = data.toString();
	                message = this.mHandler.obtainMessage(1,obj);
	                this.mHandler.sendMessage(message);
	 
	            } catch (java.io.IOException e) {
	                System.out.println("Socket連線有問題 !");
	                System.out.println("IOException :" + e.toString());
	            }
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
	
	public void SetHandler(Handler mmHandler){
		this.mHandler = mmHandler;
	}
	public void running(boolean isrunning){
		 this.OutServer = isrunning;
	 }

}
