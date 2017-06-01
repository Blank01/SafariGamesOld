package com.github.blank01.safarigames.phases;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.utils.PixelmonUtils;

public class PostPhase extends SafariPhase{

	public PostPhase() {
		super(-1, EnumPhase.POST);
	}
	
	@Override
	public void run(){
		MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/whitelist off");
		MinecraftUtils.removePlayersToWhitelist();
		MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
		MinecraftUtils.broadcast(EnumChatFormatting.RED + "Safari Games Ended");
		MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
		MinecraftUtils.clearAllInventories();
		MinecraftUtils.tpAll(SafariGames.instance.getWarpManager().getWarp("pokecenter"), "The Pokecenter");
		PixelmonUtils.healAllTeams();

	}
	
	@Override
	public void doSomething() {
		
	}

	@Override
	protected void doSomthingEndPhase() {
		// TODO Auto-generated method stub
		
	}

}
