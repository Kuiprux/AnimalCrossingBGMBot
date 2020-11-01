package com.kuiprux.tcbgmbot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class TCBGMBot {
	
	public static JDA jda;
	public static final TCBBCommandHandler ch = new TCBBCommandHandler();
	public static final AllGuildMusicHandler agm = new AllGuildMusicHandler();
	public static final MusicLoader ml = new MusicLoader();
	
	public static void main(String[] args) throws LoginException {
	    JDABuilder builder = JDABuilder.createDefault(args[0]);
	    // Disable parts of the cache
	    //builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
	    // Enable the bulk delete event
	    //builder.setBulkDeleteSplittingEnabled(false);
	    // Disable compression (not recommended)
	    //builder.setCompression(Compression.NONE);
	    // Set activity (like "playing Something")
	    builder.setActivity(Activity.playing("ACNH"));
	    builder.addEventListeners(ch);
	    jda = builder.build();
	}
}
