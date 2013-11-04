package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentBrickLava extends SegmentBase {

	private int stairType;
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		switch(Catacomb.getRank(originY)){
		case 2:
			stairType = Block.stairsCobblestone.blockID;
			break;
		case 3:
			stairType = Block.stairsNetherBrick.blockID;
			break;
		default:
			stairType = Block.stairsStoneBrick.blockID;
		}
		
		switch(wallDirection){
		case NORTH: north(); break;
		case SOUTH: south(); break;
		case EAST: east(); break;
		case WEST: west(); break;
		}
	}
	
	private void north(){
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, false), 2);
		
		WorldGenPrimitive.setBlock(world, originX, originY, originZ - 2, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ - 2, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX, originY + 2, originZ - 2, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ - 1, stairUpper, true, true);
		
		if(!world.isAirBlock(originX, originY + 1, originZ - 4)){
			WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ - 3, Block.lavaStill.blockID);
		}
	}
	
	private void south(){
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2);
		
		WorldGenPrimitive.setBlock(world, originX, originY, originZ + 2, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ + 2, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX, originY + 2, originZ + 2, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ + 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ + 1, stairUpper, true, true);
		
		if(!world.isAirBlock(originX, originY + 1, originZ + 4)){
			WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ + 3, Block.lavaStill.blockID);
		}
	}
	
	private void east(){
		
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, false), 2);
		
		WorldGenPrimitive.setBlock(world, originX + 2, originY, originZ, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 1, originZ, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 2, originZ, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ + 1, stairUpper, true, true);
		
		if(!world.isAirBlock(originX + 4, originY + 1, originZ)){
			WorldGenPrimitive.setBlock(world, originX + 3, originY + 1, originZ, Block.lavaStill.blockID);
		}
	}
	
	private void west(){
		
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2);
		
		WorldGenPrimitive.setBlock(world, originX - 2, originY, originZ, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 1, originZ, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 2, originZ, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ + 1, stairUpper, true, true);
		
		if(!world.isAirBlock(originX - 4, originY + 1, originZ)){
			WorldGenPrimitive.setBlock(world, originX - 3, originY + 1, originZ, Block.lavaStill.blockID);
		}
	}
}
