package com.github.blank01.safarigames.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.github.blank01.safarigames.Game;
import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.warps.Arena;
import com.github.blank01.safarigames.warps.Position;
import com.github.blank01.safarigames.warps.Warp;

public class SetWarpCommand extends CommandBase{
	  
	  @Override
	  public void processCommand(ICommandSender icommandsender, String[] args)
	  {
		  
		  if(args.length==0){
			  
			  icommandsender.addChatMessage(new ChatComponentText("Usage: /setwarp [biome|arena|\"warpName\"]"));
			  return;

		  }else if(args[0].equalsIgnoreCase("biome")){
			if(args.length!=1){
				icommandsender.addChatMessage(new ChatComponentText("Usage: /setwarp biome"));
				return;
			}
			EntityPlayerMP p;
				try {
					p = this.getCommandSenderAsPlayer(icommandsender);
				
					String biome = MinecraftServer.getServer().worldServers[0].getBiomeGenForCoords(p.getPosition()).biomeName;
					biome = biome.replace(" ", "");
					Warp w=new Warp(new Position(p), biome);
					SafariGames.instance.getWarpManager().addBiomeWarp(w);
					icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Added warp to " + biome));
					
				} catch (PlayerNotFoundException e) {
					e.printStackTrace();
				}
		  	}else if(args[0].equalsIgnoreCase("arena")){
			  if(args.length!=2){
				  icommandsender.addChatMessage(new ChatComponentText("Usage: /setwarp arena <1|2|save>"));
				  return;
			  }
			  
			  if(args[1].equalsIgnoreCase("save")){
				  if(SafariGames.instance.ac.getPos1()==null){
					  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Set first arena position!"));
				  }else if(SafariGames.instance.ac.getPos2()==null){
					  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Set second arena position!"));
				  }else{
					  try{
					  Arena a=SafariGames.instance.getArena();
					  System.out.println(a.getPos1() + " " + a.getPos2());
					  int n = SafariGames.instance.getWarpManager().addArena(a);
					  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Set warp to Arena " + n + "!"));
					  }catch(Exception e){
						  e.printStackTrace();
					  }
				  }
			  }else if(args[1].equalsIgnoreCase("1")){
				  EntityPlayerMP p = (EntityPlayerMP)icommandsender;
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Set first arena position"));

				  SafariGames.instance.ac.addFirst(new Position(p));
			  }else if(args[1].equalsIgnoreCase("2")){
				  EntityPlayerMP p = (EntityPlayerMP)icommandsender;
				  icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Set second arena position"));

				  SafariGames.instance.ac.addSecond(new Position(p));
			  }else if(args[0].equalsIgnoreCase("delarena")){
				  if(args.length!=2 ){
					  icommandsender.addChatMessage(new ChatComponentText("Usage: /setwarp delarena <n>"));
					  return;
				  }
				  try{
				  int n = Integer.parseInt(args[1]);
				  SafariGames.instance.getWarpManager().removeArena(n);

				  }catch(Exception e){
					  icommandsender.addChatMessage(new ChatComponentText("Usage: /setwarp delarena <n>"));
					  return;
				  }

			  }else if(args[0].equalsIgnoreCase("spawn")){
				  if(args.length!=2){
					  icommandsender.addChatMessage(new ChatComponentText("Usage: /setwarp delarena <n>"));
					  return;
				  }
			  }
			  
			  
		  }else{			  
			  EntityPlayerMP p;

			  String name = args[0];
			try {
				p = this.getCommandSenderAsPlayer(icommandsender);
			
				Warp w=new Warp(new Position(p), name);
				SafariGames.instance.getWarpManager().addWarp(w);
				icommandsender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Added warp " + name));

				} catch (PlayerNotFoundException e) {
				e.printStackTrace();
			}
		  }
		  
	    
	  }
	  
	@Override
	public String getCommandName() {
		return "setwarp";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return EnumChatFormatting.RED + "Usage: /setwarp [biome|arena|delarena|spawn|\"warpName\"]";
	}
	
	@Override
	public List<String> getCommandAliases()
  {
		List<String> aliases = new ArrayList<String>();
		aliases.add("setwarp");
      return aliases;
  }
	public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
		//only op can setwarps
		return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(((EntityPlayerMP) sender).getGameProfile());
    }
}
