package com.github.blank01.safarigames.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.utils.PixelmonUtils;

public class KillTeamCommand extends CommandBase{
	  
	  @Override
	  public void processCommand(ICommandSender icommandsender, String[] args)
	  {
		  if(args.length!=1){
			  System.out.println(args.length);
			  icommandsender.addChatMessage(new ChatComponentText(this.getCommandUsage(icommandsender)));
			  return;
		  }
		  EntityPlayerMP p = MinecraftUtils.getPlayerFromName(args[0]);
		  if(p==null){
			  System.out.println("p null");
			  icommandsender.addChatMessage(new ChatComponentText(this.getCommandUsage(icommandsender)));
			  return;
		  }
		  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + ">> " + EnumChatFormatting.GREEN + "Killed " + p.getName() + "'s team!"));
		  PixelmonUtils.killTeam(p);
	  }

	@Override
	public String getCommandName() {
		return "killteam";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return EnumChatFormatting.RED + "Usage: /killteam <player>";
	}
	
	@Override
	public List<String> getCommandAliases()
  {
		List<String> aliases = new ArrayList<String>();
		aliases.add("killteam");
      return aliases;
  }
	public boolean canCommandSenderUseCommand(ICommandSender sender)
  {
		//only op can killteam
		return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(((EntityPlayerMP) sender).getGameProfile());
  }
}
