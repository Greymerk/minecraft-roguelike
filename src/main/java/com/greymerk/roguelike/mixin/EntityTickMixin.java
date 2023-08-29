package com.greymerk.roguelike.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.greymerk.roguelike.monster.MetaEntity;
import com.greymerk.roguelike.monster.MonsterProfile;
import com.greymerk.roguelike.treasure.loot.PotionEffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@Mixin(Entity.class)
public abstract class EntityTickMixin{

	@Inject(at = @At("HEAD"), method = "tick()V")
	public void tick(CallbackInfo info) {
		Entity entity = (Entity)(Object)this;
		if(entity.age > 1) return;
		if(!(entity instanceof MobEntity)) return;
		World world = entity.getEntityWorld();
		Random rand = world.getRandom();
		MetaEntity e = new MetaEntity(entity);
		Map<StatusEffect, StatusEffectInstance> effects = ((MobEntity)entity).getActiveStatusEffects();
		StatusEffect fatigue = Registries.STATUS_EFFECT.get(PotionEffect.getEffectID(PotionEffect.FATIGUE));
		if(!effects.containsKey(fatigue)) return;
		StatusEffectInstance effect = effects.get(fatigue);
		MonsterProfile.equip(world, rand, effect.getAmplifier(), e);
	}
}
