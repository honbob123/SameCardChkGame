package com.SemeProJ.CardChkGame.Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
 
public class ClientInitUI extends JFrame {
	
	private JPanel contentPane;     //컨텐트 팬
	static JTextField userId;       //아이디 입력 창
	private JLabel character_choice;//캐럭터 선택 레이블
	private JLabel id_enter;        //아이디 입력 레이블
	private JButton btn_Make;       //확인 버튼
	private JLabel piglet;          //피글렛 사진
	private JLabel pooh;            //푸 사진
	private JLabel tiger;           //타이거 사진
	ClientMsgSend clientMsgSend;
	ClientMainUI clientMainUI;
	
	public ClientInitUI() {}
	public ClientInitUI(ClientMsgSend clientMsgSend, ClientMainUI clientMainUI) {  //생성자
		this.clientMsgSend = clientMsgSend;
		this.clientMainUI = clientMainUI;
		init();    //ID폼 화면 생성 메소드
		ID_event(); //ID폼 이벤트 처리 메소드
	}
	
	private void init() { //ID폼 화면 생성 메소드
		setBackground(new Color(224, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Home\\JAVA\\Project\\images\\atomicbearpink.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setForeground(Color.WHITE);
		setFont(new Font("Aharoni", Font.PLAIN, 15));
		setResizable(false);
		setTitle("아이디 생성");
		setBounds(100, 100, 632, 374);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 20, 147));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		id_enter = new JLabel("아이디 입력");
		id_enter.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		id_enter.setBounds(120, 287, 87, 43);
		contentPane.add(id_enter);
		
		userId = new JTextField();
		userId.setBounds(212, 297, 178, 21);
		contentPane.add(userId);
		userId.setColumns(10);
		
		character_choice = new JLabel("캐릭터 생성");
		character_choice.setFont(new Font("안상수2006굵은", Font.PLAIN, 30));
		character_choice.setBackground(new Color(30, 144, 255));
		character_choice.setHorizontalAlignment(SwingConstants.CENTER);
		character_choice.setBounds(195, 10, 210, 43);
		contentPane.add(character_choice);
		
		btn_Make = new JButton("확인");
		btn_Make.setToolTipText("");
		btn_Make.setForeground(new Color(0, 0, 0));
		btn_Make.setBackground(new Color(255, 0, 255));
		btn_Make.setFont(new Font("한컴 윤체 M", Font.PLAIN, 16));
		btn_Make.setBounds(420, 296, 79, 23);
		contentPane.add(btn_Make);
		
		piglet = new JLabel();
		piglet.setHorizontalAlignment(SwingConstants.CENTER);
		piglet.setVerticalAlignment(SwingConstants.TOP);
		piglet.setIcon(new ImageIcon("C:\\Users\\Home\\JAVA\\Project\\images\\0.jpg"));
		piglet.setBounds(87, 102, 120, 120);
		contentPane.add(piglet);
		
		pooh = new JLabel();
		pooh.setHorizontalAlignment(SwingConstants.CENTER);
		pooh.setIcon(new ImageIcon("C:\\Users\\Home\\JAVA\\Project\\images\\1.jpg"));
		pooh.setBounds(398, 102, 120, 120);
		contentPane.add(pooh);
		
		tiger = new JLabel();
		tiger.setHorizontalAlignment(SwingConstants.CENTER);
		tiger.setIcon(new ImageIcon("C:\\Users\\Home\\JAVA\\Project\\images\\2.jpg"));
		tiger.setBounds(243, 102, 120, 120);
		contentPane.add(tiger);
		
		//화면 중앙에 나오게 하는 코드
		Dimension frameSize = getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2,
				(windowSize.height - frameSize.height) / 2);
		
		this.setVisible(true);
	}
	
	private void ID_event() { //ID폼 이벤트 처리 메소드
		pooh.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) { //마우스 버튼이 눌러질 때 이벤트
				System.out.println("푸사진 눌렸다");
				clientMainUI.Player_SelectCharacter = 1;
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		piglet.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) { //마우스 버튼이 눌러질 때 이벤트
				System.out.println("피그렛사진 눌렸다");
				clientMainUI.Player_SelectCharacter = 0;
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tiger.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) { //마우스 버튼이 눌러질 때 이벤트
				System.out.println("타이거사진 눌렸다");
				clientMainUI.Player_SelectCharacter = 2;
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		btn_Make.addActionListener(new ActionListener() { //확인버튼을 눌렀을 때
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientMsgSend.Send_Msg();     //확인 누를때 채팅창에 입장글과 상대 클라이언트에게 입장글 보여준다
				clientMsgSend.Send_Info();    //player정보를 패널에 넣어준다
				clientMainUI.isFirst = false;   //처음입장여부를 아니요로 준다
				clientMainUI.setVisible(true);  //MainForm창이 뜬다
				dispose();                  //확인 버튼을 누르면 사라진다
			}
		});
	}
	
	static public String getId() { //스태틱으로 지정해서 어디서든 호출가능
		return userId.getText().trim(); //trim()메소드는 공백을 제외한다
	}
}