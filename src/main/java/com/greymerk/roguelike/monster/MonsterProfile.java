package com.greymerk.roguelike.monster;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.monster.profiles.ProfileArcher;
import com.greymerk.roguelike.monster.profiles.ProfileAshlea;
import com.greymerk.roguelike.monster.profiles.ProfileBaby;
import com.greymerk.roguelike.monster.profiles.ProfileEvoker;
import com.greymerk.roguelike.monster.profiles.ProfileFireArcher;
import com.greymerk.roguelike.monster.profiles.ProfileHusk;
import com.greymerk.roguelike.monster.profiles.ProfileJohnny;
import com.greymerk.roguelike.monster.profiles.ProfileMagicArcher;
import com.greymerk.roguelike.monster.profiles.ProfilePigman;
import com.greymerk.roguelike.monster.profiles.ProfilePoisonArcher;
import com.greymerk.roguelike.monster.profiles.ProfileRleahy;
import com.greymerk.roguelike.monster.profiles.ProfileSkeleton;
import com.greymerk.roguelike.monster.profiles.ProfileSwordsman;
import com.greymerk.roguelike.monster.profiles.ProfileTallMob;
import com.greymerk.roguelike.monster.profiles.ProfileVillager;
import com.greymerk.roguelike.monster.profiles.ProfileVindicator;
import com.greymerk.roguelike.monster.profiles.ProfileWitch;
import com.greymerk.roguelike.monster.profiles.ProfileWither;
import com.greymerk.roguelike.monster.profiles.ProfileZombie;

import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public enum MonsterProfile {

	TALLMOB, ZOMBIE, PIGMAN, SKELETON, VILLAGER, HUSK, BABY, ASHLEA, RLEAHY, 
	ARCHER, WITHER, FIREARCHER, POISONARCHER, MAGICARCHER, SWORDSMAN, EVOKER, VINDICATOR,
	WITCH, JOHNNY;
	
	public static IMonsterProfile get(MonsterProfile profile){
		switch(profile){
		case TALLMOB: return new ProfileTallMob();
		case ZOMBIE: return new ProfileZombie();
		case PIGMAN: return new ProfilePigman();
		case SKELETON: return new ProfileSkeleton();
		case VILLAGER: return new ProfileVillager();
		case HUSK: return new ProfileHusk();
		case BABY: return new ProfileBaby();
		case ASHLEA: return new ProfileAshlea();
		case RLEAHY: return new ProfileRleahy();
		case ARCHER: return new ProfileArcher();
		case WITHER: return new ProfileWither();
		case FIREARCHER: return new ProfileFireArcher();
		case POISONARCHER: return new ProfilePoisonArcher();
		case MAGICARCHER: return new ProfileMagicArcher();
		case SWORDSMAN: return new ProfileSwordsman();
		case EVOKER: return new ProfileEvoker();
		case VINDICATOR: return new ProfileVindicator();
		case WITCH: return new ProfileWitch();
		case JOHNNY: return new ProfileJohnny();
		default: return new ProfileTallMob();
		}
	}
	
	public static void equip(World world, Random rand, Difficulty diff, IEntity mob){
		
		IMonsterProfile profile = null;
		
		if(mob.instance(ZombieEntity.class)) profile = get(ZOMBIE);
		
		if(mob.instance(SkeletonEntity.class)) profile = get(SKELETON);
		
		if(profile == null) return;
		
		profile.addEquipment(world, rand, diff, mob);
	}
}
