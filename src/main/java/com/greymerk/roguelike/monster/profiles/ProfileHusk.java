package com.greymerk.roguelike.monster.profiles;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.IMonsterProfile;
import com.greymerk.roguelike.monster.MobType;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.items.Shield;
import com.greymerk.roguelike.treasure.loot.provider.ItemTool;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProfileHusk implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		mob.setMobClass(MobType.HUSK, false);
		ItemStack weapon = ItemTool.getRandom(world.getEnabledFeatures(), rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
		mob.setSlot(EquipmentSlot.MAINHAND, weapon);
		mob.setSlot(EquipmentSlot.OFFHAND, Shield.get(world.getRegistryManager(), rand));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, level, mob);
	}
}
