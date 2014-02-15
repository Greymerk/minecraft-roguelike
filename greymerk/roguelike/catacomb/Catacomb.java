package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.IDungeonFactory;
import greymerk.roguelike.config.ConfigFile;
import greymerk.roguelike.config.RogueConfig;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class Catacomb {
		
	private World world;
	public static final int DEPTH = 5;
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
	private Random rand;
	private CatacombNode previous;
	
	public Catacomb(World world, Random rand){
		
		this.world = world;
		this.rand = rand;
		this.previous = null;
	}
	
	public void generate(int inX, int inZ){
				
		int x = inX;
		int y = TOPLEVEL;
		int z = inZ;
		
		// generate levels
		while(y > DEPTH){
			
			CatacombLevel level;
			
			if(Catacomb.getLevel(y) == 0){
				level = new CatacombLevel(world, rand, x, y, z, 10, 40);
			} else {
				level = new CatacombLevel(world, rand, x, y, z);
			}
	
			while(!level.isDone()){
				level.update();
			}			

			level.generate();
			CatacombNode end = level.getEnd();
			x = end.getX();
			y = y - VERTICAL_SPACING;
			z = end.getZ();

		} 
		
		CatacombTower tower = new CatacombTower(world, rand, inX, TOPLEVEL, inZ);
		tower.generate();
		
		
	}
	
	public static boolean canSpawn(int chunkX, int chunkZ, World world){
		
		if(!RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN)){
			return false;
		}
		
		if(!(world.provider.dimensionId == RogueConfig.getInt(RogueConfig.DIMENSION))){
			return false;
		}

			
		chunkX -= 4;
		
		int x = chunkX * 16;
		int z = chunkZ * 16;
		
		BiomeGenBase[] invalidBiomes = {
				BiomeGenBase.ocean, 
				BiomeGenBase.frozenOcean, 
				BiomeGenBase.extremeHills, 
				BiomeGenBase.iceMountains, 
				BiomeGenBase.jungleHills
				};
		
		BiomeGenBase localBiome = world.getBiomeGenForCoords(x, z);

		for(BiomeGenBase biome : invalidBiomes){
			if(biome == localBiome){
				return false;
			}
		}

		int frequency = RogueConfig.getInt(RogueConfig.SPAWNFREQUENCY);
		int min = 8 * frequency / 10;
		int max = 32 * frequency / 10;
		
		min = min < 2 ? 2 : min;
		max = max < 8 ? 8 : max;
		
		int tempX = chunkX < 0 ? chunkX - (max - 1) : chunkX;
		int tempZ = chunkZ < 0 ? chunkZ - (max - 1) : chunkZ;

		int m = tempX / max;
		int n = tempZ / max;
		
		Random r = world.setRandomSeed(m, n, 10387312);
		
		m *= max;
		n *= max;
		
		m += r.nextInt(max - min);
		n += r.nextInt(max - min);
		
		if(!(chunkX == m && chunkZ == n)){
			return false;
		}
		
		return true;
	}
	
	public static IDungeonFactory getFactory(Random rand, int rank){
		
		DungeonFactory factory;
		
		switch(rank){
		case 0:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			if(RogueConfig.getBoolean(RogueConfig.GENEROUS)) factory.addSingle(Dungeon.SMITH);
			factory.addSingle(Dungeon.MUSIC);
			factory.addSingle(Dungeon.CAKE);
			if(rand.nextBoolean()){
				factory.addSingle(Dungeon.FIRE);
			} else {
				factory.addSingle(Dungeon.SLIME);
			}
			factory.addRandom(Dungeon.BRICK, 3);
			break;
		case 1:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			int choice = rand.nextInt(5);
			if(choice == 0) factory.addSingle(Dungeon.ETHO);
			if(choice == 1) factory.addSingle(Dungeon.BTEAM);
			factory.addSingle(Dungeon.PIT);
			factory.addSingle(Dungeon.MESS);
			if(RogueConfig.getInt(RogueConfig.LEVELMAXROOMS) > 30) factory.addSingle(Dungeon.MESS);
			factory.addSingle(Dungeon.ENCHANT);
			factory.addSingle(Dungeon.SMITH);
			factory.addSingle(Dungeon.LAB);
			factory.addRandom(Dungeon.BRICK, 3);
			break;
		case 2:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			factory.addSingle(Dungeon.OSSUARY);
			factory.addSingle(Dungeon.CREEPER);			
			factory.addSingle(Dungeon.FIRE);
			factory.addSingle(Dungeon.PRISON);
			factory.addSingle(Dungeon.CRYPT);
			factory.addSingle(Dungeon.PIT);
			factory.addRandom(Dungeon.BRICK, 3);
			factory.addRandom(Dungeon.FIRE, 30);
			break;	
		case 3:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			if(rand.nextInt(5) == 0) factory.addSingle(Dungeon.BAJ);
			factory.addSingle(Dungeon.OSSUARY);
			factory.addSingle(Dungeon.ENDER);
			factory.addSingle(Dungeon.CRYPT);
			factory.addSingle(Dungeon.PRISON);
			factory.addSingle(Dungeon.SPIDER);
			factory.addSingle(Dungeon.CREEPER);
			factory.addRandom(Dungeon.BRICK, 3);
			factory.addRandom(Dungeon.FIRE, 20);
			factory.addRandom(Dungeon.CRYPT, 20);
			factory.addRandom(Dungeon.PRISON, 20);
			factory.addRandom(Dungeon.SPIDER, 20);
			factory.addRandom(Dungeon.SLIME, 20);
			factory.addRandom(Dungeon.PIT, 20);
			factory.addRandom(Dungeon.CREEPER, 20);
			break;
		case 4:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			if(rand.nextInt(5) == 0) factory.addSingle(Dungeon.ENIKO);
			factory.addSingle(Dungeon.FIRE);
			factory.addRandom(Dungeon.NETHER, 3);
			factory.addRandom(Dungeon.NETHERFORT, 20);
			factory.addRandom(Dungeon.SLIME, 30);
			factory.addRandom(Dungeon.SPIDER, 30);
			factory.addRandom(Dungeon.FIRE, 50);
			break;
		default:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
		}

		return factory;
	}
	
	public static void spawnInChunk(World world, Random rand, int chunkX, int chunkZ, int frequency) {
		
		if(world.provider.dimensionId != 0){
			return;
		}
		
		if(Catacomb.canSpawn(chunkX, chunkZ, world)){
			int x = chunkX * 16 + 4;
			int z = chunkZ * 16 + 4;
			
			Catacomb cata = new Catacomb(world, rand);
			cata.generate(x, z);
		}
	}
	
	public static int getLevel(int y){
		
		if (y < 15){
			return 4;
		}
		
		if (y < 25){
			return 3;
		}
		
		if (y < 35){
			return 2;
		}
		
		if (y < 45){
			return 1;
		}
		
		return 0;
	}
}
