package com.github.blank01.safarigames.warps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.biome.BiomeGenBase;

public class WarpManager{
	private Map<String, Warp> biomeWarps;
	private Map<String, Warp> warps;
	private List<Arena> arenas;
	private String saveFolder;

	public WarpManager(String saveFolder){
		this.saveFolder = saveFolder;
		warps = new HashMap<String, Warp>();
		biomeWarps = new HashMap<String, Warp>();
		arenas = new ArrayList<Arena>();
	}
	
	
	public Position getWarp(String name) {
		for(String w:warps.keySet()){
			if(name.equalsIgnoreCase(w)){
				return warps.get(w).getPos();
			}
		}
		for(String w:this.biomeWarps.keySet()){
			if(name.equalsIgnoreCase(w)){
				return biomeWarps.get(w).getPos();
			}
		}
		return null;
	}
	public Position getBiomeWarp(String name) {
		for(String w:this.biomeWarps.keySet()){
			if(name.equalsIgnoreCase(w)){
				return biomeWarps.get(w).getPos();
			}
		}
		return null;
	}
	public Position getArena(int n, int i) {
		if(i==1)
			return arenas.get(n).getPos1();
		else if(i==2)
			return arenas.get(n).getPos2();
		else
			return getWarp("tournament");
	}
	public Collection<String> getWarpList() {
		SortedSet<String> warps=new TreeSet<String>();
		warps.addAll(this.biomeWarps.keySet());
		if(this.warps.containsKey("pokecenter"))
			warps.add("pokecenter");
		return warps;
		
	}
	public int addArena(Arena a) {
		this.arenas.add(a);
		try {
			this.writeArenas();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.arenas.size();
		
	}
	public void addWarp(Warp w){
		this.warps.put(w.getName(), w);
		try {
			this.writeWarps();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addBiomeWarp(Warp w){
		
		this.biomeWarps.put(w.getName(), w);
		try {
			this.writeBiomes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void initWarps() {		
		readFromFiles();
	}


	public void writeBiomes() throws IOException {
		
		File f = new File(this.saveFolder + "/biomes.dat");
		
		FileWriter fw = new FileWriter(new File(this.saveFolder + "/biomes.txt"));
        for(String s: this.biomeWarps.keySet()){
        	fw.write(s + " " + this.biomeWarps.get(s).getPos().getX() + " "+ this.biomeWarps.get(s).getPos().getY() + " "+ this.biomeWarps.get(s).getPos().getZ() + " " +this.biomeWarps.get(s).getPos().getYaw() + " " + this.biomeWarps.get(s).getPos().getPitch()  + "\n");
        }
        fw.close();
	}

	public void writeWarps() throws IOException{
		FileWriter fw = new FileWriter(new File(this.saveFolder + "/warps.txt"));
        for(String s: this.warps.keySet()){
        	fw.write(s + " " + this.warps.get(s).getPos().getX() + " "+ this.warps.get(s).getPos().getY() + " "+ this.warps.get(s).getPos().getZ() + " " + this.warps.get(s).getPos().getYaw() + " " + this.warps.get(s).getPos().getPitch() + "\n");
        }
        fw.close();
	}
	
	public void writeArenas() throws IOException{
		FileWriter fw = new FileWriter(new File(this.saveFolder + "/arenas.txt"));
        for(Arena a: this.arenas){
        	fw.write(a.getPos1().getX() + " "+ a.getPos1().getY() + " "+ a.getPos1().getZ() + " " + a.getPos1().getYaw() + " " + a.getPos1().getPitch() + " " + a.getPos2().getX() + " "+ a.getPos2().getY() + " "+ a.getPos2().getZ() + " " + a.getPos2().getYaw() + " " + a.getPos2().getPitch() + "\n");
        }
        fw.close();
        
	}

	public void readFromFiles() {
		try
	      {
			File dir = new File(this.saveFolder);
			dir.mkdirs();
	         BufferedReader br = new BufferedReader(new FileReader(new File(this.saveFolder + "/biomes.txt")));
	         String line="";
	         while((line=br.readLine())!=null){
	        	 StringTokenizer st=new StringTokenizer(line, " \n");
	        	 String name = st.nextToken();
	        	 double x = Double.parseDouble(st.nextToken());
	        	 double y = Double.parseDouble(st.nextToken());
	        	 double z = Double.parseDouble(st.nextToken());
	        	 float yaw = Float.parseFloat(st.nextToken());
	        	 float pitch = Float.parseFloat(st.nextToken());
	        	 this.biomeWarps.put(name, new Warp(new Position(x,y,z, yaw, pitch), name));
	   	     }
	         br.close();
	         
	         br = new BufferedReader(new FileReader(new File(this.saveFolder + "/warps.txt")));
	         line="";
	         while((line=br.readLine())!=null){
	        	 StringTokenizer st=new StringTokenizer(line, " \n");
	        	 String name = st.nextToken();
	        	 double x = Double.parseDouble(st.nextToken());
	        	 double y = Double.parseDouble(st.nextToken());
	        	 double z = Double.parseDouble(st.nextToken());
	        	 float yaw = Float.parseFloat(st.nextToken());
	        	 float pitch = Float.parseFloat(st.nextToken());

	        	 this.warps.put(name, new Warp(new Position(x,y,z, yaw, pitch), name));
	   	     }
	         br.close();
	         
	         br = new BufferedReader(new FileReader(new File(this.saveFolder + "/arenas.txt")));
	         line="";
	         while((line=br.readLine())!=null){
	        	 StringTokenizer st=new StringTokenizer(line, " \n");
	        	 
	        	 double x = Double.parseDouble(st.nextToken());
	        	 double y = Double.parseDouble(st.nextToken());
	        	 double z = Double.parseDouble(st.nextToken());
	        	 Float yaw = Float.parseFloat(st.nextToken());
	        	 float pitch = Float.parseFloat(st.nextToken());
	        	 double x1 = Double.parseDouble(st.nextToken());
	        	 double y1 = Double.parseDouble(st.nextToken());
	        	 double z1 = Double.parseDouble(st.nextToken());
	        	 float yaw1 = Float.parseFloat(st.nextToken());
	        	 float pitch1 = Float.parseFloat(st.nextToken());
	        	 
	        	 this.arenas.add(new Arena(new Position(x,y,z, yaw, pitch), new Position(x1,y1,z1, yaw1, pitch1)));
	   	     }
	         br.close();
	         
	      }catch(IOException ioe)
	      {
	         return;
	      }
	}


	public int getTotalArenas() {
		return this.arenas.size();
	}


	public void removeArena(int n) {
		this.arenas.remove(n);
		try {
			this.writeArenas();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
