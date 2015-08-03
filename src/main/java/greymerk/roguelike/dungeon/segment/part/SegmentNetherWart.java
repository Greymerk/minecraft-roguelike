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

public class SegmentNetherWart extends SegmentBase{

	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock step = theme.getSecondaryStair();
		IBlockFactory wall = theme.getSecondaryWall();
		MetaBlock air = new MetaBlock(Blocks.air);

		Coord cursor;
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 5);

		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		editor.setBlock(cursor, Blocks.nether_brick_fence);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(cursor, Blocks.nether_brick_fence);
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			WorldEditor.blockOrientation(step, Cardinal.reverse(orth), true);
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(orth, 1);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, step, true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, wall, true, true);
			cursor.add(Cardinal.reverse(orth), 1);
			editor.setBlock(rand, cursor, wall, true, true);
			cursor.add(Cardinal.DOWN, 2);
			editor.setBlock(cursor, Blocks.nether_wart);
			cursor.add(orth, 1);
			editor.setBlock(cursor, Blocks.nether_wart);
			cursor.add(Cardinal.DOWN, 1);
			editor.setBlock(cursor, Blocks.soul_sand);
			cursor.add(Cardinal.reverse(orth), 1);
			editor.setBlock(cursor, Blocks.soul_sand);
		}
		
	}

}
