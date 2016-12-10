package com.SemeProJ.CardChkGame.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientService {
	
	public static final String SERVER_IP_ADDR = "127.0.0.1";
	public static final int SERVER_PORT = 34102;
	
	public static void main(String[] args) {
		
		Socket socket;
		ClientMainUI clientUI;
		
		try {
			
			socket = new Socket(SERVER_IP_ADDR, SERVER_PORT);
			
			System.out.println("서버 주소 : " + SERVER_IP_ADDR);
			System.out.println("서버 포트 : " + SERVER_PORT);
			System.out.println("서버 접속");
			
			clientUI = new ClientMainUI(socket); // 클라이언트 UI 호출
			
			new ClientMsgReceive(socket, clientUI).start(); // 서버 메세지 수신 시작
			
			// 카드게임 정보 수신!
			
			// 카드 게임 정보 ClientMainUI에 전달!
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}