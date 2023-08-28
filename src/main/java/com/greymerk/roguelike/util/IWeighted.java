package com.greymerk.roguelike.util;

import net.minecraft.util.math.random.Random;

public interface IWeighted<T> {

	public int getWeight();
	
	public T get(Random rand);
		
}
