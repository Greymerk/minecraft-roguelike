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
	
	private int woodType;
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		stairType = getStairType(Catacomb.getLevel(y));
		woodType = Catacomb.getLevel(y);
		
		switch(wallDirection){
		case NORTH: north(); break;
		case SOUTH: south(); break;
		case EAST: east(); break;
		case WEST: west(); break;
		}
	}
	
	private void north(){
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 1, z - 2, x + 1, y + 2, z - 2, 0);

		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 1, y + 1, z - 3, x + 1, y + 2, z - 3, Block.planks.blockID, woodType, 2, false, true);
			WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 2, x + 1, y, z - 2, Block.planks.blockID, woodType, 2, false, true);
		}
	}
	
	private void south(){
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 1, z + 2, x + 1, y + 2, z + 2, 0);

		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 1, y + 1, z + 3, x + 1, y + 2, z + 3, Block.planks.blockID, woodType, 2, false, true);
			WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 2, x + 1, y, z + 2, Block.planks.blockID, woodType, 2, false, true);
		}
	}
	
	private void east(){
		WorldGenPrimitive.fillRectSolid(world, x + 2, y + 1, z - 1, x + 2, y + 2, z + 1, 0);

		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x + 3, y + 1, z - 1, x + 3, y + 2, z + 1, Block.planks.blockID, woodType, 2, false, true);
			WorldGenPrimitive.fillRectSolid(world, x + 2, y, z - 1, x + 2, y, z + 1, Block.planks.blockID, woodType, 2, false, true);
		}
	}
	
	private void west(){
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 1, z - 1, x - 2, y + 2, z + 1, 0);

		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 3, y + 1, z - 1, x - 3, y + 2, z + 1, Block.planks.blockID, woodType, 2, false, true);
			WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y, z + 1, Block.planks.blockID, woodType, 2, false, true);
		}
	}
}
