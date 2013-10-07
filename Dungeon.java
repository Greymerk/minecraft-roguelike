package greymerk.roguelike;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.World;

public class Dungeon {
	
	private World world;
	private Random rand;


	public Dungeon(World world, Random rand) {
		this.world = world;
		this.rand = rand;
		
	}
	
	public void makeChunkEvil(int chunkX, int chunkZ, int frequency) {
		makeAreaEvil(chunkX * 16 + 8, 5, chunkZ * 16 + 8, chunkX * 16 + 16 + 8, 50, chunkZ * 16 + 16 + 8, frequency);
		
		int spread = 12;
		if (rand.nextInt(3) == 0 && chunkX % spread == 0 && chunkZ % spread == 0){
			BiomeGenBase[] invalidBiomes = {BiomeGenBase.ocean, BiomeGenBase.frozenOcean, BiomeGenBase.extremeHills, BiomeGenBase.iceMountains, BiomeGenBase.jungleHills};
						
			int x = chunkX * 16;
			int z = chunkZ * 16;
			
			BiomeGenBase localBiome = world.getBiomeGenForCoords(x, z);
			
			boolean validBiome = true;
			for(BiomeGenBase biome : invalidBiomes){
				if(biome == localBiome){
					validBiome = false;
				}
			}
			
			if(validBiome){
				Catacomb cata = new Catacomb(world, rand);
				cata.generate(x, z);
			}
		}
	}
	
	public void makeAreaEvil(int x1, int y1, int z1, int x2, int y2, int z2, int attempts){		
		
		for(int i = 0; i < attempts; i++){
			
			int x = Math.min(x1, x2) + rand.nextInt(Math.max(x1, x2) - Math.min(x1, x2));
			int y = Math.min(y1, y2) + rand.nextInt(Math.max(y1, y2) - Math.min(y1, y2));
			int z = Math.min(z1, z2) + rand.nextInt(Math.max(z1, z2) - Math.min(z1, z2));
			
			// attempt to create a dungeon here
			IDungeon toGenerate = pickCaveDungeon(y);
			
			if(toGenerate.isValidDungeonLocation(world, x, y, z)) {
				toGenerate.generate(world, rand, x, y, z);
			}
		}
	}
	
	public IDungeon pickCaveDungeon(int posY) {	
		
		switch(getRank(posY)){
		
		case 3:

			if(rand.nextInt(10) == 0){
				return new DungeonsSpiderNest();
			}
			
			if(rand.nextInt(10) == 0){
				return new DungeonsCreeperDen();
			}
			
			return new DungeonsBase();

		case 2:
			
			if(rand.nextInt(20) == 0){
				return new DungeonsSpiderNest();
			}
			
			if(rand.nextInt(20) == 0){
				return new DungeonsCreeperDen();
			}
			
			return new DungeonsBase();
		
		case 1:
		
			return new DungeonsBase();

		
		case 0:
			
			return new DungeonsBase();
			
		
		default:
			return new DungeonsBase();
		}
		

	}
	
	public IDungeon pickCatacombDungeon(int posY){
		
		switch(getRank(posY)){
		
		case 3:

			if(rand.nextInt(10) == 0){
				return new DungeonsSlime();
			}

			if(rand.nextInt(10) == 0){
				return new DungeonsSpiderNest();
			}

			if(rand.nextInt(5) == 0){
				return new DungeonsNetherBrickFortress();
			}
			
			return new DungeonsNetherBrick();

		case 2:
			
			if(rand.nextInt(30) == 0){
				return new DungeonsSpiderNest();
			}

			if(rand.nextInt(30) == 0){
				return new DungeonsFire();
			}
			
			if(rand.nextInt(20) == 0){
				return new DungeonsEnder();
			}
			
			if(rand.nextInt(20) == 0){
				return new DungeonsPrison();
			}
			
			if(rand.nextInt(15) == 0){
				return new DungeonsCreeperDen();
			}
			
			
			if(rand.nextInt(15) == 0){
				return new DungeonsPit();
			}
			
			if(rand.nextInt(10) == 0){
				return new DungeonsCrypt();
			}

			return new DungeonsBrick();
		
		case 1:

			if(rand.nextInt(50) == 0){
				return new DungeonsCreeperDen();
			}
			
			if(rand.nextInt(40) == 0){
				return new DungeonsEnchant();
			}
			
			if(rand.nextInt(30) == 0){
				return new DungeonsPit();
			}

			if(rand.nextInt(30) == 0){
				return new DungeonsEnder();
			}

			if(rand.nextInt(30) == 0){
				return new DungeonsCrypt();
			}

			if(rand.nextInt(20) == 0){
				return new DungeonsFire();
			}
			
			if(rand.nextInt(20) == 0){
				return new DungeonsPrison();
			}
			
			return new DungeonsBrick();

		
		case 0:
			
			if(rand.nextInt(100) == 0){
				return new DungeonsPit();
			}
			
			if(rand.nextInt(50) == 0){
				return new DungeonsFire();
			}
			
			if(rand.nextInt(30) == 0){
				return new DungeonsMusic();
			}
			
			if(rand.nextInt(20) == 0){
				return new DungeonsSlime();
			}

			if(rand.nextInt(20) == 0){
				return new DungeonsSmithy();
			}
			
			if(rand.nextInt(20) == 0){
				return new DungeonsEnchant();
			}
			
			if(rand.nextInt(15) == 0){
				return new DungeonsWood();
			}

			return new DungeonsBrick();
			
		default:
			return new DungeonsBrick();
			
		}
		
		
	}
	
	
	public static int getRank(int y){
		
		if (y < 15){
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
