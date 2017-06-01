package com.github.blank01.safarigames.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.blank01.safarigames.Game;
import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.phases.EnumPhase;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class StartGameCommand extends CommandBase{
	  
	  @Override
	  public void processCommand(ICommandSender icommandsender, String[] astring)
	  {
		  try{	  
		  
			  SafariGames sg = SafariGames.instance;
			  
			  /*if(g!=null || g.getCurrentPhase()!=EnumPhase.POST){
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Safari Game already Running!"));
				  return;
			  }*/
			  
			  if(sg.getWarpManager().getWarp("pokecenter")==null){
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Set /warp pokecenter first!"));
				  return;
			  }else if(sg.getWarpManager().getWarp("middleRoom")==null){
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Set /warp middleRoom first!"));
				  return;
			  }else if(sg.getWarpManager().getWarp("tournament")==null){
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Set /warp tournament first!"));
				  return;
			  }			  
			  SafariGames.instance.startGame();
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }

	@Override
	public String getCommandName() {
		return "startgame";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "startgame";
	}
	
	@Override
	public List<String> getCommandAliases()
    {
		List<String> aliases = new ArrayList<String>();
		aliases.add("sg");
		aliases.add("startgame");
        return aliases;
    }
	public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
		//only op can startgame
		return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(((EntityPlayerMP) sender).getGameProfile());
    }
}
