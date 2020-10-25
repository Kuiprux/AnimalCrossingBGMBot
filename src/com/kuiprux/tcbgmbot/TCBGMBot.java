package com.kuiprux.tcbgmbot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class TCBGMBot {
	
	public static JDA jda;
	public static final TCBBCommandHandler ch = new TCBBCommandHandler();
	public static final AllGuildMusicHandler agm = new AllGuildMusicHandler();
	public static final MusicLoader ml = new MusicLoader();
	
	public static void main(String[] args) throws LoginException {
	    JDABuilder builder = new JDABuilder(args[0]);
	    builder.setGame(Game.playing("ACNH"));
	    builder.addEventListener(ch);
	    jda = builder.build();
	    
	}
}
