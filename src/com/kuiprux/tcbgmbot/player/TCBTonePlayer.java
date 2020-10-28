package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kuiprux.tcbgmbot.Music;
import com.kuiprux.tcbgmbot.TCBGMBot;

public class TCBTonePlayer extends TCBBMP3Player {
	
	//260ms
	int overlapFrame;
	List<Tone> toneList = new ArrayList<>();
			
	public TCBTonePlayer(int overlapFrame) {
		this.overlapFrame = overlapFrame;
	}
	
	public void addTone(Tone tone) {
		toneList.add(tone);
	}

	@Override
	protected void createMusicBuffer(ByteBuffer buffer) {
		if (state == PlayState.PLAYING) {
			Music music = TCBGMBot.ml.getMusic("testMusic"); // TODO nullpointerexception
			byte[] data = new byte[buffer.capacity()];
			int putLength = 0;
			do {
				int actualLength = music.getBytes(data, index, buffer.capacity() - putLength); // TODO
				processMusicBytes(data);
				buffer.put(Arrays.copyOfRange(data, 0, actualLength));
				index += actualLength; // TODO �끂�옒�걹�굹硫� stopped濡� 諛붽씀湲�
				putLength += actualLength;
				if (putLength < buffer.capacity() && loop) {
					index = 0;
				}
			} while (putLength < buffer.capacity() && loop);
			if (!loop && index >= music.getByteLength()) {
				setPlayState(PlayState.STOPPED);
			}
		}
	}
}
