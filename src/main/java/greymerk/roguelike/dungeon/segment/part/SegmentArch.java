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

public class SegmentArch extends SegmentBase {

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
			
		IStair stair = theme.getSecondaryStair(); 
		stair.setOrientation(Cardinal.reverse(dir), true);
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		Coord cursor = new Coord(origin);
		cursor.add(dir, 2);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		stair.setBlock(editor, cursor);
		
		for(Cardinal orth : Cardinal.orthogonal(dir)){
			cursor = new Coord(origin);
			cursor.add(orth, 1);
			cursor.add(dir, 2);
			theme.getSecondaryPillar().set(editor, rand, cursor);
			cursor.add(Cardinal.UP, 1);
			theme.getSecondaryPillar().set(editor, rand, cursor);
			cursor.add(Cardinal.UP, 1);
			theme.getPrimaryWall().set(editor, rand, cursor);
			cursor.add(Cardinal.reverse(dir), 1);
			stair.setBlock(editor, cursor);			
		}
	}
}
