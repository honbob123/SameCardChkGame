package com.SemeProJ.CardChkGame.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

class ClientMsgReceive extends Thread{ //서버에서 오는 데이터를 받는 스레드
	private Socket client_socket;
	private ClientMainUI clientMainUI;
	private BufferedReader bufferedReader = null;
	
	public ClientMsgReceive(Socket socket,ClientMainUI clientMainUI) { //생성자
		this.clientMainUI = clientMainUI;
		this.client_socket = socket;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
		} catch (IOException e) {
			//서버와 연결 에러났을 때 에러창을  뛰운다
			JOptionPane.showMessageDialog(null, "서버와 연결 에러!","에러창", JOptionPane.ERROR_MESSAGE);
			System.out.println("서버와 연결 에러2!");
		}
	}
	
	@Override
	public void run() { //스레드 메소드
		try {
			while(true) {
				String msg = null;
				msg = bufferedReader.readLine(); //소켓으로부터 메시지를 읽는다
				if(msg != null) {
					if(msg.substring(1, 10).equals("Send_Info")) Print_Info(msg);          //정보 출력 메소드
					else if(msg.substring(0, 9).equals("Send_Chat")) Print_Msg(msg);       //채팅 출력 메소드
					else if(msg.substring(0, 14).equals("Go_to_the_hell")) Print_Out(msg); //플레이어가 나갔을 때 해당 플레이어 정보만 삭제 메소드
					else if(msg.substring(0, 10).equals("Send_Start")) Print_Start(msg);   //게임 시작 메소드
					
					else if(msg.substring(0, 10).equals("Game_Array")) clientMainUI.gameScreen(msg);	   //게임 배열 받아서 UI로 보내는 부분
				}else
					break;
			}
		} catch (IOException e) {
			System.out.println("서버와 연결이 끊겼습니다!");
			JOptionPane.showMessageDialog(null, "서버와 연결이 끊겼습니다!","에러창", JOptionPane.ERROR_MESSAGE);
		}finally { //서버와 연결이 끊겼을 때  finally 실행
			try {
				if(bufferedReader != null) bufferedReader.close(); //입력 시스템 자원 반납
				if(client_socket != null) client_socket.close();   //?????소켓을 닫는다
			} catch (IOException e) {
				System.out.println("똥컴 버리시고 컴퓨터를 새로 사세요!");
			}
		}
	}
	
	private void Print_Start(String msg) { //게임시작 메소드
		clientMainUI.textA.append(msg.substring(10) + "\n");
		for(int i=5; i>0; i--) {
			try {
				sleep(1000);
				String s = String.valueOf(i);
				clientMainUI.textA.append(s +"초!" + "\n");
			} catch (InterruptedException e) {}
		}
		clientMainUI.textA.append("게임 시작!!" + "\n");
		//메인폼 게임화면에 카드 출력
	}
	
	private void Print_Info(String msg) { //정보출력 메소드
		clientMainUI.Player2_Id.setText("");
		clientMainUI.Player2_Img.setIcon(null);
		clientMainUI.Player2_Score.setText("");
		switch(msg.charAt(0)) {
			case '0':  {
				int i = Integer.parseInt(msg.substring(11, 12));
				clientMainUI.Player1_Img.setIcon(new ImageIcon(clientMainUI.Player_Character[i]));
				clientMainUI.Player1_Id.setText(msg.substring(12));
				break;
			}
			case '1': {
				int i = Integer.parseInt(msg.substring(11, 12));
				if(clientMainUI.Player1_Id.getText().equals("")) { //플1에 사람이 없을 때 플1에다가 정보출력
					clientMainUI.Player1_Img.setIcon(new ImageIcon(clientMainUI.Player_Character[i]));
					clientMainUI.Player1_Id.setText(msg.substring(12));
				}else if(clientMainUI.Player2_Id.getText().equals("")) { //플2에 사람이 없을 때 플2에다가 정보출력
					System.out.println("뭐지?");
					clientMainUI.Player2_Img.setIcon(new ImageIcon(clientMainUI.Player_Character[i]));
					clientMainUI.Player2_Id.setText(msg.substring(12));
				}
				break;
			}
		}
	}
	
	private void Print_Msg(String msg) { //채팅출력 메소드
		clientMainUI.textA.append(msg.substring(9) + "\n"); //textA에 메시지 출력		
	}
	
	private void Print_Out(String msg) { //플레이어 나갔을 때 해당 플레이어 정보만 삭제 메소드
		if(msg.substring(14).equals(clientMainUI.Player1_Id.getText())) {
			//mainForm.Player1_Id.removeAll();
			clientMainUI.Player1_Id.setText("");
			clientMainUI.Player1_Img.setIcon(null);
			clientMainUI.Player1_Score.setText("");
			clientMainUI.Player1.updateUI();
		}else if(msg.substring(14).equals(clientMainUI.Player2_Id.getText())) {
			clientMainUI.Player2_Id.setText("");
			clientMainUI.Player2_Img.setIcon(null);
			clientMainUI.Player2_Score.setText("");
			clientMainUI.Player2.updateUI();
		}
	}
}