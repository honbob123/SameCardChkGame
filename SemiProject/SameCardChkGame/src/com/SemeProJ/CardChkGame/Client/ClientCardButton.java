package com.SemeProJ.CardChkGame.Client;
/*
 * 
 * */
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ClientCardButton extends JButton {
	int num;
	ImageIcon cardImage;
	
	public ClientCardButton (int num, ImageIcon CardImage) {
		this.num = num;
		this.cardImage = cardImage;
		
	}
	

}
