package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class DungeonsNetherBrickFortress implements IDungeon {
	
	
	
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	Spawner spawner;
	
	public DungeonsNetherBrickFortress() {

	}
	
	
	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		// cut out air space
		WorldGenPrimitive.fillRectSolid(world, originX - 5, originY, originZ - 5,
				originX + 5, originY + 3, originZ + 5, 0);

		buildWalls();
		buildFloor();
		buildRoof();
		
		ArrayList<TreasureChest> types = new ArrayList<TreasureChest>(Arrays.asList(TreasureChest.SPECIALTY));		
		TreasureChest.createChests(world, rand, 3, WorldGenPrimitive.getRectSolid(
				originX - 6, originY, originZ - 6,
				originX + 6, originY, originZ + 6),
				types);

		return true;
	}
	
    public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ){
    	return false;
    }
	
	protected void buildRoof(){
		
		// top
		WorldGenPrimitive.fillRectSolid(world, originX - 6, originY + 4, originZ - 6,
				originX + 6, originY + 6, originZ + 6, Block.netherBrick.blockID);

		List<Coord> lavaArea = WorldGenPrimitive.getRectSolid(	originX - 3, originY + 6, originZ - 3,
																originX + 3, originY + 6, originZ + 3);
		
		for (Coord block : lavaArea){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(rand.nextBoolean()){
				WorldGenPrimitive.setBlock(world, x, y, z, Block.lavaStill.blockID);
			}
		}
		
		
		// sub-ceiling air square
		WorldGenPrimitive.fillRectSolid(world, originX - 3, originY + 4, originZ - 3,
				originX + 3, originY + 4, originZ + 3, 0);
		
		// arches
		List<Coord> arch1 = WorldGenPrimitive.getRectSolid(	originX - 5, originY + 4, originZ - 3,
															originX - 4, originY + 4, originZ + 3);
		
		List<Coord> arch2 = WorldGenPrimitive.getRectSolid(	originX + 4, originY + 4, originZ - 3,
															originX + 5, originY + 4, originZ + 3);	
		
		List<Coord> arch3 = WorldGenPrimitive.getRectSolid(	originX - 3, originY + 4, originZ - 5,
															originX + 3, originY + 4, originZ - 4);	

		List<Coord> arch4 = WorldGenPrimitive.getRectSolid(	originX - 3, originY + 4, originZ + 4,
															originX + 3, originY + 4, originZ + 5);	

		
		HashSet<Coord> arches = new HashSet<Coord>();
		arches.addAll(arch1);
		arches.addAll(arch2);
		arches.addAll(arch3);
		arches.addAll(arch4);
		
		for (Coord block : arches){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(rand.nextBoolean()){
				WorldGenPrimitive.setBlock(world, x, y, z, Block.netherFence.blockID);
			} else {
				WorldGenPrimitive.setBlock(world, x, y, z, Block.netherBrick.blockID);
			}
		}
	}
	
    protected void buildWalls(){
    	
    	// door walls
		List<Coord> outerWall = WorldGenPrimitive.getRectHollow(originX - 6, originY - 1, originZ - 6, 
																originX + 6, originY + 5, originZ + 6);
		for (Coord block : outerWall){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(y >= originY && y <= originY + 4){
				WorldGenPrimitive.setBlock(world, x, y, z, Block.netherBrick.blockID, 0, 2, false, true);
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
		
		HashSet<Coord> pillars = new HashSet<Coord>();
		pillars.addAll(arch1);
		pillars.addAll(arch2);
		pillars.addAll(arch3);
		pillars.addAll(arch4);
		
		for (Coord block : pillars){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(rand.nextInt(10) == 0){
				Spawner type = this.pickMobSpawner(rand);
				Spawner.generate(world, rand, x, y, z, type);
				continue;
			}
			
			WorldGenPrimitive.setBlock(world, x, y, z, Block.netherBrick.blockID);
		}
	}
    	
    	
    
    protected void buildFloor(){

		// base
		WorldGenPrimitive.fillRectSolid(world, 	originX - 6, originY - 4, originZ - 6,
												originX + 6, originY - 1, originZ + 6, Block.netherBrick.blockID);
    	
		List<Coord> soulSand = WorldGenPrimitive.getRectSolid(	originX - 5, originY - 1, originZ - 5,
																originX + 5, originY - 1, originZ + 5);

		for (Coord block : soulSand){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(rand.nextBoolean() && world.isAirBlock(x, y + 1, z)){
				WorldGenPrimitive.setBlock(world, x, y, z, Block.slowSand.blockID);
				if(rand.nextBoolean()){
					WorldGenPrimitive.setBlock(world, x, y + 1, z, Block.netherStalk.blockID);
				}
			}
		}
		
 
    }
    
    protected Spawner pickMobSpawner(Random random)
    {
    	
    	if(rand.nextInt(30) == 0){
    		return Spawner.CAVESPIDER;
    	}
    	
    	if(rand.nextInt(10) == 0){
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
