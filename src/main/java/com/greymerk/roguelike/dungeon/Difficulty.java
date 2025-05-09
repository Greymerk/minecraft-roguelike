package com.greymerk.roguelike.dungeon;

public enum Difficulty {

	EASIEST(0),
	EASY(1),
	MEDIUM(2),
	HARD(3),
	HARDEST(4);

	public final int value;
	Difficulty(int i) {
		this.value = i;
	}
	
	public boolean gt(Difficulty other) {
		return this.compareTo(other) > 0;
	}
	
	public boolean lt(Difficulty other) {
		return this.compareTo(other) < 0;
	}
	
	public static Difficulty from(int ord) {
		if(ord < 0) return Difficulty.EASIEST;
		if(ord > Difficulty.values().length - 1) return Difficulty.HARDEST;
		return Difficulty.values()[ord];
	}
	
	public static int value(Difficulty diff) {
		return diff.value;
	}
	
	/*
	public static Difficulty fromY(int y) {
		if(y >= 40) return EASIEST;
		if(y >= 20) return EASY;
		if(y >= -10) return MEDIUM;
		if(y >= -30) return HARD;
		return HARDEST;
	}
	*/
	
	
}
