package com.github.blank01.safarigames.warps;

import java.io.Serializable;

import net.minecraft.util.Vec3;

public class Arena implements Serializable{
	private Position pos1;
	private Position pos2;
	public Arena(Position pos12, Position pos22){
		this.pos1=pos12;
		this.pos2=pos22;
	}
	public Position getPos1(){
		return this.pos1;
	}
	public Position getPos2(){
		return this.pos2;
	}
}
