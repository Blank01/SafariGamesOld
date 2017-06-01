package com.github.blank01.safarigames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.github.blank01.safarigames.commands.BattlefaultCommand;
import com.github.blank01.safarigames.commands.EvolveCommand;
import com.github.blank01.safarigames.commands.KillTeamCommand;
import com.github.blank01.safarigames.commands.SafariCommand;
import com.github.blank01.safarigames.commands.SetWarpCommand;
import com.github.blank01.safarigames.commands.StartGameCommand;
import com.github.blank01.safarigames.commands.TournamentCommand;
import com.github.blank01.safarigames.commands.WarpCommand;
import com.github.blank01.safarigames.events.wildfightsDenier;
import com.github.blank01.safarigames.phases.EnumPhase;
import com.github.blank01.safarigames.phases.SafariPhase;
import com.github.blank01.safarigames.utils.MinecraftUtils;
import com.github.blank01.safarigames.warps.Arena;
import com.github.blank01.safarigames.warps.ArenaCreator;
import com.github.blank01.safarigames.warps.WarpManager;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.database.DatabaseHelper;
import com.pixelmonmod.pixelmon.database.DatabaseStorage;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;

@Mod(modid = SafariGames.MODID, name = "SafariGames", version = "0.0.1", serverSideOnly = true, acceptableRemoteVersions = "*")
public class SafariGames {
		public static final String MODID = "SafariGames";

        public static final int GUI_ID = 1;
        public static final int playerNumber = 16;
        public static final EventBus EVENT_BUS = new EventBus();
        public String modFolderPath;
        public List<String> whitelist;
       
		@Mod.Instance(MODID)
        public static SafariGames instance;

        public Logger log = LogManager.getLogger("SafariGames");
                
        private Game sg;
        private WarpManager wm;
        public ArenaCreator ac;
        public boolean end;
        
        @Mod.EventHandler
        public void preInit(FMLPreInitializationEvent event) {
			MinecraftUtils.checkGameStatus();
			this.modFolderPath=event.getModConfigurationDirectory().getAbsolutePath();
			
        }
        
        @Mod.EventHandler
        public void serverLoad(FMLServerStartingEvent event)
        {
        	
        	event.registerServerCommand(new StartGameCommand());
            event.registerServerCommand(new SafariCommand());
            event.registerServerCommand(new WarpCommand());
            event.registerServerCommand(new SetWarpCommand());
            event.registerServerCommand(new EvolveCommand());
            event.registerServerCommand(new BattlefaultCommand());
            event.registerServerCommand(new KillTeamCommand());
        }
		@Mod.EventHandler
        public void init(FMLInitializationEvent event){
			ac=new ArenaCreator();
			wm = new WarpManager(this.modFolderPath);
        	wm.initWarps();
        	whitelist = new ArrayList<String>();
        	this.log.log(Level.INFO, "SafariGames Loaded");
        	this.end=true;
        	Pixelmon.instance.EVENT_BUS.register(new wildfightsDenier());
        }
		
		
        
        public Game getSafariGame(){
        	return this.sg;
        }

        public EnumPhase getCurrentPhase(){
        	return this.sg.getCurrentPhase();
        }
        public void setSafariGame(Game g){
        	this.sg=g;
        }
		public WarpManager getWarpManager() {
			return this.wm;
		}

		public void startGame() {

			this.sg=new Game();

			sg.start();
			System.out.println("Game started");
		}

		public Arena getArena() {
			return ac.getArena();
		}

		public SafariPhase getPhase() {
			return this.sg.getPhase(this.getCurrentPhase());
		}
        

}
