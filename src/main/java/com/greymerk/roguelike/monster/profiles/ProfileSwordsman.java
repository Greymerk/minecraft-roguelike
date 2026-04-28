package com.greymerk.roguelike.monster.profiles;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.items.Shield;
import com.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ProfileSwordsman implements IMonsterProfile {

	@Override
	public void addEquipment(Level world, RandomSource rand, Difficulty diff, IEntity mob) {
		ItemStack weapon = ItemWeapon.getSword(world.registryAccess(), world.enabledFeatures(), rand, diff, mob.canEnchant(rand, diff));
		
		mob.setSlot(EquipmentSlot.MAINHAND, weapon);
		mob.setSlot(EquipmentSlot.OFFHAND, Shield.get(world.registryAccess(), rand));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, diff, mob);
	}

}
