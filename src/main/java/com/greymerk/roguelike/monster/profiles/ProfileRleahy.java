package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.treasure.loot.Slot;
import com.greymerk.roguelike.treasure.loot.items.Shield;
import com.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import com.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ProfileRleahy implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
		ItemStack weapon = ItemNovelty.getItem(world.registryAccess(), ItemNovelty.RLEAHY);
		mob.setSlot(EquipmentSlot.MAINHAND, weapon);
		mob.setSlot(EquipmentSlot.OFFHAND, Shield.get(world.registryAccess(), rand));
		
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
