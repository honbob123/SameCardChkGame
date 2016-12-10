package com.SemeProJ.CardChkGame.Client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClientInitUI extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static JTextField tf = new JTextField(10);
	JButton btn = new JButton("확인");
	ClientMsgSend msgSend;
	ClientMainUI clientUI;

	public ClientInitUI() {
	}

	public ClientInitUI(ClientMsgSend msgSend, ClientMainUI clientUI) {
		super("아이디 생성");
		this.msgSend = msgSend;
		this.clientUI = clientUI;

		setLayout(new FlowLayout());
		add(new JLabel("아이디"));
		add(tf);
		add(btn);
		btn.addActionListener(this);

		// 화면 정중앙에 나오도록하는 코드
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int xpos = (int) screen.getWidth() / 2 - (int) frm.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - (int) frm.getHeight() / 2;
		setLocation(xpos, ypos);
		setBounds(300, 300, 250, 100);

		setVisible(true);
	}

	static public String getId() { // 스태틱지정하여 어디든 접근 가능
		return tf.getText(); // 입력한 id값
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 확인버튼 누룰 떄 이벤트
		msgSend.sendMsg();
		clientUI.isFirst = false; // 처음 입장 false으로 줌
		clientUI.setVisible(true); // MainForm JFrame 띄우기
		this.dispose(); // 창 닫기
	}
}