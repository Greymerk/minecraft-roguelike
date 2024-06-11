package com.greymerk.roguelike.treasure.loot.potions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

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
	
	public static String getEffectID(PotionEffect type){
		switch(type) {
		case ABSORPTION: return "absorption";
		case BAD_LUCK: return "unluck";
		case BLINDNESS: return "blindness";
		case DAMAGE: return "instant_damage";
		case FATIGUE: return "mining_fatigue";
		case FIRERESIST: return "fire_resistance";
		case GLOWING: return "glowing";
		case HASTE: return "haste";
		case HEALTH: return "instant_health";
		case HEALTHBOOST: return "health_boost";
		case HUNGER: return "hunger";
		case INVISIBILITY: return "invisibility";
		case JUMP: return "jump_boost";
		case LEVITATION: return "levitation";
		case LUCK: return "luck";
		case NAUSIA: return "nausia";
		case NIGHTVISION: return "night_vision";
		case POISON: return "poison";
		case REGEN: return "regeneration";
		case RESISTANCE: return "resistance";
		case SATURATION: return "saturation";
		case SLOWNESS: return "slowness";
		case SPEED: return "speed";
		case STRENGTH: return "strength";
		case WATERBREATH: return "water_breathing";
		case WEAKNESS: return "weakness";
		case WITHER: return "wither";
		default: return "weakness";	
		}
	}
	
	public static RegistryEntry<StatusEffect> getStatusEffect(PotionEffect type) {
		switch(type) {
		case ABSORPTION: return StatusEffects.ABSORPTION;
		case BAD_LUCK: return StatusEffects.UNLUCK;
		case BLINDNESS: return StatusEffects.BLINDNESS;
		case DAMAGE: return StatusEffects.INSTANT_DAMAGE;
		case FATIGUE: return StatusEffects.MINING_FATIGUE;
		case FIRERESIST: return StatusEffects.FIRE_RESISTANCE;
		case GLOWING: return StatusEffects.GLOWING;
		case HASTE: return StatusEffects.HASTE;
		case HEALTH: return StatusEffects.INSTANT_HEALTH;
		case HEALTHBOOST: return StatusEffects.HEALTH_BOOST;
		case HUNGER: return StatusEffects.HUNGER;
		case INVISIBILITY: return StatusEffects.INVISIBILITY;
		case JUMP: return StatusEffects.JUMP_BOOST;
		case LEVITATION: return StatusEffects.LEVITATION;
		case LUCK: return StatusEffects.LUCK;
		case NAUSIA: return StatusEffects.NAUSEA;
		case NIGHTVISION: return StatusEffects.NIGHT_VISION;
		case POISON: return StatusEffects.POISON;
		case REGEN: return StatusEffects.REGENERATION;
		case RESISTANCE: return StatusEffects.RESISTANCE;
		case SATURATION: return StatusEffects.SATURATION;
		case SLOWNESS: return StatusEffects.SLOWNESS;
		case SPEED: return StatusEffects.SPEED;
		case STRENGTH: return StatusEffects.STRENGTH;
		case WATERBREATH: return StatusEffects.WATER_BREATHING;
		case WEAKNESS: return StatusEffects.WEAKNESS;
		case WITHER: return StatusEffects.WITHER;
		default: return StatusEffects.WEAKNESS;
		}
	}
	
	public static void addCustomEffect(ItemStack potion, PotionEffect type, int amplifier, int duration){
		
		RegistryEntry<StatusEffect> effect = getStatusEffect(type);
		PotionContentsComponent contents = potion.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
		List<StatusEffectInstance> effects = contents.customEffects();
		StatusEffectInstance instance = new StatusEffectInstance(effect, duration * TICKS_PER_SECOND, Math.max(0, amplifier - 1));
		
		List<StatusEffectInstance> flist = new ArrayList<StatusEffectInstance>();
		flist.addAll(effects);
		flist.add(instance);
		
		PotionContentsComponent replace = new PotionContentsComponent(Optional.empty(), contents.customColor(), flist);
		potion.set(DataComponentTypes.POTION_CONTENTS, replace);
	}
	
	public static StatusEffectInstance getInstance(PotionEffect type, int amplifier, int duration) {
		return new StatusEffectInstance(getStatusEffect(type), duration * TICKS_PER_SECOND, Math.max(0, amplifier - 1));
	}
}
