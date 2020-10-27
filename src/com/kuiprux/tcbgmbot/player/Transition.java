package com.kuiprux.tcbgmbot.player;

public class Transition {
	public final TransitionMode mode;
	public final int duration;
	public final int value;

	public Transition(TransitionMode mode, int duration) {
		this(mode, duration, 0);
	}

	public Transition(TransitionMode mode, int duration, int value) {
		this.mode = mode;
		this.duration = duration;
		this.value = value;
	}
}
