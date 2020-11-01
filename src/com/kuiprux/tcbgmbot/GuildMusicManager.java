package com.kuiprux.tcbgmbot;

import java.util.HashMap;
import java.util.Map;

import com.kuiprux.tcbgmbot.player.PlayState;
import com.kuiprux.tcbgmbot.player.TCBBMP3Player;
import com.kuiprux.tcbgmbot.player.TCBBPlayer;
import com.kuiprux.tcbgmbot.player.TCBTonePlayer;
import com.kuiprux.tcbgmbot.player.Util;

/**
 * Holder for both the player and a track scheduler for one guild.
 */
public class GuildMusicManager {
	
	ACBBAudioSendHandler apsh;
	Map<String, TCBBPlayer> audioPlayerMap = new HashMap<>();
	
	public GuildMusicManager() {
		TCBBMP3Player player = new TCBBMP3Player();
		TCBGMBot.ml.loadMusic("C:\\Users\\user\\Desktop\\testmusic.wav", "testMusic"); //TODO
		audioPlayerMap.put("test", player);
		TCBTonePlayer pl = new TCBTonePlayer(Util.BYTE_IN_A_MILLIS*300);
		TCBGMBot.ml.loadToneGroup("C:\\Users\\user\\Desktop\\testtone", "testtone-", "testtone");
			//TODO add tone
		audioPlayerMap.put("testtone", pl);
		apsh = new ACBBAudioSendHandler(audioPlayerMap);
	}
	
	public void play() {
		//audioPlayerMap.get("test").setPlayState(PlayState.PLAYING); //TODO
		 audioPlayerMap.get("testtone").setPlayState(PlayState.PLAYING);
	}

	/**
	 * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
	 */
	public ACBBAudioSendHandler getSendHandler() {
		return apsh;
	}

}