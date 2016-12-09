package com.SameCardChk.Game.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class SendThread extends Thread { //클라이언트로부터 전송된 문자열을 받아서 다른 클라이언트에게 문자열을 보내주는 스레드
   Socket socket;
   Vector<Socket> vec; //들어오는 순서대로 벡터에 넣기
   public SendThread(Socket socket, Vector<Socket> vec) {
      this.socket = socket;
      this.vec = vec;
   }
   @Override
      public void run() { //클라이언트로 보내는 run
      BufferedReader bufferedReader = null;
      try {
         bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String str = null;
         while(true) {
            //클라이언트로부터 문자열 받기
        	String Cli_Id_Array[] = new String[5];
        	int i = 0;
        	str = bufferedReader.readLine();
        	//--------------추가된 코드 ------------------
        	if(str.startsWith("@")){ // 첫글자 @를 확인
        		Cli_Id_Array[i] = str.substring(str.indexOf(":")+1, str.indexOf(" "));
        		// 들어온 문자열에서 "@:~~~~~ 님이 로그인했습니다." ~~~~~~ 만 짤라서 배열에 저장
        		System.out.println(Cli_Id_Array[i]);
        		i++;
        		str = str.substring(str.indexOf(":")+1);
        		sendMsg(str);
        	}/* else if(str == null) { //상대가 접속을 끊으면
               //벡터에서 없애기
               vec.remove(socket);
               break;
            }*/ 
        	else{
            	sendMsg(str);
            }
         }
      } catch (IOException e) {
    	  sendMsg("플레이어가 나갔습니다.");
         System.out.println(e.getMessage());
      }finally {
         try {
            if( bufferedReader != null) bufferedReader.close();
            if(socket !=null) socket.close();
         } catch (IOException e) {
        	 System.out.println("서버 run의 finally안에 catch");
            System.out.println(e.getMessage());
         }
      }
      }
   public void sendMsg(String str) {//전송받은 문자열 다른 클라이언트에게 보내주는 메소드
      try {
         for(Socket socket : vec) {
            //소켓이 데이터를 보낸 클라이언트인 경우를 제외하고 나머지 socket들에게만 데이터를 보낸다
            if(socket != this.socket) {
               PrintWriter printWriter =
                     new PrintWriter(socket.getOutputStream(), true);
               printWriter.println(str);
               System.out.println(str);
               printWriter.flush();
            }
         }
      } catch (IOException e) {
    	  System.out.println("접속이 끊겼음");
         System.out.println(e.getMessage());
      }
   }
}

public class GameChatServer {
   public static void main(String[] args) {
      ServerSocket serverSocket = null;
      Socket socket = null;
      Vector<Socket> vec = new Vector<Socket>();
      try {
         serverSocket = new ServerSocket(34102); //포트번호 6969에 bind(결합)
         while(true) {
            System.out.println("접속 대기중...");
            socket = serverSocket.accept(); //연결 댇기중
            //연결된 클라이언트 소켓 벡터에 담기
            vec.add(socket);
            //스레드 구동
            new SendThread(socket, vec).start();
         }
      } catch (IOException e) {
         
         System.out.println(e.getMessage());
      }
   }
}