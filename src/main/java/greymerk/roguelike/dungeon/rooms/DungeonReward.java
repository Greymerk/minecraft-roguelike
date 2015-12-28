package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonReward extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		editor.fillRectSolid(rand, x - 7, y, z - 7, x + 7, y + 5, z + 7, BlockType.get(BlockType.AIR), true, true);
		editor.fillRectHollow(rand, x - 8, y - 1, z - 8, x + 8, y + 6, z + 8, theme.getPrimaryWall(), false, true);
		editor.fillRectSolid(rand, x - 1, y + 4, z - 1, x + 1, y + 5, z + 1, theme.getPrimaryWall());
		
		
		Coord cursor;
		Coord start;
		Coord end;
		
		IStair stair = theme.getPrimaryStair();
		
		for(Cardinal dir : Cardinal.directions){
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(orth, 2);
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.UP, 5);
				editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
				cursor.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
				cursor.add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.UP, 2);
				editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
				cursor.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.UP);
				editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
				cursor.add(Cardinal.UP);
				cursor.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(Cardinal.UP, 2);
				end.add(orth);
				editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
				start.add(Cardinal.reverse(dir));
				start.add(Cardinal.UP);
				end.add(Cardinal.reverse(dir));
				editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
				start.add(Cardinal.reverse(dir));
				start.add(Cardinal.UP);
				end.add(Cardinal.reverse(dir));
				editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 8);
				cursor.add(Cardinal.UP, 2);
				cursor.add(orth);
				editor.setBlock(rand, cursor, stair.setOrientation(Cardinal.reverse(orth), true), true, false);
				cursor.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(orth), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				stair.setOrientation(Cardinal.reverse(orth), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				stair.setOrientation(Cardinal.reverse(orth), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				stair.setOrientation(Cardinal.reverse(orth), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(dir), 2);
				stair.setOrientation(dir, true).setBlock(editor, cursor);
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(orth, 3);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(Cardinal.UP, 2);
				end.add(orth, 2);
				editor.fillRectSolid(rand, start, end, theme.getPrimaryPillar(), true, true);
				
				start.add(Cardinal.reverse(dir));
				start.add(Cardinal.UP);
				end.add(Cardinal.reverse(dir));
				editor.fillRectSolid(rand, start, end, theme.getPrimaryPillar(), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(orth, 3);
				stair.setOrientation(orth, false).setBlock(editor, cursor);
				cursor.add(orth, 2);
				stair.setOrientation(Cardinal.reverse(orth), false).setBlock(editor, cursor);
				cursor.add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(orth), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(orth), 2);
				stair.setOrientation(orth, true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				stair.setOrientation(orth, true).setBlock(editor, cursor);
				cursor.add(orth, 2);
				stair.setOrientation(Cardinal.reverse(orth), true).setBlock(editor, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				end = new Coord(cursor);
				end.add(Cardinal.reverse(orth), 2);
				editor.fillRectSolid(rand, cursor, end, stair.setOrientation(Cardinal.reverse(dir), true), true, true);
				cursor.add(Cardinal.UP);
				end.add(Cardinal.UP);
				editor.fillRectSolid(rand, cursor, end, theme.getPrimaryWall(), true, true);
				end.add(Cardinal.reverse(dir));
				stair.setOrientation(orth, true).setBlock(editor, cursor);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(orth, 4);
				cursor.add(Cardinal.DOWN);
				editor.setBlock(rand, cursor, BlockType.get(BlockType.GLOWSTONE), true, true);
				
			}
			
			Cardinal o = Cardinal.getOrthogonal(dir)[0];
			
			start = new Coord(x, y, z);
			start.add(dir, 6);
			start.add(o, 6);
			end = new Coord(start);
			end.add(dir);
			end.add(o);
			end.add(Cardinal.UP, 5);
			editor.fillRectSolid(rand, start, end, theme.getPrimaryPillar(), true, true);
			
			cursor = new Coord(x, y, z);
			editor.setBlock(rand, cursor, theme.getPrimaryWall(), true, true);
			cursor.add(dir);
			stair.setOrientation(dir, false).setBlock(editor, cursor);
			cursor.add(o);
			stair.setOrientation(dir, false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP, 4);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(o));
			stair.setOrientation(dir, true).setBlock(editor, cursor);
			
		}
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 4);
		editor.setBlock(rand, cursor, BlockType.get(BlockType.GLOWSTONE), true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP);
		Treasure.generate(editor, rand, cursor, Treasure.REWARD, Dungeon.getLevel(cursor.getY()));
		
		return true;
	}

	@Override
	public int getSize() {
		return 10;
	}

	@Override
	public boolean validLocation(IWorldEditor editor, Cardinal dir, int x, int y, int z) {
		return false;
	}

}
