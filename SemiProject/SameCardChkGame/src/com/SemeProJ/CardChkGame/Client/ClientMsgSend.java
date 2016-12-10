package com.SemeProJ.CardChkGame.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMsgSend extends Thread { // 서버가 보내온 문자열을 전송받는 스레드
	Socket socket;
	ClientMainUI clientUI;
	String str;
	String id; // 아이디 값

	public ClientMsgSend(ClientMainUI clientUI) {
		this.clientUI = clientUI;
		this.socket = clientUI.socket;
	}

	public void getUserId() {
		id = ClientInitUI.getId();
		System.out.println("[ClientMsgSend] getUserId() - User ID :" + id);
	}
	
	public void sendMsg() { // 서버로 메시지 보내기
		// 키보드로부터 읽어오기 위한 스트림객체 생성
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printWriter = null;
		try {
			// 서버로 문자열 전송하기 위한 스트림객체 생성
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			if (clientUI.isFirst == true) {
				// 맨 처음 아이디 입력후에 한번만 발생하는 이벤트
				// 입력한 아이디를 다른 플레이어에게 뿌려줌
				InetAddress iaddr = socket.getLocalAddress();

				String ip = iaddr.getHostAddress();
				getUserId();
				//System.out.println( "IP : " + ip + " ID : " + id + " LocalAddress : " + iaddr.toString() + " LocalSocketAddress : " + socket.getLocalSocketAddress() + " SocketChanel : " + socket.getChannel());
				
				System.out.println("[ClientMsgSend] sendMsg() --------------------- START");
				System.out.println("[ClientMsgSend] sendMsg() - IP :" + ip);
				System.out.println("[ClientMsgSend] sendMsg() - User ID :" + id);
				System.out.println("[ClientMsgSend] sendMsg() - IP ADDR :" + iaddr.toString());
				System.out.println("[ClientMsgSend] sendMsg() - Server ADDR :" + socket.getLocalSocketAddress());
				System.out.println("[ClientMsgSend] sendMsg() - Socket INFO :" + socket.getChannel());
				System.out.println("[ClientMsgSend] sendMsg() --------------------- END");
				

				str = "@:" + id + " 님이 로그인했습니다!";
				// @를 앞에 붙여서 서버에서 체크 하게 만듬
				
				clientUI.txtA.append(id + "님이 로그인했습니다!" + "\n");
			} else {
				str = id + " : " + clientUI.txtF.getText(); // 클라이언트가 채팅창에 적은 메세지 표시			
			}
			
			System.out.println("[ClientMsgSend] sendMsg() - str :" + str);
			printWriter.println(str); // 입력받은 문자열 서버로 보낸다
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}