package com.SemeProJ.CardChkGame.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameButtonActionListener implements ActionListener {
	ClientMainUI clientMainUI;
	JButton[] Panelbtn;
	static boolean GameArr_chkFirst = false; //처음 클릭의 여부 확인
	static String equalChk;                  //처음 클릭한 카드 저장 값
	static int btnNumChkArr = 0;             //처음 클릭한 카드 위치 값
	static int wrong = 0;                    //틀렸을 때 알아주는 변수
	
	public GameButtonActionListener(JButton[] Panelbtn) { //생성자
		this.Panelbtn = Panelbtn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { //버튼을 클릭했을 때 이벤트 
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
				Panelbtn[btnNumChkArr].setEnabled(false);
				Panelbtn[Integer.parseInt(e.getActionCommand())].setEnabled(false);
				GameArr_chkFirst = false;
				ClientMsgSend.printWriter.println("Game_Score_and_Array," + ClientMsgSend.id + "," + Integer.toString(btnNumChkArr) + "," + e.getActionCommand() + ",Score++");
				ClientMsgSend.printWriter.flush();
			}else{
				wrong++;
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
	}
}
