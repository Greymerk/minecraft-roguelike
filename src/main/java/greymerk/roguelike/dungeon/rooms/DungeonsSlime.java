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

public class DungeonsSlime extends DungeonBase {

	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
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
		wall.fillRectHollow(editor, rand, start, end, false, true);
		
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
			wall.fillRectSolid(editor, rand, start, end, true, true);
			start.add(dir, 4);
			end.add(dir, 4);
			wall.fillRectSolid(editor, rand, start, end, true, true);
			
		}

			
		
		for(Cardinal dir : Cardinal.directions){
			if(!Arrays.asList(entrances).contains(dir)){
				start = new Coord(origin);
				start.add(dir, 4);
				end = new Coord(start);
				end.add(dir, 2);
				start.add(Cardinal.left(dir), 3);
				end.add(Cardinal.right(dir), 3);
				air.fillRectSolid(editor, rand, start, end, true, true);
				start.add(Cardinal.DOWN);
				end.add(Cardinal.DOWN);
				water.fillRectSolid(editor, rand, start, end, true, true);
				start.add(Cardinal.DOWN);
				end.add(Cardinal.DOWN);
				wall.fillRectSolid(editor, rand, start, end, true, true);
				
				start = new Coord(origin);
				start.add(dir, 3);
				end = new Coord(start);
				start.add(Cardinal.left(dir), 2);
				end.add(Cardinal.right(dir), 2);
				bars.fillRectSolid(editor, rand, start, end, true, true);
				
				cursor = new Coord(origin);
				cursor.add(dir, 7);
				wall.setBlock(editor, rand, cursor);
				cursor.add(Cardinal.UP, 2);
				wall.setBlock(editor, rand, cursor);
				cursor.add(Cardinal.DOWN);
				cursor.add(dir);
				water.setBlock(editor, cursor);
				for(Cardinal o : Cardinal.orthogonal(dir)){
					cursor = new Coord(origin);
					cursor.add(dir, 7);
					cursor.add(o);
					stair.setOrientation(o, true).setBlock(editor, cursor);
					cursor.add(Cardinal.UP);
					wall.setBlock(editor, rand, cursor);
					cursor.add(Cardinal.UP);
					stair.setOrientation(o, false).setBlock(editor, cursor);
					
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
		water.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-1, 4, -1));
		end.add(new Coord(1, 4, 1));
		Vine.fill(editor, rand, start, end);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-1, -2, -1));
		end.add(new Coord(1, -2, 1));
		wall.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			start = new Coord(origin);
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
			
			for(Cardinal d : Cardinal.directions){
				cursor = new Coord(end);
				cursor.add(d);
				stair.setOrientation(d, true).setBlock(editor, rand, cursor, true, false);
			}
			
			start = new Coord(origin);
			start.add(dir, 2);
			end = new Coord(start);
			start.add(Cardinal.left(dir));
			end.add(Cardinal.right(dir));
			bars.fillRectSolid(editor, rand, start, end, true, true);
			
		}
	}
		
	public int getSize(){
		return 8;
	}
}
