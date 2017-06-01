package com.github.blank01.safarigames.events;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.ParticipantType;

public class wildfightsDenier {
	@SubscribeEvent
	public void denyWildFights(BattleStartedEvent e){
		if(e.participant1[0]!=null || e.participant2[0]!=null){
			if(e.participant1[0].getType().equals(ParticipantType.WildPokemon)&&
					e.participant2[0].getType().equals(ParticipantType.WildPokemon))
				e.setCanceled(true);
		}
	}
}
