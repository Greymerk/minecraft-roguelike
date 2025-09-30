package com.greymerk.roguelike.monster;

import java.util.List;

import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.dungeon.Difficulty;

import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MetaEntity implements IEntity {

	private MobEntity mob;
	
	public MetaEntity(MobEntity mob){
		this.mob = mob;
	}
	
	@Override
	public void setSlot(EquipmentSlot slot, ItemStack item) {
		mob.equipStack(slot, item);
		if(Config.ofBoolean(Config.MOB_DROPS)) {
			mob.setEquipmentDropChance(slot, 0.085f);
		} else {
			mob.setEquipmentDropChance(slot, 0);
		}
	}
	
	public ItemStack getEquippedStack(EquipmentSlot slot) {
		return ((LivingEntity)this.mob).getEquippedStack(slot);
	}
	
	@Override
	public void setMobClass(MobType type, boolean clear) {
		
		LivingEntity oldMob = (LivingEntity)this.mob;
		LivingEntity newMob = (LivingEntity)MobType.getEntity(this.mob.getEntityWorld(), type);
		newMob.copyPositionAndRotation(oldMob);
		this.mob = (MobEntity)newMob;
		
		if(newMob instanceof ZombieEntity){
			((ZombieEntity)newMob).setBaby(((ZombieEntity)oldMob).isBaby());
		}
		
		if(clear) {
			List.of(EquipmentSlot.values()).forEach(slot -> {
				mob.equipStack(slot, ItemStack.EMPTY);
			});
		} else {
			List.of(EquipmentSlot.values()).forEach(slot -> {
				ItemStack toTrade = oldMob.getEquippedStack(slot);
				this.setSlot(slot, toTrade);
			});
		}
		
		oldMob.remove(RemovalReason.DISCARDED);
		newMob.getEntityWorld().spawnEntity(newMob);
	}

	@Override
	public void setChild(boolean child) {
		if(!(this.mob instanceof ZombieEntity)) return;
		((ZombieEntity)this.mob).setBaby(child);
	}

	@Override
	public boolean instance(Class<?> type) {
		return type.isInstance(this.mob);
	}

	@Override
	public void setName(String name) {
		this.mob.setCustomName(Text.of(name));
		this.mob.setCustomNameVisible(true);
	}

	@Override
	public void setOnFire(int duration) {
		this.mob.setOnFireFor(duration);;
	}
	
	@Override
	public void setEffect(StatusEffectInstance effect) {
		this.mob.addStatusEffect(effect);
	}
	
	@Override
	public boolean canEnchant(Random rand, Difficulty diff) {
		
		World world = this.mob.getEntityWorld();

		switch(world.getDifficulty()){
		case PEACEFUL: return false;
		case EASY: return rand.nextInt(6) == 0;
		case NORMAL: return diff.gt(Difficulty.EASIEST) && rand.nextInt(4) == 0;
		case HARD: return rand.nextBoolean();
		}
		
		return false;
	}
}
