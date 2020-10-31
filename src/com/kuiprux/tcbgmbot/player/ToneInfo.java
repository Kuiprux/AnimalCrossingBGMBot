package com.kuiprux.tcbgmbot.player;

public class ToneInfo {

	public final String name;
	public final int overlapFrame;
	
	public ToneInfo(String name) {
		this(name, -1);
	}
	
	public ToneInfo(String name, int overlapFrame) {
		this.name = name;
		this.overlapFrame = overlapFrame;
	}
}
