package com.greymerk.roguelike.treasure.loot;

import com.greymerk.roguelike.util.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.random.Random;

public class Firework {
	
	public static ItemStack get(Random rand, int stackSize){
	
		ItemStack rocket = new ItemStack(Items.FIREWORK_ROCKET, stackSize);
		
		NbtCompound tag = new NbtCompound();
		NbtCompound fireworks = new NbtCompound();
		
		fireworks.putByte("Flight", (byte) (rand.nextInt(3) + 1));
		
		NbtList explosion = new NbtList();
		
		NbtCompound properties = new NbtCompound();
		properties.putByte("Flicker", (byte) (rand.nextBoolean() ? 1 : 0));
		properties.putByte("Trail", (byte) (rand.nextBoolean() ? 1 : 0));
		properties.putByte("Type", (byte) (rand.nextInt(5)));
		
		int size = rand.nextInt(4) + 1;
		int[] colorArr = new int[size];
		for(int i = 0; i < size; ++i){
			colorArr[i] = Color.HSLToColor(rand.nextFloat(), (float)1.0, (float)0.5);
		}
		
		NbtIntArray colors = new NbtIntArray(colorArr);
		properties.put("Colors", colors);
		
		explosion.add(properties);
		fireworks.put("Explosions", explosion);
		tag.put("Fireworks", fireworks);
		
		rocket.setNbt(tag);
		
		return rocket;
	}
}
