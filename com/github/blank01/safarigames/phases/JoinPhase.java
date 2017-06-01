package com.github.blank01.safarigames.phases;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.events.EndPhaseEvent;
import com.github.blank01.safarigames.events.StartPhaseEvent;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.utils.PixelmonUtils;
import com.github.blank01.safarigames.time.CountDown;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerNotLoadedException;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;

import net.minecraft.command.ICommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class JoinPhase extends SafariPhase {
	private final int waitMinutes=5;
	public JoinPhase(int minutes) {
		super(minutes, EnumPhase.JOIN);
		SafariGames.instance.end=false;
	}

	
	@Override
	public void doSomething() {
		PixelmonUtils.killAllTeams();		
		MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
		MinecraftUtils.broadcast(EnumChatFormatting.AQUA + "Join Phase Started!");
		MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
		MinecraftUtils.showTitle(EnumChatFormatting.AQUA+"Join Phase");

		ICommandManager cmdManager = MinecraftServer.getServer().getCommandManager();
		cmdManager.executeCommand(MinecraftServer.getServer(), "/whitelist off");
		MinecraftForge.EVENT_BUS.register(this);
		

	}
	
	@EventHandler
	private void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e){
		System.out.println("Killing " + e.player.getDisplayNameString() + " pokes");
		PixelmonUtils.killTeam((EntityPlayerMP)e.player);
		/*
		List<EntityPlayerMP>players = MinecraftUtils.getAllPlayers();
		
		if(players.size()==SafariGames.playerNumber){			
			MinecraftForge.EVENT_BUS.unregister(this);

			ICommandManager cmdManager = MinecraftServer.getServer().getCommandManager();
			for(int i=0; i<=SafariGames.playerNumber;i++){
				cmdManager.executeCommand(MinecraftServer.getServer(), "/whitelist " + players.get(i).getName());
			}
			cmdManager.executeCommand(MinecraftServer.getServer(), "/whitelist on");
			for(int i=0; i>SafariGames.playerNumber;i++){
				cmdManager.executeCommand(MinecraftServer.getServer(), "/kick " + players.get(i).getName());
			}
		}*/
	}


	@Override
	protected void doSomthingEndPhase() {
		
	}
	
	

}
