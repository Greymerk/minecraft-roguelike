package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Quartz;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import net.minecraft.block.Block;

public class DungeonAvidya extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		MetaBlock redClay = ColorBlock.get(ColorBlock.CLAY, DyeColor.RED);
		MetaBlock whiteClay = ColorBlock.get(ColorBlock.CLAY, DyeColor.WHITE);
		MetaBlock pillarQuartz = Quartz.getPillar(Cardinal.UP);
		MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// clear space
		RectSolid.fill(editor, rand, new Coord(x - 8, y, z - 8), new Coord(x + 8, y + 5, z + 8), air);
				
		// roof
		RectSolid.fill(editor, rand, new Coord(x - 6, y + 6, z - 6), new Coord(x + 6, y + 6, z + 6), redClay, true, true);
		RectSolid.fill(editor, rand, new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 6, z + 3), glowstone);
		
		RectSolid.fill(editor, rand, new Coord(x - 7, y - 1, z - 7), new Coord(x + 7, y - 1, z + 7), air);
		
		
		// floor
		MetaBlock ying = ColorBlock.get(ColorBlock.CLAY, DyeColor.BLACK);
		MetaBlock yang = ColorBlock.get(ColorBlock.CLAY, DyeColor.WHITE);
		
		// ying
		RectSolid.fill(editor, rand, new Coord(x - 8, y - 2, z - 8), new Coord(x + 8, y - 2, z + 8), ying);
		
		// yang
		MetaBlock quartz = Quartz.get(Quartz.SMOOTH);
		Coord start = new Coord(x, y, z);
		start.add(Cardinal.DOWN, 2);
		start.add(Cardinal.WEST, 5);
		Coord end = new Coord(start);
		start.add(Cardinal.NORTH, 2);
		end.add(Cardinal.SOUTH, 2);
		RectSolid.fill(editor, rand, start, end, yang);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 1);
		start.add(Cardinal.NORTH, 2);
		end.add(Cardinal.SOUTH, 2);
		RectSolid.fill(editor, rand, start, end, yang);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 1);
		end.add(Cardinal.NORTH, 3);
		RectSolid.fill(editor, rand, start, end, yang);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 1);
		start.add(Cardinal.NORTH, 1);
		end.add(Cardinal.NORTH, 1);
		RectSolid.fill(editor, rand, start, end, yang);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 3);
		end.add(Cardinal.NORTH, 1);
		RectSolid.fill(editor, rand, start, end, yang);
		
		start.add(Cardinal.EAST, 3);
		end.add(Cardinal.EAST, 1);
		start.add(Cardinal.SOUTH, 1);
		end.add(Cardinal.NORTH, 1);
		RectSolid.fill(editor, rand, start, end, yang);
		
		start.add(Cardinal.WEST, 3);
		end.add(Cardinal.WEST, 2);
		end.add(Cardinal.NORTH, 1);
		RectSolid.fill(editor, rand, start, end, yang);
		
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.EAST, 1);
		start.add(Cardinal.SOUTH, 7);
		end.add(Cardinal.SOUTH, 7);
		RectSolid.fill(editor, rand, start, end, yang);
		
		
		
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.orthogonal(dir)){

				// upper trim
				start = new Coord(x, y, z);
				start.add(dir, 8);
				start.add(Cardinal.UP, 4);
				end = new Coord(start);
				end.add(orth, 8);
				RectSolid.fill(editor, rand, start, end, whiteClay);
				start.add(Cardinal.DOWN, 5);
				end.add(Cardinal.DOWN, 5);
				RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.STONE_BRICK));
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				end.add(orth, 7);
				RectSolid.fill(editor, rand, start, end, whiteClay);
				
				// ceiling details
				start = new Coord(x, y, z);
				start.add(dir, 4);
				start.add(Cardinal.UP, 5);
				end = new Coord(start);
				end.add(orth, 2);
				RectSolid.fill(editor, rand, start, end, quartz);
				
				Coord cursor = new Coord(end);
				cursor.add(dir, 1);
				quartz.set(editor, rand, cursor);
				cursor = new Coord(end);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				quartz.set(editor, rand, cursor);
				pillarTop(editor, rand, cursor);
				
				// pillars
				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 1);
				start.add(dir, 8);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 4);
				RectSolid.fill(editor, rand, start, end, pillarQuartz);
				start.add(orth, 4);
				end.add(orth, 4);
				RectSolid.fill(editor, rand, start, end, pillarQuartz);
				
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
				quartz.setBlock(editor, cursor2);
				cursor2.add(Cardinal.reverse(dir), 1);
				cursor2.add(Cardinal.UP, 1);
				whiteClay.setBlock(editor, cursor2);
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
				RectSolid.fill(editor, rand, start, end, whiteClay, false, true);
				
				// floor outer step circle
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(Cardinal.DOWN, 1);
				IStair step = new MetaStair(StairType.STONEBRICK);
				step.setOrientation(Cardinal.reverse(dir), false);
				step.setBlock(editor, cursor);
				
				cursor.add(orth, 1);
				step.setBlock(editor, cursor);
				
				cursor.add(orth, 1);
				step.setBlock(editor, cursor);
				
				step.setOrientation(Cardinal.reverse(orth), false);
				cursor.add(orth, 1);
				step.setBlock(editor, cursor);
				
				cursor.add(Cardinal.reverse(dir), 1);
				step.setBlock(editor, cursor);
				
				step.setOrientation(Cardinal.reverse(dir), false);
				cursor.add(orth, 1);
				step.setBlock(editor, cursor);
				
				step.setOrientation(Cardinal.reverse(orth), false);
				cursor.add(orth, 1);
				step.setBlock(editor, cursor);
				
				cursor.add(Cardinal.reverse(dir), 1);
				step.setBlock(editor, cursor);
				
				// perimeter decor
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 8);
				cursor.add(orth, 3);
				editor.setBlock(cursor, BlockType.get(BlockType.GRASS));
				MetaBlock leaves = Leaves.get(Leaves.OAK, false);
				
				editor.setBlock(cursor, leaves);
				cursor.add(orth, 1);
				editor.setBlock(cursor, BlockType.get(BlockType.GRASS));
				editor.setBlock(cursor, leaves);
				cursor.add(orth, 1);
				editor.setBlock(cursor, BlockType.get(BlockType.GRASS));
				editor.setBlock(cursor, leaves);
				cursor.add(Cardinal.reverse(dir), 1);
				editor.setBlock(cursor, BlockType.get(BlockType.COBBLESTONE));
				cursor.add(Cardinal.reverse(orth), 1);
				glowstone.setBlock(editor, cursor);
				cursor.add(orth, 2);
				air.setBlock(editor, cursor);
				cursor.add(Cardinal.DOWN, 1);
				glowstone.setBlock(editor, cursor);
				cursor.add(Cardinal.UP, 1);
				cursor.add(Cardinal.reverse(dir), 1);
				editor.setBlock(cursor, BlockType.get(BlockType.COBBLESTONE));
				cursor.add(dir, 1);
				cursor.add(orth, 1);
				editor.setBlock(cursor, BlockType.get(BlockType.COBBLESTONE));
				cursor.add(dir, 1);
				editor.setBlock(cursor, BlockType.get(BlockType.COBBLESTONE));
				cursor.add(orth, 1);
				editor.setBlock(cursor, BlockType.get(BlockType.COBBLESTONE));
				cursor.add(Cardinal.UP, 1);
				editor.setBlock(cursor, BlockType.get(BlockType.COBBLESTONE));
				cursor.add(Cardinal.UP, 3);
				editor.setBlock(cursor, BlockType.get(BlockType.WATER_FLOWING));
			}
		}
		
		return true;
	}

	@Override
	public int getSize() {
		return 10;
	}
	
	private static void pillarTop(IWorldEditor editor, Random rand, Coord cursor){
		IStair step = new MetaStair(StairType.QUARTZ);
		for(Cardinal dir : Cardinal.directions){
			step.setOrientation(dir, true);
			cursor.add(dir, 1);
			step.set(editor, rand, cursor, true, false);
			cursor.add(Cardinal.reverse(dir), 1);
		}
	}
	
	public boolean validLocation(IWorldEditor editor, Cardinal dir, int x, int y, int z){
		for(Coord pos : new RectHollow(new Coord(x - 10, y - 2, z - 10), new Coord(x + 10, y + 5, z + 10))){
			Block b = editor.getBlock(pos).getBlock();
			if(!b.getMaterial().isSolid()) return false;
		}
		
		return true;
	}

}
