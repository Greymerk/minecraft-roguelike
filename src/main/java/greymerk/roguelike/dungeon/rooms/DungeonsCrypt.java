package greymerk.roguelike.dungeon.rooms;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
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

public class DungeonsCrypt extends DungeonBase {

	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getPrimaryStair();
		IBlockFactory walls = theme.getPrimaryWall();
		IBlockFactory floor = theme.getPrimaryFloor();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-3, 0, -3);
		end.add(3, 4, 3);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-9, -1, -9);
		end.add(9, -1, 9);
		floor.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-9, 5, -9);
		end.add(9, 6, 9);
		walls.fillRectSolid(editor, rand, start, end, false, true);
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			List<?> doorways = Arrays.asList(entrances);
			
			if(doorways.contains(dir) && doorways.contains(orth[0])){
				start = new Coord(origin);
				end = new Coord(origin);
				start.add(dir, 3);
				end.add(orth[0], 5);
				end.add(dir, 5);
				end.add(Cardinal.UP, 4);
				air.fillRectSolid(editor, rand, start, end, true, true);
			}
			
			if(doorways.contains(dir)){
				// doorway air
				start = new Coord(origin);
				end = new Coord(origin);
				start.add(dir, 3);
				start.add(orth[0], 2);
				end.add(dir, 8);
				end.add(orth[1], 2);
				end.add(Cardinal.UP, 4);
				air.fillRectSolid(editor, rand, start, end, true, true);
				
				for(Cardinal o : orth){
					if(doorways.contains(o)){
						
						cursor = new Coord(origin);
						cursor.add(dir, 7);
						cursor.add(o, 3);
						cursor.add(Cardinal.UP);
						
						crypt(editor, rand, settings, cursor, o);
					} else {
						
						start = new Coord(origin);
						end = new Coord(origin);
						start.add(dir, 4);
						start.add(o, 3);
						end.add(dir, 8);
						end.add(o, 8);
						end.add(Cardinal.UP, 4);
						air.fillRectSolid(editor, rand, start, end, true, true);
						
						cursor = new Coord(origin);
						cursor.add(dir, 6);
						cursor.add(o, 3);
						cursor.add(Cardinal.UP);
						
						sarcophagus(editor, rand, settings, cursor, o);
					}
				}
					
			} else {
				cursor = new Coord(origin);
				cursor.add(dir, 4);
				mausoleumWall(editor, rand, settings, cursor, dir);
			}
			
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(orth[0], 3);
			pillar(editor, rand, settings, cursor);
			
			start = new Coord(origin);
			start.add(dir, 8);
			start.add(Cardinal.UP, 4);
			end = new Coord(start);
			start.add(orth[0], 2);
			end.add(orth[1], 2);
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.fillRectSolid(editor, rand, start, end, true, false);
		}
		
		return true;
	}
	
	private void sarcophagus(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir){
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory walls = theme.getPrimaryWall();
		IStair stair = theme.getPrimaryStair();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		start.add(dir, 5);
		end = new Coord(start);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(dir, 5);
		cursor.add(Cardinal.UP, 3);
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		
		start = new Coord(origin);
		
		for(Cardinal o : Cardinal.getOrthogonal(dir)){
			start = new Coord(origin);
			start.add(Cardinal.DOWN);
			start.add(dir);
			start.add(o, 3);
			end = new Coord(start);
			end.add(dir, 4);
			end.add(Cardinal.UP, 4);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.DOWN);
			cursor.add(dir, 5);
			cursor.add(o, 2);
			pillar(editor, rand, settings, cursor);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			start.add(o, 2);
			end = new Coord(start);
			end.add(dir, 3);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.fillRectSolid(editor, rand, start, end, true, true);
		}
		
		cursor = new Coord(origin);
		tomb(editor, rand, settings, cursor, dir);
		
		cursor.add(Cardinal.UP);
		stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
		cursor.add(Cardinal.DOWN, 2);
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		cursor.add(dir);
		walls.setBlock(editor, rand, cursor);
		cursor.add(dir);
		walls.setBlock(editor, rand, cursor);
		cursor.add(dir);
		stair.setOrientation(dir, false).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		stair.setOrientation(dir, true).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		stair.setOrientation(dir, false).setBlock(editor, cursor);
		
		for(Cardinal o : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(origin);
			cursor.add(Cardinal.DOWN);
			cursor.add(o);
			start = new Coord(cursor);
			end = new Coord(cursor);
			end.add(dir, 3);
			stair.setOrientation(o, false);
			stair.fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			stair.setOrientation(o, true);
			stair.fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			stair.setOrientation(o, false);
			stair.fillRectSolid(editor, rand, start, end, true, true);
		}
		
	}
	
	private void crypt(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir){
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory walls = theme.getPrimaryWall();
		IStair stair = theme.getPrimaryStair();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		start.add(orth[0]);
		end = new Coord(origin);
		end.add(Cardinal.UP, 3);
		end.add(orth[1]);
		end.add(dir, 3);
		
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP, 2);
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		walls.setBlock(editor, rand, cursor);
		
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(Cardinal.reverse(dir));
			cursor.add(Cardinal.UP);
			cursor.add(o);
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			walls.setBlock(editor, rand, cursor);
			cursor.add(Cardinal.UP);
			walls.setBlock(editor, rand, cursor);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			start.add(Cardinal.reverse(dir), 2);
			start.add(o, 2);
			end = new Coord(start);
			end.add(dir, 7);
			stair.setOrientation(o, true);
			stair.fillRectSolid(editor, rand, start, end, true, false);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 3);
		start.add(Cardinal.reverse(dir), 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		stair.setOrientation(Cardinal.reverse(dir), true);
		stair.fillRectSolid(editor, rand, start, end, true, true);
		
		tomb(editor, rand, settings, origin, dir);
	}
	
	private void mausoleumWall(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir){
		
		ITheme theme = settings.getTheme();
		IBlockFactory walls = theme.getPrimaryWall();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0], 3);
		end.add(orth[1], 3);
		end.add(dir, 4);
		end.add(Cardinal.UP, 4);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		tomb(editor, rand, settings, cursor, dir);
		
		cursor.add(Cardinal.UP, 2);
		tomb(editor, rand, settings, cursor, dir);
		
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP);
			cursor.add(o, 2);
			tomb(editor, rand, settings, cursor, dir);
			
			cursor.add(Cardinal.UP, 2);
			tomb(editor, rand, settings, cursor, dir);
		}
		
	}
	
	private void pillar(WorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory walls = theme.getPrimaryWall();
		IStair stair = theme.getPrimaryStair();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		end.add(Cardinal.UP, 4);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(end);
			cursor.add(dir);
			stair.setOrientation(dir, true);
			stair.setBlock(editor, rand, cursor, true, false);
		}
	}
	
	private void tomb(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir){
		
		ITheme theme = settings.getTheme();
		Coord cursor;
		
		IStair stair = theme.getPrimaryStair();
		MetaBlock tombStone = BlockType.get(BlockType.QUARTZ);
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		cursor.add(Cardinal.UP);
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(dir, true).setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		air.fillRectSolid(editor, rand, origin, cursor, true, true);
		
		if(rand.nextInt(3) == 0) return;

		cursor = new Coord(origin);
		tombStone.setBlock(editor, cursor);
		
		if(rand.nextInt(4) != 0) return;
		
		cursor.add(dir);
		Spawner spawnerType = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
		Spawner.generate(editor, rand, settings, cursor, spawnerType);
		
		cursor.add(dir);
		Treasure[] types = {Treasure.ARMOUR, Treasure.WEAPONS};
		Treasure chestType = types[rand.nextInt(types.length)];
		Treasure.generate(editor, rand, cursor, chestType, Dungeon.getLevel(cursor.getY()), false);
		
	}
	
	public int getSize(){
		return 10;
	}
}
