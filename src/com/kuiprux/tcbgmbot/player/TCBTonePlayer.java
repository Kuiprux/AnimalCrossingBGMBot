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
	public boolean setPlayState(PlayState state) {
		if(toneList.size() == 0)
			return false;
		return super.setPlayState(state);
	}

	@Override
	//TODO idk if loop function is needed, but it it is, implement it.
	protected void createMusicBuffer(ByteBuffer buffer) { //TODO Rewrite this method
		if (state == PlayState.PLAYING) {
			List<Integer> toneOffset = new ArrayList<>();
			List<ByteBuffer> toneBuffers = new ArrayList<>();
			int toneStartIndex = 0;
			//System.out.println("start" + "\t" + index + "\t" + buffer.capacity());
			for(int i = 0; i < toneList.size(); i++) {
				System.out.println("\t\t\t\t\t\t\t\t" + i + "\t" + toneStartIndex);
				if(toneStartIndex >= index+buffer.capacity())
					break;
				
				int readStartIndex = Math.max(index - toneStartIndex, 0);
				ToneInfo tone = toneList.get(i);
				ToneGroup tGroup = TCBGMBot.ml.getToneGroup("testtone"); //TODO
				byte[] data = new byte[buffer.capacity()];
				int putLength = tGroup.getBytes(tone.name, data, readStartIndex, buffer.capacity()); 
				
				ByteBuffer buf = ByteBuffer.allocate(putLength);
				buf.put(Arrays.copyOfRange(data, 0, putLength));
				toneBuffers.add(buf);
				int overlapFrame = (tone.overlapFrame < 0 ? this.overlapFrame : tone.overlapFrame)*Util.BYTE_IN_A_FRAME;
				toneOffset.add(overlapFrame);
				Util.mergeMusicBytes(toneBuffers.toArray
						(new ByteBuffer[0]), toneOffset.stream().mapToInt(j->j).toArray(), buffer);
				processMusicBytes(buffer);
				
				System.out.println("\t\t\t\t\t\t\t\t\t\t" + toneStartIndex + "\t" + readStartIndex + "\t" + putLength + "\t" + overlapFrame);
				toneStartIndex += overlapFrame;
				if(i == toneList.size()-1 && putLength < buffer.capacity())
					this.setPlayState(PlayState.STOPPED);
			}
			index += buffer.capacity();
		}
	}
}
