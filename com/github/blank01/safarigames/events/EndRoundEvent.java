package com.github.blank01.safarigames.events;


import net.minecraftforge.fml.common.eventhandler.Event;

public class EndRoundEvent extends Event{
	public int roundNumber;
	public EndRoundEvent(int n){
		this.roundNumber=n;
	}
}
