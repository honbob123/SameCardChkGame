package com.SemeProJ.CardChkGame.Client;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ClientService {
	
	public static final String SERVER_IP_ADDR = "127.0.0.1";
	public static final int SERVER_PORT = 6969;
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
				//ClientMsgReceive cmr2 = new ClientMsgReceive(socket, clientUI);				
				cmr1.start();
				//cmr2.start();
				// 서버 메세지 수신 시작
				// 게임 카운트중에도 채팅 가능하게 하기 위해 쓰레드 2개 실행
				
				
				// 카드게임 정보 수신!
				
				// 카드 게임 정보 ClientMainUI에 전달!
			}
		} catch (IOException e) {
			//서버와 연결 에러났을 때 에러창을  뛰운다
			JOptionPane.showMessageDialog(null, "서버와 연결 에러!","에러창", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
		}
	}

}