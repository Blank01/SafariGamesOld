package com.github.blank01.safarigames.tournament.model;

import java.util.UUID;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.utils.PixelmonUtils;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerNotLoadedException;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

public class Match {
	private UUID p1;
	private UUID p2;
	
	private UUID winner;
	private BattleControllerBase bc;
	private int n;
	private String p1Name;
	private String p2Name;
	
	
	public Match(UUID p1, UUID p2, int n){
		this.p1=p1;
		this.p2=p2;
		this.p1Name=MinecraftUtils.getNameFromUUID(p1);
		this.p2Name=MinecraftUtils.getNameFromUUID(p2);
		this.winner=null;
		this.bc=null;
		this.n=n;
	}
	public UUID getP1() {
		return p1;
	}
	public UUID getP2() {
		return p2;
	}
	public Match(UUID p1, int n){
		this.p1=p1;
		this.p2=null;
		this.winner=p1;
		this.bc=null;
		this.n=n;
	}
	
	public UUID getWinner(){
		return winner;
	}
	public void setWinner(UUID p){
		if(p==null){
			this.winner=p1;
		}else if(p.equals(p1)){
			this.winner=p1;
		}else if(p.equals(p2)){
			this.winner=p2;			
		}
	}
	public String getWinnerName(){
		if(this.winner ==null)
			return null;
		if(this.winner==p1)
			return this.p1Name;
		else
			return this.p2Name;
	}
	
	public boolean isBattlePlayer(UUID p) {
		if(p==null&&p2==null){
			return true;
		}
		if(p.equals(p1)||p.equals(p2)){
			return true;
		}
		
		return false;
		
	}
	public void startMatch() {
		
		if(p2 == null){
			this.setWinner(p1);
			return;
		}
		
		MinecraftUtils.tp(MinecraftUtils.getPlayerFromUUID(p1), SafariGames.instance.getWarpManager().getArena(n-1, 1), "Arena " + n);
		MinecraftUtils.tp(MinecraftUtils.getPlayerFromUUID(p2), SafariGames.instance.getWarpManager().getArena(n-1, 2), "Arena " + n);
		PixelmonUtils.healTeam(MinecraftUtils.getPlayerFromUUID(p1));
		PixelmonUtils.healTeam(MinecraftUtils.getPlayerFromUUID(p2));
		MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/pokebattle " + MinecraftUtils.getNameFromUUID(p1)+ " " + MinecraftUtils.getNameFromUUID(p2));
		
	}
	public void forceEnd() {
		if(this.winner!=null){
			MinecraftUtils.tp(MinecraftUtils.getPlayerFromUUID(p1), SafariGames.instance.getWarpManager().getWarp("tournament"), "Tournament Area");
			MinecraftUtils.tp(MinecraftUtils.getPlayerFromUUID(p2), SafariGames.instance.getWarpManager().getWarp("tournament"), "Tournament Area");
			PixelmonUtils.killTeam(MinecraftUtils.getPlayerFromUUID(p1));
			PixelmonUtils.killTeam(MinecraftUtils.getPlayerFromUUID(p1));
	
			calculateWinner();
		}
	}
	private void calculateWinner() {
		try {
			if(this.getEntityP1()==null && this.getEntityP2()==null){
				//this.winner=null; Uncomment this after test
				this.winner=this.getP1();
				return;
			}
			if(this.getEntityP1()==null){
				this.winner=this.p2;
				return;
			}
			if(this.getEntityP2()==null){
				this.winner=this.p1;
				return;
			}
			PlayerStorage ps1 = PixelmonStorage.PokeballManager.getPlayerStorage(MinecraftUtils.getPlayerFromUUID(p1));
			int ablePokemon1= ps1.countAblePokemon();
			PlayerStorage ps2 = PixelmonStorage.PokeballManager.getPlayerStorage(MinecraftUtils.getPlayerFromUUID(p2));
			int ablePokemon2= ps2.countAblePokemon();
			
			if(ablePokemon1<ablePokemon2){
				this.setWinner(p2);
			}else if(ablePokemon1>ablePokemon2){
				this.setWinner(p1);
			}else{
				int totalHealth1 = 0;
				int totalHealth2 = 0;
				
				for(int i=0;i<ps1.partyPokemon.length;i++){
					NBTTagCompound poke = ps1.partyPokemon[i];
					if(poke!=null)
						totalHealth1 +=poke.getInteger("Health");
				}
				for(int i=0;i<ps2.partyPokemon.length;i++){
					NBTTagCompound poke = ps2.partyPokemon[i];
					if(poke!=null)
						totalHealth2 +=poke.getInteger("Health");
				}
				if(totalHealth1<totalHealth2){
					this.setWinner(p2);
				}else{
					this.setWinner(p1);
				}
			}
		
		} catch (PlayerNotLoadedException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public String getLoser(){
		if(winner==null)
			return null;
		if(winner.equals(p1)){
			return this.p2Name;
		}else{
			return this.p1Name;
		}
		
	}
	
	public EntityPlayerMP getEntityP1(){
		return MinecraftUtils.getPlayerFromUUID(p1);
	}
	public EntityPlayerMP getEntityP2(){
		return MinecraftUtils.getPlayerFromUUID(p2);
	}
	
	@Override
	public int hashCode(){
		return (this.p1 + " " + this.p2).hashCode();
	}
	
	@Override
	public boolean equals(Object m){
		if(!(m instanceof Match)){
			return false;
		}
		if(this.hashCode()==((Match)m).hashCode())
			return true;
		else
			return false;
		
	}
	
	@Override
	public String toString(){
		return EnumChatFormatting.GREEN + MinecraftUtils.getNameFromUUID(p1) + EnumChatFormatting.AQUA + " vs " + EnumChatFormatting.GREEN + MinecraftUtils.getNameFromUUID(p2);
	}
	public boolean isEnded() {
		if(this.winner==null)
			return false;
		else
			return true;
	}
}
