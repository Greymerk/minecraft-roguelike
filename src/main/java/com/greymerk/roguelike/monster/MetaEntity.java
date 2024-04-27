package com.greymerk.roguelike.monster;

import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class MetaEntity implements IEntity {

	private MobEntity mob;
	
	public MetaEntity(MobEntity mob){
		this.mob = mob;
	}
	
	@Override
	public void setSlot(EquipmentSlot slot, ItemStack item) {
		mob.equipStack(slot, item);
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
		
		for(EquipmentSlot slot : EquipmentSlot.values()){
			ItemStack toTrade = oldMob.getEquippedStack(slot);
			newMob.equipStack(slot, toTrade);
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

}
