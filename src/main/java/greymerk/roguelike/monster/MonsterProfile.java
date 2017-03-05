package greymerk.roguelike.monster;

import java.util.Random;

import greymerk.roguelike.monster.profiles.ProfileArcher;
import greymerk.roguelike.monster.profiles.ProfileAshlea;
import greymerk.roguelike.monster.profiles.ProfileBaby;
import greymerk.roguelike.monster.profiles.ProfileHusk;
import greymerk.roguelike.monster.profiles.ProfileMagicArcher;
import greymerk.roguelike.monster.profiles.ProfilePigman;
import greymerk.roguelike.monster.profiles.ProfilePoisonArcher;
import greymerk.roguelike.monster.profiles.ProfileRleahy;
import greymerk.roguelike.monster.profiles.ProfileSkeleton;
import greymerk.roguelike.monster.profiles.ProfileSwordsman;
import greymerk.roguelike.monster.profiles.ProfileTallMob;
import greymerk.roguelike.monster.profiles.ProfileVillager;
import greymerk.roguelike.monster.profiles.ProfileWitch;
import greymerk.roguelike.monster.profiles.ProfileWither;
import greymerk.roguelike.monster.profiles.ProfileZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

public enum MonsterProfile {

	TALLMOB, ZOMBIE, PIGMAN, SKELETON, VILLAGER, HUSK, BABY, ASHLEA, RLEAHY, 
	ARCHER, WITHER, POISONARCHER, MAGICARCHER, SWORDSMAN, WITCH;
	
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
		case POISONARCHER: return new ProfilePoisonArcher();
		case MAGICARCHER: return new ProfileMagicArcher();
		case SWORDSMAN: return new ProfileSwordsman();
		case WITCH: return new ProfileWitch();
		default: return new ProfileTallMob();
		}
	}
	
	public static void equip(World world, Random rand, int level, IEntity mob){
		
		IMonsterProfile profile = null;
		
		if(mob.instance(EntityZombie.class)) profile = get(ZOMBIE);
		
		if(mob.instance(EntitySkeleton.class)) profile = get(SKELETON);
		
		if(profile == null) return;
		
		profile.addEquipment(world, rand, level, mob);
	}
	

	
}
