package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.treasure.loot.Loot;
import com.greymerk.roguelike.treasure.loot.Slot;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ProfileTallMob implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
		RegistryAccess reg = world.registryAccess();
		FeatureFlagSet features = world.enabledFeatures();
		
		int ilvl = diff.lt(Difficulty.HARD) ? diff.value : 2 + rand.nextInt(3);
		for(EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HEAD,
				EquipmentSlot.CHEST,
				EquipmentSlot.LEGS,
				EquipmentSlot.FEET
				}){
			ItemStack item = Loot.getEquipmentBySlot(features, reg, rand, Slot.getSlot(slot), Difficulty.from(ilvl), mob.canEnchant(rand, diff));
			mob.setSlot(slot, item);
		}

	}

}
