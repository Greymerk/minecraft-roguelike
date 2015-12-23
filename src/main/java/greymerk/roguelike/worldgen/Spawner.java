package greymerk.roguelike.worldgen;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;

public enum Spawner {
	
	CREEPER, CAVESPIDER, SPIDER, SKELETON, ZOMBIE, SILVERFISH, ENDERMAN, WITCH, WITHERBOSS, BAT,
	LAVASLIME, BLAZE, SLIME, PRIMEDTNT, PIGZOMBIE;
	
	private static final Spawner[] common = {SPIDER, SKELETON, ZOMBIE};
	
	public static void generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos){
		Spawner type = common[rand.nextInt(common.length)];
		generate(editor, rand, settings, pos, type);
	}
	
	public static void generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, Spawner type){
		
		if(settings.getSpawners() != null){
			settings.getSpawners().generate(editor, rand, pos, type, settings.getDifficulty(pos));
			return;
		}
		
		generate(editor, rand, settings.getDifficulty(pos), pos, type);
	}
	
	public static void generate(WorldEditor editor, Random rand, int level, Coord pos, Spawner type){
				
		if(!editor.setBlock(pos, new MetaBlock(Blocks.mob_spawner))) return;
		
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) editor.getTileEntity(pos);

		if (spawner == null) return;
		
		String name = getSpawnerName(type);
		MobSpawnerBaseLogic logic = spawner.func_145881_a();
		logic.setEntityName(name);
		
		if(RogueConfig.getBoolean(RogueConfig.ROGUESPAWNERS))setRoguelike(logic, level, name);

	}	
	
	public static String getSpawnerName(Spawner type) {
		switch(type){
		case CREEPER: return "Creeper";
		case CAVESPIDER: return "CaveSpider";
		case SPIDER: return "Spider";
		case SKELETON: return "Skeleton";
		case ZOMBIE: return "Zombie";
		case SILVERFISH: return "Silverfish";
		case ENDERMAN: return "Enderman";
		case WITCH:	return "Witch";
		case WITHERBOSS: return "WitherBoss";
		case BAT: return "Bat";
		case LAVASLIME:	return "LavaSlime";
		case BLAZE:	return "Blaze";
		case SLIME:	return "Slime";
		case PRIMEDTNT:	return "PrimedTnt";
		case PIGZOMBIE:	return "PigZombie";
		default: return "pig";
		}
	}

	public static void setMeta(MobSpawnerBaseLogic logic, NBTTagCompound meta){
		MobSpawnerBaseLogic.WeightedRandomMinecart cart = logic.new WeightedRandomMinecart(meta);
    	logic.setRandomEntity(cart);
    	logic.updateSpawner();
	}
	
	public static void setRoguelike(MobSpawnerBaseLogic logic, int level, String type){
    	NBTTagCompound nbt = new NBTTagCompound();
    	nbt.setString("Type", type);
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
