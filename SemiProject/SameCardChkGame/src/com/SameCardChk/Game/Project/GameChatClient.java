package com.SameCardChk.Game.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

class PutThread { //키버드로 전송문자열 입력받아 서버로 전송
   Socket socket;
   MainForm mainForm;
   String str;
   String id; //아이디 값
   
   public PutThread(MainForm mainForm) {
      this.mainForm = mainForm;
      this.socket = mainForm.socket;
   }
   
   public void sendMsg() { //서버로 메시지 보내기
      //키보드로부터 읽어오기 위한 스트림객체 생성
      BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(System.in));
      PrintWriter printWriter = null;
      try {
         //서버로 문자열 전송하기 위한 스트림객체 생성
         printWriter = new PrintWriter(socket.getOutputStream(), true);
         if(mainForm.isFirst == true) { 
        	 // 맨 처음 아이디 입력후에 한번만 발생하는 이벤트
        	 // 입력한 아이디를 다른 플레이어에게 뿌려줌
            InetAddress iaddr = socket.getLocalAddress(); 
            
            String ip = iaddr.getHostAddress();
            getId();
            System.out.println("IP : " + ip + " ID : " + id + " LocalAddress : " + iaddr.toString() + " LocalSocketAddress : " + socket.getLocalSocketAddress() + " SocketChanel : " + socket.getChannel());
            
            str = "@:"+ id + " 님이 로그인했습니다!"; 
            // @를 앞에 붙여서 서버에서 체크 하게 만듬
            mainForm.txtA.append(id + "님이 로그인했습니다!" + "\n");
         } else
            str = id + " : " + mainForm.txtF.getText(); //클라이언트가 채팅창에 적은 문자열
         
         printWriter.println(str); //입력받은 문자열 서버로 보낸다
      } catch (IOException e) {
         System.out.println(e.getMessage());
      } finally {
         try {
            if(bufferedReader != null){
            	System.out.println("센드메세지 파이널 부분");
            	bufferedReader.close();
            }
         } catch (IOException e) {
            System.out.println(e.getMessage());
         }
      }
   }
   public void getId() {
	   System.out.println("getID 부분");
      id = ID.getId();
   }
}
class ReadThread extends Thread { //서버가 보내온 문자열을 전송받는 스레드
   Socket socket;
   MainForm mainForm;
   PrintWriter printWriter = null;
   
   public ReadThread(Socket socket, MainForm mainForm) {
	   System.out.println("ReadThread 부분");
      this.socket= socket;
      this.mainForm = mainForm;
   }
   @Override
      public void run() {
	   System.out.println("run 시작 부분");
      BufferedReader bufferedReader = null;
      try {
         //서버로부터 전송된 문자열 읽어오기 위한 스트림객체 생성
         bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         while(true) {
            //소켓으로부터 문자열 읽어옴
            String str = bufferedReader.readLine();
            if(str == null) {
               System.out.println("접속이 끊겼음");
               str = "나갔다";
               break;
            }
            mainForm.txtA.append(str + "\n");
         }
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }finally {
         try {
            if(bufferedReader != null) {
               bufferedReader.close();
            }
            if(socket != null){
               socket.close();
            }
         } catch (IOException e) {}
      }
      }
}
public class GameChatClient {
   public static void main(String[] args) {
      Socket socket;
      MainForm mainForm;
      try {
         socket = new Socket("127.0.0.1", 34102);
         System.out.println("연결 성공!");
         mainForm = new MainForm(socket);
         new ReadThread(socket, mainForm).start();
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }
   }
}