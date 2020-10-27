package com.kuiprux.tcbgmbot;

import com.kuiprux.tcbgmbot.player.Transition;
import com.kuiprux.tcbgmbot.player.TransitionMode;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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
					.addTransition(new Transition(TransitionMode.FADE_IN, -1, 100));
					break;
				case "--fadeout":
					TCBGMBot.agm.tempGetGMM(event.getGuild()).getSendHandler().audioPlayerMap.get("test")
					.addTransition(new Transition(TransitionMode.FADE_OUT, -1));
					break;
				case "--setvol30":
					TCBGMBot.agm.tempGetGMM(event.getGuild()).getSendHandler().audioPlayerMap.get("test")
					.addTransition(new Transition(TransitionMode.SET_VOLUME, -1, 30));
					break;
				case "--setvol70":
					TCBGMBot.agm.tempGetGMM(event.getGuild()).getSendHandler().audioPlayerMap.get("test")
					.addTransition(new Transition(TransitionMode.SET_VOLUME, -1, 70));
					break;
				}
			}
		}
	}
}
