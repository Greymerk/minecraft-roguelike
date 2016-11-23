package greymerk.roguelike.util;

import java.util.Random;

import net.minecraft.item.EnumDyeColor;

public enum DyeColor {

	WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME,
	PINK, GRAY, LIGHT_GRAY, CYAN, PURPLE, BLUE, BROWN, GREEN,
	RED, BLACK;
	
	public static EnumDyeColor get(DyeColor color){
		switch(color){
		case WHITE: return EnumDyeColor.WHITE;
		case ORANGE: return EnumDyeColor.ORANGE;
		case MAGENTA: return EnumDyeColor.MAGENTA;
		case LIGHT_BLUE: return EnumDyeColor.LIGHT_BLUE;
		case YELLOW: return EnumDyeColor.YELLOW;
		case LIME: return EnumDyeColor.LIME;
		case PINK: return EnumDyeColor.PINK;
		case GRAY: return EnumDyeColor.GRAY;
		case LIGHT_GRAY: return EnumDyeColor.SILVER;
		case CYAN: return EnumDyeColor.CYAN;
		case PURPLE: return EnumDyeColor.PURPLE;
		case BLUE: return EnumDyeColor.BLUE;
		case BROWN: return EnumDyeColor.BROWN;
		case GREEN: return EnumDyeColor.GREEN;
		case RED: return EnumDyeColor.RED;
		case BLACK: return EnumDyeColor.BLACK;
		default: return EnumDyeColor.WHITE;
		}
	}
	
	public static DyeColor get(Random rand){
		return DyeColor.values()[rand.nextInt(DyeColor.values().length)];
	}
}
