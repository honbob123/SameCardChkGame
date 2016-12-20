package com.SemeProJ.CardChkGame.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.Icon;
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
					if (msg.substring(0, 8).equals("Game_Win")) {
						clientMainUI.turn_view.setText("승리하였습니다!");
						JOptionPane.showMessageDialog(null, "승리 하였습니다!연승에 도전하세요","승리", JOptionPane.ERROR_MESSAGE);
						clientMainUI.GameMain.removeAll(); //게임메인화면 버튼 삭제
						clientMainUI.score1 =0; //
						clientMainUI.score2= 0;
					}
					else if (msg.substring(0, 9).equals("Game_Lose")) {
						clientMainUI.turn_view.setText("패배하였습니다.");
						JOptionPane.showMessageDialog(null, "루저~외톨이~상처받은 겁쟁이~","패배", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					else if (msg.substring(0, 9).equals("Game_Draw")) {
						clientMainUI.turn_view.setText("무승부!");
						JOptionPane.showMessageDialog(null, "무승부입니다~","무승부", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					else if(msg.substring(0, 9).equals("Send_Chat")) Print_Msg(msg);                        //채팅 출력 메소드
					else if(msg.substring(0, 9).equals("Game_stop")) Print_No(msg);
					else if(msg.substring(0, 9).equals("Game_play")) Print_Yes(msg);
					else if(msg.substring(1, 10).equals("Send_Info")) Print_Info(msg);                           //정보 출력 메소드
					else if(msg.substring(0, 10).equals("Send_Start")){
						Print_Start(msg);                    //게임 시작 메소드
						if(msg.endsWith("1")) {
							turnOn();   //게임 시작 메소드
						}else{
							turnOff();
						}
					}
					else if(msg.substring(0, 8).equals("Send_End")) Print_End(msg);
					else if(msg.substring(0, 10).equals("Game_Array")) clientMainUI.gameScreen(msg);	    //게임 배열 받아서 UI로 보내는 부분
					else if(msg.substring(0, 14).equals("Go_to_the_hell")) Print_Out(msg);                  //플레이어가 나갔을 때 해당 플레이어 정보만 삭제 메소드
					else if(msg.substring(0, 20).equals("Game_Score_and_Array")) gameScreenDel(msg);		// 상대편이 보낸 정답 부분 gameScreenDel로 보냄
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
	
	private void Print_Yes(String msg) {
		clientMainUI.turn_view.setText("당신 차례입니다.");
		clientMainUI.turn_view.setVisible(true);
		System.out.println("게임해");
		String test = clientMainUI.Panelbtn[0].getIcon().toString();
		System.out.println("문자열 : " + test + " ture or false : " + test.equals("images/card.png"));
		for(int i = 0; i < 16 ; i++){
			test = clientMainUI.Panelbtn[i].getIcon().toString();
			System.out.println(i + "번째 버튼 getIcon : " + clientMainUI.Panelbtn[i].getIcon());
			if(test.equals("images/card.png")){
				clientMainUI.Panelbtn[i].setEnabled(true);
			}
		}
	}
	
	private void Print_No(String msg) {
		clientMainUI.turn_view.setVisible(false);
		System.out.println("게임못해");
		for(int i=0; i<16; i++) {
			clientMainUI.Panelbtn[i].setEnabled(false);
		}
	}
	
	private void Print_Start(String msg) { //게임시작 메소드
		clientMainUI.textA.append(msg.substring(10) + "\n");
		clientMainUI.textF.setEnabled(false);
		for(int i=5; i>0; i--) {
			try {
				sleep(1000);
				String s = String.valueOf(i);
				clientMainUI.textA.append(s +"초!" + "\n");
			} catch (InterruptedException e) {}
		}
		clientMainUI.textA.append("게임 시작!!" + "\n");
		clientMainUI.textF.setEnabled(true);
		clientMainUI.gameScreenOn(); //게임메인화면에 카드 보이게 하는 메소드
	}
	
	private void Print_End(String msg) { //게임시작 메소드
		clientMainUI.textA.append("게임 종료!!" + "\n");
		clientMainUI.gameScreenOff();
		//메인폼 게임화면에 카드 출력
	}
	
	private void Print_Info(String msg) { //정보출력 메소드
		clientMainUI.Player2_Id.setText("");
		clientMainUI.Player2_Img.setIcon(null);
		clientMainUI.Player2_Score.setText("");
		
		switch(msg.charAt(0)) {
			case '0':  {
				int i = Integer.parseInt(msg.substring(11, 12));
				clientMainUI.Player1_Img.setIcon(clientMainUI.Player_Character[i]);
				clientMainUI.Player1_Id.setText(msg.substring(12));
				clientMainUI.Player1_Score.setText("0");
				break;
			}
			case '1': {
				int i = Integer.parseInt(msg.substring(11, 12));
				if(clientMainUI.Player1_Id.getText().equals("")) { //플1에 사람이 없을 때 플1에다가 정보출력
					clientMainUI.Player1_Img.setIcon(clientMainUI.Player_Character[i]);
					clientMainUI.Player1_Id.setText(msg.substring(12));
					clientMainUI.Player1_Score.setText("0");
				}else if(clientMainUI.Player2_Id.getText().equals("")) { //플2에 사람이 없을 때 플2에다가 정보출력
					clientMainUI.Player2_Img.setIcon(clientMainUI.Player_Character[i]);
					clientMainUI.Player2_Id.setText(msg.substring(12));
					clientMainUI.Player2_Score.setText("0");
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
	
	private void gameScreenDel(String msg) { // 상대편이 맞춘부분 없에는 코드
		String [] split_other_player = msg.substring(21).split(",");
		String id = split_other_player[0]; //맞춘 id
		int GameArrDel1 = Integer.parseInt(split_other_player[1]);
		int GameArrDel2 = Integer.parseInt(split_other_player[2]);
		if(id.equals(clientMainUI.Player1_Id.getText())) {
			//아이디가 1p 아이디랑 같으면 1P 점수 올리고 출력
			clientMainUI.score1++;
			clientMainUI.Player1_Score.setText(Integer.toString(clientMainUI.score1));
			clientMainUI.Player1_Score.updateUI();
		}else if(id.equals(clientMainUI.Player2_Id.getText())) {
			//아이디가 2p 아이디랑 같으면 2p 점수 올리고 출력
			clientMainUI.score2++;
			clientMainUI.Player2_Score.setText(Integer.toString(clientMainUI.score2));
			clientMainUI.Player2_Score.updateUI();
		}
		Icon setbtnIcon = new ImageIcon("images/"+ ClientMainUI.Panelbtn[GameArrDel1].getName() +".png");
		ClientMainUI.Panelbtn[GameArrDel1].setIcon(setbtnIcon);
		ClientMainUI.Panelbtn[GameArrDel1].setEnabled(false);
		setbtnIcon = new ImageIcon("images/"+ ClientMainUI.Panelbtn[GameArrDel2].getName() +".png");
		ClientMainUI.Panelbtn[GameArrDel2].setIcon(setbtnIcon);
		ClientMainUI.Panelbtn[GameArrDel2].setEnabled(false);
	}
	
	private void turnOn(){
		clientMainUI.turn_view.setText("당신 차례입니다.");
		clientMainUI.turn_view.setVisible(true);
		for(int j = 0; j < 16; j++){
			clientMainUI.Panelbtn[j].setEnabled(true);
		}
	}
	
	private void turnOff(){
		clientMainUI.turn_view.setVisible(false);
		for(int j = 0; j < 16; j++){
			clientMainUI.Panelbtn[j].setEnabled(false);
		}
	}
}