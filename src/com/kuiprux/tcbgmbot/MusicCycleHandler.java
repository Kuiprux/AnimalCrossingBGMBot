package com.kuiprux.tcbgmbot;

import java.time.LocalDateTime;
import java.util.TimerTask;

public class MusicCycleHandler extends TimerTask {
	
	int currentHour;
	
	public MusicCycleHandler() {
		   LocalDateTime now = LocalDateTime.now(); 
		   currentHour = now.getHour();
	}
	
	@Override
	public void run() {
		   //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now(); 
		   if(currentHour != now.getHour()) {
			   
		   }
	}

}
