package com.SemeProJ.CardChkGame.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameButtonActionListener implements ActionListener {
	ClientMainUI clientMainUI;
	JButton[] Panelbtn;
	static int check=0;
	static boolean GameArr_chkFirst = false;
	static String equalChk; //배열 순서
	static int btnNumChkArr = 0;
	static int [] number; //순서 저장
	static String [] value = new String[2];  //값 저장;
	static int wrong = 0;
	public GameButtonActionListener(JButton[] Panelbtn) {
		this.Panelbtn = Panelbtn;
		number = new int[2];
	}
	
	public void return_img() {
		ImageIcon setbtnIcon5 = new ImageIcon("C:\\Users\\user2\\git\\SameCardChkGame\\SemiProject\\SameCardChkGame\\images\\0.png");
		Panelbtn[number[0]].setIcon(setbtnIcon5);
		Panelbtn[number[1]].setIcon(setbtnIcon5);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!GameArr_chkFirst){
			equalChk = Panelbtn[Integer.parseInt(e.getActionCommand())].getName();
			
			btnNumChkArr = Integer.parseInt(e.getActionCommand());
			Icon setbtnIcon = new ImageIcon("images/"+ equalChk +".png");
			Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon);
			System.out.println("처음 누른 버튼 : " + equalChk);
			GameArr_chkFirst = true;
		}else if(Panelbtn[btnNumChkArr]==Panelbtn[Integer.parseInt(e.getActionCommand())]){
			System.out.println("같은 그림 눌림");
			
		}else{
			
			if(equalChk.equals(Panelbtn[Integer.parseInt(e.getActionCommand())].getName())){
				System.out.println("같습니다");
				Panelbtn[btnNumChkArr].setEnabled(false);
				Icon setbtnIcon = new ImageIcon("images/"+ equalChk +".png");
				Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon);
				Panelbtn[Integer.parseInt(e.getActionCommand())].setEnabled(false);
				GameArr_chkFirst = false;
				ClientMsgSend.printWriter.println("Game_Score_and_Array," + ClientMsgSend.id + "," + Integer.toString(btnNumChkArr) + "," + e.getActionCommand() + ",Score++");
				ClientMsgSend.printWriter.flush();
			}else{
				wrong++;
				System.out.println("틀립니다"+ wrong);
				Icon setbtnIcon = new ImageIcon("images/0.png");
				Panelbtn[btnNumChkArr].setIcon(setbtnIcon);
				GameArr_chkFirst = false;
				if(wrong == 2) {
					//2번 틀리면 상대 턴으로 넘어가는 메소드
					ClientMsgSend.printWriter.println("Game_turn");
					ClientMsgSend.printWriter.flush();
					System.out.println("ㅠㅠ 너 이제 못해");
					wrong=0;
				}
			}
		}
	}
}
