package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentFire extends SegmentBase {

	private int stairType;
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		stairType = Block.stairsBrick.blockID;
		
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
		
		WorldGenPrimitive.fillRectHollow(world, rand, x - 1, y, z - 4, x + 1, y + 2, z - 2, Block.brick.blockID, 0, 2, false, true);
		
		WorldGenPrimitive.setBlock(world, rand, x, y, z - 2, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z - 2, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, rand, x, y + 2, z - 2, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, rand, x - 1, y + 2, z - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y + 2, z - 1, stairUpper, true, true);
		
		if(isEnclosed(x, y + 1, z - 3)){
			WorldGenPrimitive.setBlock(world, x, y, z - 3, Block.netherrack.blockID);
			WorldGenPrimitive.setBlock(world, x, y + 1, z - 3, Block.fire.blockID);
		}
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z - 2, x - 1, y + 2, z - 2, Block.brick.blockID);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z - 2, x + 1, y + 2, z - 2, Block.brick.blockID);
	}
	
	private void south(){
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2);
		
		WorldGenPrimitive.fillRectHollow(world, rand, x - 1, y, z + 2, x + 1, y + 2, z + 4, Block.brick.blockID, 0, 2, false, true);
		
		WorldGenPrimitive.setBlock(world, rand, x, y, z + 2, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 2, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, rand, x, y + 2, z + 2, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, rand, x - 1, y + 2, z + 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y + 2, z + 1, stairUpper, true, true);
		
		if(isEnclosed(x, y + 1, z + 3)){
			WorldGenPrimitive.setBlock(world, x, y, z + 3, Block.netherrack.blockID);
			WorldGenPrimitive.setBlock(world, x, y + 1, z + 3, Block.fire.blockID);
		}
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z + 2, x - 1, y + 2, z + 2, Block.brick.blockID);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 2, x + 1, y + 2, z + 2, Block.brick.blockID);
	}
	
	private void east(){
		
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, false), 2);
		
		WorldGenPrimitive.fillRectHollow(world, rand, x + 2, y, z - 1, x + 4, y + 2, z + 1, Block.brick.blockID, 0, 2, false, true);
		
		WorldGenPrimitive.setBlock(world, rand, x + 2, y, z, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, rand, x + 2, y + 2, z, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, rand, x + 1, y + 2, z - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y + 2, z + 1, stairUpper, true, true);
		
		if(isEnclosed(x + 3, y + 1, z)){
			WorldGenPrimitive.setBlock(world, x + 3, y, z, Block.netherrack.blockID);
			WorldGenPrimitive.setBlock(world, x + 3, y + 1, z, Block.fire.blockID);
		}
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y, z - 1, x + 2, y + 2, z - 1, Block.brick.blockID);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y, z + 1, x + 2, y + 2, z + 1, Block.brick.blockID);
	}
	
	private void west(){
		
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2);
		
		WorldGenPrimitive.fillRectHollow(world, rand, x - 4, y, z - 1, x - 2, y + 2, z + 1, Block.brick.blockID, 0, 2, false, true);
		
		WorldGenPrimitive.setBlock(world, rand, x - 2, y, z, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, x - 2, y + 1, z, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, rand, x - 2, y + 2, z, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, rand, x - 1, y + 2, z - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, rand, x - 1, y + 2, z + 1, stairUpper, true, true);
		
		if(isEnclosed(x - 3, y + 1, z)){
			WorldGenPrimitive.setBlock(world, x - 3, y, z, Block.netherrack.blockID);
			WorldGenPrimitive.setBlock(world, x - 3, y + 1, z, Block.fire.blockID);
		}
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y, z - 1, x - 2, y + 2, z - 1, Block.brick.blockID);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y, z + 1, x - 2, y + 2, z + 1, Block.brick.blockID);
	}
	
	private boolean isEnclosed(int x, int y, int z){
		
		if(!world.isBlockOpaqueCube(x - 1, y, z) && world.getBlockId(x - 1, y, z) != Block.fenceIron.blockID){
			return false;
		}
		
		if(!world.isBlockOpaqueCube(x + 1, y, z) && world.getBlockId(x + 1, y, z) != Block.fenceIron.blockID){
			return false;
		}
		
		if(!world.isBlockOpaqueCube(x, y, z - 1) && world.getBlockId(x, y, z - 1) != Block.fenceIron.blockID){
			return false;
		}
		
		if(!world.isBlockOpaqueCube(x, y, z + 1) && world.getBlockId(x, y, z + 1) != Block.fenceIron.blockID){
			return false;
		}
		
		if(!world.isBlockOpaqueCube(x, y - 1, z) && world.getBlockId(x, y - 1, z) != Block.fenceIron.blockID){
			return false;
		}
		
		if(!world.isBlockOpaqueCube(x, y + 1, z) && world.getBlockId(x, y + 1, z) != Block.fenceIron.blockID){
			return false;
		}
		return true;
	}
}
