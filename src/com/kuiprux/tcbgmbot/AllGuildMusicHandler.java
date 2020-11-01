package com.kuiprux.tcbgmbot;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class AllGuildMusicHandler {

	private final Map<Long, GuildMusicManager> musicManagers;

	public AllGuildMusicHandler() {
	    this.musicManagers = new HashMap<>();
	  }
	
	private synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = musicManagers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager();
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}
	
	public synchronized GuildMusicManager tempGetGMM(Guild guild) {
		return getGuildMusicManager(guild);
	}
	
	public void play(Guild guild, MessageChannel sentChannel, User sender) {
		checkAndJoin(guild, sentChannel, sender);
		GuildMusicManager gmm = getGuildMusicManager(guild);
		gmm.play(); //TODO
	}
	
	public void checkAndJoin(Guild guild, MessageChannel sentChannel, User sender) {
		VoiceChannel vChannel = getVoiceChannel(guild, sender);
		if(vChannel == null) {
			sentChannel.sendMessage("You have to join a channel.").queue();
			return;
		}
		AudioManager audioManager = guild.getAudioManager();
	    if(audioManager.isConnected()) {
	    	if(audioManager.getConnectedChannel().equals(vChannel)) {
	    		sentChannel.sendMessage("Already joined to the channel.").queue();
	    		return;
	    	}
	    }
	    audioManager.openAudioConnection(vChannel);
	}
	private VoiceChannel getVoiceChannel(Guild guild, User sender) {
		for(GuildChannel channel : guild.getChannels(true)) {
			if(channel instanceof VoiceChannel) {
				for(Member member : channel.getMembers()) {
					if(sender.equals(member.getUser())) {
						return (VoiceChannel) channel;
					}
				}
			}
		}
		return null;
	}
}
