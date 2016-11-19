package greymerk.roguelike.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.world.World;

public enum MobType {
	
	ZOMBIE, ZOMBIEVILLAGER, HUSK, SKELETON, STRAY, SPIDER, CREEPER, WITHERSKELETON;
	
	
	public static Entity getEntity(World world, MobType type){
		switch(type){
		case ZOMBIE: return new EntityZombie(world);
		case ZOMBIEVILLAGER: return new EntityZombieVillager(world);
		case HUSK: return new EntityHusk(world);
		case SKELETON: return new EntitySkeleton(world);
		case STRAY: return new EntityStray(world);
		case SPIDER: return new EntitySpider(world);
		case CREEPER: return new EntityCreeper(world);
		case WITHERSKELETON: return new EntityWitherSkeleton(world);
		
		default: return new EntityZombie(world);
		}
	}
}
