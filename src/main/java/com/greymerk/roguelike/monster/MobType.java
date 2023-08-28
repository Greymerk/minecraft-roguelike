package com.greymerk.roguelike.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.world.World;

public enum MobType {
	
	ZOMBIE, ZOMBIEVILLAGER, HUSK, SKELETON, STRAY, SPIDER, CREEPER, WITHERSKELETON, PIGZOMBIE, EVOKER, VINDICATOR, WITCH;
	
	public static Entity getEntity(World world, MobType type){
		switch(type){
		case ZOMBIE: return new ZombieEntity(world);
		case ZOMBIEVILLAGER: return new ZombieVillagerEntity(EntityType.ZOMBIE_VILLAGER, world);
		case HUSK: return new HuskEntity(EntityType.HUSK, world);
		case SKELETON: return new SkeletonEntity(EntityType.SKELETON, world);
		case STRAY: return new StrayEntity(EntityType.STRAY, world);
		case SPIDER: return new SpiderEntity(EntityType.SPIDER, world);
		case CREEPER: return new CreeperEntity(EntityType.CREEPER, world);
		case WITHERSKELETON: return new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);
		case PIGZOMBIE: return new ZombifiedPiglinEntity(EntityType.ZOMBIFIED_PIGLIN, world);
		case EVOKER: return new EvokerEntity(EntityType.EVOKER, world);
		case VINDICATOR: return new VindicatorEntity(EntityType.VINDICATOR, world);
		case WITCH: return new WitchEntity(EntityType.WITCH, world);
		
		default: return new ZombieEntity(world);
		}
	}
}
