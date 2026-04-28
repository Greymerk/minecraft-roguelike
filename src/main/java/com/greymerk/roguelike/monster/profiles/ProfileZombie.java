package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.provider.ItemTool;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ProfileZombie implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
				
		if(diff.gt(Difficulty.MEDIUM) && rand.nextInt(100) == 0){
			MonsterProfile.get(MonsterProfile.PIGMAN).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(diff.gt(Difficulty.EASY) && rand.nextInt(100) == 0){
			MonsterProfile.get(MonsterProfile.WITCH).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(diff.gt(Difficulty.MEDIUM) && rand.nextInt(300) == 0){
			MonsterProfile.get(MonsterProfile.EVOKER).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(diff.gt(Difficulty.EASY) && rand.nextInt(100) == 0){
			MonsterProfile.get(MonsterProfile.JOHNNY).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(rand.nextInt(200) == 0){
			MonsterProfile.get(MonsterProfile.RLEAHY).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(rand.nextInt(200) == 0){
			MonsterProfile.get(MonsterProfile.ASHLEA).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(rand.nextInt(100) == 0){
			MonsterProfile.get(MonsterProfile.BABY).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(diff.gt(Difficulty.EASY) && rand.nextInt(20) == 0){
			MonsterProfile.get(MonsterProfile.HUSK).addEquipment(world, rand, diff, mob);
			return;
		}
		
		if(diff.lt(Difficulty.HARD) && rand.nextInt(20) == 0){
			MonsterProfile.get(MonsterProfile.VILLAGER).addEquipment(world, rand, diff, mob);
			return;
		}

		
		if(rand.nextInt(3) == 0) {
			MonsterProfile.get(MonsterProfile.SWORDSMAN).addEquipment(world, rand, diff, mob);
			return;
		}
		
		ItemStack weapon = ItemTool.getRandom(world.registryAccess(), world.enabledFeatures(), rand, diff, mob.canEnchant(rand, diff));
		mob.setSlot(EquipmentSlot.MAINHAND, weapon);
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, diff, mob);
	}

}
