package com.kuiprux.tcbgmbot;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ToneGroup {
	
	public static final AudioFormat AUDIO_FORMAT = new AudioFormat(48000f, 16, 2, true, true);

	Map<String, ByteBuffer> toneData = new HashMap<>();
	
	public ToneGroup(String directoryPath, String fileprefix) throws UnsupportedAudioFileException, IOException {
		File dir = new File(directoryPath);
		int toneNameStart = fileprefix.length();
		for(File file : dir.listFiles()) {
			if(file.getName().startsWith(fileprefix)) {
				toneData.put(file.getName().substring(toneNameStart).split("\\.")[0], loadTone(file));
			}
		}
	}
	
	private ByteBuffer loadTone(File file) throws UnsupportedAudioFileException, IOException {
		AudioInputStream in= AudioSystem.getAudioInputStream(file);
		AudioInputStream din = AudioSystem.getAudioInputStream(AUDIO_FORMAT, in);
		byte[] bytes = din.readAllBytes();
		return ByteBuffer.wrap(bytes);
	}
	
	public int getBytes(String name, byte[] data, int index, int length) {
		if(toneData == null || toneData.get(name) == null)
			return 0;
		ByteBuffer tone = toneData.get(name);
		int actualLength = Math.min(length, tone.capacity()-index);
		//System.out.println("\t\t\t" + name + "\t" + tone.capacity() + "\t" + index + "\t" + actualLength + "\t" + length);
		if(actualLength <= 0)
			return 0;
		tone.get(index, data, 0, actualLength);
		return actualLength;
	}
	
	public int getByteLength(String name) {
		if(toneData == null || toneData.get(name) == null)
			return 0;
		return toneData.get(name).capacity();
	}
}
