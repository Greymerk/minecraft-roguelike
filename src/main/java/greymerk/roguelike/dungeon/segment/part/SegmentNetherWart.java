package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;

import java.util.Random;

public class SegmentNetherWart extends SegmentBase{

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		IStair step = theme.getSecondaryStair();
		IBlockFactory wall = theme.getSecondaryWall();
		MetaBlock air = BlockType.get(BlockType.AIR);

		Coord cursor;
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor = new Coord(origin);
		cursor.add(dir, 5);

		
		cursor = new Coord(origin);
		cursor.add(dir, 3);
		editor.setBlock(cursor, BlockType.get(BlockType.FENCE_NETHER_BRICK));
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(cursor, BlockType.get(BlockType.FENCE_NETHER_BRICK));
		
		for(Cardinal orth : Cardinal.orthogonal(dir)){
			step.setOrientation(Cardinal.reverse(orth), true);
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(orth, 1);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, step, true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, wall, true, true);
			cursor.add(Cardinal.reverse(orth), 1);
			editor.setBlock(rand, cursor, wall, true, true);
			cursor.add(Cardinal.DOWN, 2);
			editor.setBlock(cursor, Crops.get(Crops.NETHERWART));
			cursor.add(orth, 1);
			editor.setBlock(cursor, Crops.get(Crops.NETHERWART));
			cursor.add(Cardinal.DOWN, 1);
			editor.setBlock(cursor, BlockType.get(BlockType.SOUL_SAND));
			cursor.add(Cardinal.reverse(orth), 1);
			editor.setBlock(cursor, BlockType.get(BlockType.SOUL_SAND));
		}
		
	}

}
