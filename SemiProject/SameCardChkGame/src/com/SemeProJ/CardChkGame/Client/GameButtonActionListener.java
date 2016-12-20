package com.SemeProJ.CardChkGame.Client;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Panel;
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
	static boolean jo = false;
	static String v1;
	static String v2;
	static int n1;
	static int n2;
	
	public GameButtonActionListener(JButton[] Panelbtn) { //생성자
		this.Panelbtn = Panelbtn;
		number = new int[2];
	}
	
	public void return_img() {
		ImageIcon setbtnIcon = new ImageIcon("images/card.png");
		Panelbtn[n1].setIcon(setbtnIcon);
		Panelbtn[n2].setIcon(setbtnIcon);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!GameArr_chkFirst){
			equalChk = Panelbtn[Integer.parseInt(e.getActionCommand())].getName();
			
			btnNumChkArr = Integer.parseInt(e.getActionCommand());
			Icon setbtnIcon = new ImageIcon("images/"+ equalChk +".png");
			System.out.println(Panelbtn[Integer.parseInt(e.getActionCommand())].getName());
			Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon);
			System.out.println(Integer.parseInt(e.getActionCommand()));
			System.out.println("처음 누른 버튼 : " + equalChk);
			GameArr_chkFirst = true;
		}else if(Panelbtn[btnNumChkArr]==Panelbtn[Integer.parseInt(e.getActionCommand())]){
			System.out.println("같은 그림 눌림");
			
		}else{
			Icon setbtnIcon1 = new ImageIcon("images/"+ Panelbtn[Integer.parseInt(e.getActionCommand())].getName() +".png");
			System.out.println(Panelbtn[Integer.parseInt(e.getActionCommand())].getName());
			System.out.println("뭐지?");
			Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon1);
			System.out.println(Integer.parseInt(e.getActionCommand()));
			if(equalChk.equals(Panelbtn[Integer.parseInt(e.getActionCommand())].getName())){
				System.out.println("같습니다");
				Panelbtn[btnNumChkArr].setEnabled(false);
				Panelbtn[Integer.parseInt(e.getActionCommand())].setEnabled(false);
				GameArr_chkFirst = false;
				ClientMsgSend.printWriter.println("Game_Score_and_Array," + ClientMsgSend.id + "," + Integer.toString(btnNumChkArr) + "," + e.getActionCommand() + ",Score++");
				ClientMsgSend.printWriter.flush();
			}else{
				wrong++;
				System.out.println("틀립니다"+ wrong);
				Icon setbtnIcon = new ImageIcon("images/card.png");
				Panelbtn[btnNumChkArr].setIcon(setbtnIcon);
				Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon);
				GameArr_chkFirst = false;
				if(wrong == 2) {
					//2번 틀리면 상대 턴으로 넘어가는 메소드
					ClientMsgSend.printWriter.println("Game_turn");
					ClientMsgSend.printWriter.flush();
					wrong=0;
				}
			}
		}
		/*if(!GameArr_chkFirst) { //첫번째
		Icon setbtnIcon1 = new ImageIcon("images/"+ Panelbtn[Integer.parseInt(e.getActionCommand())].getName() +".png");
		v1 = Panelbtn[Integer.parseInt(e.getActionCommand())].getName();
		n1 = Integer.parseInt(e.getActionCommand());
		Panelbtn[n1].setIcon(setbtnIcon1);
		GameArr_chkFirst = true;
	}else if(Panelbtn[n1]==Panelbtn[n2]){
		System.out.println("같은 그림 눌림");	
	}else { //두번째
		Icon setbtnIcon1 = new ImageIcon("images/"+ Panelbtn[Integer.parseInt(e.getActionCommand())].getName() +".png");
		v2 = Panelbtn[Integer.parseInt(e.getActionCommand())].getName();
		n2 = Integer.parseInt(e.getActionCommand());
		Panelbtn[n2].setIcon(setbtnIcon1);
		System.out.println(" 뭐지1?");
		//GameArr_chkFirst = false;
		if(v1.equals(v2)) {
			System.out.println("같습니다");
			//Panelbtn[btnNumChkArr].setEnabled(false);
			//Panelbtn[Integer.parseInt(e.getActionCommand())].setEnabled(false);
			GameArr_chkFirst = false;
			ClientMsgSend.printWriter.println("Game_Score_and_Array," + ClientMsgSend.id + "," + Integer.toString(btnNumChkArr) + "," + e.getActionCommand() + ",Score++");
			ClientMsgSend.printWriter.flush();
		}else{
			wrong++;
			System.out.println("틀립니다"+ wrong);
			Icon setbtnIcon = new ImageIcon("images/0.png");
			Panelbtn[n1].setIcon(setbtnIcon);
			Panelbtn[n2].setIcon(setbtnIcon);
			GameArr_chkFirst = false;
			if(wrong == 2) {
				//2번 틀리면 상대 턴으로 넘어가는 메소드
				ClientMsgSend.printWriter.println("Game_turn");
				ClientMsgSend.printWriter.flush();
				wrong=0;
			}
		}
		//일단 두번째 클릭하면 뒤집어 지는 메소드
	}*/
		//한번클릭에 무조건 같은 버튼은 한번만 바뀔 수 있다
		//두번클릭 때는 두번 클릭한것을 보여준다 그리고 여기서 값이 다르면 변수에 1주고 같으면 2를 준다 
	}
}
