package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;

public class SegmentNetherStripes extends SegmentBase {
	

	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		IStair step = theme.getSecondaryStair();

		Coord start;
		Coord end;
		Coord cursor;
		MetaBlock air = new MetaBlock(Blocks.air);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 5);
		boolean isAir = editor.isAirBlock(cursor);
		boolean isLava = rand.nextInt(5) == 0;

		
		MetaBlock slab = new MetaBlock(Blocks.stone_slab);
		slab.withProperty(BlockStoneSlab.VARIANT_PROP, BlockStoneSlab.EnumType.NETHERBRICK);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, slab, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, slab, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, slab, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			start = new Coord(x, y, z);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth, 1);
			start.add(Cardinal.UP, 3);
			end.add(Cardinal.DOWN, 2);
			if(isLava && !isAir) editor.fillRectSolid(rand, start, end, new MetaBlock(Blocks.lava), false, true);
			
			step.setOrientation(Cardinal.reverse(orth), true);
			cursor = new Coord(x, y, z);
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