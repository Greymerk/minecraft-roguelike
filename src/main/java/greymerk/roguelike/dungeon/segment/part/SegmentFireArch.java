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

public class SegmentFireArch extends SegmentBase {


	
	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory walls = theme.getPrimaryWall();
		
		Coord start;
		Coord end;
		Coord cursor;
		
		Cardinal[] orths = Cardinal.getOrthogonal(dir);
		
		start = new Coord(x, y, z);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orths[0]);
		end.add(orths[0]);
		end.add(Cardinal.UP, 2);
		end.add(dir);
		editor.fillRectSolid(rand, start, end, walls, true, true);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 2);
		WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(editor, cursor);
		cursor.add(Cardinal.DOWN, 2);
		cursor.add(dir);
		editor.setBlock(cursor, Blocks.netherrack);
		cursor.add(Cardinal.UP);
		editor.setBlock(cursor, Blocks.fire);
		cursor.add(Cardinal.reverse(dir));
		editor.setBlock(cursor, Blocks.iron_bars);
		
		for(Cardinal orth : orths){
			
			cursor = new Coord(x, y, z);
			cursor.add(dir);
			cursor.add(orth);
			cursor.add(Cardinal.UP, 2);
			WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(editor, cursor);
			
		}
	}
}
