package com.github.blank01.blank01.time;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.minecraft.util.EnumChatFormatting;

import com.github.blank01.safarigames.utils.MinecraftUtils;

public class CountDown extends Thread {
	private CountDownLatch latch;
	private int seconds;
	private String name;
	private boolean timeEdited;
	private int i;
	private boolean ended;
	private String endMsg;
	private String countdownMsg;
	
	
	public CountDown(CountDownLatch latch, int seconds, String name) {
	    this.latch = latch;
	    this.seconds = seconds;
	    this.name=name;
	    this.timeEdited=false;
	    this.ended=false;
	    this.endMsg="";
	    this.countdownMsg="";
	    i=0;
	}
	public CountDown(CountDownLatch latch, int seconds) {
	    this.latch = latch;
	    this.seconds = seconds;
	    this.name="";
	    this.timeEdited=false;
	    this.ended=false;
	    this.endMsg="";
	    this.countdownMsg="";
	    i=0;
	}
	
	public void run() {
	
	    try {
	    	for(i=seconds;i>=0;i--){
	    		if(ended){
	    			break;
	    		}
	    		if(timeEdited){
	    			timeEdited=false;
	    			i=seconds;
	    		}
	    		Thread.sleep(1000);
	    		broadcastTimeLeft(i);
	    	}
	    } catch (InterruptedException e) {
	        latch.countDown();
	    }
	
	    latch.countDown();
	}
	
	public void editTime(int seconds){
		this.timeEdited=true;
		this.seconds=seconds;
	}

	public void setCustomEndMessage(String s){
		this.endMsg=s;
	}
	
	public void setCustomCountdownMessage(String s){
		this.countdownMsg=s;
	}
	private void broadcastTimeLeft(int i) {
		if(i==0){
			if(endMsg.equals(""))
				MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">>" + EnumChatFormatting.GOLD + name + " terminated");
			else
				MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">>" +EnumChatFormatting.GOLD +  endMsg);

			return;
		}else if(i==30||i==10||i<=5||i==60){
			if(endMsg.equals(""))
				MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">> " +EnumChatFormatting.RED + i + " seconds left to the end of " + name);
			else
				MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">> " +EnumChatFormatting.RED +i + " seconds "+ this.countdownMsg);
		}else if(i%(5*60)==0){
			if(endMsg.equals(""))
				MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">> " +EnumChatFormatting.YELLOW +(i/60) + " minutes left to end of " + name );
			else
				MinecraftUtils.broadcast(EnumChatFormatting.GRAY + ">> " +EnumChatFormatting.YELLOW +(i/60) + " minutes " + this.countdownMsg);
		}
	}
	
	//returns time in seconds
	public int getTimeLeft(){
		return i;
	}

	public String toString(){
		String s="";
		if((i/60)>0){
			s+= i + "minutes ";
			if(i%60!=0)
				s+="and ";
		}
		s+=(i%60) + " seconds";
		
		return s;
	}
	public void end() {
		this.ended=true;
	}
}
