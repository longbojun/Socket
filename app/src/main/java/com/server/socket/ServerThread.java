package com.server.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread extends Thread {
	private Socket socket;
	private BufferedReader br;

	public ServerThread(Socket socket) throws Exception {
		super();
		this.socket = socket;
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
	}
	@Override
	public void run() {
		super.run();
		try {
			String msg;
			while((msg=br.readLine())!=null){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化日期
				//将接收到的字符串读取出来，加上日期返回给客户端
				PrintStream ps = new PrintStream(socket.getOutputStream(),true,"UTF-8");
					ps.println("服务器说还给你:"+msg+">>>>>"+df.format(new Date()));
					System.out.println(msg);
					ps.flush();
			}
		} catch (Exception e) {
			
		}
	}
}
