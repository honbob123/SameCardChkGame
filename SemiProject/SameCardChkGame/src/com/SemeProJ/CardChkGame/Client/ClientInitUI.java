package com.SemeProJ.CardChkGame.Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ClientInitUI extends JFrame {

	private JPanel contentPane;      // 컨텐트 팬
	static JTextField userId;        // 아이디 입력 창
	private JLabel character_choice; // 캐럭터 선택 레이블
	private JLabel id_enter;         // 아이디 입력 레이블
	private JButton btn_Make;        // 확인 버튼
	private JLabel character_label;  // 캐릭터 소개 레이블
	private JTextArea character_Name;// 캐릭터 소개 텍스트창
	private JButton[] jr;            // 캐릭터 이미지 저장용 배열 필드

	ClientMsgSend clientMsgSend;
	ClientMainUI clientMainUI;

	public ClientInitUI() {
	}

	public ClientInitUI(ClientMsgSend clientMsgSend, ClientMainUI clientMainUI) { // 생성자
		this.clientMsgSend = clientMsgSend;
		this.clientMainUI = clientMainUI;
		init(); // ID폼 화면 생성 메소드
		ID_event(); // ID폼 이벤트 처리 메소드
	}

	private void init() { // ID폼 화면 생성 메소드
		setBackground(new Color(224, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\atomicbearpink.png"));
		// 윈도우창  좌상단 아이콘 변경	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setForeground(Color.WHITE);
		setFont(new Font("Aharoni", Font.PLAIN, 15));
		setResizable(false);
		setTitle("아이디 생성");
		setBounds(100, 100, 632, 374);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(177, 205, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		character_label = new JLabel("    캐릭터");
		character_label.setHorizontalAlignment(SwingConstants.LEFT);
		character_label.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		character_label.setBounds(120, 257, 87, 43);
		contentPane.add(character_label);
		
		character_Name = new JTextArea();
		character_Name.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		character_Name.setBounds(212, 266, 178, 21);
		character_Name.setDisabledTextColor(Color.BLACK);
		character_Name.setEnabled(false);
		contentPane.add(character_Name);
		
		id_enter = new JLabel("아이디 입력");
		id_enter.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		id_enter.setBounds(120, 287, 87, 43);
		contentPane.add(id_enter);

		userId = new JTextField();
		userId.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
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
		btn_Make.setBackground(new Color(177, 205, 255));
		btn_Make.setFont(new Font("한컴 윤체 M", Font.PLAIN, 16));
		btn_Make.setBounds(420, 296, 79, 23);
		contentPane.add(btn_Make);
		
		jr = new JButton[3];

		for (int i = 0; i < jr.length; i++) { // 캐릭터 이미지 jr 배열필드에 저장 시키는 for문
			ActionButtonListener action01 = new ActionButtonListener(i);
			jr[i] = new JButton(new ImageIcon("images\\c_" + i + ".png"));

			switch (i) { // 변수 i값 으로 각 캐릭터 버튼의 좌표값을 설정 해 준다.
			case 0:
				jr[i].setBounds(87, 102, 120, 120);
				jr[i].setOpaque(false);
				jr[i].setFocusable(false);
				jr[i].setContentAreaFilled(false);
				jr[i].setBorderPainted(false);
				jr[i].setRolloverEnabled(false);
				contentPane.add(jr[i]);
				break;
			case 1:
				jr[i].setBounds(398, 102, 120, 120);
				jr[i].setOpaque(false);
				jr[i].setFocusable(false);
				jr[i].setContentAreaFilled(false);
				jr[i].setBorderPainted(false);
				jr[i].setRolloverEnabled(false);
				contentPane.add(jr[i]);
				break;
			case 2:
				jr[i].setBounds(243, 102, 120, 120);
				jr[i].setOpaque(false);
				jr[i].setFocusable(false);
				jr[i].setContentAreaFilled(false);
				jr[i].setBorderPainted(false);
				jr[i].setRolloverEnabled(false);
				contentPane.add(jr[i]);
				break;
			}
			jr[i].addActionListener(action01); // 각 캐릭터 버튼 클릭시 실행할 이벤트 호출 (MouseListener 대체코드)
		}

		// 화면 중앙에 나오게 하는 코드
		Dimension frameSize = getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);

		this.setVisible(true);
	}

	private void ID_event() { // ID폼 이벤트 처리 메소드
		btn_Make.addActionListener(new ActionListener() { // 확인버튼을 눌렀을 때

			@Override
			public void actionPerformed(ActionEvent e) {
				if(clientMainUI.Player_SelectCharacter == -1 || userId.getText().equals("")){
					JOptionPane.showMessageDialog(null, "아이디와 캐릭터는 반드시 선택해야 합니다!","님?", JOptionPane.ERROR_MESSAGE);
				}
				else{
				clientMsgSend.Send_Msg();      // 확인 누를때 채팅창에 입장글과 상대 클라이언트에게 입장글을 보여준다
				clientMsgSend.Send_Info();     // player정보를 패널에 넣어준다
				clientMainUI.isFirst = false;  // 처음입장여부를 아니요로 준다
				clientMainUI.setVisible(true); // MainForm창이 뜬다
				dispose();                     // 확인 버튼을 누르면 사라진다
				}
			}
		});
	}

	static public String getId() {      // 스태틱으로 지정해서 어디서든 호출가능
		return userId.getText().trim(); // trim()메소드는 공백을 제외한다
	}

	public class ActionButtonListener implements ActionListener { //내부 클래스 선언  인터페이스 호출 : ActionListener
		int Characternumber; // 선택된 캐릭터 값 저장용 필드

		public ActionButtonListener() {
		};

		public ActionButtonListener(int i) {
			Characternumber = i;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch (Characternumber) {
			case 0:
				character_Name.setText("");
				character_Name.setText("디바");
				System.out.println("피그렛사진 눌렸다");
				clientMainUI.Player_SelectCharacter = 0;
				break;
			case 1:
				character_Name.setText("");
				character_Name.setText("메르시");
				System.out.println("푸사진 눌렸다");
				clientMainUI.Player_SelectCharacter = 1;
				break;
			case 2:
				character_Name.setText("");
				character_Name.setText("위도우 메이커");
				System.out.println("타이거사진 눌렸다");
				clientMainUI.Player_SelectCharacter = 2;
				break;
			default:
				break;
			}
		}
	}
}