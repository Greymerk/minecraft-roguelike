package com.greymerk.roguelike.util.math;

public class MathHelper {

	public static int clamp(int num, int min, int max) {
		return Math.max(min, Math.min(max, num));
	}
	
}
