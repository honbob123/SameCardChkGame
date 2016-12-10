package com.SemeProJ.CardChkGame.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMsgReceive extends Thread { // 서버 메세지 수신
	Socket socket;
	ClientMainUI clientUI;
	PrintWriter printWriter = null;

	public ClientMsgReceive(Socket socket, ClientMainUI clientUI) {
		this.socket = socket;
		this.clientUI = clientUI;
	}
	
	// 추가 작업사항
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * 서버에서 채팅메세지와 카드 정보, 턴 정보, 점수 등등 각각의 메세지들이 수신되므로,
	 * 메세지의 수신시 Command 구간을 잘 분류하여, ClientMainUI에서 표시할 작업들을 지정해야 함
	 * 현재 메세지 구조 ------> id : TEXT
	 * 이후 변경해야 할 메세지 구소
	 * final byte FILED_SEPARATOR_B = (byte)0x11;
	 * 
	 * 예) 1. 카드 위치 수정   		----> CARD_CH:INFO(배열 형태)
	 *    2. 턴 정보 및 타이머 표시  	----> TURN:ID:점수정보(숫자형태) 
	 *    3. 점수 표시  			----> POINT:id:점수정보(숫자형태)

	 *    4. 일반 채팅 메세지 수신  	----> CHAT:id : TEXT
	 *    5. 로그인  			----> LOGIN:id 님이 로그인했습니다!
	 */
	@Override
	public void run() {
		System.out.println("[ClientMsgReceive] run() - 서버에서 전송한 메세지 수신 시작");
		BufferedReader bufferedReader = null;
		try {
			// 서버로부터 전송된 문자열 읽어오기 위한 스트림객체 생성
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						
			while (true) {
				// 서버에서 채팅 메세지 수신
				String sMsg = bufferedReader.readLine();
				
				if (sMsg == null) {
					System.out.println("[ClientMsgReceive] run() - 상대방 클라이언트 접속 종료");
					sMsg = "User Connection Out";
					break;
				}
				String[] arrInfo = sMsg.split(":");
				String sCmd = arrInfo[0];
				String sInfo = arrInfo[1];
				
				if("CARD_CH".equals(sCmd)) {
					// 처음 시작 했을시 카드 배치 이후 이벤트 발생 할때마다 카드 배치 업데이트
					//clientUI.gameScreenUpdate("");
				}
				if("TURN".equals(sCmd)) {
					// 상대의 턴 또는 남의 턴인 경우 화면 조정
					//clientUI.gameScreenTurn("");
				}
				if("CHAT".equals(sCmd)) {
					clientUI.txtA.append(sInfo + "\n"); // 메시지 표시 
					clientUI.txtA.setCaretPosition(clientUI.txtA.getDocument().getLength()); // 채팅 메세지 포지션 새로고침 
				}
				
				System.out.println("[ClientMsgReceive] run() - 상대방 메세지 sMsg : " + sMsg);
				
				clientUI.txtA.append(sMsg + "\n"); // 메시지 표시 
				clientUI.txtA.setCaretPosition(clientUI.txtA.getDocument().getLength()); // 채팅 메세지 포지션 새로고침 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				
			}
		}
	}
}