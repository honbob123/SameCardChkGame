package com.SemeProJ.CardChkGame.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameButtonActionListener implements ActionListener {
	JButton[] Panelbtn;
	int check=0;
	boolean GameArr_chkFirst = false;
	String equalChk;
	

	public GameButtonActionListener(JButton[] Panelbtn) {
		// TODO Auto-generated constructor stub
		this.Panelbtn = Panelbtn;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println(e.getActionCommand());
		if(!GameArr_chkFirst){
			equalChk = Panelbtn[Integer.parseInt(e.getActionCommand())].getIcon().toString();
			GameArr_chkFirst = true;
		}else{
			if(equalChk.equals(Panelbtn[Integer.parseInt(e.getActionCommand())].getIcon().toString())){
				System.out.println("같습니다");
			}else{
				System.out.println("틀립니다");
			}
			
		}


	}

	
	
}
