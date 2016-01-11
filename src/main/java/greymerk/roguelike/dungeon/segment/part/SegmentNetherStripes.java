package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Slab;

import java.util.Random;

public class SegmentNetherStripes extends SegmentBase {
	

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		IStair step = theme.getSecondaryStair();

		Coord start;
		Coord end;
		Coord cursor;
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor = new Coord(origin);
		cursor.add(dir, 5);
		boolean isAir = editor.isAirBlock(cursor);
		boolean isLava = rand.nextInt(5) == 0;

		
		MetaBlock slab = Slab.get(Slab.NETHERBRICK, false, false, false);
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, slab, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, slab, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, slab, true, true);
		
		for(Cardinal orth : Cardinal.orthogonal(dir)){
			start = new Coord(origin);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth, 1);
			start.add(Cardinal.UP, 3);
			end.add(Cardinal.DOWN, 2);
			if(isLava && !isAir) editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.LAVA_FLOWING), false, true);
			
			step.setOrientation(Cardinal.reverse(orth), true);
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(orth, 1);
			editor.setBlock(rand, cursor, step, true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, step, true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, step, true, true);
			cursor.add(Cardinal.reverse(dir), 1);
			editor.setBlock(rand, cursor, step, true, true);
		}
	}
}