package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MobType;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.treasure.loot.Slot;
import com.greymerk.roguelike.treasure.loot.potions.PotionEffect;
import com.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import com.greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import com.greymerk.roguelike.treasure.loot.trim.Trim;
import com.greymerk.roguelike.treasure.loot.trim.TrimMaterial;
import com.greymerk.roguelike.treasure.loot.trim.TrimPattern;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ProfileFireArcher implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
		
		mob.setMobClass(MobType.STRAY, false);
		
		mob.setOnFire(60 * 60);
		mob.setEffect(PotionEffect.getInstance(PotionEffect.FIRERESIST, 1, 60 * 60));
		mob.setEffect(PotionEffect.getInstance(PotionEffect.SPEED, 1, 60 * 60));
		
		mob.setSlot(EquipmentSlot.MAINHAND, ItemNovelty.getItem(world.registryAccess(), ItemNovelty.BURNING));
		
		for(EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HEAD,
				EquipmentSlot.CHEST,
				EquipmentSlot.LEGS,
				EquipmentSlot.FEET
				}){
			ItemStack item = ItemArmour.get(rand, Slot.getSlot(slot), Quality.WOOD);
			Enchant.enchantItem(world.registryAccess(), rand, item, 20);
			ItemArmour.dyeArmor(item, 200, 50, 52); // dark red
			Trim.set(world.registryAccess(), item, TrimPattern.RIB, TrimMaterial.GOLD);
			mob.setSlot(slot, item);
		}
		
	}

}
