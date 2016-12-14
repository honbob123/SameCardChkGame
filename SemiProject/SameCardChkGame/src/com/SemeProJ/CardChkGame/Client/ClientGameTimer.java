package com.SemeProJ.CardChkGame.Client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientGameTimer {

	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			int a = 0;
			public void run() {
				// task to run goes here
				if(a = 0){
				System.out.println("플레이어 1 턴");
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
	}
}
