package com.SemeProJ.CardChkGame.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerService {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		try {			
			ServerSocket serverSocket = new ServerSocket(34102); // 포트번호 6969에 bind(결합)
			Vector<Socket> vec = new Vector<Socket>();
			int i=0;
			while (true) {
				
				System.out.println("접속 대기중...");
				Socket socket = serverSocket.accept(); // 연결 댇기중
				
				// 연결된 클라이언트 소켓 벡터에 담기
				vec.add(socket);
				
				// 스레드 구동
				new ServerMsgRXtoTX(socket, vec).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}