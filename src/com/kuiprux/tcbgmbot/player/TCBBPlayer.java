package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;

public interface TCBBPlayer {

	public boolean provide(ByteBuffer buffer);

	public boolean setPlayState(PlayState state);

	public void addTransition(Transition transition);
}
