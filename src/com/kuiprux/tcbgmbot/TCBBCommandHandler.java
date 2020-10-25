package com.kuiprux.tcbgmbot;

import com.kuiprux.tcbgmbot.player.TCBBSinePlayer;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

public class TCBBCommandHandler extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		MessageChannel chnl = event.getChannel();
		User sender = event.getAuthor();
		if(!sender.isBot()) {
			if(sender.getId().equals("368687855003893763")) {
				if(event.getMessage().getContentRaw().equals("--playac")) {
					TCBGMBot.agm.play(event.getGuild(), chnl, sender);
				}
			}
		}
	}
}
