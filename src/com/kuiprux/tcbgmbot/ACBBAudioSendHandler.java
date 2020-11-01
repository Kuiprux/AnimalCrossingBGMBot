package com.kuiprux.tcbgmbot;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import com.kuiprux.tcbgmbot.player.TCBBPlayer;
import com.kuiprux.tcbgmbot.player.Util;

import net.dv8tion.jda.api.audio.AudioSendHandler;

/**
 * This is a wrapper around AudioPlayer which makes it behave as an
 * AudioSendHandler for JDA. As JDA calls canProvide before every call to
 * provide20MsAudio(), we pull the frame in canProvide() and use the frame we
 * already pulled in provide20MsAudio().
 */
public class ACBBAudioSendHandler implements AudioSendHandler {

	Map<String, TCBBPlayer> audioPlayerMap;

	private final ByteBuffer buffer;

	/**
	 * @param audioPlayer Audio player to wrap.
	 */
	public ACBBAudioSendHandler(Map<String, TCBBPlayer> audioPlayerMap) {
		this.audioPlayerMap = audioPlayerMap;
		this.buffer = ByteBuffer.allocate(Util.BYTE_IN_TWENTY_MILLIS);
		this.buffer.order(ByteOrder.BIG_ENDIAN);
	}

	@Override
	public boolean canProvide() {
		// returns true if audio was provided
		return audioPlayerMap.get("testtone").provide(buffer); //TODO merge all sounds
		//return audioPlayerMap.get("test").provide(buffer); //TODO merge all sounds
	}

	@Override
	public ByteBuffer provide20MsAudio() {
		// flip to make it a read buffer
		/*buffer.position(0);
		while(buffer.hasRemaining()) {
			System.out.println(buffer.getShort());
		}*/
		return buffer.flip();
	}

	@Override
	public boolean isOpus() {
		return false;
	}
}