package com.github.blank01.safarigames.events;

import com.github.blank01.safarigames.phases.EnumPhase;

import net.minecraftforge.fml.common.eventhandler.Event;

public class StartPhaseEvent extends Event{

	public EnumPhase phase;
	public StartPhaseEvent(EnumPhase phase){
		this.phase=phase;
	}
}
