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
