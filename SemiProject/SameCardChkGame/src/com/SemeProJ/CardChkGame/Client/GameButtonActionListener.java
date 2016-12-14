package com.SemeProJ.CardChkGame.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameButtonActionListener implements ActionListener {
	JButton[] Panelbtn;
	int check=0;
	static boolean GameArr_chkFirst = false;
	static String equalChk;
	static int btnNumChkArr = 0;
	

	public GameButtonActionListener(JButton[] Panelbtn) {
		// TODO Auto-generated constructor stub
		this.Panelbtn = Panelbtn;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//System.out.println(e.getActionCommand());
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
				System.out.println("틀립니다");
				/*Icon flashbtnIcon = new ImageIcon("images/"+Panelbtn[Integer.parseInt(e.getActionCommand())].getName()+".png");
				Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(flashbtnIcon);
				try{
				Thread.sleep(1000);
				} catch (InterruptedException Ie){
					Ie.printStackTrace();
				}*/
				Icon setbtnIcon = new ImageIcon("images/0.png");
				Panelbtn[btnNumChkArr].setIcon(setbtnIcon);
				GameArr_chkFirst = false;
			}
		}
	}
}
