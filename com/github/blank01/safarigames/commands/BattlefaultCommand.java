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
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import com.github.blank01.safarigames.Game;
import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.phases.TournamentPhase;
import com.github.blank01.safarigames.tournament.model.Round;
import com.github.blank01.safarigames.tournament.model.Tournament;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.ExitBattle;
public class BattlefaultCommand extends CommandBase{
	  
	  @Override
	  public void processCommand(ICommandSender icommandsender, String[] args)
	  {
		  Pixelmon.network.sendTo((IMessage)new ExitBattle(), (EntityPlayerMP)icommandsender);
	  }

	@Override
	public String getCommandName() {
		return "battlefault";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return EnumChatFormatting.RED + "Usage: /battlefault";
	}
	
	@Override
	public List<String> getCommandAliases()
{
		List<String> aliases = new ArrayList<String>();
		aliases.add("battlefault");
  return aliases;
}
	public boolean canCommandSenderUseCommand(ICommandSender sender)
{
		return true;
}
}

