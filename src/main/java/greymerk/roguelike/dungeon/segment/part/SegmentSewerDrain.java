package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

import java.util.Random;

public class SegmentSewerDrain extends SegmentBase {

	
	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		IStair stair = theme.getSecondaryStair();
		MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		editor.fillRectSolid(rand, start, end, air, true, true);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		editor.fillRectSolid(rand, start, end, water, false, true);
				
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, air, true, true);
		start.add(dir);
		end.add(dir);
		editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.COBBLESTONE_MOSSY), true, true);
		
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(o);
			stair.setOrientation(Cardinal.reverse(o), false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			bars.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.UP);
		end = new Coord(start);
		end.add(dir, 5);
		editor.fillRectSolid(rand, start, end, air, true, true);
		water.setBlock(editor, end);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		cursor.add(dir);
		air.setBlock(editor, cursor);
		
	}
}
