package com.greymerk.roguelike.util;

import net.minecraft.util.math.random.Random;

import net.minecraft.util.DyeColor;

// @ TODO change this to use color objects
public enum Color {

	WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME,
	PINK, GRAY, LIGHT_GRAY, CYAN, PURPLE, BLUE, BROWN, GREEN,
	RED, BLACK;
	
	public static DyeColor get(Color color){
		switch(color){
		case WHITE: return DyeColor.WHITE;
		case ORANGE: return DyeColor.ORANGE;
		case MAGENTA: return DyeColor.MAGENTA;
		case LIGHT_BLUE: return DyeColor.LIGHT_BLUE;
		case YELLOW: return DyeColor.YELLOW;
		case LIME: return DyeColor.LIME;
		case PINK: return DyeColor.PINK;
		case GRAY: return DyeColor.GRAY;
		case LIGHT_GRAY: return DyeColor.LIGHT_GRAY;
		case CYAN: return DyeColor.CYAN;
		case PURPLE: return DyeColor.PURPLE;
		case BLUE: return DyeColor.BLUE;
		case BROWN: return DyeColor.BROWN;
		case GREEN: return DyeColor.GREEN;
		case RED: return DyeColor.RED;
		case BLACK: return DyeColor.BLACK;
		default: return DyeColor.WHITE;
		}
	}
	
	public static Color get(Random rand){
		return Color.values()[rand.nextInt(Color.values().length)];
	}
	
	public static int RGBToColor(int r, int g, int b){
		return r << 16 | g << 8 | b << 0;
	}
	
	public static int HSLToColor(float h, float s, float l){
	    float r, g, b;

	    if (s == 0f) {
	        r = g = b = l;
	    } else {
	        float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
	        float p = 2 * l - q;
	        r = hueToRgb(p, q, h + 1f/3f);
	        g = hueToRgb(p, q, h);
	        b = hueToRgb(p, q, h - 1f/3f);
	    }
	    return RGBToColor((int) (r * 255), (int) (g * 255), (int) (b * 255));
	}

	public static float hueToRgb(float p, float q, float t) {
	    if (t < 0f)
	        t += 1f;
	    if (t > 1f)
	        t -= 1f;
	    if (t < 1f/6f)
	        return p + (q - p) * 6f * t;
	    if (t < 1f/2f)
	        return q;
	    if (t < 2f/3f)
	        return p + (q - p) * (2f/3f - t) * 6f;
	    return p;
	}
}
