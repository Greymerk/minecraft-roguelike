package com.greymerk.roguelike.monster;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public interface IEntity {

	public void setSlot(EquipmentSlot slot, ItemStack item);
	
	public void clear();
	
	public void setMobClass(MobType type, boolean clear);
	
	public void setChild(boolean child);
	
	public boolean instance(Class<?> type);
	
	public void setName(String name);
	
}
