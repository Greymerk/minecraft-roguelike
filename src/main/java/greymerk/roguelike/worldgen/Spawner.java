package greymerk.roguelike.worldgen;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public enum Spawner {
	
	CREEPER, CAVESPIDER, SPIDER, SKELETON, ZOMBIE, SILVERFISH, ENDERMAN, WITCH, WITHERBOSS, BAT,
	LAVASLIME, BLAZE, SLIME, PRIMEDTNT, PIGZOMBIE;
	
	private static final Spawner[] common = {SPIDER, SKELETON, ZOMBIE};
	
	public static void generate(World world, Random rand, CatacombLevelSettings level, Coord pos){
		Spawner type = common[rand.nextInt(common.length)];
		generate(world, rand, level, pos, type);
	}
	
	public static void generate(World world, Random rand, CatacombLevelSettings level, Coord pos, Spawner type){
		
		if(level.getSpawners() != null){
			level.getSpawners().generate(world, rand, pos, type, getDifficulty(level, pos));
			return;
		}
		
		generate(world, rand, getDifficulty(level, pos), pos, type);
	}
	
	public static void generate(World world, Random rand, int level, Coord pos, Spawner type){
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(!WorldGenPrimitive.setBlock(world, x, y, z, Blocks.mob_spawner)) return;
		
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(x, y, z);

		if (spawner == null) return;
		
		String name = getSpawnerName(type);
		MobSpawnerBaseLogic logic = spawner.func_145881_a();
		logic.setEntityName(name);
		
		setRoguelike(logic, level);

	}	
	
	public static String getSpawnerName(Spawner type) {
		switch(type){
		case CREEPER:
			return "Creeper";
		case CAVESPIDER:
			return "CaveSpider";
		case SPIDER:
			return "Spider";
		case SKELETON:
			return "Skeleton";
		case ZOMBIE:
			return "Zombie";
		case SILVERFISH:
			return "Silverfish";
		case ENDERMAN:
			return "Enderman";
		case WITCH:
			return "Witch";
		case WITHERBOSS:
			return "WitherBoss";
		case BAT:
			return "Bat";
		case LAVASLIME:
			return "LavaSlime";
		case BLAZE:
			return "Blaze";
		case SLIME:
			return "Slime";
		case PRIMEDTNT:
			return "PrimedTnt";
		case PIGZOMBIE:
			return "PigZombie";
		default:
			return "pig";
		}

	}

	public static void setMeta(MobSpawnerBaseLogic logic, NBTTagCompound meta){
		MobSpawnerBaseLogic.WeightedRandomMinecart cart = logic.new WeightedRandomMinecart(meta);
    	logic.setRandomEntity(cart);
    	logic.updateSpawner();
	}
	
	public static void setRoguelike(MobSpawnerBaseLogic logic, int level){
    	NBTTagCompound nbt = new NBTTagCompound();
    	nbt.setString("Type", logic.getEntityNameToSpawn());
    	nbt.setInteger("Weight", 1);
    	
    	NBTTagCompound properties = new NBTTagCompound();
    	nbt.setTag("Properties", properties);
    	    	
    	NBTTagList activeEffects = new NBTTagList();
    	properties.setTag("ActiveEffects", activeEffects);
    	
    	NBTTagCompound buff = new NBTTagCompound();
    	activeEffects.appendTag(buff);
    	
    	buff.setByte("Id", (byte) 4);
    	buff.setByte("Amplifier", (byte) level);
    	buff.setInteger("Duration", 10);
    	buff.setByte("Ambient", (byte) 0);
    	
    	
    	MobSpawnerBaseLogic.WeightedRandomMinecart cart = logic.new WeightedRandomMinecart(nbt);
    	logic.setRandomEntity(cart);
    	logic.updateSpawner();
    }
	
	public static int getDifficulty(CatacombLevelSettings level, Coord pos){
		
		int levelDifficulty = level.getDifficulty();
		
		if(levelDifficulty == -1){
			return Catacomb.getLevel(pos.getY());
		}
		
		return levelDifficulty;
	}
}
