package com.kuiprux.tcbgmbot;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

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
		for(Channel channel : guild.getChannels(true)) {
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
