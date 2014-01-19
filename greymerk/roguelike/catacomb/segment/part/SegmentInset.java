package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.segment.alcove.SilverfishNest;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentInset extends SegmentBase {

	private int stairType;
	TreasureChest[] types = {TreasureChest.ARMOUR, TreasureChest.WEAPONS, TreasureChest.TOOLS, TreasureChest.BLOCKS};
	private static final int SPRUCE = 1;
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		stairType = getStairType(Catacomb.getLevel(y));

		switch(wallDirection){
		case NORTH: north(); break;
		case SOUTH: south(); break;
		case EAST: east(); break;
		case WEST: west(); break;
		}
	}
	
	private void north(){
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 2, x + 1, y + 2, z - 2, 0);

		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y, z - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z - 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, false), 2, true, true);
		
		if(Catacomb.getLevel(y) < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 3, x + 1, y + 2, z - 3, Block.planks.blockID, SPRUCE, 2, false, true);
		}
		
		if(!world.isAirBlock(x, y + 1, z - 3)){
			WorldGenPrimitive.setBlock(world, x, y + 1, z - 3, 0);
			WorldGenPrimitive.setBlock(world, x, y + 2, z - 3, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		}
		
		bonus(x, y, z, Cardinal.NORTH);

	}
	
	private void south(){
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 2, x + 1, y + 2, z + 2, 0);

		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y, z + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z + 2, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, false), 2, true, true);
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 3, x + 1, y + 2, z + 3, Block.planks.blockID, SPRUCE, 2, false, true);
		}
		
		if(!world.isAirBlock(x, y + 1, z + 3)){
			WorldGenPrimitive.setBlock(world, x, y + 1, z + 3, 0);
			WorldGenPrimitive.setBlock(world, x, y + 2, z + 3, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		}
		
		bonus(x, y, z, Cardinal.SOUTH);
	}
	
	private void east(){
		WorldGenPrimitive.fillRectSolid(world, x + 2, y, z - 1, x + 2, y + 2, z + 1, 0);

		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y, z - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y, z + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2, true, true);
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x + 3, y, z - 1, x + 3, y + 2, z + 1, Block.planks.blockID, SPRUCE, 2, false, true);
		}
		
		if(!world.isAirBlock(x + 3, y + 1, z)){
			WorldGenPrimitive.setBlock(world, x + 3, y + 1, z, 0);
			WorldGenPrimitive.setBlock(world, x + 3, y + 2, z, stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		}
		
		bonus(x, y , z, Cardinal.EAST);
	}
	
	private void west(){
		WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y + 2, z + 1, 0);

		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 2, y, z - 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, x - 2, y, z + 1, stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2, true, true);
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 3, y, z - 1, x - 3, y + 2, z + 1, Block.planks.blockID, SPRUCE, 2, false, true);
		}
		
		if(!world.isAirBlock(x - 3, y + 1, z)){
			WorldGenPrimitive.setBlock(world, x - 3, y + 1, z, 0);
			WorldGenPrimitive.setBlock(world, x - 3, y + 2, z, stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		}
		
		bonus(x, y, z, Cardinal.WEST);
	}
	
	private void bonus(int x, int y, int z, Cardinal wall){
		
		if(rand.nextInt(5) != 0) return;
		
		if(Catacomb.getLevel(y) == 3 && rand.nextBoolean()){
			IAlcove nest = new SilverfishNest();
			if(nest.isValidLocation(world, x, y, z, wall)){
				nest.generate(world, rand, x, y, z, wall);
				return;
			}
		}
		
		Coord shelf = new Coord(x, y, z);
		shelf.add(wall, 3);
		shelf.add(Cardinal.UP, 1);
		
		if(world.isAirBlock(shelf.getX(), shelf.getY() - 1, shelf.getZ())) return;
		
		if(Catacomb.getLevel(y) == 1 && rand.nextBoolean()){
			WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY(), shelf.getZ(), Block.bookShelf.blockID);
			return;
		}
		
		if(rand.nextBoolean()){
			boolean trapped = Catacomb.getLevel(y) == 3 && rand.nextInt(10) == 0;
			TreasureChest type = types[rand.nextInt(types.length)];
			TreasureChest.generate(world, rand, shelf.getX(), shelf.getY(), shelf.getZ(), type, Catacomb.getLevel(y), trapped);
			if(trapped){
				WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY() - 2, shelf.getZ(), Block.tnt.blockID);
				if(rand.nextBoolean()) WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY() - 3, shelf.getZ(), Block.tnt.blockID);
			}
		} else if(Catacomb.getLevel(y) > 0){
			Spawner.generate(world, rand, shelf.getX(), shelf.getY(), shelf.getZ());
		}
	}
}
