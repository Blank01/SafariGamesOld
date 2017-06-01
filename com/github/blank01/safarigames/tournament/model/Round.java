package com.github.blank01.safarigames.tournament.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.events.EndPhaseEvent;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.time.Timer;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.enums.BattleResults;
import com.pixelmonmod.pixelmon.api.events.PlayerBattleEndedEvent;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.ExitBattle;

public class Round {
	
	private List<UUID> players;
	private List<UUID> winners;
	private List<Match> matches;
	private Timer timer;
	private int roundNumber;
	public Round(List<UUID> players, int minutes, int roundNumber){
		this.players=players;
		this.winners=new ArrayList<UUID>();
		if(players.size()==1)
			return;
		this.matches=generateMatches();
		this.timer=new Timer(minutes*60);
		this.roundNumber=roundNumber;
		Pixelmon.instance.EVENT_BUS.register(this);

		
	}
	
	public List<Match> generateMatches(){
		List<Match> matches = new ArrayList<Match>();
		
		Collections.shuffle(players);
		matches.clear();
		Match m;
		UUID p1 = null;
		UUID p2 = null;
		for(int i=0;i<=players.size()-1;i+=2){
			
			p1=players.get(i);
			try{
			p2=players.get(i+1);
			}catch(IndexOutOfBoundsException e){
				p2=null;
			}
			m=new Match(p1, p2, matches.size()+1);
			if(p2!=null)
				MinecraftUtils.broadcast(m.toString());
			matches.add(m);
			
		}
		return matches;
	}

	public void startRound(){
		MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">> " + EnumChatFormatting.GOLD + "Round " + this.roundNumber + " started");
		//MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">> " + EnumChatFormatting.GOLD + "Starting " + matches.size() + " matches");

		for(Match m:matches){
			
			m.startMatch();
			if(m.getP2()==null)
				setWinner(m, m.getP1());
			
		}
		if(winners.size()!=this.matches.size()){
			timer.setCustomMessage("to the end of Round " + this.roundNumber);
			timer.setCustomEndMessage("Time is up for Round " + (this.roundNumber)+"!");
			timer.runAndWait();
		}
		endAllMatches();
		
	}

	private void endAllMatches() {
		for(Match m: matches){
			m.forceEnd();
			setWinner(m, m.getWinner());
		}
	}

	public void setWinner(Match m, UUID winner) {
		if(this.winners.contains(winner))
			return;
		if(winner==null)
			return;
		for(int i=0;i<matches.size();i++){
			if(m.equals(matches.get(i))){
				winners.add(winner);
				matches.get(i).setWinner(winner);
				if(m.getP2()!=null){
					MinecraftUtils.tp(m.getEntityP1(), SafariGames.instance.getWarpManager().getWarp("tournament"), "Tournament Area");
					MinecraftUtils.tp(m.getEntityP2(), SafariGames.instance.getWarpManager().getWarp("tournament"), "Tournament Area");
				}
				broadcastWinner(m);
			}
		}
		if(isRoundEnd()){
			endRound();
		}
	}
	private boolean isRoundEnd() {
		return winners.size()==this.matches.size();
	}

	private void broadcastWinner(Match m) {
		if(m.getEntityP2()==null)
			MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">> " + EnumChatFormatting.GREEN
			+ m.getWinnerName() + EnumChatFormatting.GOLD + " won his match by default");
		else
			MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">> " + EnumChatFormatting.GREEN
					+ m.getWinnerName() + EnumChatFormatting.GOLD + " won against "
					+ m.getLoser());
	}

	
	public void endRound() {
		Pixelmon.instance.EVENT_BUS.unregister(this);
		timer.end();
	}
	
	public int getRoundNumber(){
		return this.roundNumber;
	}

	public List<Match> getMatches() {
		return this.matches;
	}

	public List<UUID> getWinners() {
		return this.winners;
	}
	public List<String> getWinnersNames() {
		List<String> names = new ArrayList<String>();
		for(Match m:matches){
			names.add(m.getWinnerName());
		}
		return names;
	}
	
	public List<UUID> getPlayers(){
		return this.players;
	}

	
	public void onLogOut(EntityPlayerMP p){
		System.out.println(p.getName() + " Logged Out");
		Pixelmon.network.sendTo((IMessage)new ExitBattle(), p);

		for(int i=0;i<matches.size();i++){
			if(matches.get(i).isBattlePlayer(p.getUniqueID())){
				if(matches.get(i).getP1().equals(p.getUniqueID()))
					this.setWinner(matches.get(i).getP2());
				else
					this.setWinner(matches.get(i).getP1());

				/*CountDown t;
				CountDownLatch l =new CountDownLatch(1);
				if(timer.getTimeLeft()>secondsToRelog){
					MinecraftUtils.broadcast(EnumChatFormatting.GOLD + MinecraftUtils.getPlayerFromUUID(matches.get(i).getP1()).getName()+EnumChatFormatting.RED+" logged out, has " + timer.toString() + " to relog");
					t = new CountDown(l, timer.getTimeLeft());
					
				}else{
					MinecraftUtils.broadcast(EnumChatFormatting.GOLD + MinecraftUtils.getPlayerFromUUID(matches.get(i).getP1()).getName()+EnumChatFormatting.RED+" logged out, has " + timer.toString() + " to relog");
					t = new CountDown(l, secondsToRelog);
				}*/
					
			}
		}
	}
	
	@SubscribeEvent
	public void a(PlayerBattleEndedEvent a)
	{
		System.out.println("BattleEnded " + a.player.getName());
		
		if(a.result==BattleResults.VICTORY && !this.winners.contains(a.player.getUniqueID())){
			setWinner(a.player.getUniqueID());
		}else if(a.result==BattleResults.DRAW&&!MinecraftUtils.getAllPlayers().contains(a.player)){
			onLogOut(a.player);
		}
	}

	private void setWinner(UUID p) {
		for(Match m: matches){
			if(m.isBattlePlayer(p)&&!m.isEnded())
				setWinner(m, p);
		}
	}
}
