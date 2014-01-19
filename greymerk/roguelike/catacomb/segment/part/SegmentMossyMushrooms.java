package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentMossyMushrooms extends SegmentBase {

	private int stairType;
	private BlockRandomizer mushrooms;
	
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		stairType = getStairType(Catacomb.getLevel(y));
		
		mushrooms = new BlockRandomizer(rand, new MetaBlock(Block.mushroomBrown.blockID));
		mushrooms.addBlock(new MetaBlock(Block.mushroomRed.blockID), 3);
		mushrooms.addBlock(new MetaBlock(0), 10);
		
		switch(wallDirection){
		case NORTH: north(); break;
		case SOUTH: south(); break;
		case EAST: east(); break;
		case WEST: west(); break;
		}
	}
	
	private void north(){
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 2, x + 1, y + 1, z - 2, 0);

		WorldGenPrimitive.setBlock(world, x - 1, y + 1, z - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 1, z - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		if(rand.nextInt(5) == 0){
			WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 2, x + 1, y - 1, z - 2, Block.waterMoving.blockID);
		} else {
			WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 2, x + 1, y - 1, z - 2, Block.mycelium.blockID);
			WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 2, x + 1, y, z - 2, mushrooms);
		}
		
		WorldGenPrimitive.randomVines(world, rand, x - 1, y + 2, z - 2, x + 1, y + 2, z - 2);

	}
	
	private void south(){
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 2, x + 1, y + 1, z + 2, 0);

		WorldGenPrimitive.setBlock(world, x - 1, y + 1, z + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 1, z + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		if(rand.nextInt(5) == 0){
			WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z + 2, x + 1, y - 1, z + 2, Block.waterMoving.blockID);
		} else {
			WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z + 2, x + 1, y - 1, z + 2, Block.mycelium.blockID);
			WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 2, x + 1, y, z + 2, mushrooms);
		}
		
		WorldGenPrimitive.randomVines(world, rand, x - 1, y + 2, z + 2, x + 1, y + 2, z + 2);
	}
	
	private void east(){
		WorldGenPrimitive.fillRectSolid(world, x + 2, y, z - 1, x + 2, y + 1, z + 1, 0);

		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		if(rand.nextInt(5) == 0){
			WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z - 1, x + 2, y - 1, z + 1, Block.waterMoving.blockID);
		} else {
			WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z - 1, x + 2, y - 1, z + 1, Block.mycelium.blockID);
			WorldGenPrimitive.fillRectSolid(world, x + 2, y, z - 1, x + 2, y, z + 1, mushrooms);		
		}
		
		WorldGenPrimitive.randomVines(world, rand, x + 2, y + 2, z - 1, x + 2, y + 2, z + 1);
	}
	
	private void west(){
		WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y + 1, z + 1, 0);

		WorldGenPrimitive.setBlock(world, x - 2, y + 1, z - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x - 2, y + 1, z + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		if(rand.nextInt(5) == 0){
			WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z - 1, x - 2, y - 1, z + 1, Block.waterMoving.blockID);
		} else {
			WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z - 1, x - 2, y - 1, z + 1, Block.mycelium.blockID);
			WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y, z + 1, mushrooms);
		}
		
		WorldGenPrimitive.randomVines(world, rand, x - 2, y + 2, z - 1, x - 2, y + 2, z + 1);
	}
}
