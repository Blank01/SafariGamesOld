package com.github.blank01.safarigames.events;

import com.github.blank01.safarigames.phases.EnumPhase;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EndPhaseEvent extends Event{

	public EnumPhase p;
	public EndPhaseEvent(EnumPhase phase){
		this.p=phase;
	}
	
}
