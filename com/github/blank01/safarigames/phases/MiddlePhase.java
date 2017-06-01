package com.github.blank01.safarigames.phases;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.time.CountDown;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.utils.PixelmonUtils;
import com.github.blank01.safarigames.warps.WarpManager;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;

public class MiddlePhase extends SafariPhase{

	public MiddlePhase(int minutes) {
		super(minutes, EnumPhase.MIDDLE);
		
	}
	//64 safari balls, 1 masterball, 2 Stones, 1 Evo item, 2 Use Items, and idk if there was held item
	@Override
	public void doSomething() {
		try{
	
			MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
			MinecraftUtils.broadcast(EnumChatFormatting.AQUA + "Starting Trading Phase");
			MinecraftUtils.broadcast(EnumChatFormatting.LIGHT_PURPLE +"=============");
			MinecraftUtils.tpAll(SafariGames.instance.getWarpManager().getWarp("middleRoom"), "Trading Area");
			MinecraftUtils.showTitle(EnumChatFormatting.GOLD+"Trading Phase");
			PixelmonUtils.healAllTeams();
			Pixelmon.instance.EVENT_BUS.register(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@SubscribeEvent
	public void a(BattleStartedEvent a)
	{
	  a.setCanceled(true);
	}
	@Override
	protected void doSomthingEndPhase() {
		
	}

}
