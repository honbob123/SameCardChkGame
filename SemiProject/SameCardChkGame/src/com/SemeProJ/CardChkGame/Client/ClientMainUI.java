package com.SemeProJ.CardChkGame.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientMainUI extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel gaugeBa;
	private JPanel gameScreen;
	private JPanel player1;
	private JPanel player2;
	private JPanel chatting;
	
	private ImageIcon icon;
	private JLabel image;
	
	private Container cPane;

	private JButton[] Panelbtn = new JButton[24];

	boolean isFirst = true;
	
	JTextArea txtA = new JTextArea(15, 32);
	JTextField txtF = new JTextField(32);
	
	JLabel text;
	
	Socket socket;
	
	ClientMsgSend msgSend;
	
	String sUserID = "";
	
	public void setUserID(String sUserID) {
		this.sUserID = sUserID;
	}
	public String getUserID() {
		
		return this.sUserID; 
	}
	public ClientMainUI(Socket socket) {
		
		super("PlayUp!");
		this.socket = socket;
		
		msgSend = new ClientMsgSend(this);
		
		new ClientInitUI(msgSend, this); // 클라이언트 로그인 UI 호출

		// Main UI Config INFO
		setSize(1500, 800); // 사이즈
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false); // 화면 출력
		setResizable(false); // 사이즈 변경 불가
		txtA.setEnabled(false);
		
		// Main UI JFrame Detail Setting
		formInit();
	}

	public void formInit() {
		
		// 화면 정중앙에 나오도록하는 코드
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int xpos = (int) screen.getWidth() / 2 - (int) frm.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - (int) frm.getHeight() / 2;
		setLocation(xpos, ypos);

		setLayout(null); // 배치관리자 없는 컨테이너
		gaugeBa = new JPanel();
		gaugeBa.setBackground(Color.YELLOW);
		gameScreen = new JPanel();
		gameScreen.setOpaque(true);// gameScreen.setBackground(Color.BLACK);
		player1 = new JPanel();
		player1.setBackground(Color.RED);
		player2 = new JPanel();
		player2.setBackground(Color.green);
		chatting = new JPanel();
		chatting.setOpaque(false); // chatting 패널 색깔 투명

		cPane = getContentPane();
		cPane.setLayout(null);

		icon = new ImageIcon("images/bookshelves_visible.png");
		image = new JLabel(icon);

		image.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

		cPane.add(image);

		for (int i = 0; i < 24; i++) {

			Panelbtn[i] = new JButton(new ImageIcon("images/px-01_90_190.png"));
			Panelbtn[i].setRolloverEnabled(false);
			Panelbtn[i].setOpaque(false);
			Panelbtn[i].setFocusPainted(false);
			Panelbtn[i].setContentAreaFilled(false);
			Panelbtn[i].setBorderPainted(false);
			
			gameScreen.add(Panelbtn[i]);
		}
		
		// Main UI 각각 레이아웃 초기화 구성
		gaugeBa.setLayout(new FlowLayout());
		gameScreen.setLayout(new FlowLayout());
		
		player1.setLayout(new BorderLayout());
		player2.setLayout(null);
		
		chatting.setLayout(new FlowLayout());

		gaugeBa.setSize(1100, 75);
		gaugeBa.setLocation(5, 10);
		add(gaugeBa);
		
		gameScreen.setSize(1100, 670);
		gameScreen.setLocation(5, 90);
		add(gameScreen);
		
		player1.setSize(365, 200);
		player1.setLocation(1115, 10);
		add(player1);
		
		player2.setSize(365, 200);
		player2.setLocation(1115, 225);
		add(player2);
		
		chatting.setSize(365, 340);
		chatting.setLocation(1115, 435);
		add(chatting);

		// icon = new ImageIcon("2.PNG");
		// image = new JLabel(icon);
		// player1.add(image);

		// 채팅 화면 구성
		chatting.add(new JScrollPane(txtA));
		chatting.add(txtF);
		
		// 채팅창 내 엔터 이벤트 리스너 추가
		txtF.addKeyListener(new KeyEvents());
		// txtA.setFont(new Font("Serif", Font.ITALIC, 10));

	}
	
	// 게임 화면 업데이트
	/*
	 * gameScreenUpdate()는 서버에서 전달한 카드 게임의 정보를 수신하여
	 * gameScreen에 표시하기 위해 gameScreen을 재구성 또는 초기화 하는 메서드이다.
	 */
	public void gameScreenUpdate(String sCmd) {
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	class KeyEvents extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (txtF.getText().equals("")) {
					return; // 메시지 입력없이 눌렀을 경우
				}
				sUserID = ClientInitUI.getId();
				
				msgSend.sendMsg(); // 채팅 메세지 서버로 전송
				
				txtA.append(sUserID + " : " + txtF.getText() + "\n"); // 입력한 채팅 메세지 화면에 표시
				txtA.setCaretPosition(txtA.getDocument().getLength()); // 화면 포지션 적용
				txtF.setText(""); // 채팅 입력란 초기화!
			}
		}
	}

}
