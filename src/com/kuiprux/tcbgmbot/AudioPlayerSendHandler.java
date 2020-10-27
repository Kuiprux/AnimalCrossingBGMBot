package com.kuiprux.tcbgmbot;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import com.kuiprux.tcbgmbot.player.TCBBPlayer;

import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * This is a wrapper around AudioPlayer which makes it behave as an
 * AudioSendHandler for JDA. As JDA calls canProvide before every call to
 * provide20MsAudio(), we pull the frame in canProvide() and use the frame we
 * already pulled in provide20MsAudio().
 */
public class AudioPlayerSendHandler implements AudioSendHandler {

	Map<String, TCBBPlayer> audioPlayerMap;

	private final ByteBuffer buffer;

	/**
	 * @param audioPlayer Audio player to wrap.
	 */
	public AudioPlayerSendHandler(Map<String, TCBBPlayer> audioPlayerMap) {
		this.audioPlayerMap = audioPlayerMap;
		this.buffer = ByteBuffer.allocate(960 * 4);
		this.buffer.order(ByteOrder.BIG_ENDIAN);
	}

	@Override
	public boolean canProvide() {
		// returns true if audio was provided
		return audioPlayerMap.get("test").provide(buffer); //TODO merge all sounds
	}

	@Override
	public byte[] provide20MsAudio() {
		// flip to make it a read buffer
		// ((Buffer) buffer).flip();
		return buffer.array();
	}

	@Override
	public boolean isOpus() {
		return false;
	}
}