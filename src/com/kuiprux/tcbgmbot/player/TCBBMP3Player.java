package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kuiprux.tcbgmbot.Music;
import com.kuiprux.tcbgmbot.TCBGMBot;

public class TCBBMP3Player implements TCBBPlayer {

	PlayState state = PlayState.STOPPED;
	boolean loop = true;
	int index = 0;
	int volume = 100;

	List<Transition> transitionList = new ArrayList<Transition>();
	int countingFrames = -1;
	int beforeValue = 0;

	@Override
	public boolean provide(ByteBuffer buffer) {
		if (state != PlayState.PLAYING)
			return false;
		buffer.clear();
		// update();
		createMusicBuffer(buffer);
		return true;
	}

	public boolean setPlayState(PlayState state) {
		this.state = state;
		if (state == PlayState.STOPPED) {
			index = 0;
		}
		return true;
	}

	public void setVolume(int vol) {
		volume = vol;
	}

	/*
	 * private void update() { if(countingMillis < 0) { if(transitionList.size() >
	 * 0) { countingMillis = 0; } } else { if(transitionList.size() == 0) // Just in
	 * case countingMillis = -1; Transition transition = transitionList.get(0);
	 * switch(transition.mode) { case FADE_IN: volume = transi case FADE_OUT: case
	 * SET_VOLUME: } } }
	 */

	public void addTransition(Transition transition) {
		transitionList.add(transition);
	}

	private short processMusicShort(short datum, boolean increaseFrame) {
		System.out.println(volume);
		// int volume = this.volume;
		if (countingFrames < 0) {
			if (transitionList.size() > 0) {
				countingFrames = 0;
				beforeValue = volume;
			}
		} else if (transitionList.size() == 0) {
			countingFrames = -1;
		}

		if (countingFrames >= 0) {
			Transition transition = transitionList.get(0);
			switch (transition.mode) {
			case FADE_IN:
				volume = (int) (transition.value * ((float) countingFrames / (transition.duration * 48)));
				break;
			case FADE_OUT:
				volume = (int) (beforeValue * (1 - (float) countingFrames / (transition.duration * 48)));
				break;
			case SET_VOLUME:
				volume = transition.value;
			}

			if (transition.duration < 0 || transition.duration * 48 <= countingFrames) {
				if (transition.duration >= 0 && transition.mode == TransitionMode.SET_VOLUME) {
					volume = beforeValue;
				}
				transitionList.remove(0);
				countingFrames = 0;
			} else {
				if (increaseFrame)
					countingFrames++;
			}
		}
		return (short) (datum * volume / 100F);
	}

	private void createMusicBuffer(ByteBuffer buffer) {
		if (state == PlayState.PLAYING) {
			Music music = TCBGMBot.ml.getMusic("testMusic"); // TODO nullpointerexception
			byte[] data = new byte[buffer.capacity()];
			int putLength = 0;
			do {
				int actualLength = music.getBytes(data, index, buffer.capacity() - putLength); // TODO
				for (int i = 0; i < data.length / 4; i++) {
					short valLeft = processMusicShort((short) (((data[i * 4] & 0xFF) << 8) | (data[i * 4 + 1] & 0xFF)),
							true);
					data[i * 4] = (byte) ((valLeft >> 8) & 0xff);
					data[i * 4 + 1] = (byte) (valLeft & 0xff);

					short valRight = processMusicShort(
							(short) (((data[i * 4 + 2] & 0xFF) << 8) | (data[i * 4 + 3] & 0xFF)), false);
					data[i * 4 + 2] = (byte) ((valRight >> 8) & 0xff);
					data[i * 4 + 3] = (byte) (valRight & 0xff);
				}
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
