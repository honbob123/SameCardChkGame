package com.SameCardChk.Game.Project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class ID extends JFrame implements ActionListener {
   static JTextField tf = new JTextField(10);
   JButton btn = new JButton("확인");
   PutThread putThread;
   MainForm mainForm;
   
   public ID() {}
   public ID(PutThread putThread, MainForm mainForm) {
      super("아이디 생성");
      this.putThread = putThread;
      this.mainForm = mainForm;
      
      setLayout(new FlowLayout());
      add(new JLabel("아이디"));
      add(tf);
      add(btn);
      btn.addActionListener(this);
      
      //화면 정중앙에 나오도록하는 코드
      Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frm=super.getSize();
      int xpos=(int)screen.getWidth() / 2 - (int)frm.getWidth() / 2;
      int ypos=(int)screen.getHeight() / 2 - (int)frm.getHeight() / 2;
      setLocation(xpos, ypos);
      setBounds(300, 300, 250, 100);
      
      setVisible(true);
   }
   static public String getId() { //스태틱지정하여 어디든 접근 가능
      return tf.getText(); //입력한 id값
   }
   @Override
   public void actionPerformed(ActionEvent e) { //확인버튼 누룰 떄 이벤트
      putThread.sendMsg(); 
      mainForm.isFirst = false;  //처음 입장 false으로 줌
      mainForm.setVisible(true); //MainForm JFrame 띄우기
      this.dispose(); //창 닫기
   }
}
public class MainForm extends JFrame implements Runnable{
   private JPanel gaugeBa;
   private JPanel gameScreen;
   private JPanel player1;
   private JPanel player2;
   private JPanel chatting;
   private ImageIcon icon;
   private JLabel image;
   private Container cPane;
   
   private JButton[] Panelbtn = new JButton[18];
   
   boolean isFirst = true;
   JTextArea txtA = new JTextArea(15,32);
   JTextField txtF = new JTextField(32);
   JLabel text;
   Socket socket;
   PutThread putThread;
   
   public MainForm(Socket socket) {
      super("PlayUp!");
      this.socket = socket;
      putThread = new PutThread(this);
      new ID(putThread, this);
      
      setSize(1500,800); //사이즈 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(false);  //화면 출력
      setResizable(false); //사이즈 변경 불가
      txtA.setEnabled(false);
      
      
      formInit();
   }
   
   public void formInit() {
      //화면 정중앙에 나오도록하는 코드
      Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frm=super.getSize();
      int xpos=(int)screen.getWidth() / 2 - (int)frm.getWidth() / 2;
      int ypos=(int)screen.getHeight() / 2 - (int)frm.getHeight() / 2;
      setLocation(xpos, ypos);
      
      
      
      setLayout(null); //배치관리자 없는 컨테이너
      gaugeBa = new JPanel(); gaugeBa.setBackground(Color.YELLOW);
      gameScreen = new JPanel(); gameScreen.setOpaque(true);//gameScreen.setBackground(Color.BLACK);
      player1 = new JPanel(); player1.setBackground(Color.RED);
      player2 = new JPanel(); player2.setBackground(Color.green);
      chatting = new JPanel();
      chatting.setOpaque(false); //chatting 패널 색깔 투명
      
      cPane = getContentPane();
      cPane.setLayout(null);
      
      icon = new ImageIcon("images/bookshelves_visible.png");
      image = new JLabel(icon);
      
      image.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
      
      cPane.add(image);
      
      Panelbtn[0] = new JButton(new ImageIcon("images/px-01_90_190.png"));
      Panelbtn[0].setBorderPainted(false);
      Panelbtn[0].setFocusPainted(false);
      Panelbtn[0].setContentAreaFilled(false);
      Panelbtn[0].setOpaque(false);
      
      gameScreen.add(Panelbtn[0], BorderLayout.NORTH);
      
      
      gaugeBa.setLayout(new FlowLayout());
      gameScreen.setLayout(new FlowLayout());
      player1.setLayout(new BorderLayout());
      player2.setLayout(null);
      chatting.setLayout(new FlowLayout());
      
      gaugeBa.setSize(1100, 75); gaugeBa.setLocation(5, 10);
      add(gaugeBa);
      gameScreen.setSize(1100, 670); gameScreen.setLocation(5, 90);
      add(gameScreen);
      player1.setSize(365, 200); player1.setLocation(1115, 10);
      add(player1);
      player2.setSize(365, 200); player2.setLocation(1115, 225);
      add(player2);
      chatting.setSize(365, 340); chatting.setLocation(1115, 435);
      add(chatting);
      
      
      
      
      //icon = new ImageIcon("2.PNG"); 
      //image = new JLabel(icon);
      //player1.add(image);
      
      // 채팅구현
            chatting.add(new JScrollPane(txtA));
            chatting.add(txtF);
            txtF.addKeyListener(new MyKeyListen());
            //txtA.setFont(new Font("Serif", Font.ITALIC, 10));
      //
   }
   
   @Override
   public void run() {
      // TODO Auto-generated method stub
   }
   
   class MyKeyListen extends KeyAdapter {
      @Override
      public void keyPressed(KeyEvent e) {
         String id = ID.getId();
         if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(txtF.getText().equals("")) {
               return; //메시지 입력없이 눌렀을 경우
            }
            putThread.sendMsg(); //키보드로 받은거 서버로 보낸다
            txtA.append(id + " : " + txtF.getText() + "\n");
            txtF.setText(""); //채팅창에 아무것도 없게 한다
         }
      }
   }
   
}