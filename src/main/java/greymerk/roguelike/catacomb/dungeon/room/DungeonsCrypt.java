package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class DungeonsCrypt extends DungeonBase {

	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory floor = theme.getPrimaryFloor();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-3, 0, -3);
		end.add(3, 4, 3);
		air.fillRectSolid(world, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-9, -1, -9);
		end.add(9, -1, 9);
		floor.fillRectSolid(world, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-9, 5, -9);
		end.add(9, 6, 9);
		floor.fillRectSolid(world, rand, start, end, false, true);
		
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
				air.fillRectSolid(world, rand, start, end, true, true);
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
				air.fillRectSolid(world, rand, start, end, true, true);
				
				for(Cardinal o : orth){
					if(doorways.contains(o)){
						
						cursor = new Coord(origin);
						cursor.add(dir, 7);
						cursor.add(o, 3);
						cursor.add(Cardinal.UP);
						
						crypt(world, rand, settings, cursor, o);
					} else {
						
						start = new Coord(origin);
						end = new Coord(origin);
						start.add(dir, 4);
						start.add(o, 3);
						end.add(dir, 8);
						end.add(o, 8);
						end.add(Cardinal.UP, 4);
						air.fillRectSolid(world, rand, start, end, true, true);
						
						cursor = new Coord(origin);
						cursor.add(dir, 6);
						cursor.add(o, 3);
						cursor.add(Cardinal.UP);
						
						sarcophagus(world, rand, settings, cursor, o);
					}
				}
					
			} else {
				cursor = new Coord(origin);
				cursor.add(dir, 4);
				mausoleumWall(world, rand, settings, cursor, dir);
			}
			
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(orth[0], 3);
			pillar(world, rand, settings, cursor);
			
			start = new Coord(origin);
			start.add(dir, 8);
			start.add(Cardinal.UP, 4);
			end = new Coord(start);
			start.add(orth[0], 2);
			end.add(orth[1], 2);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
			stair.fillRectSolid(world, rand, start, end, true, false);
		}
		
		return true;
	}
	
	private void sarcophagus(World world, Random rand, CatacombLevelSettings settings, Coord origin, Cardinal dir){
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory walls = theme.getPrimaryWall();
		MetaBlock stair = theme.getPrimaryStair();
		
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
		walls.fillRectSolid(world, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(dir, 5);
		cursor.add(Cardinal.UP, 3);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
		
		start = new Coord(origin);
		
		for(Cardinal o : Cardinal.getOrthogonal(dir)){
			start = new Coord(origin);
			start.add(Cardinal.DOWN);
			start.add(dir);
			start.add(o, 3);
			end = new Coord(start);
			end.add(dir, 4);
			end.add(Cardinal.UP, 4);
			walls.fillRectSolid(world, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.DOWN);
			cursor.add(dir, 5);
			cursor.add(o, 2);
			pillar(world, rand, settings, cursor);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			start.add(o, 2);
			end = new Coord(start);
			end.add(dir, 3);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true);
			stair.fillRectSolid(world, rand, start, end, true, true);
		}
		
		cursor = new Coord(origin);
		tomb(world, rand, settings, cursor, dir);
		
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
		cursor.add(Cardinal.DOWN, 2);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
		cursor.add(dir);
		walls.setBlock(world, rand, cursor);
		cursor.add(dir);
		walls.setBlock(world, rand, cursor);
		cursor.add(dir);
		WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
		
		for(Cardinal o : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(origin);
			cursor.add(Cardinal.DOWN);
			cursor.add(o);
			start = new Coord(cursor);
			end = new Coord(cursor);
			end.add(dir, 3);
			WorldGenPrimitive.blockOrientation(stair, o, false);
			stair.fillRectSolid(world, rand, start, end, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, o, true);
			stair.fillRectSolid(world, rand, start, end, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, o, false);
			stair.fillRectSolid(world, rand, start, end, true, true);
		}
		
	}
	
	private void crypt(World world, Random rand, CatacombLevelSettings settings, Coord origin, Cardinal dir){
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory walls = theme.getPrimaryWall();
		MetaBlock stair = theme.getPrimaryStair();
		
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
		
		walls.fillRectSolid(world, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP, 2);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		walls.setBlock(world, rand, cursor);
		
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(Cardinal.reverse(dir));
			cursor.add(Cardinal.UP);
			cursor.add(o);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			walls.setBlock(world, rand, cursor);
			cursor.add(Cardinal.UP);
			walls.setBlock(world, rand, cursor);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			start.add(Cardinal.reverse(dir), 2);
			start.add(o, 2);
			end = new Coord(start);
			end.add(dir, 7);
			WorldGenPrimitive.blockOrientation(stair, o, true);
			stair.fillRectSolid(world, rand, start, end, true, true);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 3);
		start.add(Cardinal.reverse(dir), 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
		stair.fillRectSolid(world, rand, start, end, true, true);
		
		tomb(world, rand, settings, origin, dir);
	}
	
	private void mausoleumWall(World world, Random rand, CatacombLevelSettings settings, Coord origin, Cardinal dir){
		
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
		walls.fillRectSolid(world, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		tomb(world, rand, settings, cursor, dir);
		
		cursor.add(Cardinal.UP, 2);
		tomb(world, rand, settings, cursor, dir);
		
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP);
			cursor.add(o, 2);
			tomb(world, rand, settings, cursor, dir);
			
			cursor.add(Cardinal.UP, 2);
			tomb(world, rand, settings, cursor, dir);
		}
		
	}
	
	private void pillar(World world, Random rand, CatacombLevelSettings settings, Coord origin){
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory walls = theme.getPrimaryWall();
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		end.add(Cardinal.UP, 4);
		walls.fillRectSolid(world, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(end);
			cursor.add(dir);
			WorldGenPrimitive.blockOrientation(stair, dir, true);
			stair.setBlock(world, rand, cursor, true, false);
		}
	}
	
	private void tomb(World world, Random rand, CatacombLevelSettings settings, Coord origin, Cardinal dir){
		
		ITheme theme = settings.getTheme();

		MetaBlock stair = theme.getPrimaryStair();
		MetaBlock tombStone = new MetaBlock(Blocks.quartz_block);
		
		
		Coord cursor = new Coord(origin);

		tombStone.setBlock(world, cursor);
		cursor.add(dir);
		Spawner spawnerType = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
		Spawner.generate(world, rand, settings, cursor, spawnerType);
		
		cursor.add(dir);
		TreasureChest[] types = {TreasureChest.ARMOUR, TreasureChest.WEAPONS, TreasureChest.SPECIAL};
		TreasureChest chestType = types[rand.nextInt(types.length)];
		TreasureChest.generate(world, rand, settings.getLoot(), cursor, chestType);
		
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
		
		cursor.add(Cardinal.reverse(dir));
		WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
		
	}
	
	public int getSize(){
		return 10;
	}
}
