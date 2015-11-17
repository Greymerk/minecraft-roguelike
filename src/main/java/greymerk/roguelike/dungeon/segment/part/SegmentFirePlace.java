package greymerk.roguelike.dungeon.segment.part;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class SegmentFirePlace extends SegmentBase {
	
	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getSecondaryStair();
		
		Coord cursor = new Coord(x, y, z);
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		cursor.add(dir, 2);
		start = new Coord(cursor);
		start.add(orth[0], 1);
		end = new Coord(cursor);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		// front wall
		start.add(dir, 1);
		end.add(dir, 1);
		editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), false, true);

		// stairs
		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			stair.setOrientation(Cardinal.reverse(d), true);
			editor.setBlock(rand, c, stair, true, true);
		}
		
		stair = theme.getPrimaryStair();
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		stair.setOrientation(Cardinal.reverse(dir), false);
		stair.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		editor.setBlock(cursor, BlockType.get(BlockType.IRON_BAR));
		cursor.add(Cardinal.UP);
		stair.setOrientation(Cardinal.reverse(dir), true);
		stair.setBlock(editor, cursor);
		
		start = new Coord(x, y, z);
		start.add(dir, 4);
		end = new Coord(start);
		start.add(Cardinal.DOWN);
		start.add(orth[0]);
		end.add(Cardinal.UP, 3);
		end.add(orth[1]);
		end.add(dir, 2);
		List<Coord> box = editor.getRectHollow(start, end);
		for(Coord c : box){
			if(!editor.getBlock(c).getBlock().getMaterial().isSolid()) return;
		}
		
		editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 4);
		editor.setBlock(cursor, BlockType.get(BlockType.NETHERRACK));
		cursor.add(Cardinal.UP);
		editor.setBlock(cursor, BlockType.get(BlockType.FIRE));
	}	
}
