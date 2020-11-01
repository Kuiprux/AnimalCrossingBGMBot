package com.kuiprux.tcbgmbot;

import com.kuiprux.tcbgmbot.player.TCBTonePlayer;
import com.kuiprux.tcbgmbot.player.ToneInfo;
import com.kuiprux.tcbgmbot.player.Transition;
import com.kuiprux.tcbgmbot.player.TransitionMode;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TCBBCommandHandler extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		MessageChannel chnl = event.getChannel();
		User sender = event.getAuthor();
		System.out.println("bb");
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
				case "--settone":
					TCBTonePlayer pl = (TCBTonePlayer) TCBGMBot.agm.tempGetGMM(event.getGuild()).getSendHandler().audioPlayerMap.get("testtone");
					pl.clearTone();
					pl.addTone(new ToneInfo("A3"));
					pl.addTone(new ToneInfo("B3"));
					pl.addTone(new ToneInfo("C4"));
					pl.addTone(new ToneInfo("D4"));
					pl.addTone(new ToneInfo("E4"));
					pl.addTone(new ToneInfo("F4"));
					pl.addTone(new ToneInfo("G4"));
				}
			}
		}
	}
}
