package com.kuiprux.tcbgmbot.player;

public class Tone {

	public final String name;
	public final int overlapFrame;
	
	public Tone(String name) {
		this(name, -1);
	}
	
	public Tone(String name, int overlapFrame) {
		this.name = name;
		this.overlapFrame = overlapFrame;
	}
}
