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

public class SegmentSewerArch extends SegmentBase {

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
			
		IStair stair = theme.getSecondaryStair(); 
		stair.setOrientation(Cardinal.reverse(dir), true);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
		MetaBlock mossy = BlockType.get(BlockType.COBBLESTONE_MOSSY);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.orthogonal(dir);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 3);
		editor.setBlock(rand, cursor, mossy, false, true);
		cursor.add(Cardinal.UP);
		editor.setBlock(rand, cursor, water, false, true);
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, stair, true, true);
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		bars.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		bars.setBlock(editor, cursor);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		editor.fillRectSolid(rand, start, end, air, true, true);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		editor.fillRectSolid(rand, start, end, water, true, true);
		
		for(Cardinal o : orth){
			cursor = new Coord(origin);
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
