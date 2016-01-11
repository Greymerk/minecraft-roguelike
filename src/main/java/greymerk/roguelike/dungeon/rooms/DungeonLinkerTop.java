package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonLinkerTop extends DungeonBase{

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory pillar = theme.getPrimaryPillar();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory floor = theme.getPrimaryFloor();
		IStair stair = theme.getPrimaryStair();
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, 5, 4);
		wall.fillRectHollow(editor, rand, start, end, false, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 5);
		editor.setBlock(cursor, BlockType.get(BlockType.GLOWSTONE));
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, -1, 4);
		floor.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.orthogonal(dir);
			
			start = new Coord(origin);
			end = new Coord(origin);
			start.add(dir, 3);
			start.add(orth[0], 3);
			end.add(dir, 4);
			end.add(orth[0], 4);
			end.add(Cardinal.UP, 4);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(orth[0], 2);
			start.add(Cardinal.UP, 4);
			end = new Coord(start);
			end.add(orth[1], 4);
			wall.fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.reverse(dir));
			end.add(Cardinal.reverse(dir));
			editor.fillRectSolid(rand, start, end, stair.setOrientation(Cardinal.reverse(dir), true), true, true);
			
			for(Cardinal o : orth){
				cursor = new Coord(origin);
				cursor.add(dir, 3);
				cursor.add(Cardinal.UP, 2);
				cursor.add(o, 2);
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				wall.setBlock(editor, rand, cursor);
				cursor.add(Cardinal.reverse(o));
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
			}
		}
		

		
		
		return true;
	}

	@Override
	public int getSize() {
		return 6;
	}

}
