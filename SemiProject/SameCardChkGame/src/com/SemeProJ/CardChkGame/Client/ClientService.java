package com.SemeProJ.CardChkGame.Client;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ClientService {
	
	public static final String SERVER_IP_ADDR = "127.0.0.1"; //"192.168.20.23";//"192.168.20.24";
	public static final int SERVER_PORT = 6968;
	public static final long time = System.currentTimeMillis();
	
	public static void main(String[] args) {
		Socket socket;
		ClientMainUI clientUI;
		try {
			socket = new Socket(SERVER_IP_ADDR, SERVER_PORT);
			if(socket != null) {
				System.out.println("서버 주소 : " + SERVER_IP_ADDR);
				System.out.println("서버 포트 : " + SERVER_PORT);
				System.out.println("서버 접속");
				
				clientUI = new ClientMainUI(socket); // 클라이언트 UI 호출
				
				ClientMsgReceive cmr1 = new ClientMsgReceive(socket, clientUI);			
				cmr1.start();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와 연결 에러!","에러창", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
		}
	}

}