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

public class MusicLoader {

	
	private Map<String, Music> musics = new HashMap<>();

	public void loadMusic(String filename, String name) {
		try {			
			musics.put(name, new Music(filename));
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	public void unloadMusic(String name) {
		musics.remove(name);
	}
	
	public Music getMusic(String name) {
		return musics.get(name);
	}
}
