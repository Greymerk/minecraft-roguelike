package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class SegmentSewerArch extends SegmentBase {

	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
			
		MetaBlock stair = theme.getSecondaryStair(); 
		WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true);
		MetaBlock water = new MetaBlock(Blocks.flowing_water);
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		MetaBlock mossy = new MetaBlock(Blocks.mossy_cobblestone);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		editor.setBlock(rand, cursor, mossy, false, true);
		cursor.add(Cardinal.UP);
		editor.setBlock(rand, cursor, water, false, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, stair, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		bars.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		bars.setBlock(editor, cursor);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		editor.fillRectSolid(rand, start, end, air, true, true);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		editor.fillRectSolid(rand, start, end, water, true, true);
		
		for(Cardinal o : orth){
			cursor = new Coord(x, y, z);
			cursor.add(o, 1);
			cursor.add(dir, 2);
			editor.setBlock(rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, theme.getPrimaryWall(), true, true);
			cursor.add(Cardinal.reverse(dir), 1);
			editor.setBlock(rand, cursor, stair, true, true);			
		}
	}
}
