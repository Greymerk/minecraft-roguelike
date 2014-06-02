package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.IDungeonFactory;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.catacomb.theme.ThemeTower;
import greymerk.roguelike.catacomb.theme.Themes;
import greymerk.roguelike.config.ConfigFile;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class Catacomb {
		
	public static final int DEPTH = 5;
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
	
	public static void generateNear(World world, Random rand, int x, int z){
		int attempts = 50;
		
		for(int i = 0;i < attempts;i++){
			Coord location = getNearbyCoord(rand, x, z, 40, 100);
			if(validLocation(world, rand, location.getX(), location.getZ())){
				Catacomb.generate(world, location.getX(), location.getZ());
				return;
			}
		}
	}
	
	public static void generate(World world, int inX, int inZ){
				
		int x = inX;
		int y = TOPLEVEL;
		int z = inZ;
		
    	Random rand = getRandom(world, inX, inZ);
    	
		// generate levels
		while(y > DEPTH){
			
			CatacombLevel level;
			
			ITheme theme = Themes.getByLevel(world.getBiomeGenForCoords(inX, inZ), getLevel(y), rand);
			
			if(Catacomb.getLevel(y) == 0){
				level = new CatacombLevel(world, rand, theme, x, y, z, 10, 40);
			} else {
				level = new CatacombLevel(world, rand, theme, x, y, z);
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
		
		CatacombTower tower = new CatacombTower();
		tower.generate(world, rand, inX, TOPLEVEL, inZ);
		
		
	}
	
	public static boolean canSpawnInChunk(int chunkX, int chunkZ, World world){
		
		if(!RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN)){
			return false;
		}
		
		if(!RogueConfig.getIntList(RogueConfig.DIMENSION).contains((Integer)world.provider.dimensionId)){
			return false;
		}

		int x = chunkX * 16;
		int z = chunkZ * 16;
		
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
	
	public static IDungeonFactory getFactory(Random rand, int level){
		
		DungeonFactory factory;
		int choice = rand.nextInt(10);
		
		switch(level){
		case 0:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			factory.addSingle(Dungeon.CAKE);
			factory.addSingle(Dungeon.SMITH);
			factory.addSingle(Dungeon.FIRE);
			factory.addRandom(Dungeon.BRICK, 3);
			break;
		case 1:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			choice = rand.nextInt(10);
			if(choice == 0) factory.addSingle(Dungeon.ETHO);
			if(choice == 1) factory.addSingle(Dungeon.BTEAM);
			if(choice == 2) factory.addSingle(Dungeon.AVIDYA);
			if(choice == 3) factory.addSingle(Dungeon.ASHLEA);
			factory.addSingle(Dungeon.MUSIC);
			factory.addSingle(Dungeon.PIT);
			factory.addSingle(Dungeon.ENCHANT);
			factory.addSingle(Dungeon.LAB);
			factory.addSingle(Dungeon.BRICK);
			factory.addByRatio(Dungeon.MESS, 30);
			factory.addByRatio(Dungeon.STORAGE, 30);
			factory.addRandom(Dungeon.BRICK, 3);
			break;
		case 2:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			choice = rand.nextInt(10);
			if(choice == 0) factory.addSingle(Dungeon.ENIKO);
			factory.addSingle(Dungeon.OSSUARY);
			factory.addSingle(Dungeon.CREEPER);
			factory.addSingle(Dungeon.FIRE);
			factory.addByRatio(Dungeon.PRISON, 10);
			factory.addByRatio(Dungeon.CRYPT, 10);
			factory.addByRatio(Dungeon.PIT, 10);
			factory.addByRatio(Dungeon.STORAGE, 30);
			factory.addRandom(Dungeon.BRICK, 3);
			break;	
		case 3:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			if(choice == 0) factory.addSingle(Dungeon.BAJ);
			if(choice == 1)factory.addSingle(Dungeon.NEBRIS);
			factory.addSingle(Dungeon.OSSUARY);
			factory.addSingle(Dungeon.ENDER);
			factory.addByRatio(Dungeon.CRYPT, 15);
			factory.addByRatio(Dungeon.PRISON, 15);
			factory.addByRatio(Dungeon.SPIDER, 15);
			factory.addByRatio(Dungeon.CREEPER, 15);
			factory.addByRatio(Dungeon.FIRE, 20);
			factory.addByRatio(Dungeon.STORAGE, 30);
			factory.addRandom(Dungeon.BRICK, 3);
			factory.addRandom(Dungeon.SPIDER, 20);
			factory.addRandom(Dungeon.SLIME, 20);
			factory.addRandom(Dungeon.PIT, 20);

			break;
		case 4:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			factory.addSingle(Dungeon.OBSIDIAN);
			factory.addByRatio(Dungeon.FIRE, 30);
			factory.addByRatio(Dungeon.NETHERFORT, 20);
			factory.addByRatio(Dungeon.SLIME, 15);
			factory.addByRatio(Dungeon.STORAGE, 30);
			factory.addRandom(Dungeon.NETHER, 3);
			factory.addRandom(Dungeon.SLIME, 20);
			factory.addRandom(Dungeon.SPIDER, 20);

			break;
		default:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
		}

		return factory;
	}
	
	public static void spawnInChunk(World world, Random rand, int chunkX, int chunkZ) {
		
		if(Catacomb.canSpawnInChunk(chunkX, chunkZ, world)){
			int x = chunkX * 16 + 4;
			int z = chunkZ * 16 + 4;
			
			Catacomb.generateNear(world, rand, x, z);
		}
	}
	
	public static int getLevel(int y){
		
		if (y < 15)	return 4;
		if (y < 25) return 3;
		if (y < 35) return 2;
		if (y < 45) return 1;
		return 0;
	}
	
	public static boolean validLocation(World world, Random rand, int x, int z){
		
		if(!world.isAirBlock(x, 100, z)){
			return false;
		}
		
		int y = 100;
		
		while(!world.isBlockOpaqueCube(x, y, z) && y > 50){
			--y;
		}
		
		if(y < 60){
			return false;
		}
		
		List<Coord> above = WorldGenPrimitive.getRectSolid(x - 4, y + 4, z - 4, x + 4, y + 4, z + 4);

		for (Coord c : above){
			if(world.isBlockOpaqueCube(c.getX(), c.getY(), c.getZ())){
				return false;
			}
		}
		
		List<Coord> below = WorldGenPrimitive.getRectSolid(x - 4, y - 3, z - 4, x + 4, y - 3, z + 4);
		
		int airCount = 0;
		for (Coord c : below){
			if(!world.isBlockOpaqueCube(c.getX(), c.getY(), c.getZ())){
				airCount++;
			}
			if(airCount > 8){
				return false;
			}
		}
		
		return true;
	}
	
	public static Coord getNearbyCoord(Random rand, int x, int z, int min, int max){
		
		int distance = min + rand.nextInt(max - min);
		
		double angle = rand.nextDouble() * 2 * Math.PI;
		
		int xOffset = (int) (Math.cos(angle) * distance);
		int zOffset = (int) (Math.sin(angle) * distance);
		
		Coord nearby = new Coord(x + xOffset, 0, z + zOffset);		
		return nearby;
	}
	
	public static Random getRandom(World world, int x, int z){
		long seed = (long) (world.getSeed() ^ (world.getSeed() >>> 32));
		seed = 31 * seed + (long) (x ^ (x >>> 32));
		seed = 31 * seed + (long) (z ^ (z >>> 32));

		Random rand = new Random();
    	rand.setSeed(seed);
    	return rand;
	}
}
