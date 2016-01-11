package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class SegmentInset extends SegmentBase {

	
	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getSecondaryStair();
		
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.orthogonal(dir);		
		
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, air, true, true);
		start.add(dir, 1);
		end.add(dir, 1);
		editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), true, true);
		
		for(Cardinal d : orth){
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 2);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			stair.setOrientation(Cardinal.reverse(dir), true);
			editor.setBlock(rand, cursor, stair, true, true);
			
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			stair.setOrientation(Cardinal.reverse(d), false);
			editor.setBlock(rand, cursor, stair, true, true);
		}
	
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 1);
		cursor.add(dir, 3);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		stair.setOrientation(Cardinal.reverse(dir), true);
		editor.setBlock(rand, cursor, stair, true, true);
	}
}
