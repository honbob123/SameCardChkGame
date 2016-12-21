package com.SemeProJ.CardChkGame.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

class ClientMsgSend { //서버로 전송하는 클래스
	private Socket socket;
	private ClientMainUI clientMainUI;
	public static PrintWriter printWriter;
	public static String id;                //플레이어 ID
	
	
	public ClientMsgSend(ClientMainUI clientMainUI) { //생성자
		this.socket = clientMainUI.socket;
		this.clientMainUI = clientMainUI;
		try {
			printWriter = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와 연결 에러!","에러창", JOptionPane.ERROR_MESSAGE);
			System.out.println("서버와 연결 에러1");
		}
	}
	
	public void getId() { //플레이어 ID get메소드
		id= ClientInitUI.getId();
	}
	
	public void Send_Info() { //서버로 플레이어정보를 보내는 메소드
		String msg= null;
		getId();
		switch(clientMainUI.Player_SelectCharacter) {
			case 0: msg = "Send_Info/0" + id; break;
			case 1: msg = "Send_Info/1" + id; break;
			case 2: msg = "Send_Info/2" + id; break;
			default: break;
		}
		printWriter.println(msg); //처음 입장시 플레이어 ID
		printWriter.flush();
	}
 
	public void Send_Msg() { //서버로 채팅 메시지 보내는 메소드
		
		String msg = null;
		if(clientMainUI.isFirst == true) { //처음 연결됬는지 여부
			getId();
			//연결된 IP 주소 알아낸다
			InetAddress iaddr = socket.getLocalAddress();
			String ip = iaddr.getHostAddress();
			System.out.println("IP : " + ip + " ID : " + id); //상대 ID와 IP 콘솔 출력
			clientMainUI.turn_view.setText(id + " 님 환영합니다");
			clientMainUI.turn_view.setVisible(true);
			msg = "Send_Chat" + id + "님이 로그인했습니다"; //처음 입장시 서버로 보내서 다른 클라이언트에게 보내는 메시지
			clientMainUI.textA.append(id + "님이 로그인했습니다" + "\n");
		}else
			msg = "Send_Chat" + id + " : " + clientMainUI.textF.getText(); //채팅창에 적은 메시지 서버로 보내서 다른 클라이언트에게 보내는 메시지
		
		//메시지를 서버로 보낸다
		printWriter.println(msg);
		printWriter.flush();
	}
}