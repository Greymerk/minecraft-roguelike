package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MobType;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.treasure.loot.Slot;
import com.greymerk.roguelike.treasure.loot.items.TippedArrow;
import com.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import com.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import com.greymerk.roguelike.treasure.loot.trim.Trim;
import com.greymerk.roguelike.treasure.loot.trim.TrimMaterial;
import com.greymerk.roguelike.treasure.loot.trim.TrimPattern;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ProfilePoisonArcher implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, Difficulty diff, IEntity mob) {
		
		mob.setMobClass(MobType.BOGGED, false);
		
		mob.setSlot(EquipmentSlot.OFFHAND, TippedArrow.get(Potions.STRONG_POISON));
		mob.setSlot(EquipmentSlot.MAINHAND, ItemWeapon.getBow(world.getRegistryManager(), world.getEnabledFeatures(), rand, diff, mob.canEnchant(rand, diff)));
		
		for(EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HEAD,
				EquipmentSlot.CHEST,
				EquipmentSlot.LEGS,
				EquipmentSlot.FEET
				}){
			ItemStack item = ItemArmour.get(rand, Slot.getSlot(slot), Quality.WOOD);
			Enchant.enchantItem(world.getRegistryManager(), rand, item, 20);
			ItemArmour.dyeArmor(item, 178, 255, 102); //bright lime green
			Trim.set(world.getRegistryManager(), item, TrimPattern.WILD, TrimMaterial.REDSTONE);
			mob.setSlot(slot, item);
		}
	}
}
