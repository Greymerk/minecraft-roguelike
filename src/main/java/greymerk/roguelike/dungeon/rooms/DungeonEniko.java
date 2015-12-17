package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Collections;
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
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import scala.actors.threadpool.Arrays;


public class DungeonEniko extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getPrimaryStair();
		IBlockFactory walls = theme.getPrimaryWall();
		IBlockFactory floor = theme.getPrimaryFloor();
		Coord start;
		Coord end;
		Coord cursor;
		List<Coord> chests = new ArrayList<Coord>();
				
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(6, -1, 6));
		end.add(new Coord(-6, 4, -6));
		walls.fillRectHollow(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(6, 4, 6));
		end.add(new Coord(-6, 5, -6));
		theme.getSecondaryWall().fillRectSolid(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(3, 4, 3));
		end.add(new Coord(-3, 4, -3));
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, -1, -3));
		end.add(new Coord(3, -1, 3));
		floor.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(dir, 5);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o, 2);
				pillar(editor, rand, theme, c);
				
				c = new Coord(cursor);
				c.add(o, 3);
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, c);
				c.add(o);
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, c);
				c.add(Cardinal.UP);
				chests.add(new Coord(c));
				c.add(Cardinal.reverse(o));
				chests.add(new Coord(c));
			}
			
			cursor.add(orth[0], 5);
			pillar(editor, rand, theme, cursor);
			
			if(Arrays.asList(entrances).contains(dir)){
				start = new Coord(origin);
				start.add(Cardinal.DOWN);
				end = new Coord(start);
				start.add(orth[0]);
				end.add(orth[1]);
				end.add(dir, 6);
				floor.fillRectSolid(editor, rand, start, end, true, true);
			}
		}
		
		Spawner.generate(editor, rand, settings, origin);
		
		Collections.shuffle(chests);
		Treasure.generate(editor, rand, chests.remove(0), settings.getDifficulty(origin), false);
		
		return true;
	}

	
	private static void pillar(WorldEditor editor, Random rand, ITheme theme, Coord origin){
		
		IStair stair = theme.getPrimaryStair();
		IBlockFactory pillar = theme.getPrimaryPillar();
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(start);
		end.add(Cardinal.UP, 3);
		pillar.fillRectSolid(editor, rand, start, end, true, true);
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(end);
			cursor.add(dir);
			stair.setOrientation(dir, true).setBlock(editor, rand, cursor, true, false);
		}
	}
	
	public int getSize(){
		return 7;
	}
	
	@Override
	public boolean validLocation(WorldEditor editor, Cardinal dir, int x, int y, int z){
		
		int size = getSize();
		List<Coord> box = editor.getRectHollow(x - size, y - 2, z - size, x + size, y + 5, z + size);
		
		for(Coord pos : box){
			if(editor.isAirBlock(pos)) return false;
		}
		
		return true;
	}
	
}
