package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockQuartz;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class DungeonAvidya extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		MetaBlock redClay = new MetaBlock(Blocks.stained_hardened_clay);
		redClay.withProperty(BlockColored.COLOR, EnumDyeColor.RED);
		MetaBlock whiteClay = new MetaBlock(Blocks.stained_hardened_clay);
		whiteClay.withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);
		MetaBlock pillarQuartz = new MetaBlock(Blocks.quartz_block);
		pillarQuartz.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_Y);
		MetaBlock glowstone = new MetaBlock(Blocks.glowstone);
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// clear space
		editor.fillRectSolid(rand, x - 8, y, z - 8, x + 8, y + 5, z + 8, air);
				
		// roof
		editor.fillRectSolid(rand, x - 6, y + 6, z - 6, x + 6, y + 6, z + 6, redClay, true, true);
		editor.fillRectSolid(rand, x - 3, y + 6, z - 3, x + 3, y + 6, z + 3, glowstone);
		
		editor.fillRectSolid(rand, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, air);
		
		
		// floor
		MetaBlock ying = new MetaBlock(Blocks.stained_hardened_clay);
		ying.withProperty(BlockColored.COLOR, EnumDyeColor.BLACK);
		MetaBlock yang = new MetaBlock(Blocks.stained_hardened_clay);
		yang.withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);
		
		// ying
		editor.fillRectSolid(rand, x - 8, y - 2, z - 8, x + 8, y - 2, z + 8, ying, true, true);
		
		// yang
		MetaBlock quartz = new MetaBlock(Blocks.quartz_block);
		Coord start = new Coord(x, y, z);
		start.add(Cardinal.DOWN, 2);
		start.add(Cardinal.WEST, 5);
		Coord end = new Coord(start);
		start.add(Cardinal.NORTH, 2);
		end.add(Cardinal.SOUTH, 2);
		editor.fillRectSolid(rand, start, end, yang, true, true);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 1);
		start.add(Cardinal.NORTH, 2);
		end.add(Cardinal.SOUTH, 2);
		editor.fillRectSolid(rand, start, end, yang, true, true);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 1);
		end.add(Cardinal.NORTH, 3);
		editor.fillRectSolid(rand, start, end, yang, true, true);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 1);
		start.add(Cardinal.NORTH, 1);
		end.add(Cardinal.NORTH, 1);
		editor.fillRectSolid(rand, start, end, yang, true, true);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 3);
		end.add(Cardinal.NORTH, 1);
		editor.fillRectSolid(rand, start, end, yang, true, true);
		
		start.add(Cardinal.EAST, 3);
		end.add(Cardinal.EAST, 1);
		start.add(Cardinal.SOUTH, 1);
		end.add(Cardinal.NORTH, 1);
		editor.fillRectSolid(rand, start, end, yang, true, true);
		
		start.add(Cardinal.WEST, 3);
		end.add(Cardinal.WEST, 2);
		end.add(Cardinal.NORTH, 1);
		editor.fillRectSolid(rand, start, end, ying, true, true);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 1);
		start.add(Cardinal.SOUTH, 7);
		end.add(Cardinal.SOUTH, 7);
		editor.fillRectSolid(rand, start, end, yang, true, true);
		
		
		
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){

				// upper trim
				start = new Coord(x, y, z);
				start.add(dir, 8);
				start.add(Cardinal.UP, 4);
				end = new Coord(start);
				end.add(orth, 8);
				editor.fillRectSolid(rand, start, end, whiteClay, true, true);
				start.add(Cardinal.DOWN, 5);
				end.add(Cardinal.DOWN, 5);
				editor.fillRectSolid(rand, start, end, new MetaBlock(Blocks.stonebrick), true, true);
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				end.add(orth, 7);
				editor.fillRectSolid(rand, start, end, whiteClay, true, true);
				
				// ceiling details
				start = new Coord(x, y, z);
				start.add(dir, 4);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				end.add(orth, 2);
				editor.fillRectSolid(rand, start, end, quartz, true, true);
				
				Coord cursor = new Coord(end);
				cursor.add(dir, 1);
				editor.setBlock(rand, cursor, quartz, true, true);
				cursor = new Coord(end);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, quartz, true, true);
				pillarTop(editor, rand, cursor);
				
				// pillars
				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 1);
				start.add(dir, 8);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 4);
				editor.fillRectSolid(rand, start, end, pillarQuartz, true, true);
				start.add(orth, 4);
				end.add(orth, 4);
				editor.fillRectSolid(rand, start, end, pillarQuartz, true, true);
				
				// pillar tops
				cursor = new Coord(x, y, z);
				cursor.add(dir, 8);
				cursor.add(orth, 2);
				cursor.add(Cardinal.UP, 3);
				Coord cursor2 = new Coord(cursor);
				pillarTop(editor, rand, cursor);
				cursor2.add(orth, 4);
				pillarTop(editor, rand, cursor2);
				cursor2.add(Cardinal.reverse(dir), 1);
				cursor2.add(Cardinal.UP, 1);
				editor.setBlock(rand, cursor2, quartz, true, true);
				cursor2.add(Cardinal.reverse(dir), 1);
				cursor2.add(Cardinal.UP, 1);
				editor.setBlock(rand, cursor2, whiteClay, true, true);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(Cardinal.UP, 1);
				pillarTop(editor, rand, cursor);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(Cardinal.UP, 1);
				pillarTop(editor, rand, cursor);		
				
				// outer wall shell
				start = new Coord(x, y, z);
				start.add(dir, 9);
				end = new Coord(start);
				end.add(orth, 9);
				end.add(Cardinal.UP, 3);
				editor.fillRectSolid(rand, start, end, whiteClay, false, true);
				
				// floor outer step circle
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(Cardinal.DOWN, 1);
				MetaBlock step = new MetaBlock(Blocks.stone_brick_stairs);
				WorldEditor.blockOrientation(step, Cardinal.reverse(dir), false);
				editor.setBlock(rand, cursor, step, true, true);
				
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, step, true, true);
				
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, step, true, true);
				
				WorldEditor.blockOrientation(step, Cardinal.reverse(orth), false);
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, step, true, true);
				
				cursor.add(Cardinal.reverse(dir), 1);
				editor.setBlock(rand, cursor, step, true, true);
				
				WorldEditor.blockOrientation(step, Cardinal.reverse(dir), false);
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, step, true, true);
				
				WorldEditor.blockOrientation(step, Cardinal.reverse(orth), false);
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, step, true, true);
				
				cursor.add(Cardinal.reverse(dir), 1);
				editor.setBlock(rand, cursor, step, true, true);
				
				// perimeter decor
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 8);
				cursor.add(orth, 3);
				editor.setBlock(cursor, Blocks.grass);
				MetaBlock leaves = new MetaBlock(Blocks.leaves);
				leaves.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.OAK);
				
				editor.setBlock(cursor.getX(), cursor.getY() + 1, cursor.getZ(), leaves);
				cursor.add(orth, 1);
				editor.setBlock(cursor, Blocks.grass);
				editor.setBlock(cursor.getX(), cursor.getY() + 1, cursor.getZ(), leaves);
				cursor.add(orth, 1);
				editor.setBlock(cursor, Blocks.grass);
				editor.setBlock(cursor.getX(), cursor.getY() + 1, cursor.getZ(), leaves);
				cursor.add(Cardinal.reverse(dir), 1);
				editor.setBlock(cursor, Blocks.cobblestone);
				cursor.add(Cardinal.reverse(orth), 1);
				glowstone.setBlock(editor, cursor);
				cursor.add(orth, 2);
				air.setBlock(editor, cursor);
				cursor.add(Cardinal.DOWN, 1);
				glowstone.setBlock(editor, cursor);
				cursor.add(Cardinal.UP, 1);
				cursor.add(Cardinal.reverse(dir), 1);
				editor.setBlock(cursor, Blocks.cobblestone);
				cursor.add(dir, 1);
				cursor.add(orth, 1);
				editor.setBlock(cursor, Blocks.cobblestone);
				cursor.add(dir, 1);
				editor.setBlock(cursor, Blocks.cobblestone);
				cursor.add(orth, 1);
				editor.setBlock(cursor, Blocks.cobblestone);
				cursor.add(Cardinal.UP, 1);
				editor.setBlock(cursor, Blocks.cobblestone);
				cursor.add(Cardinal.UP, 3);
				editor.setBlock(cursor, Blocks.flowing_water);
			}
		}
		
		ITreasureChest chest = new TreasureChestEmpty();
		chest.generate(editor, rand, settings.getLoot(), new Coord(x, y - 1, z), 0, false);
		int middle = chest.getInventorySize() / 2;
		chest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.AVIDYA), middle);
		
		return true;
	}

	@Override
	public int getSize() {
		return 10;
	}
	
	private static void pillarTop(WorldEditor editor, Random rand, Coord cursor){
		MetaBlock step = new MetaBlock(Blocks.quartz_stairs);
		for(Cardinal dir : Cardinal.directions){
			WorldEditor.blockOrientation(step, dir, true);
			cursor.add(dir, 1);
			editor.setBlock(rand, cursor, step, true, false);
			cursor.add(Cardinal.reverse(dir), 1);
		}
	}
	
	public boolean validLocation(WorldEditor editor, Cardinal dir, int x, int y, int z){
		
		List<Coord> box = editor.getRectHollow(x - 10, y - 2, z - 10, x + 10, y + 5, z + 10);
		
		for(Coord pos : box){
			Block b = editor.getBlock(pos).getBlock();
			if(!b.getMaterial().isSolid()) return false;
		}
		
		return true;
	}

}
