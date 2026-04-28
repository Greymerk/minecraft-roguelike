package com.greymerk.roguelike.monster;

import com.greymerk.roguelike.dungeon.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public interface IMonsterProfile {
	
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob);
	
}
