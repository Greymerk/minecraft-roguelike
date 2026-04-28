package com.greymerk.roguelike.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.zombie.Husk;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.level.Level;

public enum MobType {
	
	ZOMBIE, ZOMBIEVILLAGER, HUSK, SKELETON, STRAY, SPIDER, CREEPER, WITHERSKELETON, PIGLIN, EVOKER, VINDICATOR, WITCH, BOGGED;
	
	public static Entity getEntity(Level world, MobType type){
		switch(type){
		case ZOMBIE: return new Zombie(world);
		case ZOMBIEVILLAGER: return new ZombieVillager(EntityType.ZOMBIE_VILLAGER, world);
		case HUSK: return new Husk(EntityType.HUSK, world);
		case SKELETON: return new Skeleton(EntityType.SKELETON, world);
		case STRAY: return new Stray(EntityType.STRAY, world);
		case SPIDER: return new Spider(EntityType.SPIDER, world);
		case CREEPER: return new Creeper(EntityType.CREEPER, world);
		case WITHERSKELETON: return new WitherSkeleton(EntityType.WITHER_SKELETON, world);
		case PIGLIN: return new ZombifiedPiglin(EntityType.ZOMBIFIED_PIGLIN, world);
		case EVOKER: return new Evoker(EntityType.EVOKER, world);
		case VINDICATOR: return new Vindicator(EntityType.VINDICATOR, world);
		case WITCH: return new Witch(EntityType.WITCH, world);
		case BOGGED: return new Bogged(EntityType.BOGGED, world);
		
		default: return new Zombie(world);
		}
	}
}
