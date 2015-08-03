package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class SegmentNetherLava extends SegmentBase {

	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock step = theme.getSecondaryStair();
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock lava = new MetaBlock(Blocks.flowing_lava);
		
		Coord start;
		Coord end;
		Coord cursor;
		
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		air.setBlock(editor, cursor);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 5);
		boolean isAir = editor.isAirBlock(cursor);
		boolean isLava = true;
		IBlockFactory wall = theme.getSecondaryWall();
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			start = new Coord(x, y, z);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth, 1);
			start.add(Cardinal.UP, 2);
			end.add(Cardinal.DOWN, 1);
			if(isLava && !isAir){
				editor.fillRectSolid(rand, start, end, air, true, true);
				lava.setBlock(editor, start);
				start.add(Cardinal.reverse(orth), 1);
				lava.setBlock(editor, start);
			}
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			
			WorldEditor.blockOrientation(step, Cardinal.reverse(orth), false);
			cursor.add(orth, 1);
			editor.setBlock(rand, cursor, step, true, true);
			
			WorldEditor.blockOrientation(step, Cardinal.reverse(orth), true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, step, true, true);
			
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, wall, true, true);
			cursor.add(Cardinal.reverse(orth), 1);
			editor.setBlock(rand, cursor, wall, true, true);
		}
		
	}

}
