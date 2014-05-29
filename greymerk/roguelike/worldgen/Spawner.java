package greymerk.roguelike.worldgen;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MobSpawnerBaseLogic;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.WeightedRandomMinecart;
import net.minecraft.src.World;

public enum Spawner {
	
	CREEPER, CAVESPIDER, SPIDER, SKELETON, ZOMBIE, SILVERFISH, ENDERMAN, WITCH, WITHERBOSS, BAT,
	LAVASLIME, BLAZE, SLIME, PRIMEDTNT, PIGZOMBIE;
	
	private static final Spawner[] common = {SPIDER, SKELETON, ZOMBIE};
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ){
		Spawner type = common[rand.nextInt(common.length)];
		generate(world, rand, posX, posY, posZ, type);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, Spawner type){
		
		if(!WorldGenPrimitive.setBlock(world, posX, posY, posZ, Block.mobSpawner.blockID)){
			return;
		}
		
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getBlockTileEntity(posX, posY, posZ);

        if (spawner != null)
        {
        	String name = getSpawnerName(type);
        	MobSpawnerBaseLogic logic = spawner.getSpawnerLogic();
        	logic.setMobID(name);
        	
        	setRoguelike(logic);
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + posX + ", " + posY + ", " + posZ + ")");
        }
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

	
	private static void setRoguelike(MobSpawnerBaseLogic logic){
    	NBTTagCompound nbt = new NBTTagCompound();
    	nbt.setString("Type", logic.getEntityNameToSpawn());
    	nbt.setInteger("Weight", 1);
    	
    	NBTTagCompound properties = new NBTTagCompound();
    	nbt.setCompoundTag("Properties", properties);
    	    	
    	NBTTagList activeEffects = new NBTTagList();
    	properties.setTag("ActiveEffects", activeEffects);
    	
    	NBTTagCompound buff = new NBTTagCompound();
    	activeEffects.appendTag(buff);
    	
    	buff.setByte("Id", (byte) 4);
    	buff.setByte("Amplifier", (byte) 1);
    	buff.setInteger("Duration", 10);
    	buff.setByte("Ambient", (byte) 0);
    	
    	WeightedRandomMinecart cart = new WeightedRandomMinecart(logic, nbt);
    	logic.setRandomMinecart(cart);
    	logic.updateSpawner();
    }
}
