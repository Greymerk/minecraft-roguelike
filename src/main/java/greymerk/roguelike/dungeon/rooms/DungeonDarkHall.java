package greymerk.roguelike.dungeon.rooms;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class DungeonDarkHall extends DungeonBase{

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory outerWall = theme.getPrimaryWall();
		IBlockFactory wall = theme.getSecondaryWall();
		IBlockFactory pillar = theme.getSecondaryPillar();
		IStair stair = theme.getSecondaryStair();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.NORTH, 7);
		start.add(Cardinal.WEST, 7);
		end.add(Cardinal.SOUTH, 7);
		end.add(Cardinal.EAST, 7);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 7);
		
		editor.fillRectHollow(rand, start, end, outerWall, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);

		start.add(Cardinal.NORTH, 4);
		start.add(Cardinal.WEST, 4);
		end.add(Cardinal.SOUTH, 4);
		end.add(Cardinal.EAST, 4);
		start.add(Cardinal.UP, 6);
		end.add(Cardinal.UP, 9);

		editor.fillRectHollow(rand, start, end, outerWall, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.NORTH, 6);
		start.add(Cardinal.WEST, 6);
		end.add(Cardinal.SOUTH, 6);
		end.add(Cardinal.EAST, 6);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		
		editor.fillRectSolid(rand, start, end, theme.getPrimaryFloor(), false, true);
		
		for (Cardinal dir : entrances){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(orth[0]);
			end = new Coord(origin);
			end.add(orth[1]);
			end.add(dir, 7);
			editor.fillRectSolid(rand, start, end, theme.getSecondaryFloor(), false, true);
		}
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(origin);
			start.add(dir, 6);
			start.add(orth[0], 6);
			end = new Coord(start);
			end.add(Cardinal.UP, 5);
			editor.fillRectSolid(rand, start, end, pillar, true, true);
			
			start = new Coord(origin);
			start.add(dir, 6);
			start.add(Cardinal.UP, 6);
			end = new Coord(start);
			start.add(orth[0], 6);
			end.add(orth[1], 6);
			editor.fillRectSolid(rand, start, end, wall, true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(Cardinal.UP, 6);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			editor.fillRectSolid(rand, start, end, wall, true, true);
			start.add(Cardinal.UP, 2);
			end.add(Cardinal.UP, 2);
			editor.fillRectSolid(rand, start, end, wall, true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(Cardinal.UP, 7);
			pillar.setBlock(editor, rand, start);
			start.add(Cardinal.UP);
			end = new Coord(start);
			end.add(Cardinal.reverse(dir), 3);
			editor.fillRectSolid(rand, start, end, wall, true, true);
			
			if(Arrays.asList(entrances).contains(dir)){
				start = new Coord(origin);
				start.add(dir, 7);
				start.add(Cardinal.UP, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				start.add(orth[0], 2);
				end.add(orth[1], 2);
				editor.fillRectSolid(rand, start, end, wall, true, true);
				
				cursor = new Coord(origin);
				cursor.add(dir, 7);
				cursor.add(Cardinal.UP, 2);
				air.setBlock(editor, cursor);
				
				for (Cardinal o : orth){
					cursor = new Coord(origin);
					cursor.add(dir, 7);
					cursor.add(Cardinal.UP, 2);
					cursor.add(o);
					stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
					
					cursor = new Coord(origin);
					cursor.add(dir, 6);
					cursor.add(o, 3);
					pillar(editor, rand, settings, Cardinal.reverse(o), cursor);
					
					cursor = new Coord(origin);
					cursor.add(dir, 7);
					cursor.add(o, 2);
					pillar.setBlock(editor, rand, cursor);
					cursor.add(Cardinal.UP);
					pillar.setBlock(editor, rand, cursor);
				}
			} else {
				cursor = new Coord(origin);
				cursor.add(dir, 6);
				pillar(editor, rand, settings, Cardinal.reverse(dir), cursor);
			}
			
			start = new Coord(origin);
			start.add(dir, 6);
			start.add(Cardinal.UP, 6);
			end = new Coord(start);
			end.add(Cardinal.reverse(dir), 2);
			editor.fillRectSolid(rand, start, end, wall, true, true);
			
			for (Cardinal o : orth){
				cursor = new Coord(origin);
				cursor.add(dir, 6);
				cursor.add(o, 3);
				pillar(editor, rand, settings, Cardinal.reverse(dir), cursor);
				start = new Coord(cursor);
				start.add(Cardinal.UP, 6);
				end = new Coord(start);
				end.add(Cardinal.reverse(dir), 6);
				editor.fillRectSolid(rand, start, end, wall, true, true);
			}
		}
		
		return false;
	}

	private void pillar(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();

		IBlockFactory wall = theme.getSecondaryWall();
		IBlockFactory pillar = theme.getSecondaryPillar();
		IStair stair = theme.getSecondaryStair();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(start);
		end.add(Cardinal.UP, 5);
		editor.fillRectSolid(rand, start, end, pillar, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 3);
		cursor.add(dir);
		stair.setOrientation(dir, true).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
		cursor.add(dir);
		stair.setOrientation(dir, true).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
		cursor.add(dir);
		if(editor.isAirBlock(cursor)){
			stair.setOrientation(dir, true).setBlock(editor, cursor);	
		} else {
			wall.setBlock(editor, rand, cursor);
		}
		
	}
	
	@Override
	public int getSize() {
		return 9;
	}

	
	
}
