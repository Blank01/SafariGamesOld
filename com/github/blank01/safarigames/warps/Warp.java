package com.github.blank01.safarigames.warps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.blank01.safarigames.phases.EnumPhase;

import net.minecraft.util.Vec3;

public class Warp implements Serializable {
	private Position pos;
	private String name;
	private List<EnumPhase> disabledPhases;
	private boolean systemWarp;
	
	public Warp(Position pos, String name){
		this.name=name;
		this.pos=pos;
		disabledPhases=new ArrayList<EnumPhase>();
		systemWarp=false;
	}
	public void setSystemWarp(boolean b){
		this.systemWarp=b;
	}
	public boolean isSystemWarp(){
		return this.isSystemWarp();
	}
	public void disableDuringPhase(EnumPhase phase){
		this.disabledPhases.add(phase);
	}
	public Position getPos() {
		return this.pos;
	}
	public String getName() {
		return name;
	}
}
