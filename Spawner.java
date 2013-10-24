package greymerk.roguelike;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MobSpawnerBaseLogic;
import net.minecraft.src.NBTTagCompound;
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
		world.setBlock(posX, posY, posZ, Block.mobSpawner.blockID, 0, 2);
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getBlockTileEntity(posX, posY, posZ);

        if (spawner != null)
        {
        	String name = getSpawnerName(type);
        	MobSpawnerBaseLogic logic = spawner.getSpawnerLogic();
        	logic.setMobID(name);
        	
        	//setRoguelike(logic);
        	
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

    public static void addEquipment(World world, int rank, Entity mob){
   			
		Random rand = world.rand;
				
		int difficulty = world.difficultySetting;
		
		boolean enchant = difficulty == 3 ? true : false;
		
		ItemStack weapon;
		
		// zombie gets a sword
		if(mob instanceof EntityZombie){
			
			if(((EntityZombie)mob).isChild() && enchant && rand.nextInt(100) == 0){
				weapon = ItemNovelty.getItem(ItemNovelty.ASHLEA);
			} else if(rand.nextInt(5) == 0){
				weapon = ItemLoot.getSword(rand, rank, enchant);
			} else {
				weapon = ItemLoot.getTool(rand, rank, enchant);
			}
			
			mob.setCurrentItemOrArmor(0, weapon);
		}
		
		// skelly gets a bow
		if(mob instanceof EntitySkeleton){
			
			if(rand.nextInt(10) == 0 && rank > 1){
				((EntitySkeleton) mob).setSkeletonType(1);
				mob.setCurrentItemOrArmor(0, ItemLoot.getSword(rand, rank, enchant));
			} else {
				if(rand.nextInt(20) == 0){
					mob.setCurrentItemOrArmor(0, ItemLoot.getSword(rand, rank, enchant));
				} else {
					mob.setCurrentItemOrArmor(0, ItemLoot.getBow(rand, rank, enchant));
				}
			}
		}
		
		// put on some armour
		for(int i = 1; i < 5; i++){
			
			int chance;
			chance = 5 - rank + ((3 - difficulty) * 2);
			if (difficulty == 3 && rank >= 3){
				mob.setCurrentItemOrArmor(i, ItemLoot.getEquipmentBySlot(rand, i, rank, enchant));
			} else if(rand.nextInt(chance) == 0){
				mob.setCurrentItemOrArmor(i, ItemLoot.getEquipmentBySlot(rand, i, rank, enchant));
			}
		}
    }
	
	private static void setRoguelike(MobSpawnerBaseLogic logic){
    	NBTTagCompound nbt = new NBTTagCompound();
    	nbt.setString("Type", logic.getEntityNameToSpawn());
    	nbt.setInteger("Weight", 1);
    	
    	NBTTagCompound properties = new NBTTagCompound();
    	nbt.setCompoundTag("Properties", properties);
    	
    	properties.setString("CustomName", "roguelike");
    	
    	WeightedRandomMinecart cart = new WeightedRandomMinecart(logic, nbt);
    	logic.setRandomMinecart(cart);
    	logic.updateSpawner();
    }
}
