package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;

import com.kuiprux.tcbgmbot.Music;
import com.kuiprux.tcbgmbot.TCBGMBot;

public class TCBBMP3Player implements TCBBPlayer {

	PlayState state = PlayState.STOPPED;
	boolean loop = false;
	int index = 0;

	@Override
	public boolean provide(ByteBuffer buffer) {
		if(state != PlayState.PLAYING)
			return false;
		buffer.clear();
		createMusicBuffer(buffer);
		return true;
	}
	
	public boolean setPlayState(PlayState state) {
		System.out.println(state);
		this.state = state;
		if(state == PlayState.STOPPED)
			index = 0;
		return true;
	}

	private void createMusicBuffer(ByteBuffer buffer) {
		if(state == PlayState.PLAYING) {
			Music music = TCBGMBot.ml.getMusic("testMusic"); //TODO
			byte[] data = music.getBytes(index, buffer.capacity()); //TODO
			buffer.put(data);
			index += data.length; //TODO 노래끝나면 stopped로 바꾸기
			//TODO loop
			if(index >= music.getByteLength()) {
				setPlayState(PlayState.STOPPED);
			}
		}
	}
}
