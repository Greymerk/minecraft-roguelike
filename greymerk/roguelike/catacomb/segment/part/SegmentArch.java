package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.segment.alcove.PrisonCell;
import greymerk.roguelike.worldgen.BlockFactoryProvider;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentArch extends SegmentBase {

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
		MetaBlock stair = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2);
		
		WorldGenPrimitive.setBlock(world, x, y, z - 2, 0);
		WorldGenPrimitive.setBlock(world, x, y + 1, z - 2, 0);
		WorldGenPrimitive.setBlock(world, x, y + 2, z - 2, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 1, stair, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 1, stair, true, true);
		
		if(rand.nextInt(10) == 0 && Catacomb.getLevel(y) == 2){
			IAlcove cell = new PrisonCell();
			if(cell.isValidLocation(world, x, y, z, Cardinal.NORTH)){
				cell.generate(world, rand, x, y, z, Cardinal.NORTH);
			}
		}
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 2, x - 1, y + 1, z - 2, Block.wood.blockID, woodType, 2, true, true);
			WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 2, Block.planks.blockID, woodType, 2, true, true);
			WorldGenPrimitive.fillRectSolid(world, x + 1, y, z - 2, x + 1, y + 1, z - 2, Block.wood.blockID, woodType, 2, true, true);
			WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 2, Block.planks.blockID, woodType, 2, true, true);
			WorldGenPrimitive.fillRectSolid(world, x, y, z - 3, x, y + 1, z - 3, Block.planks.blockID, woodType, 2, false, true);
			WorldGenPrimitive.setBlock(world, x, y + 2, z - 2, Block.planks.blockID, woodType, 2, true, true);
		}
	}
	
	private void south(){
		MetaBlock stair = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2);
		
		WorldGenPrimitive.setBlock(world, x, y, z + 2, 0);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 2, 0);
		WorldGenPrimitive.setBlock(world, x, y + 2, z + 2, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 1, stair, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 1, stair, true, true);
		
		if(rand.nextInt(10) == 0 && Catacomb.getLevel(y) == 2){
			IAlcove cell = new PrisonCell();
			if(cell.isValidLocation(world, x, y, z, Cardinal.SOUTH)){
				cell.generate(world, rand, x, y, z, Cardinal.SOUTH);
			}
		}
		
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 2, x - 1, y + 1, z + 2, Block.wood.blockID, woodType, 2, true, true);
			WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 2, Block.planks.blockID, woodType, 2, true, true);
			WorldGenPrimitive.fillRectSolid(world, x + 1, y, z + 2, x + 1, y + 1, z + 2, Block.wood.blockID, woodType, 2, true, true);
			WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 2, Block.planks.blockID, woodType, 2, true, true);
			WorldGenPrimitive.fillRectSolid(world, x, y, z + 3, x, y + 1, z + 3, Block.planks.blockID, woodType, 2, false, true);
			WorldGenPrimitive.setBlock(world, x, y + 2, z + 2, Block.planks.blockID, woodType, 2, true, true);
		}
	}
	
	private void east(){
		MetaBlock stair = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2);
		
		WorldGenPrimitive.setBlock(world, x + 2, y, z, 0);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z, 0);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 1, stair, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 1, stair, true, true);
		
		if(rand.nextInt(10) == 0 && Catacomb.getLevel(y) == 2){
			IAlcove cell = new PrisonCell();
			if(cell.isValidLocation(world, x, y, z, Cardinal.EAST)){
				cell.generate(world, rand, x, y, z, Cardinal.EAST);
			}
		}
		
		if(Catacomb.getLevel(y)  < 2){
			WorldGenPrimitive.fillRectSolid(world, x + 2, y, z - 1, x + 2, y + 1, z - 1, Block.wood.blockID, woodType, 2, true, true);
			WorldGenPrimitive.setBlock(world, x + 2, y + 2, z - 1, Block.planks.blockID, woodType, 2, true, true);
			WorldGenPrimitive.fillRectSolid(world, x + 2, y, z + 1, x + 2, y + 1, z + 1, Block.wood.blockID, woodType, 2, true, true);
			WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 1, Block.planks.blockID, woodType, 2, true, true);
			WorldGenPrimitive.fillRectSolid(world, x + 3, y, z, x + 3, y + 1, z, Block.planks.blockID, woodType, 2, false, true);
			WorldGenPrimitive.setBlock(world, x + 2, y + 2, z, Block.planks.blockID, woodType, 2, true, true);
		}
	}
	
	private void west(){
		MetaBlock stair = new MetaBlock(stairType, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2);
		
		WorldGenPrimitive.setBlock(world, x - 2, y, z, 0);
		WorldGenPrimitive.setBlock(world, x - 2, y + 1, z, 0);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z, stair, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 1, stair, true, true);
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 1, stair, true, true);
		
		if(rand.nextInt(10) == 0 && Catacomb.getLevel(y) == 2){
			IAlcove cell = new PrisonCell();
			if(cell.isValidLocation(world, x, y, z, Cardinal.WEST)){
				cell.generate(world, rand, x, y, z, Cardinal.WEST);
			}
		}
		
		if(Catacomb.getLevel(y) < 2){
			WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y + 1, z - 1, Block.wood.blockID, woodType, 2, true, true);
			WorldGenPrimitive.setBlock(world, x - 2, y + 2, z - 1, Block.planks.blockID, woodType, 2, true, true);
			WorldGenPrimitive.fillRectSolid(world, x - 2, y, z + 1, x - 2, y + 1, z + 1, Block.wood.blockID, woodType, 2, true, true);
			WorldGenPrimitive.setBlock(world, x - 2, y + 2, z + 1, Block.planks.blockID, woodType, 2, true, true);
			WorldGenPrimitive.fillRectSolid(world, x - 3, y, z, x - 3, y + 1, z, Block.planks.blockID, woodType, 2, false, true);
			WorldGenPrimitive.setBlock(world, x - 2, y + 2, z, Block.planks.blockID, woodType, 2, true, true);
		}
	}
	

}
