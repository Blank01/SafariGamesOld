package com.github.blank01.safarigames.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.github.blank01.safarigames.Game;
import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.phases.EnumPhase;

public class SafariCommand extends CommandBase{
	  
	  @Override
	  public void processCommand(ICommandSender icommandsender, String[] args)
	  {
		  try{
			  			  
			  if(SafariGames.instance.getSafariGame()==null){
				  icommandsender.addChatMessage(new ChatComponentText(SafariGames.instance.getCurrentPhase().getName()));

				  return;
			  }
		  if(args.length==0){
			  System.out.println(SafariGames.instance.getSafariGame().getCurrentPhase()==null);
			  icommandsender.addChatMessage(new ChatComponentText(SafariGames.instance.getCurrentPhase().getName()));
			  return;
		  }else if(args[0].equalsIgnoreCase("time")){
			  if(args.length==1 && SafariGames.instance.getSafariGame()!=null){
				  if(SafariGames.instance.getPhase().timer.isRunning()){
					  int s = SafariGames.instance.getPhase().timer.getTimeLeft();
					  int min = SafariGames.instance.getPhase().timer.getTimeLeft()/60;
					  int sec = SafariGames.instance.getPhase().timer.getTimeLeft()%60;
					  String timeLeft = "";
					  if(min>0)
						  timeLeft+= min + " minutes and ";
					  timeLeft = timeLeft + sec + " seconds left to the end of this phase";
					  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + ">> " + EnumChatFormatting.AQUA + timeLeft));
				  }else{
					  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + ">> " + EnumChatFormatting.AQUA + "No timer currently running!"));
				  }
				  return;
			  }
			  if(args.length!=2){
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /safari time <30s|15m|1h>"));
				  return;
			  }
			  int seconds = parseSeconds(args[1]);
			  if(seconds<0){
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /safari time <30s|15m|1h>"));
				  return;
			  }
			  
			  SafariGames.instance.getSafariGame().setTime(seconds);
		  }else if(args[0].equalsIgnoreCase("endphase")){
			  if(args.length>1){
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /safari endphase"));
				  return;
			  }
			  SafariGames.instance.getSafariGame().setTime(1);
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
		return EnumChatFormatting.RED + "Usage: /safari <time|endphase>";
	}
	
	@Override
	public List<String> getCommandAliases()
  {
		List<String> aliases = new ArrayList<String>();
		aliases.add("safari");
      return aliases;
  }
	public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
		//only op can use /safari
		return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(((EntityPlayerMP) sender).getGameProfile());
    }
}
