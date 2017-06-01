package com.github.blank01.blank01.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

import com.pixelmonmod.pixelmon.comm.EnumUpdateType;
import com.pixelmonmod.pixelmon.customStarters.CustomStarters;
import com.pixelmonmod.pixelmon.customStarters.SelectPokemonController;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerNotLoadedException;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;

public class PixelmonUtils {
	public static void killTeam(EntityPlayerMP p){
		if(p==null)
			return;
		try {
			PlayerStorage ps = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p);
			PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p).recallAllPokemon();
	       
			for(int i=0;i<ps.partyPokemon.length;i++){
				NBTTagCompound poke = ps.partyPokemon[i];
				if(poke!=null){
					poke.setInteger("Health", 0);
					poke.setFloat("HealF", 0f);
					poke.setBoolean("isFainted", true);
					ps.partyPokemon[i]=poke;
					PixelmonStorage.PokeballManager.getPlayerStorage(p).updateClient(poke, EnumUpdateType.HP);

				}
			}
			sendUpdatedList(p);
		} catch (PlayerNotLoadedException e1) {
			e1.printStackTrace();
		}
	}
	public static void killAllTeams(){
		List<EntityPlayerMP>players = MinecraftUtils.getAllPlayers();
		for(EntityPlayerMP p:players){
			recallPokes(p);
			killTeam(p);
		}
	}
	public static void healAllTeams(){
		List<EntityPlayerMP>players = MinecraftUtils.getAllPlayers();
		for(EntityPlayerMP p:players){
			healTeam(p);
		}
	}
	public static void healTeam(EntityPlayerMP p){
		recallPokes(p);
		MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/pokeheal " + p.getName());
	}
	public static void recallPokes(EntityPlayerMP p) {
		PlayerStorage ps;
		try {
			ps = PixelmonStorage.PokeballManager.getPlayerStorage(p);
			ps.recallAllPokemon();
		} catch (PlayerNotLoadedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void clearAllMoney() {
		for(EntityPlayerMP p : MinecraftUtils.getAllPlayers()){
			try {
				PlayerStorage userStorage = PixelmonStorage.PokeballManager.getPlayerStorage(p);
				userStorage.addCurrency(-1*userStorage.getCurrency());
			} catch (PlayerNotLoadedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void giveAllMoney(int money) {
		for(EntityPlayerMP p : MinecraftUtils.getAllPlayers()){
			try {
				PlayerStorage userStorage = PixelmonStorage.PokeballManager.getPlayerStorage(p);
				userStorage.addCurrency(money);
			} catch (PlayerNotLoadedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
	}
	public static void selectStarter() {
		for(EntityPlayerMP p : MinecraftUtils.getAllPlayers()){
			SelectPokemonController.sendSelectPokemon(p, CustomStarters.starterList);
		}
	}
	
	public static void sendUpdatedList(EntityPlayerMP p) {
		try {
			PixelmonStorage.PokeballManager.getPlayerStorage(p).sendUpdatedList();
		} catch (PlayerNotLoadedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void deletePokes(EntityPlayerMP p) {
		if(PixelmonStorage.PokeballManager.hasPlayerFile(p)){
			try {
				File f = new File(PixelmonStorage.PokeballManager.getPlayerStorage(p).saveFile);
				f.delete();
			} catch (PlayerNotLoadedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
