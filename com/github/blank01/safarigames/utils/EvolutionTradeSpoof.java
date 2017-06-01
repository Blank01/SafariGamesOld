package com.github.blank01.safarigames.utils;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.EvolutionType;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.IEvolutionData;
import com.pixelmonmod.pixelmon.enums.EnumPokemon;

public class EvolutionTradeSpoof implements IEvolutionData{

	@Override
	public boolean doEvolution(EntityPixelmon arg0, EnumPokemon arg1) {
		try
	    {
	      Thread.sleep(50L);
	    }
	    catch (InterruptedException e)
	    {
	      e.printStackTrace();
	    }
	    return true;
	}

	@Override
	public EnumPokemon getEvolution(EntityPixelmon arg0) {
		return null;
	}

	@Override
	public EvolutionType getType() {
		return EvolutionType.Trade;
	}

}
