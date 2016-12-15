package com.SemeProJ.CardChkGame.Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;
 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
 
public class ClientMainUI extends JFrame{
	JPanel contentPane;
	JPanel GameMain;                       //게임 화면
	JPanel Timer;                          //타이머
	JTextArea textA = new JTextArea();     //채팅창
	JScrollPane scroll;	                   //채팅창 스크롤바
	JTextField textF = new JTextField(32); //채팅입력창
	JPanel Player1;                        //플레이어1 패널
	JLabel Player1_Id;    	  			   //플레이어1 아이디
	JLabel Player1_Img;   	  			   //플레이어1 캐릭터사진
	JLabel Player1_Score;                  //플레이어1 점수
	JPanel Player2;          			   //플레이어2 패널
	JLabel Player2_Id;    	 		       //플레이어2 아이디
	JLabel Player2_Img;     			   //플레이어2 캐릭터사진
	JLabel Player2_Score;    			   //플레이어2 점수
	int score1 = 0, score2 = 0; //초기값 0주고
	ImageIcon [] Player_Character;  	   //플레이어 캐릭터 사진
	int Player_SelectCharacter = -1;   	       //플레이어캐릭터사진 선택 번호 (피글렛:0,푸:1,타이거:2)
	ImageIcon [] Card_Character;   		   //게임 카드 사진
	boolean isFirst = true;      	       //입장 순서 변수 (처음은 0, 1번째는 1반환, 2번째는 2반환)
	static public JButton[] Panelbtn = new JButton[16]; // 게임 스크린 버튼
	Socket socket;
	ClientMsgSend clientMsgSend;
	
	public ClientMainUI(Socket socket) { //생성자
		this.socket = socket;                          //연결된 소켓 = 메인폼소켓
		Player_Character = new ImageIcon[3];           //플레이어 캐릭터사진을 배열로 담는다
		Card_Character = new ImageIcon[9];             //게임 카드사진을 배열로 담는다
		for(int i=0; i<Player_Character.length; i++) { //플레이어 캐릭터 사진 삽입
			Player_Character[i] = new ImageIcon("images\\"+i+".jpg");
		}
		for(int i=0; i<Card_Character.length; i++) {   //카드 캐릭터 사진 삽입
			Card_Character[i] = new ImageIcon("images\\" +i+".png");
		}
		clientMsgSend = new ClientMsgSend(this);         //서버로 보내는 클래스 객체 생성
		new ClientInitUI(clientMsgSend, this);           //ID폼 생성
		init();                                          //메인폼 화면 생성 메소드
		MainForm_event();                                //메인폼 이벤트 처리 메소드
	}
	
	public void init() { //메인폼 화면 생성 메소드
		
		//화면 정중앙에 나오도록하는 코드
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm=super.getSize();
		int xpos=(int)screen.getWidth() / 2 - (int)frm.getWidth() / 2;
		int ypos=(int)screen.getHeight() / 2 - (int)frm.getHeight() / 2;
		setLocation(xpos, ypos);
		
		setFont(new Font("Andalus", Font.PLAIN, 12));
		setTitle("카드 맞추기 게임");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\atomicbearpink.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1034, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Player1 = new JPanel();
		Player1.setBounds(685, 10, 326, 163);
		contentPane.add(Player1);
		Player1.setLayout(null);
		
		Player1_Id = new JLabel("");
		Player1_Id.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		Player1_Id.setBounds(157, 44, 143, 41);
		Player1.add(Player1_Id);
		
		Player1_Img = new JLabel("");
		Player1_Img.setBounds(12, 36, 120, 120);
		Player1.add(Player1_Img);
		
		Player1_Score = new JLabel("");
		Player1_Score.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		Player1_Score.setBounds(157, 98, 143, 41);
		Player1.add(Player1_Score);
		
		textF = new JTextField("");
		textF.setBounds(685, 731, 326, 21);
		contentPane.add(textF);
		textF.setColumns(10);
		
		textA = new JTextArea();
	    scroll = new JScrollPane(textA);
	    scroll.setBounds(685, 356, 326, 365);
	    textA.setEnabled(false);
	    scroll.setBorder(null);
	    textA.setFont(new Font("Monospaced", Font.PLAIN, 14));
	    textA.setDisabledTextColor(Color.BLACK);
	    DefaultCaret caret = (DefaultCaret) textA.getCaret();	// 채팅스크롤 가장 밑으로 옮기는 부분
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);	    // 위와 동일
	    contentPane.add(scroll);

		
		Player2 = new JPanel();
		Player2.setLayout(null);
		Player2.setBounds(685, 183, 326, 163);
		contentPane.add(Player2);
		
		Player2_Id = new JLabel("");
		Player2_Id.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		Player2_Id.setBounds(157, 44, 143, 41);
		Player2.add(Player2_Id);
		
		Player2_Img = new JLabel("");
		Player2_Img.setBounds(12, 36, 120, 120);
		Player2.add(Player2_Img);
		
		Player2_Score = new JLabel("");
		Player2_Score.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
		Player2_Score.setBounds(157, 98, 143, 41);
		Player2.add(Player2_Score);
		
		GameMain = new JPanel();
		GameMain.setBackground(Color.WHITE);
		GameMain.setBounds(12, 91, 661, 661);
		contentPane.add(GameMain);
		
		Timer = new JPanel();
		Timer.setBackground(Color.DARK_GRAY);
		Timer.setBounds(12, 10, 661, 71);
		contentPane.add(Timer);
				
		setVisible(false);
		setResizable(false);
	}
	
	private void MainForm_event() { //메인폼 이벤트 처리 메소드
		
		//채팅 입력창에 대한 이벤트
		textF.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				String id = ClientInitUI.getId();          //입력받은 id값
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {  //엔터키 눌렀을 때
					if(textF.getText().equals("")) return; //메시지 입력없이 눌렀을 때 
					textA.append(id + " : " + textF.getText() + "\n"); //자기 txtA에 출력
					clientMsgSend.Send_Msg(); //서버에 보내서 다른클라이언트에게 보여준다
					textF.setText("");        //채팅창에 아무것도 업게 한다
				}
			}
		});
	}
	
	public void gameScreen(String msg) { // 게임 화면 버튼 출력 메소드
		int return_GameArr[] = new int[16];
		String[] split_return = msg.substring(msg.indexOf(":")+1).split(",");
		GameButtonActionListener gba;
		for(int i = 0; i <split_return.length; i++){
			return_GameArr[i] = Integer.parseInt(split_return[i]);
			Panelbtn[i] = new JButton(new ImageIcon("images/0.png"));
			Panelbtn[i].setRolloverEnabled(false);			// 버튼 투명 관련 옵션
			Panelbtn[i].setOpaque(false);					// 버튼 투명 관련 옵션
	      	Panelbtn[i].setFocusPainted(false);				// 버튼 투명 관련 옵션
	      	Panelbtn[i].setContentAreaFilled(false);		// 버튼 투명 관련 옵션
	      	Panelbtn[i].setBorderPainted(false);			// 버튼 투명 관련 옵션
	      	gba = new GameButtonActionListener(Panelbtn);
	      	Panelbtn[i].getIcon();
	      	Panelbtn[i].addActionListener(gba);
	      	Panelbtn[i].setName(split_return[i]); 				// 버튼 저장값
	      	Panelbtn[i].setActionCommand(Integer.toString(i));  // 버튼 순서 
	      	GameMain.add(Panelbtn[i]);
		}
		GameMain.setOpaque(true);
		GameMain.setVisible(false);
	}
	
	public void gameScreenOn(){
		GameMain.setVisible(true);
	}
	
}
