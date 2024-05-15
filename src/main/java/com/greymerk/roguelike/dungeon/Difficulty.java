package com.greymerk.roguelike.dungeon;

public enum Difficulty {

	EASIEST(0),
	EASY(1),
	MEDIUM(2),
	HARD(3),
	HARDEST(4);

	private int value;
	Difficulty(int i) {
		this.value = i;
	}
	
	public static int value(Difficulty diff) {
		return diff.value;
	}
	
	public static int fromY(int y) {
		if(y >= 40) return EASIEST.value;
		if(y >= 20) return EASY.value;
		if(y >= -10) return MEDIUM.value;
		if(y >= -30) return HARD.value;
		return HARDEST.value;
	}
	
	
}
