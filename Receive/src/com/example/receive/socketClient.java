package com.example.receive;
/*author CT*/
import java.io.BufferedOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.R.integer;
import android.R.string;

public class socketClient extends Thread {

	private String ip = null;
	private int port ;
	private String str ;
	
	public socketClient(String ip , int port){
		this.ip = ip;
		this.port = port;
	}
	
	public void run(){
			System.out.println("thread working...");
		try {
			Socket client = new Socket();
			InetSocketAddress isa = new InetSocketAddress(this.ip, this.port);
			try {
				System.out.println("IP"+this.ip);
				System.out.println("Port"+this.port);
				client.connect(isa, 1000);
				BufferedOutputStream out = new BufferedOutputStream(client
						.getOutputStream());
				//送出字串!!!
				out.write(str.getBytes());
				out.flush();
				out.close();
				client.close();
				client = null;
				
					Thread.sleep(10);
					socketClient.interrupted();
			} catch (InterruptedException e) {
				// TODO: handle exception
				System.out.println("Client Socket連線有問題 !");
				System.out.println("Thread 被終止");
			    System.out.println("Exception :" + e.toString());
			}
		} catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
	}

	public void str(String s){
		this.str = s ;
	}
}
