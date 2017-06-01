package com.github.blank01.safarigames.phases;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.time.CountDown;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.utils.PixelmonUtils;
import com.github.blank01.safarigames.warps.WarpManager;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.config.PixelmonItemsHeld;
import com.pixelmonmod.pixelmon.items.heldItems.ItemExpShare;

public class TrainPhase extends SafariPhase{

	public TrainPhase(int minutes) {
		super(minutes, EnumPhase.TRAIN);
		
	}
	@Override
	public void doSomething() {
		try{
			MinecraftUtils.addPlayersToWhitelist();

			MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/whitelist on");
			MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
			MinecraftUtils.broadcast(EnumChatFormatting.GREEN + "Starting Training Phase");
			MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
			
			MinecraftUtils.tpAll(SafariGames.instance.getWarpManager().getWarp("pokecenter"), "The Pokecenter");
			MinecraftUtils.showTitle(EnumChatFormatting.GREEN + "Training Phase");

			PixelmonUtils.clearAllMoney();
			PixelmonUtils.giveAllMoney(500);
			MinecraftUtils.clearAllInventories();
			
			giveItems();
			PixelmonUtils.healAllTeams();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void giveItems() {
		MinecraftUtils.giveItemToPlayers(Item.getByNameOrId("pixelmon:item.Rare_Candy"), 7);
		MinecraftUtils.giveItemToPlayers(Item.getByNameOrId("pixelmon:item.Master_Ball"), 1);
		MinecraftUtils.giveItemToPlayers(Item.getByNameOrId("pixelmon:item.Safari_Ball"), 64);
		MinecraftUtils.giveItemToPlayers(PixelmonItems.oldRunningShoes, 1);
		MinecraftUtils.giveItemToPlayers(PixelmonItems.itemFinder, 1);
		MinecraftUtils.giveItemToPlayers(PixelmonItemsHeld.expShare, 1);
		for(EntityPlayerMP p:MinecraftUtils.getAllPlayers()){
			p.inventory.addItemStackToInventory(new ItemStack(MinecraftUtils.getRandomEvoStone(), 1));
			p.inventory.addItemStackToInventory(new ItemStack(MinecraftUtils.getRandomTM(), 1));
			p.inventory.addItemStackToInventory(new ItemStack(MinecraftUtils.getRandomTM(), 1));
		}
		
	}
	@Override
	protected void doSomthingEndPhase() {
	}
	
}
