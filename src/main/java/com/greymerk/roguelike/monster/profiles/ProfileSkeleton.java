package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MonsterProfile;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ProfileSkeleton implements IMonsterProfile{

	@Override
	public void addEquipment(World world, Random rand, Difficulty diff, IEntity mob) {
		
		if(diff.gt(Difficulty.EASY) && rand.nextInt(40) == 0){
			MonsterProfile.get(MonsterProfile.POISONARCHER).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(diff.gt(Difficulty.EASY) && rand.nextInt(50) == 0){
			MonsterProfile.get(MonsterProfile.MAGICARCHER).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(diff.gt(Difficulty.EASY) && rand.nextInt(10) == 0){
			MonsterProfile.get(MonsterProfile.WITHER).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(diff.gt(Difficulty.EASIEST) && rand.nextInt(20) == 0){
			MonsterProfile.get(MonsterProfile.SWORDSMAN).addEquipment(world, rand, diff, mob);
			return;
		}

		MonsterProfile.get(MonsterProfile.ARCHER).addEquipment(world, rand, diff, mob);
	}

}
