package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentShelf extends SegmentBase {

	private int stairType;
	
	private static final int SPRUCE = 1;
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		stairType = getStairType(Catacomb.getLevel(originY));
		
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
		
		if(Catacomb.getLevel(originY)  < 2){
			WorldGenPrimitive.fillRectSolid(world, originX - 1, originY + 1, originZ - 3, originX + 1, originY + 2, originZ - 3, Block.planks.blockID, SPRUCE, 2, false, true);
			WorldGenPrimitive.fillRectSolid(world, originX - 1, originY, originZ - 2, originX + 1, originY, originZ - 2, Block.planks.blockID, SPRUCE, 2, false, true);
		}
	}
	
	private void south(){
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY + 1, originZ + 2, originX + 1, originY + 2, originZ + 2, 0);

		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		if(Catacomb.getLevel(originY)  < 2){
			WorldGenPrimitive.fillRectSolid(world, originX - 1, originY + 1, originZ + 3, originX + 1, originY + 2, originZ + 3, Block.planks.blockID, SPRUCE, 2, false, true);
			WorldGenPrimitive.fillRectSolid(world, originX - 1, originY, originZ + 2, originX + 1, originY, originZ + 2, Block.planks.blockID, SPRUCE, 2, false, true);
		}
	}
	
	private void east(){
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY + 1, originZ - 1, originX + 2, originY + 2, originZ + 1, 0);

		WorldGenPrimitive.setBlock(world, originX + 2, originY + 2, originZ - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 2, originZ + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		if(Catacomb.getLevel(originY)  < 2){
			WorldGenPrimitive.fillRectSolid(world, originX + 3, originY + 1, originZ - 1, originX + 3, originY + 2, originZ + 1, Block.planks.blockID, SPRUCE, 2, false, true);
			WorldGenPrimitive.fillRectSolid(world, originX + 2, originY, originZ - 1, originX + 2, originY, originZ + 1, Block.planks.blockID, SPRUCE, 2, false, true);
		}
	}
	
	private void west(){
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY + 1, originZ - 1, originX - 2, originY + 2, originZ + 1, 0);

		WorldGenPrimitive.setBlock(world, originX - 2, originY + 2, originZ - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 2, originZ + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		if(Catacomb.getLevel(originY)  < 2){
			WorldGenPrimitive.fillRectSolid(world, originX - 3, originY + 1, originZ - 1, originX - 3, originY + 2, originZ + 1, Block.planks.blockID, SPRUCE, 2, false, true);
			WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ - 1, originX - 2, originY, originZ + 1, Block.planks.blockID, SPRUCE, 2, false, true);
		}
	}
}
