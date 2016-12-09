package com.SameCardChk.Game.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class SendThread extends Thread { //Ŭ���̾�Ʈ�κ��� ���۵� ���ڿ��� �޾Ƽ� �ٸ� Ŭ���̾�Ʈ���� ���ڿ��� �����ִ� ������
   Socket socket;
   Vector<Socket> vec; //������ ������� ���Ϳ� �ֱ�
   public SendThread(Socket socket, Vector<Socket> vec) {
      this.socket = socket;
      this.vec = vec;
   }
   @Override
      public void run() { //Ŭ���̾�Ʈ�� ������ run
      BufferedReader bufferedReader = null;
      try {
         bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String str = null;
         while(true) {
            //Ŭ���̾�Ʈ�κ��� ���ڿ� �ޱ�
        	String Cli_Id_Array[] = new String[5];
        	int i = 0;
        	str = bufferedReader.readLine();
        	//--------------�߰��� �ڵ� ------------------
        	if(str.startsWith("@")){ // ù���� @�� Ȯ��
        		Cli_Id_Array[i] = str.substring(str.indexOf(":")+1, str.indexOf(" "));
        		// ���� ���ڿ����� "@:~~~~~ ���� �α����߽��ϴ�." ~~~~~~ �� ©�� �迭�� ����
        		System.out.println(Cli_Id_Array[i]);
        		i++;
        		str = str.substring(str.indexOf(":")+1);
        		sendMsg(str);
        	}/* else if(str == null) { //��밡 ������ ������
               //���Ϳ��� ���ֱ�
               vec.remove(socket);
               break;
            }*/ 
        	else{
            	sendMsg(str);
            }
         }
      } catch (IOException e) {
    	  sendMsg("�÷��̾ �������ϴ�.");
         System.out.println(e.getMessage());
      }finally {
         try {
            if( bufferedReader != null) bufferedReader.close();
            if(socket !=null) socket.close();
         } catch (IOException e) {
        	 System.out.println("���� run�� finally�ȿ� catch");
            System.out.println(e.getMessage());
         }
      }
      }
   public void sendMsg(String str) {//���۹��� ���ڿ� �ٸ� Ŭ���̾�Ʈ���� �����ִ� �޼ҵ�
      try {
         for(Socket socket : vec) {
            //������ �����͸� ���� Ŭ���̾�Ʈ�� ��츦 �����ϰ� ������ socket�鿡�Ը� �����͸� ������
            if(socket != this.socket) {
               PrintWriter printWriter =
                     new PrintWriter(socket.getOutputStream(), true);
               printWriter.println(str);
               System.out.println(str);
               printWriter.flush();
            }
         }
      } catch (IOException e) {
    	  System.out.println("������ ������");
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
         serverSocket = new ServerSocket(34102); //��Ʈ��ȣ 6969�� bind(����)
         while(true) {
            System.out.println("���� �����...");
            socket = serverSocket.accept(); //���� ������
            //����� Ŭ���̾�Ʈ ���� ���Ϳ� ���
            vec.add(socket);
            //������ ����
            new SendThread(socket, vec).start();
         }
      } catch (IOException e) {
         
         System.out.println(e.getMessage());
      }
   }
}