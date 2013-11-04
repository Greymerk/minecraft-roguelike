package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentBrickShelf extends SegmentBase {

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
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY + 1, originZ - 2, originX + 1, originY + 2, originZ - 2, 0);

		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
	}
	
	private void south(){
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY + 1, originZ + 2, originX + 1, originY + 2, originZ + 2, 0);

		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
	}
	
	private void east(){
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY + 1, originZ - 1, originX + 2, originY + 2, originZ + 1, 0);

		WorldGenPrimitive.setBlock(world, originX + 2, originY + 2, originZ - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 2, originZ + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
	}
	
	private void west(){
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY + 1, originZ - 1, originX - 2, originY + 2, originZ + 1, 0);

		WorldGenPrimitive.setBlock(world, originX - 2, originY + 2, originZ - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 2, originZ + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);

	}
}
