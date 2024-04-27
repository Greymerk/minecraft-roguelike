package com.greymerk.roguelike.monster.profiles;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.treasure.loot.Shield;
import com.greymerk.roguelike.treasure.loot.Slot;
import com.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import com.greymerk.roguelike.treasure.loot.provider.ItemNovelty;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProfileRleahy implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		ItemStack weapon = ItemNovelty.getItem(ItemNovelty.RLEAHY);
		mob.setSlot(EquipmentSlot.MAINHAND, weapon);
		mob.setSlot(EquipmentSlot.OFFHAND, Shield.get(world.getRegistryManager(), rand));
		
		ItemStack item = ItemArmour.get(rand, Slot.FEET, Quality.WOOD);
		ItemArmour.dyeArmor(item, 32, 32, 32);
		mob.setSlot(EquipmentSlot.FEET, item);

		item = ItemArmour.get(rand, Slot.LEGS, Quality.WOOD);
		ItemArmour.dyeArmor(item, 0, 51, 102);
		mob.setSlot(EquipmentSlot.LEGS, item);
		
		item = ItemArmour.get(rand, Slot.CHEST, Quality.WOOD);
		ItemArmour.dyeArmor(item, 255, 204, 229);
		mob.setSlot(EquipmentSlot.CHEST, item);
	}
}
