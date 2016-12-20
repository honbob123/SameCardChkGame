package com.SemeProJ.CardChkGame.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

class ServerMsgRxtoTX extends Thread { //클라이언트로부터 전송된 메시지를 받고 다른 클라이언트한테 보내는 스레드
	private Socket client_socekt;        //클라이언트 소켓
	private Vector<Socket> socket_vec;   //들어오는 순서대로 벡터에 저장
	private Vector<String> Info_vec;     //들어오는 순서대로 아이디와 캐릭터값 저장
	private BufferedReader bufferedReader = null;
	//private PrintWriter printWriter = null; (쓸 필요가 없다)
	private int [] arr = new int[16];
	int turntoChk = 0;
	static int end = 0;
	static int score1 = 0, score2 = 0;
	
	public ServerMsgRxtoTX(Socket socket, Vector<Socket> socket_vec,Vector<String> Info_vec) { //생성자
		this.client_socekt = socket;
		this.socket_vec = socket_vec;
		this.Info_vec = Info_vec;
		try {
			//클라이언트 소켓에서 데이터를 수신받기 위한 객체생성
			bufferedReader = new BufferedReader(new InputStreamReader(client_socekt.getInputStream()));
			//클라이언트 소켓에서 데이터를 전송하기 위한 객체 생성
			//printWriter = new PrintWriter(client_socekt.getOutputStream(), true); (쓸 필요가 없다)
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "스트림 설정 에러!","에러창", JOptionPane.ERROR_MESSAGE);
			System.out.println("스트림 설정 에러");
		}
	}
	
	@Override
	public void run() {
		String Id = null;
		String fullname = null;
		try {
			while(true) {
				String msg = null;
				msg = bufferedReader.readLine(); //메시지 받아서 읽기
				if(msg != null) {
					if(msg.substring(0, 9).equals("Send_Info")) {        //해당 메시지가 플레이어 정보에 대한 메시지일 때
						fullname = msg.substring(10);                    //Info_vec에서 삭제하기 위해 값을 저장
						Id = msg.substring(11);							 //아이디값 넣어서 나갔을 때 아이디 표시해준다
						Send_Info(msg);                                  //클라이언트한테 플레이어 정보 보내는 메소드
						if(socket_vec.size() == 2){
							randNumber();
							Send_Start();         //소켓벡터에 2명이 들어오면 게임 시작메소드 실행
						}
					}else if(msg.substring(0, 9).equals("Send_Chat")) {   //해당 메시지가 채팅에 대한 메시지일 때
						Send_Msg(msg);                                   //다른 모든 클라이언트에게 메시지 보내기
					}else if(msg.substring(0, 9).equals("Game_turn")) {
						Send_Turn();
					}
					else if(msg.substring(0, 20).equals("Game_Score_and_Array")) {
						Send_GameInfo(msg);
						Send_GameResult(msg);
					}
				}else //클라이언트가 나갔을 때 while문을 빠져 나간다
					
					break;
			}
		} catch (IOException e) {
			//JOptionPane.showMessageDialog(null, "클라이언트와 접속이 끊겼습니다","에러창", JOptionPane.ERROR_MESSAGE);
			System.out.println("클라이언트와 접속이 끊겼습니다");
		}finally { //플레이어가 나갔을 때 finally 실행
			try {
				Send_Msg("Send_Chat" + Id + "님이 방에서 나갔습니다");
				Send_Out(Id);                                          //플레이어가 나갔을 때 정보 삭제하는 메소드
				socket_vec.remove(client_socekt);                      //벡터에서 해당 소켓 삭제
				Info_vec.remove(fullname);                             //해당 소켓 Info_vec벡터 삭제
				if(bufferedReader != null) bufferedReader.close();    //사용한 시스템 자원을 반납하고 입력 스트림을 닫는다
				if(client_socekt != null) client_socekt.close();      //소켓을 닫는다
				System.out.println("Server 종료2");
			} catch (IOException e) {}
		}
	}
	
	private void Send_GameResult(String msg) {
		String [] split_End = msg.substring(21).split(",");
		String id = split_End[0]; //맞춘 id
		if(id.equals(Info_vec.get(0).substring(1))) score1++; //플1이 맞추면 
		else if (id.equals(Info_vec.get(1).substring(1))) score2++; //플2가 맞추면
		end++;
		System.out.println(end + " " + score1 + " " + score2);
		if(end == 8) { //정답을 다 맞췄을 때
			PrintWriter printWriter;
			try {
				if(score1 > score2) {
					Socket socket_player1 = socket_vec.get(0);
					Socket socket_player2 = socket_vec.get(1);
					printWriter = new PrintWriter(socket_player1.getOutputStream(), true);
					printWriter.println("Game_Win");
					printWriter = new PrintWriter(socket_player2.getOutputStream(), true);
					printWriter.println("Game_Lose");
				}
				else if(score1 < score2) {
					Socket socket_player1 = socket_vec.get(0);
					Socket socket_player2 = socket_vec.get(1);
					printWriter = new PrintWriter(socket_player1.getOutputStream(), true);
					printWriter.println("Game_Lose");
					printWriter = new PrintWriter(socket_player2.getOutputStream(), true);
					printWriter.println("Game_Win");
				}
				else { //비겼을 때 모든 플레이어 나가진다
					for(Socket socket : socket_vec) {
						printWriter = new PrintWriter(socket.getOutputStream(), true);
						printWriter.println("Game_Draw");
					}
				}
			} catch (IOException e) {}
			end=0;
			score1=0;
			score2=0;
		}
	}
	
	private void Send_Turn() { //상대한테 턴 넘긴다
		for(Socket socket : socket_vec) {
			PrintWriter printWriter;
			try {
				printWriter = new PrintWriter(socket.getOutputStream(), true);
				if(socket != this.client_socekt){
					printWriter.println("Game_play");	
				}else {		
					printWriter.println("Game_stop");
				}
			} catch (IOException e) {}
		}
	}
	
	private void Send_GameInfo(String msg) {
		for(Socket socket : socket_vec) {
			PrintWriter printWriter;
			try {
				printWriter = new PrintWriter(socket.getOutputStream(), true);
				printWriter.println(msg);
			} catch (IOException e) {}
		}
	}
	
	private void Send_Start() { //클라이언트한테 게임시작 보내는 메소드
		try {
			sleep(3000);
		} catch (InterruptedException e) {}
		for(Socket socket : socket_vec) {
			PrintWriter printWriter;
			try {
				printWriter = new PrintWriter(socket.getOutputStream(), true);
				printWriter.println("Send_Start" + "5초 후에 게임이 시작됩니다!" + turntoChk++);
			} catch (IOException e) {}
		}
	}
	
	private void Send_Info(String msg) {  //클라이언트한테 플레이어 정보 보내는 메소드
		Info_vec.add(msg.substring(10));  //벡터에 순서대로 이미지순서,ID 삽입
		for(Socket socket : socket_vec) { //모든 클라이언트한테 정보를 낸다  
			PrintWriter printWriter;
			for(int i=0; i<Info_vec.size(); i++) {
				try {
					String s = String.valueOf(i);
					printWriter = new PrintWriter(socket.getOutputStream(), true);
					printWriter.println(s + "Send_Info/" + Info_vec.get(i));
					printWriter.flush();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "스트림 설정 에러!","에러창", JOptionPane.ERROR_MESSAGE);
					System.out.println("클라이언트한테 메시지 보내는 에러");
				}
			}
		}
	}
	
	private void Send_Msg(String msg) { //모든 클라이언트한테 메시지 보내는 메소드
		for(Socket socket : socket_vec) {  //자신을 제외한 다른 클라이언트에게 메시지를 보낸다
			PrintWriter printWriter;
			if(socket != this.client_socekt) {
				try {
					printWriter = new PrintWriter(socket.getOutputStream(), true);
					printWriter.println(msg); //클라이언트에게 보내기
					printWriter.flush(); //????
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "스트림 설정 에러!","에러창", JOptionPane.ERROR_MESSAGE);
					System.out.println("클라이언트한테 메시지 보내는 에러");
				}
			}
		}	
	}
	
	private void Send_Out(String Id) { //클라이언트한테 플레이어가 나갔을 때 정보 삭제하는 메소드
		for(Socket socket : socket_vec) {
			PrintWriter printWriter;
			//if(socket != this.client_socekt) //자신을 제외한 다른 클라이언트에게
			try {
				printWriter = new PrintWriter(socket.getOutputStream(), true);
				end = 0;
				score1 = 0;
				score2 = 0;
				String removePanel = "Go_to_the_hell" + Id;
				printWriter.println(removePanel);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "스트림 설정 에러!","에러창", JOptionPane.ERROR_MESSAGE);
				System.out.println("클라이언트한테 메시지 보내는 에러");
			}
		}
	}
	public void randNumber() { // 게임화면 배열 보내는 부분
		String str_GameArr = "TEST :";
		int check=0;
		Random rand=new Random();
		int return_GameArr[] = new int[16];
		
		for(int i=0;i<arr.length;i++) {
			arr[i]=rand.nextInt(8);
			for(int x=0;x<i;x++) {
				if(arr[i]==arr[x])
					check++;
			}
			if(check==2) {
				check=0;
				i--;
				continue;
			}
			System.out.println(arr[i]);
			check = 0; // if2의 조건이 만족하지 않으면, 변수 check에 0을 대입한다.
			str_GameArr = str_GameArr.concat(Integer.toString(arr[i]));
			str_GameArr = str_GameArr.concat(",");
			System.out.println(str_GameArr);
		} // for1 조건이 만족하면 for1종료
		String[] split_return = (str_GameArr.substring(str_GameArr.indexOf(":")+1).split(","));
		for(int i = 0; i < split_return.length; i++) {
			return_GameArr[i] = Integer.parseInt(split_return[i]);
			System.out.println(return_GameArr[i]);
		}
		
		for(Socket socket : socket_vec) {
			PrintWriter printWriter;
			try {
				printWriter = new PrintWriter(socket.getOutputStream(), true);
				printWriter.println("Game_Array" + str_GameArr);
				printWriter.flush();
			} catch (IOException e) {}
		}
	}
}