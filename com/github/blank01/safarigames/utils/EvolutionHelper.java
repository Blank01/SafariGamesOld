package com.github.blank01.safarigames.utils;

import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.Evolution;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.EvolutionTrade;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.EvolutionType;
import com.pixelmonmod.pixelmon.items.ItemHeld;

public class EvolutionHelper {
	public static boolean checkEvolution(EntityPlayerMP player, EntityPixelmon poke, EvolutionType... typeList)
	  {
		Evolution[] evolutions = poke.baseStats.evolutions;
		String lastEvo = "";
	    for (Evolution e : evolutions) {
	      for (EvolutionType type : typeList) {
	    	  if(e.data instanceof EvolutionTrade){
	    		 e.data=new EvolutionTradeSpoof();
	    	}
	        if (e.handleEvolution(poke, type)) {
	          return true;
	        }else if(!lastEvo.equals(e.evolveInto.name)){
	        	player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Can't evolve " + poke.getName() + " in " + e.evolveInto.name));
	        	lastEvo=e.evolveInto.name;
	        }
	      }
	    }
	    return false;
	  }

	
}
