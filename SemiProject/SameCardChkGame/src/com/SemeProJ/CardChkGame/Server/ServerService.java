package com.SemeProJ.CardChkGame.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ServerService {
	private ServerSocket server_socket = null;
	private Socket socket = null;
	private Vector<Socket> socket_vec;
	private Vector<String> Info_vec;
	
	public ServerService() { //생성자
		 socket_vec = new Vector<Socket>();
		 Info_vec = new Vector<String>();
		Server_Start(); //서버 시작 메소드
	}
	
	private void Server_Start() { //서버 시작 메소드
		try {
			server_socket = new ServerSocket(6969);
		} catch (IOException e) {
			System.out.println("이미 포트번호를 쓰고 있습니다");
		}
		if(server_socket != null) { //정상적으로 포트가 열렸을 경우
			Connection(); //클라이언트와 연결 메소드
		}
	}
	
	private void Connection() { //클라이언트와 연결 메소드
		try {
			while(true){
				System.out.println("접속 대기중...");
				socket = server_socket.accept(); //사용자 무한 대기
				socket_vec.add(socket);          //연결된 클라이언트르 순서대로 소켓 벡터에 저장
				System.out.println(socket.getInetAddress() + " : " + socket.getPort()+ "에서 접속하였습니다!");
				new ServerMsgRxtoTX(socket, socket_vec, Info_vec).start(); //서버에서 받는 스레드 실행
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "accept에러발생!","에러창", JOptionPane.ERROR_MESSAGE);
			System.out.println("클라이언트와 연결 에러");
		} finally {//실행안됨 일단 적어논 것
			if(server_socket != null) {
				try {
					server_socket.close();
				} catch (IOException e) {
					System.out.println("소켓을 닫을 때 에러 발생");
				}
			}
		}
	}
	
	public static void main(String[] args) { //메인 메소드(스레드)
		new ServerService(); //게임서버 가동!
	}
}