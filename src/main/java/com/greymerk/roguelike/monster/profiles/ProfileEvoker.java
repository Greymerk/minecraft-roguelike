package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MobType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class ProfileEvoker implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
		mob.setMobClass(MobType.EVOKER, true);
	}

}
