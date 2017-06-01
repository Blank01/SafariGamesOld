package com.github.blank01.safarigames.phases;

public enum EnumPhase {
JOIN("Join Phase"), 
TRAIN("Train Phase"), 
MIDDLE("Trading Phase"), 
TOURNAMENT("Tournament Phase"),
POST("Post Phase");

private String name;

EnumPhase(String name) {
    this.name = name;
}

public String getName() {
    return name;
}
public static EnumPhase getNextPhase(EnumPhase p){
	if(p==EnumPhase.JOIN){
		return EnumPhase.TRAIN;
	}else if(p==EnumPhase.TRAIN){
		return EnumPhase.MIDDLE;
	}else if(p==EnumPhase.MIDDLE){
		return EnumPhase.TOURNAMENT;
	}else if(p==EnumPhase.TOURNAMENT){
		return EnumPhase.POST;
	}else{
		return null;
	}
}
}
