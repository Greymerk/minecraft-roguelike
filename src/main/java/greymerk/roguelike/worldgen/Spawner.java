package greymerk.roguelike.worldgen;

import greymerk.roguelike.catacomb.Catacomb;

import java.util.List;
import java.util.Map;
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
	
	public static void generate(World world, Random rand, Coord cursor, Spawner type){
		generate(world, rand, cursor.getX(), cursor.getY(), cursor.getZ(), type);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, int level){
		Spawner type = common[rand.nextInt(common.length)];
		generate(world, rand, posX, posY, posZ, level, type);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ){
		Spawner type = common[rand.nextInt(common.length)];
		generate(world, rand, posX, posY, posZ, Catacomb.getLevel(posY), type);
	}
	
	public static void generate(World world, Random rand, SpawnerSettings settings, int x, int y, int z){
		Spawner type = common[rand.nextInt(common.length)];
		generate(world, rand, settings, x, y, z, Catacomb.getLevel(y), type);
	}
	
	public static void generate(World world, Random rand, SpawnerSettings settings, int x, int y, int z, int level, Spawner type){
		if(settings == null){
			generate(world, rand, x, y, z, type);
			return;
		}
		
		settings.generate(world, rand, new Coord(x, y, z), type, level);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, Spawner type){
		generate(world, rand, posX, posY, posZ, Catacomb.getLevel(posY), type);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, int level, Spawner type){
		
		if(!WorldGenPrimitive.setBlock(world, posX, posY, posZ, Blocks.mob_spawner)) return;
		
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(posX, posY, posZ);

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
}
