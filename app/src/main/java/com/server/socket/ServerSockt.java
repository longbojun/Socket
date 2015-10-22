package com.server.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerSockt {
	public static List<Socket> lists = Collections.synchronizedList(new ArrayList<Socket>());
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8285);
			while(true){
				Socket s =ss.accept();
				lists.add(s);
				new Thread(new ServerThread(s)).start();
			}
			
		} catch (Exception e) {
			
		}
	}
}
