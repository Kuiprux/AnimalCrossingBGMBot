package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kuiprux.tcbgmbot.Music;
import com.kuiprux.tcbgmbot.TCBGMBot;
import com.kuiprux.tcbgmbot.ToneGroup;

public class TCBTonePlayer extends TCBBMP3Player {
	
	//260ms
	int overlapFrame;
	List<ToneInfo> toneList = new ArrayList<>();
			
	public TCBTonePlayer(int overlapFrame) {
		this.overlapFrame = overlapFrame;
	}
	
	public void addTone(ToneInfo tone) {
		toneList.add(tone);
	}
	
	public void clearTone() {
		toneList.clear();
	}

	@Override
	//TODO idk if loop function is needed, but it it is, implement it.
	protected void createMusicBuffer(ByteBuffer buffer) { //TODO Rewrite this method
		if (state == PlayState.PLAYING) {
			List<Integer> toneOffset = new ArrayList<>();
			List<ByteBuffer> toneBuffers = new ArrayList<>();
			int tmpIndex = index;
			int toneStartIndex = 0;
			for(int i = 0; i < toneList.size(); i++) {
				if(tmpIndex >= index+buffer.capacity())
					break;
				int readStartIndex = Math.max(index - toneStartIndex, toneStartIndex);
				ToneInfo tone = toneList.get(i);
				ToneGroup tGroup = TCBGMBot.ml.getToneGroup("testTone"); //TODO
				byte[] data = new byte[buffer.capacity()];
				int putLength = tGroup.getBytes(tone.name, data, readStartIndex, buffer.capacity()-readStartIndex); 
				ByteBuffer buf = ByteBuffer.allocate(putLength);
				buf.put(Arrays.copyOfRange(data, 0, putLength));
				toneBuffers.add(buf);
				toneOffset.add(tone.overlapFrame);
				toneStartIndex += (tone.overlapFrame < 0 ? overlapFrame : tone.overlapFrame);
			}
			Util.mergeMusicBytes(toneBuffers.toArray
					(new ByteBuffer[0]), toneOffset.stream().mapToInt(i->i).toArray(), buffer);
			processMusicBytes(buffer);
		}
	}
}
