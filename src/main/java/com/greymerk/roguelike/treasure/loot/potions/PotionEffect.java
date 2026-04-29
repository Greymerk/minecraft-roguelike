package com.greymerk.roguelike.treasure.loot.potions;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;

public enum PotionEffect {
	
	SPEED(1), SLOWNESS(2), HASTE(3), FATIGUE(4), STRENGTH(5), HEALTH(6), DAMAGE(7), JUMP(8), 
	NAUSIA(9), REGEN(10), RESISTANCE(11), FIRERESIST(12), WATERBREATH(13), INVISIBILITY(14),
	BLINDNESS(15), NIGHTVISION(16), HUNGER(17), WEAKNESS(18), POISON(19), WITHER(20),
	HEALTHBOOST(21), ABSORPTION(22), SATURATION(23),
	GLOWING(24), LEVITATION(25), LUCK(26), BAD_LUCK(27);
	
	public static int TICKS_PER_SECOND = 20;
	
	public int id;
	PotionEffect(int id){
		this.id = id;
	}
	
	public static Holder<MobEffect> getStatusEffect(PotionEffect type) {
		switch(type) {
		case ABSORPTION: return MobEffects.ABSORPTION;
		case BAD_LUCK: return MobEffects.UNLUCK;
		case BLINDNESS: return MobEffects.BLINDNESS;
		case DAMAGE: return MobEffects.INSTANT_DAMAGE;
		case FATIGUE: return MobEffects.MINING_FATIGUE;
		case FIRERESIST: return MobEffects.FIRE_RESISTANCE;
		case GLOWING: return MobEffects.GLOWING;
		case HASTE: return MobEffects.HASTE;
		case HEALTH: return MobEffects.INSTANT_HEALTH;
		case HEALTHBOOST: return MobEffects.HEALTH_BOOST;
		case HUNGER: return MobEffects.HUNGER;
		case INVISIBILITY: return MobEffects.INVISIBILITY;
		case JUMP: return MobEffects.JUMP_BOOST;
		case LEVITATION: return MobEffects.LEVITATION;
		case LUCK: return MobEffects.LUCK;
		case NAUSIA: return MobEffects.NAUSEA;
		case NIGHTVISION: return MobEffects.NIGHT_VISION;
		case POISON: return MobEffects.POISON;
		case REGEN: return MobEffects.REGENERATION;
		case RESISTANCE: return MobEffects.RESISTANCE;
		case SATURATION: return MobEffects.SATURATION;
		case SLOWNESS: return MobEffects.SLOWNESS;
		case SPEED: return MobEffects.SPEED;
		case STRENGTH: return MobEffects.STRENGTH;
		case WATERBREATH: return MobEffects.WATER_BREATHING;
		case WEAKNESS: return MobEffects.WEAKNESS;
		case WITHER: return MobEffects.WITHER;
		default: return MobEffects.WEAKNESS;
		}
	}
	
	public static void addCustomEffect(ItemStack potion, PotionEffect type, int amplifier, int duration){
		
		Holder<MobEffect> effect = getStatusEffect(type);
		PotionContents contents = potion.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
		List<MobEffectInstance> effects = contents.customEffects();
		MobEffectInstance instance = new MobEffectInstance(effect, duration * TICKS_PER_SECOND, Math.max(0, amplifier - 1));
		
		List<MobEffectInstance> flist = new ArrayList<MobEffectInstance>();
		flist.addAll(effects);
		flist.add(instance);
		
		PotionContents replace = new PotionContents(java.util.Optional.empty(), contents.customColor(), flist, contents.customName());
		potion.set(DataComponents.POTION_CONTENTS, replace);
	}
	
	public static MobEffectInstance getInstance(PotionEffect type, int amplifier, int duration) {
		return new MobEffectInstance(getStatusEffect(type), duration * TICKS_PER_SECOND, Math.max(0, amplifier - 1));
	}
}
