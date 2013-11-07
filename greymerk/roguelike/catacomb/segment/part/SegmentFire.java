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
		
		WorldGenPrimitive.setBlock(world, originX, originY, originZ - 2, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ - 2, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX, originY + 2, originZ - 2, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ - 1, stairUpper, true, true);
		
		if(isEnclosed(originX, originY + 1, originZ - 3)){
			WorldGenPrimitive.setBlock(world, originX, originY, originZ - 3, Block.netherrack.blockID);
			WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ - 3, Block.fire.blockID);
		}
		
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY, originZ - 2, originX - 1, originY + 2, originZ - 2, Block.brick.blockID);
		WorldGenPrimitive.fillRectSolid(world, originX + 1, originY, originZ - 2, originX + 1, originY + 2, originZ - 2, Block.brick.blockID);
	}
	
	private void south(){
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2);
		
		WorldGenPrimitive.setBlock(world, originX, originY, originZ + 2, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ + 2, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX, originY + 2, originZ + 2, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ + 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ + 1, stairUpper, true, true);
		
		if(isEnclosed(originX, originY + 1, originZ + 3)){
			WorldGenPrimitive.setBlock(world, originX, originY, originZ + 3, Block.netherrack.blockID);
			WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ + 3, Block.fire.blockID);
		}
		
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY, originZ + 2, originX - 1, originY + 2, originZ + 2, Block.brick.blockID);
		WorldGenPrimitive.fillRectSolid(world, originX + 1, originY, originZ + 2, originX + 1, originY + 2, originZ + 2, Block.brick.blockID);
	}
	
	private void east(){
		
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, false), 2);
		
		WorldGenPrimitive.setBlock(world, originX + 2, originY, originZ, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 1, originZ, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 2, originZ, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ + 1, stairUpper, true, true);
		
		if(isEnclosed(originX + 3, originY + 1, originZ)){
			WorldGenPrimitive.setBlock(world, originX + 3, originY, originZ, Block.netherrack.blockID);
			WorldGenPrimitive.setBlock(world, originX + 3, originY + 1, originZ, Block.fire.blockID);
		}
		
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY, originZ - 1, originX + 2, originY + 2, originZ - 1, Block.brick.blockID);
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY, originZ + 1, originX + 2, originY + 2, originZ + 1, Block.brick.blockID);
	}
	
	private void west(){
		
		MetaBlock stairUpper = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2);
		MetaBlock stairLower = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2);
		
		WorldGenPrimitive.setBlock(world, originX - 2, originY, originZ, stairLower, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 1, originZ, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 2, originZ, stairUpper, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ - 1, stairUpper, true, true);
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ + 1, stairUpper, true, true);
		
		if(isEnclosed(originX - 3, originY + 1, originZ)){
			WorldGenPrimitive.setBlock(world, originX - 3, originY, originZ, Block.netherrack.blockID);
			WorldGenPrimitive.setBlock(world, originX - 3, originY + 1, originZ, Block.fire.blockID);
		}
		
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ - 1, originX - 2, originY + 2, originZ - 1, Block.brick.blockID);
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ + 1, originX - 2, originY + 2, originZ + 1, Block.brick.blockID);
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
