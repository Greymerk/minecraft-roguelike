package com.greymerk.roguelike.monster.profiles;



import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.TippedArrow;
import com.greymerk.roguelike.treasure.loot.provider.ItemWeapon;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.world.World;

public class ProfileArcher implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		
		if(Enchant.canEnchant(world.getDifficulty(), rand, level) && rand.nextInt(10) == 0){
			mob.setSlot(EquipmentSlot.OFFHAND, TippedArrow.getHarmful(rand, 1));
		}
		
		mob.setSlot(EquipmentSlot.MAINHAND, ItemWeapon.getBow(world.getEnabledFeatures(), rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level)));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, level, mob);
	}

}
