package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentBrickInset extends SegmentBase {

	private int stairType;
	TreasureChest[] types = {TreasureChest.ARMOUR, TreasureChest.WEAPONS, TreasureChest.TOOLS};
	
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
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY, originZ - 2, originX + 1, originY + 2, originZ - 2, 0);

		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY, originZ - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY, originZ - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, false), 2, true, true);
		
		if(!world.isAirBlock(originX, originY + 1, originZ - 3)){
			WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ - 3, 0);
			WorldGenPrimitive.setBlock(world, originX, originY + 2, originZ - 3, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		}
		
		bonus(originX, originY + 1, originZ - 3);

	}
	
	private void south(){
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY, originZ + 2, originX + 1, originY + 2, originZ + 2, 0);

		WorldGenPrimitive.setBlock(world, originX - 1, originY + 2, originZ + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 2, originZ + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY, originZ + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY, originZ + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, false), 2, true, true);
		
		if(!world.isAirBlock(originX, originY + 1, originZ + 3)){
			WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ + 3, 0);
			WorldGenPrimitive.setBlock(world, originX, originY + 2, originZ + 3, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		}
		
		bonus(originX, originY + 1, originZ + 3);
	}
	
	private void east(){
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY, originZ - 1, originX + 2, originY + 2, originZ + 1, 0);

		WorldGenPrimitive.setBlock(world, originX + 2, originY + 2, originZ - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 2, originZ + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, originX + 2, originY, originZ - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY, originZ + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2, true, true);
		
		if(!world.isAirBlock(originX + 3, originY + 1, originZ)){
			WorldGenPrimitive.setBlock(world, originX + 3, originY + 1, originZ, 0);
			WorldGenPrimitive.setBlock(world, originX + 3, originY + 2, originZ, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		}
		
		bonus(originX + 3, originY + 1, originZ);
	}
	
	private void west(){
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ - 1, originX - 2, originY + 2, originZ + 1, 0);

		WorldGenPrimitive.setBlock(world, originX - 2, originY + 2, originZ - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 2, originZ + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 2, originY, originZ - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY, originZ + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2, true, true);
		
		if(!world.isAirBlock(originX - 3, originY + 1, originZ)){
			WorldGenPrimitive.setBlock(world, originX - 3, originY + 1, originZ, 0);
			WorldGenPrimitive.setBlock(world, originX - 3, originY + 2, originZ, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		}
		
		bonus(originX - 3, originY + 1, originZ);
	}
	
	private void bonus(int x, int y, int z){
		
		if(rand.nextInt(5) != 0){
			return;
		}
		
		if(world.isAirBlock(x, y - 1, z)){
			return;
		}
		
		if(rand.nextBoolean()){
			TreasureChest type = types[rand.nextInt(types.length)];
			TreasureChest.generate(world, rand, x, y, z, type);
		} else if(Catacomb.getRank(y) > 0){
			Spawner.generate(world, rand, x, y, z);
		}
	}
}
