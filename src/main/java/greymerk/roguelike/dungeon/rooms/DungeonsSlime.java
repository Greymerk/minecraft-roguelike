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
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Vine;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsSlime extends DungeonBase {

	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getPrimaryStair();
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-8,-1,-8));
		end.add(new Coord(8, 5, 8));
		RectHollow.fill(editor, rand, start, end, wall, false, true);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir, 5);
			cursor.add(Cardinal.left(dir), 5);
			corner(editor, rand, settings, cursor);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 4);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(Cardinal.left(dir), 8);
			end.add(Cardinal.right(dir), 8);
			RectSolid.fill(editor, rand, start, end, wall);
			start.add(dir, 4);
			end.add(dir, 4);
			RectSolid.fill(editor, rand, start, end, wall);
			
		}

			
		
		for(Cardinal dir : Cardinal.directions){
			if(!Arrays.asList(entrances).contains(dir)){
				start = new Coord(origin);
				start.add(dir, 4);
				end = new Coord(start);
				end.add(dir, 2);
				start.add(Cardinal.left(dir), 3);
				end.add(Cardinal.right(dir), 3);
				RectSolid.fill(editor, rand, start, end, air);
				start.add(Cardinal.DOWN);
				end.add(Cardinal.DOWN);
				RectSolid.fill(editor, rand, start, end, water);
				start.add(Cardinal.DOWN);
				end.add(Cardinal.DOWN);
				RectSolid.fill(editor, rand, start, end, wall);
				
				start = new Coord(origin);
				start.add(dir, 3);
				end = new Coord(start);
				start.add(Cardinal.left(dir), 2);
				end.add(Cardinal.right(dir), 2);
				RectSolid.fill(editor, rand, start, end, bars);
				
				cursor = new Coord(origin);
				cursor.add(dir, 7);
				wall.set(editor, rand, cursor);
				cursor.add(Cardinal.UP, 2);
				wall.set(editor, rand, cursor);
				cursor.add(Cardinal.DOWN);
				cursor.add(dir);
				water.set(editor, cursor);
				for(Cardinal o : Cardinal.orthogonal(dir)){
					cursor = new Coord(origin);
					cursor.add(dir, 7);
					cursor.add(o);
					stair.setOrientation(o, true).set(editor, cursor);
					cursor.add(Cardinal.UP);
					wall.set(editor, rand, cursor);
					cursor.add(Cardinal.UP);
					stair.setOrientation(o, false).set(editor, cursor);
					
				}
			}
		}

		
		return false;
	}
	
	private void corner(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-1, -1, -1));
		end.add(new Coord(1, -1, 1));
		RectSolid.fill(editor, rand, start, end, water);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-1, 4, -1));
		end.add(new Coord(1, 4, 1));
		Vine.fill(editor, rand, start, end);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-1, -2, -1));
		end.add(new Coord(1, -2, 1));
		RectSolid.fill(editor, rand, start, end, wall);
		
		for(Cardinal dir : Cardinal.directions){
			start = new Coord(origin);
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, pillar);
			
			for(Cardinal d : Cardinal.directions){
				cursor = new Coord(end);
				cursor.add(d);
				stair.setOrientation(d, true).set(editor, rand, cursor, true, false);
			}
			
			start = new Coord(origin);
			start.add(dir, 2);
			end = new Coord(start);
			start.add(Cardinal.left(dir));
			end.add(Cardinal.right(dir));
			RectSolid.fill(editor, rand, start, end, bars);
			
		}
	}
		
	public int getSize(){
		return 8;
	}
}
