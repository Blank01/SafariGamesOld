package com.github.blank01.safarigames.phases;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import net.minecraft.util.EnumChatFormatting;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.events.EndPhaseEvent;
import com.github.blank01.safarigames.events.StartPhaseEvent;
import com.github.blank01.safarigames.time.CountDown;
import com.github.blank01.safarigames.time.Timer;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.pixelmonmod.pixelmon.Pixelmon;


public abstract class SafariPhase extends Thread{

	protected int timeLength;
	protected EnumPhase phase;
	public Timer timer;
	
	
	public SafariPhase(int minutes, EnumPhase phase){
		
		timer = new Timer(minutes*60, phase.getName());
		this.timeLength=minutes;
		this.phase=phase;
	}
	
	public void run(){
		if(SafariGames.instance.end)
			return;
		Pixelmon.instance.EVENT_BUS.register(this);
		SafariGames.EVENT_BUS.post(new StartPhaseEvent(phase));
		doSomething();
		startTimer();
		
		Pixelmon.instance.EVENT_BUS.unregister(this);
		SafariGames.EVENT_BUS.post(new EndPhaseEvent(phase));
		doSomthingEndPhase();
		startNextPhase();
		

	}	
	protected abstract void doSomething();

	protected abstract void doSomthingEndPhase();

	public void startNextPhase(){
		boolean end=SafariGames.instance.getSafariGame().isEndGame();
		if(end){
			SafariGames.instance.getSafariGame().startPhase(EnumPhase.POST);
		}
		if(this.phase==EnumPhase.POST)
			return;
		EnumPhase p= EnumPhase.getNextPhase(this.phase);
		SafariGames.instance.getSafariGame().startPhase(p);
	}
		
	public void startTimer(){		
		timer.runAndWait();

	}
	
	public void editTime(int seconds){
		timer.editTime(seconds);
	}
	

	public void end() {
		this.timer.interrupt();
		this.interrupt();
	}
}
