package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonStorage extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		HashSet<Coord> chestSpaces = new HashSet<Coord>();
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// space
		editor.fillRectSolid(rand, x - 6, y, z - 6, x + 6, y + 3, z + 6, air);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		IBlockFactory blocks = theme.getPrimaryWall();
		
		editor.fillRectSolid(rand, x - 6, y - 1, z - 6, x + 6, y - 1, z + 6, blocks, true, true);
		editor.fillRectSolid(rand, x - 5, y + 4, z - 5, x + 5, y + 4, z + 5, blocks, true, true);
		
		for(Cardinal dir : Cardinal.directions){			
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 3);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				pillarTop(editor, rand, theme, cursor);
				cursor.add(dir, 3);
				cursor.add(orth, 3);
				pillarTop(editor, rand, theme, cursor);
				start = new Coord(cursor);
				
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 1);
				pillarTop(editor, rand, theme, cursor);
				
				end = new Coord(cursor);
				end.add(Cardinal.DOWN, 3);
				end.add(dir, 1);
				end.add(orth, 1);
				editor.fillRectSolid(rand, start, end, blocks, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				pillar(editor, rand, cursor, theme, 4);
				cursor.add(dir, 4);
				pillar(editor, rand, cursor, theme, 3);

				
				cursor.add(Cardinal.UP, 2);
				pillarTop(editor, rand, theme, cursor);
				
				cursor.add(Cardinal.UP, 1);
				cursor.add(Cardinal.reverse(dir), 1);
				pillarTop(editor, rand, theme, cursor);
				
				cursor.add(Cardinal.reverse(dir), 3);
				pillarTop(editor, rand, theme, cursor);
				
				start = new Coord(x, y, z);
				start.add(dir, 6);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(orth, 5);
				editor.fillRectSolid(rand, start, end, blocks, true, true);
				start.add(dir, 1);
				end.add(dir, 1);
				end.add(Cardinal.DOWN, 3);
				editor.fillRectSolid(rand, start, end, blocks, false, true);				
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 6);
				cursor.add(orth, 3);
				IStair step = theme.getSecondaryStair();
				step.setOrientation(Cardinal.reverse(dir), true);
				editor.setBlock(rand, cursor, step, true, true);
				cursor.add(orth, 1);
				editor.setBlock(rand, cursor, step, true, true);
				cursor.add(Cardinal.UP, 1);
				chestSpaces.add(new Coord(cursor));
				cursor.add(Cardinal.reverse(orth), 1);
				chestSpaces.add(new Coord(cursor));				
				
				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 1);
				start.add(dir, 3);
				start.add(orth, 3);
				end = new Coord(start);
				end.add(dir, 3);
				end.add(orth, 1);
				editor.fillRectSolid(rand, start, end, theme.getSecondaryFloor(), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 5);
				cursor.add(orth, 5);
				pillar(editor, rand, cursor, theme, 4);
				
			}
		}
		
		List<Coord> spaces = new ArrayList<Coord>(chestSpaces);
		Collections.shuffle(spaces);
		Treasure.generate(editor, rand, spaces.remove(0), Treasure.SUPPLIES, settings.getDifficulty(origin));
		Treasure.generate(editor, rand, spaces.remove(0), Treasure.BLOCKS, settings.getDifficulty(origin));
		return true;
	}

	@Override
	public int getSize() {
		return 10;
	}

	private static void pillarTop(IWorldEditor editor, Random rand, ITheme theme, Coord cursor){
		IStair step = theme.getSecondaryStair();
		for(Cardinal dir : Cardinal.directions){
			step.setOrientation(dir, true);
			cursor.add(dir, 1);
			editor.setBlock(rand, cursor, step, true, false);
			cursor.add(Cardinal.reverse(dir), 1);
		}
	}
	
	private static void pillar(IWorldEditor editor, Random rand, Coord base, ITheme theme, int height){
		Coord top = new Coord(base);
		top.add(Cardinal.UP, height);
		editor.fillRectSolid(rand, base, top, theme.getSecondaryPillar(), true, true);
	}	
}
