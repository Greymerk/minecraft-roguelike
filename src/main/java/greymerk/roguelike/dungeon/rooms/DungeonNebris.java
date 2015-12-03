package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.StairType;

public class DungeonNebris extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock cobble = BlockType.get(BlockType.COBBLESTONE);
		BlockWeightedRandom waterFloor = new BlockWeightedRandom();
		waterFloor.addBlock(cobble, 40);
		waterFloor.addBlock(BlockType.get(BlockType.GLOWSTONE), 7);
		
		
		IStair step = new MetaStair(StairType.COBBLE);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		
		// space
		editor.fillRectSolid(rand, x - 8, y - 3, z - 8, x + 8, y + 5, z + 8, air);
		editor.fillRectSolid(rand, x - 8, y - 3, z - 8, x + 8, y - 3, z + 8, waterFloor);
		editor.fillRectSolid(rand, x - 8, y - 2, z - 8, x + 8, y - 2, z + 8, water);
		editor.fillRectSolid(rand, x - 8, y + 6, z - 8, x + 8, y + 6, z + 8, cobble, false, true);
		
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
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 9);
				start.add(Cardinal.DOWN, 1);
				end = new Coord(start);
				start.add(orth, 2);
				end.add(Cardinal.UP, 6);
				end.add(orth, 6);
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 2);
				start.add(orth, 2);
				start.add(dir, 8);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				start.add(orth, 5);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				start.add(orth, 1);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				start.add(Cardinal.reverse(dir), 1);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				editor.setBlock(rand, cursor, cobble, true, true);
				cursor.add(dir, 1);
				editor.setBlock(rand, cursor, cobble, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 8);
				cursor.add(orth, 3);
				editor.setBlock(rand, cursor, cobble, true, true);
				cursor.add(orth, 3);
				editor.setBlock(rand, cursor, cobble, true, true);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, cobble, true, true);
			
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 4);
				cursor.add(dir, 8);
				cursor.add(orth, 3);
				editor.setBlock(rand, cursor, cobble, true, true);
				cursor.add(orth, 3);
				editor.setBlock(rand, cursor, cobble, true, true);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, cobble, true, true);
				
				//upper blocks
				start = new Coord(x, y, z);
				start.add(dir, 8);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				end.add(orth, 6);
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				start.add(orth, 2);
				end.add(orth, 6);
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 6);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				start.add(orth, 5);
				end.add(orth, 6);
				editor.fillRectSolid(rand, start, end, cobble, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 5);
				cursor.add(dir, 8);
				editor.setBlock(rand, cursor, cobble, true, true);
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, cobble, true, true);
				cursor.add(Cardinal.DOWN, 1);
				
				
				step.setOrientation(Cardinal.reverse(orth), true);
				editor.setBlock(rand, cursor, step, true, true);
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
				editor.fillRectSolid(rand, start, end, air, true, true);
				
				start.add(dir, 1);
				end.add(dir, 1);
				editor.fillRectSolid(rand, start, end, ColorBlock.get(ColorBlock.WOOL, rand), true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 8);
				start.add(orth, 7);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				
				editor.fillRectSolid(rand, start, end, ColorBlock.get(ColorBlock.WOOL, rand), true, true);
			}
		}
		
		
		ITreasureChest chest = Treasure.generate(editor, rand, settings, new Coord(x, y, z), Treasure.EMPTY, 1, false);
		
		int middle = chest.getSize() / 2;
		if(rand.nextBoolean()){
			chest.setSlot(middle, ItemNovelty.getItem(ItemNovelty.NEBRISCROWN));	
		} else {
			chest.setSlot(middle, ItemNovelty.getItem(ItemNovelty.NEBRISSWORD));
		}
		
		
		return true;
	}

	@Override
	public int getSize() {
		return 11;
	}

}
