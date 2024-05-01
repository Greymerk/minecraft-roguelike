package com.greymerk.roguelike.events;

import java.util.Map;

import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.MetaEntity;
import com.greymerk.roguelike.monster.MonsterProfile;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents.Load;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class EntityLoadEvent implements Load{

	@Override
	public void onLoad(Entity entity, ServerWorld world) {
		if(!(entity instanceof SkeletonEntity || entity instanceof ZombieEntity)) return;
		Random rand = world.getRandom();
		MobEntity mob = (MobEntity)entity;
		
		Map<RegistryEntry<StatusEffect>, StatusEffectInstance> effects = mob.getActiveStatusEffects();
		if(!effects.containsKey(StatusEffects.MINING_FATIGUE)) return;
		
		StatusEffectInstance effect = effects.get(StatusEffects.MINING_FATIGUE);
		int amp = effect.getAmplifier();
		//int level = rand.nextInt(amp) + 1;
		mob.removeStatusEffect(StatusEffects.MINING_FATIGUE);
		
		IEntity monster = new MetaEntity(mob);
		
		System.out.println("level: " + amp);
		MonsterProfile.equip(world, rand, amp, monster);
	}
}
