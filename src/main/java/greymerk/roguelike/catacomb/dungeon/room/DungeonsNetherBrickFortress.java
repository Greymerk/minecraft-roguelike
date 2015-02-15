package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsNetherBrickFortress extends DungeonBase {
	
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	Spawner spawner;
	
	public DungeonsNetherBrickFortress() {

	}
	
	
	public boolean generate(World inWorld, Random inRandom, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		world = inWorld;
		rand = inRandom;
		originX = origin.getX();
		originY = origin.getY();
		originZ = origin.getZ();
		
		MetaBlock air = new MetaBlock(Blocks.air);

		// cut out air space
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY, originZ - 5,
				originX + 5, originY + 3, originZ + 5, air);

		buildWalls(settings);
		buildFloor();
		buildRoof();
		
		ArrayList<TreasureChest> types = new ArrayList<TreasureChest>(Arrays.asList(TreasureChest.SPECIAL));		
		TreasureChest.createChests(world, rand, settings.getLoot(), 2, WorldGenPrimitive.getRectSolid(
				originX - 6, originY, originZ - 6,
				originX + 6, originY, originZ + 6),
				types);

		return true;
	}
	
    public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ){
    	return false;
    }
	
	protected void buildRoof(){
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// top
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 6, originY + 4, originZ - 6,
				originX + 6, originY + 6, originZ + 6, new MetaBlock(Blocks.nether_brick));

		List<Coord> lavaArea = WorldGenPrimitive.getRectSolid(	originX - 3, originY + 6, originZ - 3,
																originX + 3, originY + 6, originZ + 3);
		
		for (Coord block : lavaArea){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(rand.nextBoolean()){
				WorldGenPrimitive.setBlock(world, x, y, z, Blocks.lava);
			}
		}
		
		
		// sub-ceiling air square
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 3, originY + 4, originZ - 3,
				originX + 3, originY + 4, originZ + 3, air);
		
		
		// arches
		BlockJumble fenceRandom = new BlockJumble();
		fenceRandom.addBlock(new MetaBlock(Blocks.nether_brick));
		fenceRandom.addBlock(new MetaBlock(Blocks.nether_brick_fence));
		
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY + 4, originZ - 3, originX - 4, originY + 4, originZ + 3, fenceRandom);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 4, originY + 4, originZ - 3, originX + 5, originY + 4, originZ + 3, fenceRandom);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 3, originY + 4, originZ - 5, originX + 3, originY + 4, originZ - 4, fenceRandom);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 3, originY + 4, originZ + 4, originX + 3, originY + 4, originZ + 5, fenceRandom);

	}
	
    protected void buildWalls(CatacombLevelSettings settings){
    	
    	// door walls
		List<Coord> outerWall = WorldGenPrimitive.getRectHollow(originX - 6, originY - 1, originZ - 6, 
																originX + 6, originY + 5, originZ + 6);
		for (Coord block : outerWall){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(y >= originY && y <= originY + 4){
				WorldGenPrimitive.setBlock(world, rand, x, y, z, new MetaBlock(Blocks.nether_brick), false, true);
			}
		}
		
		// pillars
		List<Coord> arch1 = WorldGenPrimitive.getRectSolid(	originX - 5, originY, originZ - 5,
															originX - 4, originY + 4, originZ - 4);

		List<Coord> arch2 = WorldGenPrimitive.getRectSolid(	originX - 5, originY, originZ + 4,
															originX - 4, originY + 4, originZ + 5);	
		
		List<Coord> arch3 = WorldGenPrimitive.getRectSolid(	originX + 4, originY, originZ - 5,
															originX + 5, originY + 4, originZ - 4);	
		
		List<Coord> arch4 = WorldGenPrimitive.getRectSolid(	originX + 4, originY, originZ + 4,
															originX + 5, originY + 4, originZ + 5);	
		
		List<Coord> pillars = new ArrayList<Coord>();
		pillars.addAll(arch1);
		pillars.addAll(arch2);
		pillars.addAll(arch3);
		pillars.addAll(arch4);
		
		Collections.shuffle(pillars, rand);
		
		for (Coord block : pillars){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			WorldGenPrimitive.setBlock(world, x, y, z, Blocks.nether_brick);
		}
		
		for(int i = 0; i < rand.nextInt(5) + 5; ++i){
			Spawner toSpawn = this.pickMobSpawner(rand);
			Spawner.generate(world, rand, settings, pillars.get(i), toSpawn);
		}
	}
    	
    	
    
    protected void buildFloor(){

		// base
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 6, originY - 4, originZ - 6, originX + 6, originY - 1, originZ + 6, new MetaBlock(Blocks.nether_brick));
    	
		List<Coord> soulSand = WorldGenPrimitive.getRectSolid(	originX - 5, originY - 1, originZ - 5,
																originX + 5, originY - 1, originZ + 5);

		MetaBlock sand = new MetaBlock(Blocks.soul_sand);
		MetaBlock wart = new MetaBlock(Blocks.nether_wart);
		
		for (Coord block : soulSand){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(rand.nextBoolean() && world.isAirBlock(x, y + 1, z)){
				WorldGenPrimitive.setBlock(world, x, y, z, sand);
				if(rand.nextBoolean()){
					WorldGenPrimitive.setBlock(world, x, y + 1, z, wart);
				}
			}
		}
		
 
    }
    
    protected Spawner pickMobSpawner(Random random)
    {
    	
    	if(rand.nextInt(10) == 0){
    		return Spawner.CAVESPIDER;
    	}
    	
    	if(rand.nextInt(5) == 0){
    		return Spawner.CREEPER;
    	}
    	
        int choice = random.nextInt(3);
        
        switch(choice){
        
        case 0:
        	return Spawner.SKELETON;
        case 1:
        	return Spawner.BLAZE;
        case 2:
        	return Spawner.ZOMBIE;
        
        default:
        	return Spawner.ZOMBIE;
        }
    }
    
	public int getSize(){
		return 8;
	}
    
    
}
