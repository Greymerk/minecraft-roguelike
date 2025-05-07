package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MobType;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.treasure.loot.Slot;
import com.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import com.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import com.greymerk.roguelike.treasure.loot.trim.Trim;
import com.greymerk.roguelike.treasure.loot.trim.TrimMaterial;
import com.greymerk.roguelike.treasure.loot.trim.TrimPattern;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ProfileFireArcher implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, Difficulty diff, IEntity mob) {
		
		mob.setMobClass(MobType.STRAY, false);
		
//		mob.setOnFire(60 * 60);
//		mob.setEffect(PotionEffect.getInstance(PotionEffect.FIRERESIST, 1, 60 * 60));
//		mob.setEffect(PotionEffect.getInstance(PotionEffect.SPEED, 1, 60 * 60));
		
		mob.setSlot(EquipmentSlot.MAINHAND, ItemNovelty.getItem(world.getRegistryManager(), ItemNovelty.BURNING));
		
		for(EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HEAD,
				EquipmentSlot.CHEST,
				EquipmentSlot.LEGS,
				EquipmentSlot.FEET
				}){
			ItemStack item = ItemArmour.get(rand, Slot.getSlot(slot), Quality.WOOD);
			Enchant.enchantItem(world.getEnabledFeatures(), rand, item, 20);
			ItemArmour.dyeArmor(item, 200, 50, 52); // dark red
			Trim.set(world.getRegistryManager(), item, TrimPattern.RIB, TrimMaterial.GOLD);
			mob.setSlot(slot, item);
		}
		
	}

}
