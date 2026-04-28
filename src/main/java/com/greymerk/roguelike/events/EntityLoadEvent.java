package com.greymerk.roguelike.events;

import java.util.Map;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.IEntity;
import com.greymerk.roguelike.monster.MetaEntity;
import com.greymerk.roguelike.monster.MonsterProfile;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents.Load;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;

public class EntityLoadEvent implements Load{

	@Override
	public void onLoad(Entity entity, ServerLevel world) {
		if(!(entity instanceof Skeleton || entity instanceof Zombie)) return;
		RandomSource rand = world.getRandom();
		Mob mob = (Mob)entity;
		
		Map<Holder<MobEffect>, MobEffectInstance> effects = mob.getActiveEffectsMap();
		if(!effects.containsKey(MobEffects.MINING_FATIGUE)) return;
		
		MobEffectInstance effect = effects.get(MobEffects.MINING_FATIGUE);
		int amp = effect.getAmplifier();
		//int level = rand.nextInt(amp) + 1;
		mob.removeEffect(MobEffects.MINING_FATIGUE);
		
		IEntity monster = new MetaEntity(mob);
		
		Difficulty diff = Difficulty.from(amp); 
		
		MonsterProfile.equip(world, rand, diff, monster);
	}
}
