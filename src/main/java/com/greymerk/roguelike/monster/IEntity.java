package com.greymerk.roguelike.monster;

import com.greymerk.roguelike.dungeon.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public interface IEntity {

	public void setSlot(EquipmentSlot slot, ItemStack item);
	
	public void setMobClass(MobType type, boolean clear);
	
	public void setChild(boolean child);
	
	public boolean instance(Class<?> type);
	
	public void setName(String name);
	
	public boolean canEnchant(RandomSource rand, Difficulty diff);

	void setOnFire(int duration);

	void setEffect(MobEffectInstance effect);
}
