package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonNebris extends DungeonBase {

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock cobble = new MetaBlock(Blocks.cobblestone);
		BlockWeightedRandom waterFloor = new BlockWeightedRandom();
		waterFloor.addBlock(cobble, 40);
		waterFloor.addBlock(new MetaBlock(Blocks.glowstone), 7);
		
		
		MetaBlock step = new MetaBlock(Blocks.stone_stairs);
		MetaBlock water = new MetaBlock(Blocks.flowing_water);
		
		// space
		WorldGenPrimitive.fillRectSolid(world, rand, x - 8, y - 3, z - 8, x + 8, y + 5, z + 8, air);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 8, y - 3, z - 8, x + 8, y - 3, z + 8, waterFloor);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 8, y - 2, z - 8, x + 8, y - 2, z + 8, water);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 8, y + 6, z - 8, x + 8, y + 6, z + 8, cobble, false, true);
		
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
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 9);
				start.add(Cardinal.DOWN, 1);
				end = new Coord(start);
				start.add(orth, 2);
				end.add(Cardinal.UP, 6);
				end.add(orth, 6);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 2);
				start.add(orth, 2);
				start.add(dir, 8);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				start.add(orth, 5);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				start.add(orth, 1);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				start.add(Cardinal.reverse(dir), 1);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				cursor.add(dir, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 8);
				cursor.add(orth, 3);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				cursor.add(orth, 3);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
			
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 4);
				cursor.add(dir, 8);
				cursor.add(orth, 3);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				cursor.add(orth, 3);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				
				//upper blocks
				start = new Coord(x, y, z);
				start.add(dir, 8);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				end.add(orth, 6);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				start.add(orth, 2);
				end.add(orth, 6);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 6);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				start.add(orth, 5);
				end.add(orth, 6);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, cobble, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 5);
				cursor.add(dir, 8);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, cobble, true, true);
				cursor.add(Cardinal.DOWN, 1);
				
				
				step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
				WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
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
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
				
				start.add(dir, 1);
				end.add(dir, 1);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.wool, 14), true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 8);
				start.add(orth, 7);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.wool, 11), true, true);
			}
		}
		
		
		ITreasureChest chest = new TreasureChestEmpty();
		chest.generate(world, rand, settings.getLoot(), new Coord(x, y, z), 0, false);
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
