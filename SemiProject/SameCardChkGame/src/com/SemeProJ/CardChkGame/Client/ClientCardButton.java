package com.SemeProJ.CardChkGame.Client;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ClientCardButton extends JButton {
	int value;
	ImageIcon cardImage;
	ImageIcon backcardImage;
	boolean cheak;
	public ClientCardButton(String number, ImageIcon backcardImage) {
		setIcon(backcardImage);
	}
	
	public ClientCardButton (String number, int value, ImageIcon cardImage, ImageIcon backcardImage) { //생성자
	    setIcon(backcardImage);
		this.value = value;
		this.cardImage = cardImage;
		this.backcardImage = backcardImage;
		cheak = true;
	}
	
	public int clickButton() { //클릭했을 실행되는 메소드
		this.setIcon(cardImage); //앞면을 보여준다
		return value;       //저장값을 리턴한
	}
	
	public void returnImage() { //뒷면으로 다시 보여주는 메소드
		this.setIcon(backcardImage);
	}
	
}
