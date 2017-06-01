package com.github.blank01.safarigames.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.github.blank01.safarigames.Game;
import com.github.blank01.safarigames.SafariGames;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.warps.Position;
import com.ibm.icu.util.StringTokenizer;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerNotLoadedException;

public class MinecraftUtils {
	
	public static void safariBroadcast(String msg){
		broadcast(new ChatComponentText(EnumChatFormatting.GOLD + "[SafariGames] " + EnumChatFormatting.RED + msg));
	}
	public static void broadcast(String msg){
		//System.out.println(msg);
		broadcast(new ChatComponentText(msg));
	}
	public static void broadcast(IChatComponent msg){
		List<EntityPlayerMP> allp = getAllPlayers();
		for(int i=0;i<allp.size();i++){
			allp.get(i).addChatMessage(msg);
		}
	}
	public static EntityPlayerMP getPlayerFromUUID(UUID parUUID) 
	{
	    if (parUUID == null) 
	    {
	        return null;
	    }
	    List<EntityPlayerMP> allPlayers = MinecraftUtils.getAllPlayers();
	    for (EntityPlayerMP player : allPlayers) 
	    {
	        if (player.getUniqueID().equals(parUUID)) 
	        {
	            return player;
	        }
	    }
	    return null;
	}
	public static List<EntityPlayerMP> getAllPlayers(){
		return MinecraftServer.getServer().getConfigurationManager().playerEntityList;
	}
	
	public static void clearAllInventories(){
		List<EntityPlayerMP> players = getAllPlayers();
		for(EntityPlayerMP p: players ){	
			p.inventory.clear();
		}
	}
	
	
	
	public static Item getRandomTM(){
		String[] items = {"HM10_-_Dive", "HM1_-_Cut", "HM2_-_Fly", "HM3_-_Surf", "HM4_-_Strength", "HM5_-_Defog", "HM6_-_Rock_Smash", "HM7_-_Waterfall", "HM8_-_Rock_Climb", "HM9_-_Whirlpool", "TM100_-_Horn_Drill", "TM101_-_Body_Slam", "TM102_-_Take_Down", "TM103_-_Double-Edge", "TM104_-_BubbleBeam", "TM105_-_Water_Gun", "TM106_-_Pay_Day", "TM107_-_Submission", "TM108_-_Counter", "TM109_-_Seismic_Toss", "TM10_-_Hidden_Power", "TM110_-_Rage", "TM111_-_Mega_Drain", "TM112_-_Dragon_Rage", "TM113_-_Fissure", "TM114_-_Teleport", "TM115_-_Mimic", "TM116_-_Bide", "TM117_-_Metronome", "TM118_-_Selfdestruct", "TM119_-_Egg_Bomb", "TM11_-_Sunny_Day", "TM120_-_Swift", "TM121_-_Skull_Bash", "TM122_-_Softboiled", "TM123_-_Sky_Attack", "TM124_-_Psywave", "TM125_-_Tri_Attack", "TM126_-_DynamicPunch", "TM127_-_Headbutt", "TM128_-_Curse", "TM129_-_Rollout", "TM12_-_Taunt", "TM130_-_Zap_Cannon", "TM131_-_Sweet_Scent", "TM132_-_Snore", "TM133_-_Icy_Wind", "TM134_-_Giga_Drain", "TM135_-_Endure", "TM136_-_Iron_Tail", "TM137_-_DragonBreath", "TM138_-_Mud-Slap", "TM139_-_Ice_Punch", "TM13_-_Ice_Beam", "TM140_-_Sleep_Talk", "TM141_-_Defense_Curl", "TM142_-_Thunder_Punch", "TM143_-_Detect", "TM144_-_Steel_Wing", "TM145_-_Fire_Punch", "TM146_-_Fury_Cutter", "TM147_-_Nightmare", "TM148_-_Focus_Punch", "TM149_-_Water_Pulse", "TM14_-_Blizzard", "TM150_-_Bullet_Seed", "TM151_-_Shock_Wave", "TM152_-_Secret_Power", "TM153_-_Skill_Swap", "TM154_-_Snatch", "TM155_-_Roost", "TM156_-_Brine", "TM157_-_Dragon_Pulse", "TM158_-_Drain_Punch", "TM159_-_Silver_Wind", "TM15_-_Hyper_Beam", "TM160_-_Recycle", "TM161_-_Avalanche", "TM162_-_Stealth_Rock", "TM163_-_Captivate", "TM164_-_Dark_Pulse", "TM165_-_Natural_Gift", "TM16_-_Light_Screen", "TM17_-_Protect", "TM18_-_Rain_Dance", "TM19_-_Telekinesis", "TM1_-_Hone_Claws", "TM20_-_Safeguard", "TM21_-_Frustration", "TM22_-_SolarBeam", "TM23_-_Smack_Down", "TM24_-_Thunderbolt", "TM25_-_Thunder", "TM26_-_Earthquake", "TM27_-_Return", "TM28_-_Dig", "TM29_-_Psychic", "TM2_-_Dragon_Claw", "TM30_-_Shadow_Ball", "TM31_-_Brick_Break", "TM32_-_Double_Team", "TM33_-_Reflect", "TM34_-_Sludge_Wave", "TM35_-_Flamethrower", "TM36_-_Sludge_Bomb", "TM37_-_Sandstorm", "TM38_-_Fire_Blast", "TM39_-_Rock_Tomb", "TM3_-_Psyshock", "TM40_-_Aerial_Ace", "TM41_-_Torment", "TM42_-_Facade", "TM43_-_Flame_Charge", "TM44_-_Rest", "TM45_-_Attract", "TM46_-_Thief", "TM47_-_Low_Sweep", "TM48_-_Round", "TM49_-_Echoed_Voice", "TM4_-_Calm_Mind", "TM50_-_Overheat", "TM51_-_Ally_Switch", "TM52_-_Focus_Blast", "TM53_-_Energy_Ball", "TM54_-_False_Swipe", "TM55_-_Scald", "TM56_-_Fling", "TM57_-_Charge_Beam", "TM58_-_Sky_Drop", "TM59_-_Incinerate", "TM5_-_Roar", "TM60_-_Quash", "TM61_-_Will-O-Wisp", "TM62_-_Acrobatics", "TM63_-_Embargo", "TM64_-_Explosion", "TM65_-_Shadow_Claw", "TM66_-_Payback", "TM67_-_Retaliate", "TM68_-_Giga_Impact", "TM69_-_Rock_Polish", "TM6_-_Toxic", "TM70_-_Flash", "TM71_-_Stone_Edge", "TM72_-_Volt_Switch", "TM73_-_Thunder_Wave", "TM74_-_Gyro_Ball", "TM75_-_Swords_Dance", "TM76_-_Struggle_Bug", "TM77_-_Psych_Up", "TM78_-_Bulldoze", "TM79_-_Frost_Breath", "TM7_-_Hail", "TM80_-_Rock_Slide", "TM81_-_X-Scissor", "TM82_-_Dragon_Tail", "TM83_-_Work_Up", "TM84_-_Poison_Jab", "TM85_-_Dream_Eater", "TM86_-_Grass_Knot", "TM87_-_Swagger", "TM88_-_Pluck", "TM89_-_U-Turn", "TM8_-_Bulk_Up", "TM90_-_Substitute", "TM91_-_Flash_Cannon", "TM92_-_Trick_Room", "TM93_-_Wild_Charge", "TM94_-_Rock_Smash", "TM95_-_Snarl", "TM96_-_Mega_Punch", "TM97_-_Razor_Wind", "TM98_-_Whirlwind", "TM99_-_Mega_Kick", "TM9_-_Venoshock"};
		int i = (int) (Math.random() * items.length);
		return Item.getByNameOrId("pixelmon:" + items[i]);
	}
	
	public static Item getRandomEvoStone(){
		String[] items = {"item.Sun_Stone", "item.Thunder_Stone", "item.Water_Stone", "item.Dawn_Stone"/*, "item.Ever_Stone"*/, "item.Dusk_Stone", "item.Fire_Stone", "item.Leaf_Stone", "item.Moon_Stone", "item.Shiny_Stone"};
		int i = (int) (Math.random() * items.length);
		return Item.getByNameOrId("pixelmon:" + items[i]);
	}
	
	public static void tpAll(Position position, String name){
		for(EntityPlayerMP p : getAllPlayers()){
			endBattle(p);
			tp(p, position, name);
		}
	}
	
	public static void endBattle(EntityPlayerMP p){
		if(BattleRegistry.getBattle(p.getDisplayNameString()) != null)
			MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/endbattle " + p.getName());
	}
	public static void tp(EntityPlayerMP p, Position pos, String name) {
		if(p==null)
			return;
		try{
			endBattle(p);
			try {
				PixelmonStorage.PokeballManager.getPlayerStorage(p).recallAllPokemon();
			} catch (PlayerNotLoadedException e) {
				e.printStackTrace();
			}
			if (p.ridingEntity != null) {
	            p.mountEntity(null);
	        }
	       
	        p.playerNetServerHandler.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());
			
			if(name.equals(""))
				p.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + ">>" + EnumChatFormatting.GOLD + "Teleported to " + pos.getX() +" "+ pos.getY()+" "+pos.getZ()));
			else
				p.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + ">>" + EnumChatFormatting.GOLD + "Teleported to " + name));
		}catch(Exception e){
			SafariGames.instance.log.log(Level.ERROR, "Couldn't tp " + p.getDisplayNameString() + " to " + name);
		}
	}
	
	
	
	
	
	
	public static void checkGameStatus() {
    	/*File f = new File(SafariGames.instance.modFolderPath + "/status.cfg");
    	try {
			if(f.createNewFile()){
				FileWriter fw = new FileWriter(f);
				fw.write("Safari phase: " + EnumPhase.POST.toString());
				fw.close();
			}
			FileReader fr = new FileReader(f);
        	BufferedReader br = new BufferedReader(fr);
        	String line = br.readLine();
        	StringTokenizer st = new StringTokenizer(line, ": ");
        	st.nextToken();
        	String status = st.nextToken();
        	br.close();
        	if(status.equals(EnumPhase.POST.toString())){
        		
        	}else if(status.equals(EnumPhase.JOIN.toString())){
        		deletePlayerFiles();
        	}else{
        		Game g= new Game();
        		
        	}
        	
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	private static void deletePlayerFiles() {
		File playerDir = new File(DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath()+ "/pokemon/");
		for(File f:playerDir.listFiles()){
			f.delete();
		}
	}
	public static List<UUID> getAllUUID() {
		List<UUID>uuid=new ArrayList<UUID>();
		for(EntityPlayerMP p:getAllPlayers()){
			uuid.add(p.getUniqueID());
		}
		return uuid;
	}
	public static void giveItemToPlayers(Item item, int q) {
		for(EntityPlayerMP p: getAllPlayers() ){
			p.inventory.addItemStackToInventory(new ItemStack(item, q));
		}
	}
	public static void addPlayersToWhitelist() {
		for(EntityPlayerMP p:getAllPlayers()){
			MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/whitelist add " + p.getName());
			SafariGames.instance.whitelist.add(p.getName());
		}
	}
	public static void removePlayersToWhitelist() {
		for(String s:SafariGames.instance.whitelist){
			MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/whitelist remove " + s);
		}
		SafariGames.instance.whitelist.clear();
	}
	public static boolean isOp(ICommandSender sender) {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(((EntityPlayerMP) sender).getGameProfile());
	}
	public static String getNameFromUUID(UUID p1) {
		EntityPlayerMP p= getPlayerFromUUID(p1);
		if(p!=null)
			return p.getName();
		else if(p1==null)
			return "null";
		else
			return p1.toString();
	}
	public static void showTitle(String s) {
		MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/title @a times 1 10 1" );
		MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "/title @a title {text:\"" + s + "\",bold:true,underlined:false,italic:false,strikethrough:false,obfuscated:false} " );

	}
	public static EntityPlayerMP getPlayerFromName(String name) {
		for(EntityPlayerMP p : getAllPlayers()){
			if(p.getName().equalsIgnoreCase(name))
				return p;
		}
		return null;
		
	}
}
