package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class DungeonsPrison extends DungeonBase {

	public DungeonsPrison(){}
	
	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
				
		Coord cursor;
		
		this.largeRoom(editor, rand, settings, origin);
	
		for(Cardinal dir : Arrays.asList(entrances)){
			cursor = new Coord(origin);
			cursor.add(dir, 6);
			this.sideRoom(editor, rand, settings, cursor, dir);
		}
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 3);
			pillar(editor, rand, settings, cursor, 4);
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal side = Cardinal.getOrthogonal(dir)[0];
			List<Cardinal> doors = new ArrayList<Cardinal>();
			
			if(Arrays.asList(entrances).contains(dir)){
				doors.add(Cardinal.reverse(side));
			}
			
			if(Arrays.asList(entrances).contains(side)){
				doors.add(Cardinal.reverse(dir));
			}
			
			if(doors.isEmpty()) continue;
			
			cursor = new Coord(origin);
			cursor.add(dir, 6);
			cursor.add(side, 6);
			
			cell(editor, rand, settings, cursor, doors);
		}
		
		return true;
	}
	
	public void largeRoom(WorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		Coord start;
		Coord end;
		Coord cursor;
		Cardinal[] orth;
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = settings.getTheme().getPrimaryStair();
		
		IBlockFactory wall = settings.getTheme().getPrimaryWall();
		start = new Coord(origin);
		start.add(Cardinal.UP, 6);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.EAST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.WEST, 3);
		wall.fillRectSolid(editor, rand, start, end, false, true);
		
		IBlockFactory floor = settings.getTheme().getPrimaryFloor();
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.EAST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.WEST, 3);
		floor.fillRectSolid(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.WEST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.EAST, 3);
		end.add(Cardinal.UP, 4);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.NORTH, 4);
		start.add(Cardinal.WEST, 4);
		end.add(Cardinal.SOUTH, 4);
		end.add(Cardinal.EAST, 4);
		end.add(Cardinal.UP, 5);
		settings.getTheme().getPrimaryWall().fillRectHollow(editor, rand, start, end, false, true);

		for(Cardinal dir : Cardinal.directions){
			orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(orth[0], 3);
			pillar(editor, rand, settings, cursor, 4);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 5);
		end = new Coord(start);
		start.add(Cardinal.NORTH);
		start.add(Cardinal.EAST);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.WEST);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		
		for(Cardinal dir : Cardinal.directions){
			orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 5);
			cursor.add(dir, 2);
			air.setBlock(editor, cursor);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				WorldEditor.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(editor, c);
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 6);
			air.setBlock(editor, cursor);
			cursor.add(dir, 1);
			WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(editor, cursor);
		}			
	}
	
	private void sideRoom(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal roomDir){
		
		Coord start;
		Coord end;
		Coord cursor;
		Cardinal[] orth;
		orth = Cardinal.getOrthogonal(roomDir);
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = settings.getTheme().getPrimaryStair();
		int height = 3;
		
		IBlockFactory wall = settings.getTheme().getPrimaryWall();
		start = new Coord(origin);
		start.add(Cardinal.UP, 6);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.EAST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.WEST, 3);
		wall.fillRectSolid(editor, rand, start, end, false, true);
		
		IBlockFactory floor = settings.getTheme().getPrimaryFloor();
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.EAST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.WEST, 3);
		floor.fillRectSolid(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.WEST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.EAST, 3);
		end.add(Cardinal.UP, height);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.NORTH, 4);
		start.add(Cardinal.WEST, 4);
		end.add(Cardinal.SOUTH, 4);
		end.add(Cardinal.EAST, 4);
		end.add(Cardinal.UP, height + 1);
		settings.getTheme().getPrimaryWall().fillRectHollow(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(roomDir);
		end.add(Cardinal.reverse(roomDir), 3);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.getOrthogonal(roomDir)){
			orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(roomDir, 3);
			pillar(editor, rand, settings, cursor, height);
		}
		
		orth = Cardinal.getOrthogonal(roomDir);
	
		start = new Coord(origin);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		start.add(Cardinal.reverse(roomDir), 3);
		end.add(roomDir, 2);
		
		for(Cardinal dir : orth){
			Cardinal[] o = Cardinal.getOrthogonal(dir);				
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			start = new Coord(cursor);
			start.add(dir, 2);
			end = new Coord(start);
			start.add(o[0], 3);
			end.add(o[1], 3);
			WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
			
			cursor.add(Cardinal.UP, 1);
			start = new Coord(cursor);
			start.add(dir);
			end = new Coord(start);
			start.add(o[0], 3);
			end.add(o[1], 3);
			WorldEditor.blockOrientation(stair, Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
		}
		
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		start = new Coord(cursor);
		start.add(roomDir, 2);
		end = new Coord(start);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		WorldEditor.blockOrientation(stair, Cardinal.reverse(roomDir), true).fillRectSolid(editor, rand, start, end, true, true);
		
		cursor.add(Cardinal.UP, 1);
		air.setBlock(editor, cursor);
		start = new Coord(cursor);
		start.add(roomDir, 1);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		WorldEditor.blockOrientation(stair, Cardinal.reverse(roomDir), true).fillRectSolid(editor, rand, start, end, true, true);
	}
	
	private void pillar(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, int height){
		Coord cursor;
		IBlockFactory pillar = settings.getTheme().getPrimaryPillar();
		MetaBlock stair = settings.getTheme().getPrimaryStair();
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, height - 1);
		editor.fillDown(rand, new Coord(cursor), pillar);
		cursor.add(Cardinal.UP);
		pillar.setBlock(editor, rand, cursor);
		for(Cardinal dir : Cardinal.directions){
			cursor.add(dir);
			WorldEditor.blockOrientation(stair, dir, true).setBlock(editor, rand, cursor, true, false);
		}
	}
	
	private void cell(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Cardinal> entrances){
		
		Coord start;
		Coord end;
		Coord cursor;
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		if(editor.isAirBlock(cursor)) return;
		
		IBlockFactory wall = settings.getTheme().getPrimaryWall();
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock bar = new MetaBlock(Blocks.iron_bars);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.DOWN);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		end.add(Cardinal.UP, 4);
		wall.fillRectHollow(editor, rand, start, end, false, true);
		
		IBlockFactory floor = settings.getTheme().getPrimaryFloor();
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 1);
		start.add(Cardinal.EAST, 1);
		end.add(Cardinal.SOUTH, 1);
		end.add(Cardinal.WEST, 1);
		floor.fillRectSolid(editor, rand, start, end, false, true);
		
		for(Cardinal dir : entrances){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			start = new Coord(cursor);
			end = new Coord(cursor);
			start.add(orth[0]);
			end.add(orth[1]);
			end.add(Cardinal.UP, 2);
			bar.fillRectSolid(editor, rand, start, end, true, true);
			
			air.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			air.setBlock(editor, cursor);
		}
		
		// ceiling holes
		start = new Coord(origin);
		end = new Coord(origin);
		end.add(Cardinal.UP, 10);
		air.fillRectSolid(editor, rand, start, end, true, true);
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(dir);
			start.add(orth[0]);
			end = new Coord(start);
			end.add(Cardinal.UP, 10);
			air.fillRectSolid(editor, rand, start, end, true, true);
		}
	}
	
	public int getSize(){
		return 12;
	}
}
