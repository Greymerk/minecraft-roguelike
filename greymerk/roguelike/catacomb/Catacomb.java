package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonCustomization;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.DungeonFactoryProvider;
import greymerk.roguelike.catacomb.dungeon.IDungeonFactory;
import greymerk.roguelike.catacomb.dungeon.RoomSet;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.src.World;

public class Catacomb {
		
	public static final int DEPTH = 5;
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
	public static final int NUM_LEVELS = 5;
	
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
			
			ITheme theme = DungeonCustomization.getTheme(world.getBiomeGenForCoords(inX, inZ), getLevel(y)); 
			if(theme == null) theme = Theme.getByLevel(world.getBiomeGenForCoords(inX, inZ), getLevel(y));
			IDungeonFactory rooms = DungeonCustomization.getRooms(world.getBiomeGenForCoords(inX, inZ), getLevel(y)); 
			if(rooms == null) rooms = DungeonFactoryProvider.getByLevel(Catacomb.getLevel(y));
			
			rand = getRandom(world, x, z);
			if(Catacomb.getLevel(y) == 0){
				level = new CatacombLevel(world, rand, rooms, theme, x, y, z, 8, 40);
			} else {
				level = new CatacombLevel(world, rand, rooms, theme, x, y, z);
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
		rand = getRandom(world, inX, inZ);
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
		long seed = world.getSeed() * x * z;
		Random rand = new Random();
    	rand.setSeed(seed);
    	return rand;
	}
}
