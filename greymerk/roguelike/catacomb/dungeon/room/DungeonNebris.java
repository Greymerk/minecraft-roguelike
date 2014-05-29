package greymerk.roguelike.catacomb.dungeon.room;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class DungeonNebris implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		BlockRandomizer waterFloor = new BlockRandomizer(rand, new MetaBlock(Block.cobblestone.blockID));
		waterFloor.addBlock(new MetaBlock(Block.glowStone.blockID), 7);
		
		
		// space
		WorldGenPrimitive.fillRectSolid(world, x - 8, y - 3, z - 8, x + 8, y + 5, z + 8, 0);
		WorldGenPrimitive.fillRectSolid(world, x - 8, y - 3, z - 8, x + 8, y - 3, z + 8, waterFloor);
		WorldGenPrimitive.fillRectSolid(world, x - 8, y - 2, z - 8, x + 8, y - 2, z + 8, Block.waterMoving.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 8, y + 6, z - 8, x + 8, y + 6, z + 8, new MetaBlock(Block.cobblestone.blockID), false, true);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 1);
				end = new Coord(start);
				end.add(dir, 9);
				end.add(orth, 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 9);
				start.add(Cardinal.DOWN, 1);
				end = new Coord(start);
				start.add(orth, 2);
				end.add(Cardinal.UP, 6);
				end.add(orth, 6);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 2);
				start.add(orth, 2);
				start.add(dir, 8);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				start.add(orth, 5);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				start.add(orth, 1);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				start.add(Cardinal.reverse(dir), 1);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				cursor.add(dir, 1);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 8);
				cursor.add(orth, 3);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				cursor.add(orth, 3);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
			
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 4);
				cursor.add(dir, 8);
				cursor.add(orth, 3);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				cursor.add(orth, 3);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				
				//upper blocks
				start = new Coord(x, y, z);
				start.add(dir, 8);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				end.add(orth, 6);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				start.add(orth, 2);
				end.add(orth, 6);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 6);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				start.add(orth, 5);
				end.add(orth, 6);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cobblestone.blockID), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 5);
				cursor.add(dir, 8);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, cursor, Block.cobblestone.blockID);
				cursor.add(Cardinal.DOWN, 1);
				MetaBlock step = new MetaBlock(Block.stairsCobblestone.blockID);
				step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
				WorldGenPrimitive.setBlock(world, cursor, step, true, true);
			}
		}
		
		
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				
				// wool
				start = new Coord(x, y, z);
				start.add(dir, 9);
				start.add(orth, 4);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				end.add(orth, 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(0), true, true);
				
				start.add(dir, 1);
				end.add(dir, 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cloth.blockID, 14), true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 8);
				start.add(orth, 7);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.cloth.blockID, 11), true, true);
			}
		}
		
		
		ITreasureChest chest = new TreasureChestEmpty();
		chest.generate(world, rand, x, y, z);
		int middle = chest.getInventorySize() / 2;
		if(rand.nextBoolean()){
			chest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.NEBRISCROWN), middle);	
		} else {
			chest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.NEBRISSWORD), middle);
		}
		
		
		return true;
	}

	@Override
	public int getSize() {
		return 11;
	}

}
