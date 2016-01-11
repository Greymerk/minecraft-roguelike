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
import greymerk.roguelike.worldgen.blocks.Leaves;

public class SegmentJungle extends SegmentBase {


	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal wallDirection, ITheme theme, Coord origin) {
		
		IStair stair = theme.getSecondaryStair();
		
		MetaBlock leaves = Leaves.get(Leaves.JUNGLE, false);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.orthogonal(wallDirection);
		start = new Coord(origin);
		start.add(wallDirection, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 1);
		editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.AIR), true, true);
		start.add(Cardinal.DOWN, 1);
		end.add(Cardinal.DOWN, 2);
		
		if(rand.nextInt(5) == 0){
			editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.WATER_FLOWING), true, true);
		} else {
			editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.GRASS), true, true);
			start.add(Cardinal.UP, 1);
			end.add(Cardinal.UP, 1);
			if(rand.nextBoolean()) editor.fillRectSolid(rand, start, end, leaves, true, true);
		}
		
		for(Cardinal d : orth){
			cursor = new Coord(origin);
			cursor.add(wallDirection, 2);
			cursor.add(d, 1);
			cursor.add(Cardinal.UP, 1);
			stair.setOrientation(Cardinal.reverse(d), true);
			editor.setBlock(rand, cursor, stair, true, true);
		}

	}
}
