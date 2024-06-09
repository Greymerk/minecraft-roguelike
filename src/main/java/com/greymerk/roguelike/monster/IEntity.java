package com.greymerk.roguelike.monster;

import com.greymerk.roguelike.dungeon.Difficulty;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public interface IEntity {

	public void setSlot(EquipmentSlot slot, ItemStack item);
	
	public void setMobClass(MobType type, boolean clear);
	
	public void setChild(boolean child);
	
	public boolean instance(Class<?> type);
	
	public void setName(String name);
	
	public boolean canEnchant(Random rand, Difficulty diff);
}
