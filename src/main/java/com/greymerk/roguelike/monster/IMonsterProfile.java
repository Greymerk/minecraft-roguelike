package com.greymerk.roguelike.monster;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public interface IMonsterProfile {
	
	public void addEquipment(World world, Random rand, int level, IEntity mob);
	
}
