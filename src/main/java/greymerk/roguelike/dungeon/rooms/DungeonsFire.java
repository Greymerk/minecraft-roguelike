package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class DungeonsFire extends DungeonBase {

	
	
	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();

		IBlockFactory wall = theme.getPrimaryWall();
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory pillar = theme.getPrimaryPillar();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.NORTH, 8);
		start.add(Cardinal.WEST, 8);
		start.add(Cardinal.DOWN);
		end = new Coord(origin);
		end.add(Cardinal.SOUTH, 8);
		end.add(Cardinal.EAST, 8);
		end.add(Cardinal.UP, 7);
		
		editor.fillRectHollow(rand, start, end, wall, false, true);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 8);
		start.add(Cardinal.WEST, 8);
		end.add(Cardinal.SOUTH, 8);
		end.add(Cardinal.EAST, 8);
		editor.fillRectSolid(rand, start, end, theme.getPrimaryFloor(), false, true);
		
		for(Cardinal dir : Cardinal.directions){
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				start = new Coord(origin);
				start.add(dir, 7);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 6);
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				
				cursor = new Coord(origin);
				cursor.add(dir, 8);
				cursor.add(orth);
				cursor.add(Cardinal.UP, 2);
				editor.setBlock(rand, cursor, WorldEditor.blockOrientation(stair, Cardinal.reverse(orth), true), true, false);
				
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				WorldEditor.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(editor, cursor);
				
				start = new Coord(cursor);
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				
				cursor.add(Cardinal.reverse(dir));
				cursor.add(orth);
				WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(editor, cursor);
				
				start = new Coord(cursor);
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				
				cursor.add(dir);
				cursor.add(orth);
				WorldEditor.blockOrientation(stair, orth, true).setBlock(editor, cursor);
				
				start = new Coord(cursor);
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				editor.fillRectSolid(rand, start, end, pillar, true, true);

			}
			
			cursor = new Coord(origin);
			cursor.add(dir, 6);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 6);
			
			genFire(editor, rand, theme, cursor);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir);
			start = new Coord(cursor);
			end = new Coord(cursor);
			end.add(dir, 6);
			editor.fillRectSolid(rand, start, end, wall, true, true);
			cursor.add(Cardinal.getOrthogonal(dir)[0]);
			editor.setBlock(rand, cursor, wall, true, true);
			
			start = new Coord(end);
			end.add(Cardinal.UP, 2);
			end.add(Cardinal.reverse(dir));
			editor.fillRectSolid(rand, start, end, wall, true, true);
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true);
			
			cursor = new Coord(end);
			start = new Coord(cursor);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			editor.fillRectSolid(rand, start, end, wall, true, false);
			
			start = new Coord(cursor);
			start.add(Cardinal.DOWN);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			editor.fillRectSolid(rand, start, end, stair, true, false);
			
			start = new Coord(cursor);
			start.add(Cardinal.reverse(dir));
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			editor.fillRectSolid(rand, start, end, stair, true, false);
		}
		
		
		
		
		return false;
	}
	
	public static void genFire(WorldEditor editor, Random rand, ITheme theme, Coord origin){
		
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock stair = theme.getPrimaryStair();
		
		
		Coord cursor;
		Coord start;
		Coord end;
		
		cursor = new Coord(origin);
		editor.setBlock(cursor, Blocks.netherrack);
		cursor.add(Cardinal.UP);
		editor.setBlock(cursor, Blocks.fire);
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);

			start = new Coord(origin);
			start.add(dir);
			start.add(orth[0]);
			end = new Coord(start);
			end.add(Cardinal.UP, 2);
			editor.fillRectSolid(rand, start, end, pillar, true, false);
			
			cursor = new Coord(origin);
			cursor.add(dir);
			editor.setBlock(rand, cursor, WorldEditor.blockOrientation(stair, dir, false), true, false);
			cursor.add(Cardinal.UP);
			editor.setBlock(cursor, Blocks.iron_bars);
			cursor.add(Cardinal.UP);
			editor.setBlock(rand, cursor, WorldEditor.blockOrientation(stair, dir, true), true, false);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 6);
			cursor.add(dir, 3);
			
			for(Cardinal o : Cardinal.getOrthogonal(dir)){
				Coord c = new Coord(cursor);
				c.add(o, 2);
				editor.setBlock(rand, c, WorldEditor.blockOrientation(stair, dir, true), true, false);
				c.add(o);
				editor.setBlock(rand, c, WorldEditor.blockOrientation(stair, dir, true), true, false);
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP);
			cursor.add(dir, 2);
			
			if(!editor.isAirBlock(cursor)){
				continue;
			}
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			start.add(dir, 2);
			end = new Coord(start);
			start.add(orth[0], 2);
			end.add(orth[1], 2);
			WorldEditor.blockOrientation(stair, dir, true);
			editor.fillRectSolid(rand, start, end, stair, true, false);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 3);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		end = new Coord(origin);
		end.add(Cardinal.UP, 7);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		
		editor.fillRectSolid(rand, start, end, wall, true, false);

	}
	
	
	public int getSize(){
		return 10;
	}

}
