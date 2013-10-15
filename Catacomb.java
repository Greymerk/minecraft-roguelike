package greymerk.roguelike;

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
			
			CatacombLevel level = new CatacombLevel(world, rand, x, y, z);
	
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
		
		chunkX -= 4;
		
		int x = chunkX * 16;
		int z = chunkZ * 16;
		
		BiomeGenBase[] invalidBiomes = {BiomeGenBase.ocean, BiomeGenBase.frozenOcean, BiomeGenBase.extremeHills, BiomeGenBase.iceMountains, BiomeGenBase.jungleHills};
		BiomeGenBase localBiome = world.getBiomeGenForCoords(x, z);

		for(BiomeGenBase biome : invalidBiomes){
			if(biome == localBiome){
				return false;
			}
		}

		int min = 8;
		int max = 32;
		
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
	
	
}
