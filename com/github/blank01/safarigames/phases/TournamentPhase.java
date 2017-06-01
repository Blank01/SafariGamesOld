package com.github.blank01.safarigames.phases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import scala.reflect.internal.Trees.This;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.events.EndPhaseEvent;
import com.github.blank01.safarigames.events.EndRoundEvent;
import com.github.blank01.safarigames.events.StartPhaseEvent;
import com.github.blank01.safarigames.events.StartRoundEvent;
import com.github.blank01.safarigames.time.CountDown;
import com.github.blank01.safarigames.tournament.model.Tournament;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.utils.PixelmonUtils;
import com.pixelmonmod.pixelmon.Pixelmon;

public class TournamentPhase extends SafariPhase{
	private Tournament t;

	private int minutes;
	
	public TournamentPhase(int minutes) {
		super(minutes, EnumPhase.TOURNAMENT);
		this.minutes=minutes;
		
	}

	@Override
	public void run(){
		SafariGames.EVENT_BUS.post(new StartPhaseEvent(phase));
		Pixelmon.instance.EVENT_BUS.register(this);
		doSomething();
		Pixelmon.instance.EVENT_BUS.unregister(this);
		startNextPhase();
		SafariGames.EVENT_BUS.post(new EndPhaseEvent(phase));
	}

	public void startTimer(){
		
	}
	
	@Override
	public void doSomething() {
		try{
			
			MinecraftUtils.tpAll(SafariGames.instance.getWarpManager().getWarp("tournament"), "Tournament Area");
			PixelmonUtils.killAllTeams();
			MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
			MinecraftUtils.broadcast(EnumChatFormatting.GREEN + "Starting Tournament Phase");
			MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
			MinecraftUtils.showTitle(EnumChatFormatting.LIGHT_PURPLE + "Tournament Phase");

			
			//Tournament starts here
			t=new Tournament(MinecraftUtils.getAllUUID(), minutes);
			t.start();
			while(!t.isEnded()){
				Thread.sleep(1000);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Tournament getTournament() {
		return t;
	}
	
	public void end() {
		this.interrupt();
	}

	@Override
	protected void doSomthingEndPhase() {
		Pixelmon.instance.EVENT_BUS.register(t);
	}
	
}
