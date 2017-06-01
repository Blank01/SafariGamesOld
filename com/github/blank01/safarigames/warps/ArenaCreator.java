package com.github.blank01.safarigames.warps;

import net.minecraft.util.Vec3;

public class ArenaCreator {
	private Position pos1;
	private Position pos2;
	

	public ArenaCreator(){
		pos1=null;
		pos2=null;
	}

	public void addFirst(Position loc){
		this.pos1=loc;
	}
	public void addSecond(Position loc){
		this.pos2=loc;

	}
	public Arena getArena(){
		if(pos1==null||pos2==null){
			return null;
		}else{
			
			Arena a=new Arena(pos1, pos2);
			pos1=null;
			pos2=null;
			return a;
		}
	}
	public Position getPos1() {
		return pos1;
	}

	public Position getPos2() {
		return pos2;
	}

	
}
