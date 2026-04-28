package com.greymerk.roguelike.monster;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.dungeon.Difficulty;

public class MetaEntity implements IEntity {

	private Mob mob;
	
	public MetaEntity(Mob mob){
		this.mob = mob;
	}
	
	@Override
	public void setSlot(EquipmentSlot slot, ItemStack item) {
		mob.setItemSlot(slot, item);
		if(Config.ofBoolean(Config.MOB_DROPS)) {
			mob.setDropChance(slot, 0.085f);
		} else {
			mob.setDropChance(slot, 0);
		}
	}
	
	public ItemStack getEquippedStack(EquipmentSlot slot) {
		return ((LivingEntity)this.mob).getItemBySlot(slot);
	}
	
	@Override
	public void setMobClass(MobType type, boolean clear) {
		
		LivingEntity oldMob = (LivingEntity)this.mob;
		LivingEntity newMob = (LivingEntity)MobType.getEntity(this.mob.level(), type);
		newMob.copyPosition(oldMob);
		this.mob = (Mob)newMob;
		
		if(newMob instanceof Zombie){
			((Zombie)newMob).setBaby(((Zombie)oldMob).isBaby());
		}
		
		if(clear) {
			List.of(EquipmentSlot.values()).forEach(slot -> {
				mob.setItemSlot(slot, ItemStack.EMPTY);
			});
		} else {
			List.of(EquipmentSlot.values()).forEach(slot -> {
				ItemStack toTrade = oldMob.getItemBySlot(slot);
				this.setSlot(slot, toTrade);
			});
		}
		
		oldMob.remove(RemovalReason.DISCARDED);
		newMob.level().addFreshEntity(newMob);
	}

	@Override
	public void setChild(boolean child) {
		if(!(this.mob instanceof Zombie)) return;
		((Zombie)this.mob).setBaby(child);
	}

	@Override
	public boolean instance(Class<?> type) {
		return type.isInstance(this.mob);
	}

	@Override
	public void setName(String name) {
		this.mob.setCustomName(Component.nullToEmpty(name));
		this.mob.setCustomNameVisible(true);
	}

	@Override
	public void setOnFire(int duration) {
		this.mob.igniteForSeconds(duration);;
	}
	
	@Override
	public void setEffect(MobEffectInstance effect) {
		this.mob.addEffect(effect);
	}
	
	@Override
	public boolean canEnchant(RandomSource rand, Difficulty diff) {
		
		Level world = this.mob.level();

		switch(world.getDifficulty()){
		case PEACEFUL: return false;
		case EASY: return rand.nextInt(6) == 0;
		case NORMAL: return diff.gt(Difficulty.EASIEST) && rand.nextInt(4) == 0;
		case HARD: return rand.nextBoolean();
		}
		
		return false;
	}
}
