package com.github.blank01.safarigames.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.utils.EvolutionHelper;
import com.github.blank01.safarigames.utils.PixelmonUtils;
import com.pixelmonmod.pixelmon.comm.packetHandlers.Evolution;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.FriendShip;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.evolution.EvolutionType;
import com.pixelmonmod.pixelmon.enums.EnumPokemon;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerNotLoadedException;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;


public class EvolveCommand extends CommandBase{
	  
	  @Override
	  public void processCommand(ICommandSender sender, String[] args)
	  {
		  int slot=-1;
		  if(args.length!=1){
			  sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
			  return;
		  }
		  try{
			  slot= Integer.parseInt(args[0]);
		  }catch(NumberFormatException e){
			  sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
			  return;
		  }
		  
		  try {
			  EntityPlayerMP p =(EntityPlayerMP)sender;
				PlayerStorage ps =PixelmonStorage.PokeballManager.getPlayerStorage(p);
				String name = ps.partyPokemon[slot-1].getString("Name").toLowerCase();
				
				for(int i=0;i<ps.getAllAblePokemonIDs().size();i++){
					EntityPixelmon poke = ps.getPokemon(ps.getAllAblePokemonIDs().get(i), sender.getEntityWorld());
					
					if(name.equals(poke.getName().toLowerCase())){
						poke.friendship.setFriendship(255);
						if(EvolutionHelper.checkEvolution(p, poke, EvolutionType.Trade, EvolutionType.Friendship))
							PixelmonUtils.recallPokes(p);
							PixelmonUtils.sendUpdatedList(p);

					}
				}
		  } catch (PlayerNotLoadedException e) {
			
		}
			  
	  }
	  
	  

	@Override
	public String getCommandName() {
		return "evolve";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /evolve <slot 1-6>";
	}
	
	@Override
	public List<String> getCommandAliases()
    {
		List<String> aliases = new ArrayList<String>();
		aliases.add("evolve");
        return aliases;
    }
	public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
		return true;
    }
}

