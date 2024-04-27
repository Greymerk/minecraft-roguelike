package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Loot;
import com.greymerk.roguelike.treasure.loot.Slot;
import com.greymerk.roguelike.treasure.loot.trim.Trim;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ProfileTallMob implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		int ilvl = level <= 2 ? level : 2 + rand.nextInt(3);
		for(EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HEAD,
				EquipmentSlot.CHEST,
				EquipmentSlot.LEGS,
				EquipmentSlot.FEET
				}){
			ItemStack item = Loot.getEquipmentBySlot(rand, Slot.getSlot(slot), ilvl, Enchant.canEnchant(world.getDifficulty(), rand, level));
			DynamicRegistryManager reg = world.getRegistryManager();
			Trim.addRandom(reg, item, rand);
			mob.setSlot(slot, item);
		}

	}

}
