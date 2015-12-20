package greymerk.roguelike.worldgen.blocks;

import java.util.Random;

public enum DyeColor {

	WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME,
	PINK, GRAY, SILVER, CYAN, PURPLE, BLUE, BROWN, GREEN,
	RED, BLACK;
	
	public static DyeColor get(Random rand){
		return DyeColor.values()[rand.nextInt(DyeColor.values().length)];
	}
	
	public static int get(DyeColor color){
		switch(color){
		case WHITE: return 0;
		case ORANGE: return 1;
		case MAGENTA: return 2;
		case LIGHT_BLUE: return 3;
		case YELLOW: return 4;
		case LIME: return 5;
		case PINK: return 6;
		case GRAY: return 7;
		case SILVER: return 8;
		case CYAN: return 9;
		case PURPLE: return 10;
		case BLUE: return 11;
		case BROWN: return 12;
		case GREEN: return 13;
		case RED: return 14;
		case BLACK: return 15;
		default: return 0;
		}
	}
}
