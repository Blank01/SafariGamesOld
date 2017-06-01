package com.github.blank01.safarigames.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.github.blank01.safarigames.Game;
import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.phases.TournamentPhase;
import com.github.blank01.safarigames.tournament.model.Round;
import com.github.blank01.safarigames.tournament.model.Tournament;

public class TournamentCommand extends CommandBase{
	  
	  @Override
	  public void processCommand(ICommandSender icommandsender, String[] args)
	  {
		  try{
			  			  
			  if(SafariGames.instance.getSafariGame().getCurrentPhase()!=EnumPhase.TOURNAMENT){
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "There is no tournament right now"));

				  return;
			  }
			  Game g = SafariGames.instance.getSafariGame();
			  Tournament t=((TournamentPhase) g.getPhase(EnumPhase.TOURNAMENT)).getTournament();
			  List<Round> rounds = t.getRounds();
			  if(args.length==0){
				  
				  System.out.println(t.players.toString());
				  for(Round r:rounds){
					  
					  System.out.println("Round " + r.getRoundNumber());
					  System.out.println("Matches: " + r.getMatches());
					  System.out.println("Winners: " + r.getWinners().toString());
				  }
				  //icommandsender.addChatMessage(new ChatComponentText(SafariGames.instance.getCurrentPhase().getName()));
				  return;
			  }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	    
	  }

	private int parseSeconds(String s) {
		int seconds=-1;
		try{
		if(s.endsWith("s"))
			seconds=Integer.parseInt(s.substring(0, s.length()-1));
		else if(s.endsWith("m"))
			seconds=(Integer.parseInt(s.substring(0, s.length()-1)))*60;
		else if(s.endsWith("h"))
			seconds=(Integer.parseInt(s.substring(0, s.length()-1)))*60*60;	
		}catch(NumberFormatException e){}
		catch(NullPointerException e){}
		return seconds;
	}

	@Override
	public String getCommandName() {
		return "safari";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return EnumChatFormatting.RED + "Usage: /tournament add";
	}
	
	@Override
	public List<String> getCommandAliases()
{
		List<String> aliases = new ArrayList<String>();
		aliases.add("tournament");
    return Collections.<String>emptyList();
}
	public boolean canCommandSenderUseCommand(ICommandSender sender)
  {
		//only op can use /safari
		return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(((EntityPlayerMP) sender).getGameProfile());
  }
}

