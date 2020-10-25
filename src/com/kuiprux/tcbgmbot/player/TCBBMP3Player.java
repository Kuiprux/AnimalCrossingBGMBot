package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.kuiprux.tcbgmbot.Music;
import com.kuiprux.tcbgmbot.TCBGMBot;

public class TCBBMP3Player implements TCBBPlayer {

	PlayState state = PlayState.STOPPED;
	boolean loop = true;
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
			Music music = TCBGMBot.ml.getMusic("testMusic"); //TODO nullpointerexception
			byte[] data = new byte[buffer.capacity()];
			int putLength = 0;
			do {
				int actualLength = music.getBytes(data, index, buffer.capacity() - putLength); //TODO
				buffer.put(Arrays.copyOfRange(data, 0, actualLength));
				index += actualLength; //TODO �끂�옒�걹�굹硫� stopped濡� 諛붽씀湲�
				putLength += actualLength;
				if(putLength < buffer.capacity() && loop) {
					index = 0;
				}
			} while(putLength < buffer.capacity() && loop);
			//TODO loop
			if(!loop && index >= music.getByteLength()) {
				setPlayState(PlayState.STOPPED);
			}
		}
	}
}
