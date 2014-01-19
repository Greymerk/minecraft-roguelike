package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentMossyArch extends SegmentBase {

	private int stairType;
	private static final int SPRUCE = 1;
	
	private boolean spawnHoleSet = false;
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		stairType = Block.stairsCobblestone.blockID;
		
		switch(wallDirection){
		case NORTH: north(); break;
		case SOUTH: south(); break;
		case EAST: east(); break;
		case WEST: west(); break;
		}
		
		if(!spawnHoleSet){
			spawnHole();
			spawnHoleSet = true;
		}
	}
	
	private void north(){
		MetaBlock stair = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2);
		
		WorldGenPrimitive.setBlock(world, x, y, z - 2, 0);
		WorldGenPrimitive.setBlock(world, x, y + 1, z - 2, 0);
		WorldGenPrimitive.setBlock(world, x, y + 2, z - 2, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 1, stair, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 1, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x, y + 3, z - 1, new MetaBlock(Block.vine.blockID, rand.nextInt(15)));
		WorldGenPrimitive.setBlock(world, x, y - 1, z - 2, Block.waterMoving.blockID);
	}
	
	private void south(){
		MetaBlock stair = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2);
		
		WorldGenPrimitive.setBlock(world, x, y, z + 2, 0);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 2, 0);
		WorldGenPrimitive.setBlock(world, x, y + 2, z + 2, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 1, stair, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 1, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x, y + 3, z + 1, new MetaBlock(Block.vine.blockID, rand.nextInt(15)));
		WorldGenPrimitive.setBlock(world, x, y - 1, z + 2, Block.waterMoving.blockID);
	}
	
	private void east(){
		MetaBlock stair = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2);
		
		WorldGenPrimitive.setBlock(world, x + 2, y, z, 0);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z, 0);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 1, stair, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 1, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 1, y + 3, z, new MetaBlock(Block.vine.blockID, rand.nextInt(15)));
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z, Block.waterMoving.blockID);
	}
	
	private void west(){
		MetaBlock stair = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2);
		
		WorldGenPrimitive.setBlock(world, x - 2, y, z, 0);
		WorldGenPrimitive.setBlock(world, x - 2, y + 1, z, 0);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 1, stair, true, true);
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 1, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y + 3, z, new MetaBlock(Block.vine.blockID, rand.nextInt(15)));
		WorldGenPrimitive.setBlock(world, x - 2, y - 1, z, Block.waterMoving.blockID);
	}
	
	private void spawnHole(){
		WorldGenPrimitive.fillRectSolid(world, x, y + 2, z, x, y + 5, z, 0);
		WorldGenPrimitive.randomVines(world, rand, x, y + 3, z, x, y + 5, z);
		
		if(!world.isAirBlock(x, y + 6, z)) WorldGenPrimitive.setBlock(world, x, y + 7, z, Block.waterMoving.blockID);
	}
}
