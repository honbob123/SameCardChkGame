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
				//System.out.println("Game_Score_and_Array," + ClientMsgSend.id + "," + Integer.toString(btnNumChkArr) + "," + e.getActionCommand() + ",Score++");
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
	
	/*@Override
	public void actionPerformed(ActionEvent e) {
		
		if(GameArr_chkFirst == false) {//첫번째 선택
			GameArr_chkFirst = true; //두번째 선택하게 만든다
			equalChk1 = Panelbtn[Integer.parseInt(e.getActionCommand())].getName(); //배열 저장값
			value[0] = equalChk1;                                               //0번째에 첫밴째 선택 저장값 저장
			number[0] = Integer.parseInt(e.getActionCommand());                //0번째에 첫번째 선택 배열 순서
			ImageIcon setbtnIcon1 = new ImageIcon("C:\\Users\\user2\\git\\SameCardChkGame\\SemiProject\\SameCardChkGame\\images\\"+ equalChk1 + ".png");
			Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon1);  //클릭한 배열 저장값의 이미지 출력
			System.out.println("뭐냐고");
		}else { //두번째 선택
			System.out.println("진짜 뭐냐고");
			equalChk2 = Panelbtn[Integer.parseInt(e.getActionCommand())].getName(); //배열 저장값
			value[1] = equalChk2;  //1번째에 두번째 선택 저장값 저장
			number[1] = Integer.parseInt(e.getActionCommand());  //1번째에 두번째 선택배열 순서
			//ImageIcon setbtnIcon2 = new ImageIcon("images/"+ equalChk2 +".png"); //
			//Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon2); //클릭한 배열 저장값의 이미지 출력
			if(value[0].equals(value[1])) {  //저장값이 같을 경우 사라지게 한다
				System.out.println(equalChk2);
				ImageIcon setbtnIcon3 = new ImageIcon("C:\\Users\\user2\\git\\SameCardChkGame\\SemiProject\\SameCardChkGame\\images\\"+ equalChk2 + ".png");
				Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon3);
				Panelbtn[number[0]].setEnabled(false);
				Panelbtn[number[0]].setDisabledIcon(new ImageIcon("C:\\Users\\user2\\Desktop\\99.png"));
				Panelbtn[number[1]].setEnabled(false);
				Panelbtn[number[1]].setDisabledIcon(new ImageIcon("C:\\Users\\user2\\Desktop\\99.png"));
				//clientMainUI.GameMain.updateUI();
				GameArr_chkFirst = false;
			}else {
				System.out.println(equalChk2);
				ImageIcon setbtnIcon4 = new ImageIcon("C:\\Users\\user2\\git\\SameCardChkGame\\SemiProject\\SameCardChkGame\\images\\"+ equalChk2 + ".png");
				//ImageIcon setbtnIcon5 = new ImageIcon("C:\\Users\\user2\\git\\SameCardChkGame\\SemiProject\\SameCardChkGame\\images\\0.png");
				Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon4);
				System.out.println("다른이미지 보여줬다");
				//Panelbtn[number[0]].setIcon(setbtnIcon5);
				//Panelbtn[number[1]].setIcon(setbtnIcon5);
				return_img();
				System.out.println("뒤집었다!");
				//clientMainUI.GameMain.updateUI();
				GameArr_chkFirst = false;
			}
			//clientMainUI.GameMain.updateUI();
		}
		//clientMainUI.GameMain.updateUI()
	}*/
		//System.out.println(e.getActionCommand());
		/*if(!GameArr_chkFirst){
			equalChk = Panelbtn[Integer.parseInt(e.getActionCommand())].getName(); //배열 저장값
			
			btnNumChkArr = Integer.parseInt(e.getActionCommand());  //배열 순서
			Icon setbtnIcon = new ImageIcon("images/"+ equalChk +".png"); //
			Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(setbtnIcon); //클릭한 배열 저장값의 이미지 출력
			System.out.println("처음 누른 버튼 : " + equalChk);
			GameArr_chkFirst = true;
		}else if(Panelbtn[btnNumChkArr]==Panelbtn[Integer.parseInt(e.getActionCommand())]){ 
			System.out.println("같은 그림 눌림");
			
		}else{

			if(equalChk.equals(Panelbtn[Integer.parseInt(e.getActionCommand())].getName())){    
				System.out.println("같습니다");
				Panelbtn[btnNumChkArr].setVisible(false);
				Panelbtn[Integer.parseInt(e.getActionCommand())].setVisible(false);
				GameArr_chkFirst = false;
			}else{
				System.out.println("틀립니다");
				Icon setbtnIcon = new ImageIcon("images/0.png");
				Panelbtn[btnNumChkArr].setIcon(setbtnIcon);     //뒷면으로 표시
				GameArr_chkFirst = false;
			}
		}*/
		
		//System.out.println(e.getActionCommand());
		/*if(!GameArr_chkFirst){
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
			}else{
				System.out.println("틀립니다");
				Icon flashbtnIcon = new ImageIcon("images/"+Panelbtn[Integer.parseInt(e.getActionCommand())].getName()+".png");
				Panelbtn[Integer.parseInt(e.getActionCommand())].setIcon(flashbtnIcon);
				Icon setbtnIcon = new ImageIcon("images/0.png");
				Panelbtn[btnNumChkArr].setIcon(setbtnIcon);
				GameArr_chkFirst = false;
			} 
		}
	}*/
}
