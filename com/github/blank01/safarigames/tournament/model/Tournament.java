package com.github.blank01.safarigames.tournament.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.events.EndPhaseEvent;
import com.github.blank01.safarigames.events.EndRoundEvent;
import com.github.blank01.safarigames.events.StartPhaseEvent;
import com.github.blank01.safarigames.events.StartRoundEvent;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.time.CountDown;
import com.github.blank01.safarigames.time.Timer;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.utils.PixelmonUtils;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.PlayerBattleEndedEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.api.enums.BattleResults;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class Tournament extends Thread{
	public List<UUID> players;
	private int minutes;
	private Round currentRound;
	private Timer timer;
	private List<Round> rounds;
	private int totalRoundsNumber;
	private boolean ended;
	
	public Tournament (List<UUID>players, int minutes){
		this.players=new ArrayList<UUID>();
		this.minutes=minutes;
		this.players = players;
		this.rounds = new ArrayList<Round>();
		this.totalRoundsNumber = calculateTotalRounds();
		this.ended=false;
	}

	
	private int calculateTotalRounds() {
		int players = this.players.size();
		int res= 0;
		while(players>1){
			players=players/2;
			res++;
		}
		return res;
	}


	private void setWinner(UUID p) {
		Match m=getMatch(p);
		if(m!=null)
			currentRound.setWinner(m, p);
	}
	private Match getMatch(UUID p){
		for(Match m: currentRound.getMatches()){
			if(m.isBattlePlayer(p))
				return m;
		}
		return null;
	}

	public void run() {
		
		this.startNextRound();
		
	}

	private Round createRound() {
		Round r;
		if(this.currentRound==null){
			r= new Round(this.players, minutes, 1);
		}else{
			r= new Round(currentRound.getWinners(), minutes, currentRound.getRoundNumber()+1);
			
		}
		return r;
	}


	public List<Round> getRounds() {
		return this.rounds;
	}

	private void proclaimWinner(UUID winner) {
		MinecraftUtils.broadcast(EnumChatFormatting.GOLD + "Congratulations to " +EnumChatFormatting.GREEN + EnumChatFormatting.UNDERLINE+ MinecraftUtils.getPlayerFromUUID(winner).getName()+EnumChatFormatting.RESET +EnumChatFormatting.GOLD +  " for winning this Safari Game!");
		SafariGames.instance.EVENT_BUS.post(new EndPhaseEvent(EnumPhase.TOURNAMENT));
		this.ended=true;
		
	}
	
	public void startNextRound() {
		currentRound=createRound();
		if(this.currentRound.getPlayers().size()==1){
			proclaimWinner(this.currentRound.getPlayers().get(0));
			return;
		}
		Timer timer = new Timer(30);
		
		timer.setCustomMessage("to the beginning of the Round " + this.currentRound.getRoundNumber());
		timer.setCustomEndMessage("Tournament Round " + this.currentRound.getRoundNumber() + " begin");
		timer.runAndWait();
		
		this.rounds.add(currentRound);

		if(this.currentRound.getPlayers().size()>=2){
			currentRound.startRound();
			
			String winners = currentRound.getWinnersNames().toString().replace("[", "").replace("]", "");
			MinecraftUtils.broadcast(EnumChatFormatting.AQUA + "Round " + this.currentRound.getRoundNumber() +
										" winners: " + EnumChatFormatting.GREEN + winners);
			for(UUID p: currentRound.getWinners()){
				System.out.println("Killing battle for player " + MinecraftUtils.getNameFromUUID(p));
				MinecraftUtils.endBattle(MinecraftUtils.getPlayerFromUUID(p));
			}
			startNextRound();
			
		}
		
	}


	public Round getCurrentRound() {
		return this.currentRound;
	}


	public boolean isEnded() {
		return this.ended;
	}
}
