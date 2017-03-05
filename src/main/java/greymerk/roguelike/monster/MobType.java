package greymerk.roguelike.monster;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public enum MobType {
	
	ZOMBIE, ZOMBIEVILLAGER, HUSK, SKELETON, STRAY, SPIDER, CREEPER, WITHERSKELETON, PIGZOMBIE, WITCH;
	
	
	@SuppressWarnings("deprecation")
	public static Entity getEntity(World world, MobType type){
		switch(type){
		case ZOMBIE: return new EntityZombie(world);
		case ZOMBIEVILLAGER:
			EntityZombie zombie = new EntityZombie(world);
			List<VillagerProfession> professions = VillagerRegistry.instance().getRegistry().getValues();
			VillagerProfession profession = professions.get(world.rand.nextInt(professions.size()));
			zombie.setVillagerType(profession);
			return zombie;
		case HUSK:
			EntityZombie husk = new EntityZombie(world);
			husk.setZombieType(ZombieType.HUSK);
			return husk;
		case SKELETON: return new EntitySkeleton(world);
		case STRAY:
			EntitySkeleton stray = new EntitySkeleton(world);
			stray.setSkeletonType(SkeletonType.STRAY);
			return stray;
		case SPIDER: return new EntitySpider(world);
		case CREEPER: return new EntityCreeper(world);
		case WITHERSKELETON:
			EntitySkeleton wither = new EntitySkeleton(world);
			wither.setSkeletonType(SkeletonType.STRAY);
			return wither;
		case PIGZOMBIE: return new EntityPigZombie(world);
		case WITCH: return new EntityWitch(world);
		
		default: return new EntityZombie(world);
		}
	}
}
