package com.greymerk.roguelike.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
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
		case ZOMBIEVILLAGER: return new ZombieVillager(EntityTypes.ZOMBIE_VILLAGER, world);
		case HUSK: return new Husk(EntityTypes.HUSK, world);
		case SKELETON: return new Skeleton(EntityTypes.SKELETON, world);
		case STRAY: return new Stray(EntityTypes.STRAY, world);
		case SPIDER: return new Spider(EntityTypes.SPIDER, world);
		case CREEPER: return new Creeper(EntityTypes.CREEPER, world);
		case WITHERSKELETON: return new WitherSkeleton(EntityTypes.WITHER_SKELETON, world);
		case PIGLIN: return new ZombifiedPiglin(EntityTypes.ZOMBIFIED_PIGLIN, world);
		case EVOKER: return new Evoker(EntityTypes.EVOKER, world);
		case VINDICATOR: return new Vindicator(EntityTypes.VINDICATOR, world);
		case WITCH: return new Witch(EntityTypes.WITCH, world);
		case BOGGED: return new Bogged(EntityTypes.BOGGED, world);
		
		default: return new Zombie(world);
		}
	}
}
