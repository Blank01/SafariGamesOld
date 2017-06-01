package com.github.blank01.safarigames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.blank01.safarigames.events.EndPhaseEvent;
import com.github.blank01.safarigames.events.EndRoundEvent;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.phases.JoinPhase;
import com.github.blank01.safarigames.phases.MiddlePhase;
import com.github.blank01.safarigames.phases.PostPhase;
import com.github.blank01.safarigames.phases.SafariPhase;
import com.github.blank01.safarigames.phases.TournamentPhase;
import com.github.blank01.safarigames.phases.TrainPhase;

public class Game {
	private Map<EnumPhase, SafariPhase> phases;
	private List<String> players;
	private EnumPhase currentPhase;
	private SafariPhase phase;
	private boolean endGame;
	
	public Game(){
		phases=new HashMap<EnumPhase, SafariPhase>();
		phases.put(EnumPhase.JOIN, new JoinPhase(5));
		phases.put(EnumPhase.TRAIN,new TrainPhase(30));
		phases.put(EnumPhase.MIDDLE, new MiddlePhase(5));
		phases.put(EnumPhase.TOURNAMENT, new TournamentPhase(15));
		phases.put(EnumPhase.POST, new PostPhase());
		endGame=false;
		SafariGames.instance.EVENT_BUS.register(this);
	}
	public void start(){
		startPhase(EnumPhase.JOIN);
	}
	
	public void startPhase(EnumPhase p){
		if(this.phase!=null){
			this.phase.end();
			this.phase=null;
		}
		this.currentPhase= p;
		this.phase = phases.get(p);
		this.phase.start();
	}
	
	public EnumPhase getCurrentPhase(){
		return this.currentPhase;
	}
	public EnumPhase setCurrentPhase(EnumPhase p){
		return this.currentPhase;
	}
	
	public void setTime(int seconds) {
		this.phases.get(currentPhase).editTime(seconds);
	}
	public void endGame() {
		this.endGame=true;
		this.phases.get(currentPhase).end();
		this.phases.get(EnumPhase.POST).start();
	}
	public boolean isEndGame(){
		return endGame;
	}
	public SafariPhase getPhase(EnumPhase phase) {
		return this.phases.get(phase);
	}

}
