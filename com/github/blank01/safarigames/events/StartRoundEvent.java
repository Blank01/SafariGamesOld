package com.github.blank01.safarigames.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class StartRoundEvent extends Event{
	public int roundNumber;
	public StartRoundEvent(int n){
		this.roundNumber=n;
	}
}
