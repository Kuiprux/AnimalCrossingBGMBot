package com.kuiprux.tcbgmbot;

import com.kuiprux.tcbgmbot.player.TCBBSinePlayer;
import com.kuiprux.tcbgmbot.player.Transition;
import com.kuiprux.tcbgmbot.player.TransitionMode;

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
			System.out.println(sender.getId());
			if(sender.getId().equals("368687855003893763")) {
				System.out.println(event.getMessage().getContentRaw());
				switch(event.getMessage().getContentRaw()) {
				case "--playac":
					TCBGMBot.agm.play(event.getGuild(), chnl, sender);
					break;
				case "--fadein":
					TCBGMBot.agm.tempGetGMM(event.getGuild()).getSendHandler().audioPlayerMap.get("test")
					.addTransition(new Transition(TransitionMode.FADE_IN, 3000, 100));
					break;
				case "--fadeout":
					TCBGMBot.agm.tempGetGMM(event.getGuild()).getSendHandler().audioPlayerMap.get("test")
					.addTransition(new Transition(TransitionMode.FADE_OUT, 3000));
					break;
				case "--setvol0":
					TCBGMBot.agm.tempGetGMM(event.getGuild()).getSendHandler().audioPlayerMap.get("test")
					.addTransition(new Transition(TransitionMode.SET_VOLUME, 3000, 0));
					break;
				case "--setvol50":
					TCBGMBot.agm.tempGetGMM(event.getGuild()).getSendHandler().audioPlayerMap.get("test")
					.addTransition(new Transition(TransitionMode.SET_VOLUME, 3000, 50));
					break;
				}
			}
		}
	}
}
