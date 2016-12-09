package com.SameCardChk.Game.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

class PutThread { //Ű����� ���۹��ڿ� �Է¹޾� ������ ����
   Socket socket;
   MainForm mainForm;
   String str;
   String id; //���̵� ��
   
   public PutThread(MainForm mainForm) {
      this.mainForm = mainForm;
      this.socket = mainForm.socket;
   }
   
   public void sendMsg() { //������ �޽��� ������
      //Ű����κ��� �о���� ���� ��Ʈ����ü ����
      BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(System.in));
      PrintWriter printWriter = null;
      try {
         //������ ���ڿ� �����ϱ� ���� ��Ʈ����ü ����
         printWriter = new PrintWriter(socket.getOutputStream(), true);
         if(mainForm.isFirst == true) { 
        	 // �� ó�� ���̵� �Է��Ŀ� �ѹ��� �߻��ϴ� �̺�Ʈ
        	 // �Է��� ���̵� �ٸ� �÷��̾�� �ѷ���
            InetAddress iaddr = socket.getLocalAddress(); 
            
            String ip = iaddr.getHostAddress();
            getId();
            System.out.println("IP : " + ip + " ID : " + id + " LocalAddress : " + iaddr.toString() + " LocalSocketAddress : " + socket.getLocalSocketAddress() + " SocketChanel : " + socket.getChannel());
            
            str = "@:"+ id + " ���� �α����߽��ϴ�!"; 
            // @�� �տ� �ٿ��� �������� üũ �ϰ� ����
            mainForm.txtA.append(id + "���� �α����߽��ϴ�!" + "\n");
         } else
            str = id + " : " + mainForm.txtF.getText(); //Ŭ���̾�Ʈ�� ä��â�� ���� ���ڿ�
         
         printWriter.println(str); //�Է¹��� ���ڿ� ������ ������
      } catch (IOException e) {
         System.out.println(e.getMessage());
      } finally {
         try {
            if(bufferedReader != null){
            	System.out.println("����޼��� ���̳� �κ�");
            	bufferedReader.close();
            }
         } catch (IOException e) {
            System.out.println(e.getMessage());
         }
      }
   }
   public void getId() {
	   System.out.println("getID �κ�");
      id = ID.getId();
   }
}
class ReadThread extends Thread { //������ ������ ���ڿ��� ���۹޴� ������
   Socket socket;
   MainForm mainForm;
   PrintWriter printWriter = null;
   
   public ReadThread(Socket socket, MainForm mainForm) {
	   System.out.println("ReadThread �κ�");
      this.socket= socket;
      this.mainForm = mainForm;
   }
   @Override
      public void run() {
	   System.out.println("run ���� �κ�");
      BufferedReader bufferedReader = null;
      try {
         //�����κ��� ���۵� ���ڿ� �о���� ���� ��Ʈ����ü ����
         bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         while(true) {
            //�������κ��� ���ڿ� �о��
            String str = bufferedReader.readLine();
            if(str == null) {
               System.out.println("������ ������");
               str = "������";
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
         System.out.println("���� ����!");
         mainForm = new MainForm(socket);
         new ReadThread(socket, mainForm).start();
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }
   }
}