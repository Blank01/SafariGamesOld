package com.github.blank01.safarigames.commands;

import java.util.ArrayList;
import java.util.List;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.warps.Position;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;

public class WarpCommand extends CommandBase{

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "warp";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return EnumChatFormatting.RED + "Usage: /warp <name>";
	}

	@Override
	public List<String> getCommandAliases() {
		List<String> alias = new ArrayList<String>();
		alias.add("warp");
		return alias;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
			throws CommandException {
		try{
			if(args.length==0){
			sender.addChatMessage(new ChatComponentText("Available warps: " + SafariGames.instance.getWarpManager().getWarpList().toString().replace("[", "").replace("]", "")));
			if(MinecraftUtils.isOp(sender)){
				sender.addChatMessage(new ChatComponentText("Total arenas: " + SafariGames.instance.getWarpManager().getTotalArenas()));
			}
			return;
		}
			String s = "";
			for(int i=0;i<args.length; i++)
				s+= args[i]+" ";
			s=s.trim();
			Position pos = SafariGames.instance.getWarpManager().getWarp(s);
		if(pos==null){
			sender.addChatMessage(new ChatComponentText(this.getCommandUsage(sender)));
		}
		MinecraftUtils.tp(this.getCommandSenderAsPlayer(sender), pos, args[0]);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if(MinecraftUtils.isOp(sender))
			return true;
		if(SafariGames.instance.getCurrentPhase()!=null&&SafariGames.instance.getCurrentPhase().equals(EnumPhase.TRAIN)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender,
			String[] args, BlockPos pos) {
		List<String> res=new ArrayList<String>();
		if(args[0]==""){
			res.add("pokecenter");
			SafariGames.instance.getWarpManager().getWarpList();
			return res;
		}
		
		
		for(String name:SafariGames.instance.getWarpManager().getWarpList()){
			if(name.toLowerCase().startsWith(args[0].toLowerCase()))
				res.add(name);

		}

		return res;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
