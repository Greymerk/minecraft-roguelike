package com.greymerk.roguelike.monster;

import com.greymerk.roguelike.dungeon.Difficulty;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public interface IMonsterProfile {
	
	public void addEquipment(World world, Random rand, Difficulty diff, IEntity mob);
	
}
