package greymerk.roguelike;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.World;

public enum Dungeon {
	
	BASE, BRICK, CREEPER, CRYPT, ENCHANT, ENDER, FIRE, MUSIC, NETHER, NETHERFORT, PIT, PRISON,
	SLIME, SMITH, SPIDER, CAKE;
	
	public static IDungeon getInstance(Dungeon choice){
		
		switch(choice){
		case BASE: return new DungeonsBase();
		case BRICK: return new DungeonsBrick();
		case CREEPER: return new DungeonsCreeperDen();
		case CRYPT: return new DungeonsCrypt();
		case ENCHANT: return new DungeonsEnchant();
		case ENDER: return new DungeonsEnder();
		case FIRE: return new DungeonsFire();
		case MUSIC: return new DungeonsMusic();
		case NETHER: return new DungeonsNetherBrick();
		case NETHERFORT: return new DungeonsNetherBrickFortress();
		case PIT: return new DungeonsPit();
		case PRISON: return new DungeonsPrison();
		case SLIME: return new DungeonsSlime();
		case SMITH: return new DungeonsSmithy();
		case SPIDER: return new DungeonsSpiderNest();
		case CAKE: return new DungeonsWood();
		default: return new DungeonsBase();
		}
		
	}
		
	public static void makeChunkEvil(World world, Random rand, int chunkX, int chunkZ, int frequency) {
		
		if(world.provider.dimensionId != 0){
			return;
		}
		
		makeAreaEvil(world, rand, chunkX * 16 + 8, 5, chunkZ * 16 + 8, chunkX * 16 + 16 + 8, 50, chunkZ * 16 + 16 + 8, frequency);

		if(Catacomb.canSpawn(chunkX, chunkZ, world)){
			int x = chunkX * 16 + 4;
			int z = chunkZ * 16 + 4;
			
			Catacomb cata = new Catacomb(world, rand);
			cata.generate(x, z);
		}
		

	}
	
	public static void makeAreaEvil(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2, int attempts){		
		
		for(int i = 0; i < attempts; i++){
			
			int x = Math.min(x1, x2) + rand.nextInt(Math.max(x1, x2) - Math.min(x1, x2));
			int y = Math.min(y1, y2) + rand.nextInt(Math.max(y1, y2) - Math.min(y1, y2));
			int z = Math.min(z1, z2) + rand.nextInt(Math.max(z1, z2) - Math.min(z1, z2));
			
			// attempt to create a dungeon here
			IDungeon toGenerate = pickCaveDungeon(rand, y);
			
			if(toGenerate.isValidDungeonLocation(world, x, y, z)) {
				toGenerate.generate(world, rand, x, y, z);
			}
		}
	}
	
	public static IDungeon pickCaveDungeon(Random rand, int posY) {	
		
		switch(getRank(posY)){
		case 3:
			if(rand.nextInt(10) == 0){
				return getInstance(SPIDER);
			}
			if(rand.nextInt(10) == 0){
				return getInstance(CREEPER);
			}
			return getInstance(BASE);
		case 2:
			if(rand.nextInt(20) == 0){
				return getInstance(SPIDER);
			}
			if(rand.nextInt(20) == 0){
				return getInstance(CREEPER);
			}
			return getInstance(BASE);
		case 1:
			return getInstance(BASE);
		case 0:
			return getInstance(BASE);
		default:
			return getInstance(BASE);
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
