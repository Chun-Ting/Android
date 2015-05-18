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
            System.out.println("Socket�Ұʦ����D !");
            System.out.println("IOException :" + e.toString());
		}
	}
	public void run(){
		Socket socket ;
		java.io.BufferedInputStream in;
		
		System.out.println("���A���Ұ�");
		while (!OutServer) {
			try {
				socket = null;
//				���լݬݦ��S�����D
//				 Message message;
//	                String hh ="h2555689877";
//	                String obj = hh.toString();
//	                message = this.mHandler.obtainMessage(1,obj);
//	                this.mHandler.sendMessage(message);
//	                Thread.sleep(1000);
//	                ����END
	            try {
	                synchronized (server) {
	                    socket = server.accept();
	                }
	                System.out.println("���o�s�u : InetAddress = "
	                        + socket.getInetAddress());
	                // TimeOut�ɶ�
	                socket.setSoTimeout(15000);
	 
	                in = new java.io.BufferedInputStream(socket.getInputStream());
	                byte[] b = new byte[1024];
	                String data = "";
	                int length;
	                while ((length = in.read(b)) > 0)// <=0���ܴN�O�����F
	                {
	                    data += new String(b, 0, length);
	                }
	 
	                System.out.println("�ڨ��o����:" + data);
//	                mainact.sbuf(data);//��data��JmainActivity
	                in.close();
	                in = null;
	                socket.close();
//	                Handler & Message �B
	                Message message;
	                String obj = data.toString();
	                message = this.mHandler.obtainMessage(1,obj);
	                this.mHandler.sendMessage(message);
	 
	            } catch (java.io.IOException e) {
	                System.out.println("Socket�s�u�����D !");
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
