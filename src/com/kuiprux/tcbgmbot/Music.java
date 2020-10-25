package com.kuiprux.tcbgmbot;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	
	public static final AudioFormat AUDIO_FORMAT = new AudioFormat(48000f, 16, 2, true, true);

	ByteBuffer musicData;
	
	public Music(String filename) throws UnsupportedAudioFileException, IOException {
			File file = new File(filename);
			AudioInputStream in= AudioSystem.getAudioInputStream(file);
			AudioInputStream din = AudioSystem.getAudioInputStream(AUDIO_FORMAT, in);
			byte[] bytes = din.readAllBytes();
			musicData = ByteBuffer.wrap(bytes);
	}
	
	public int getBytes(byte[] data, int index, int length) {
		if(musicData == null)
			return 0;
		int actualLength = Math.min(length, musicData.capacity()-index);
		musicData.get(index, data, 0, actualLength);
		return actualLength;
	}
	
	public int getByteLength() {
		return musicData.capacity();
	}
}
