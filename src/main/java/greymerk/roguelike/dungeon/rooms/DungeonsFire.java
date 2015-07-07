package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsFire extends DungeonBase {

	
	
	@Override
	public boolean generate(World world, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
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
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, wall, false, true);
		
		for(Cardinal dir : Cardinal.directions){
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				start = new Coord(origin);
				start.add(dir, 7);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 6);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				
				cursor = new Coord(origin);
				cursor.add(dir, 8);
				cursor.add(orth);
				cursor.add(Cardinal.UP, 2);
				WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true), true, false);
				
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				
				start = new Coord(cursor);
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				
				cursor.add(Cardinal.reverse(dir));
				cursor.add(orth);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				
				start = new Coord(cursor);
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				
				cursor.add(dir);
				cursor.add(orth);
				WorldGenPrimitive.blockOrientation(stair, orth, true).setBlock(world, cursor);
				
				start = new Coord(cursor);
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);

			}
			
			cursor = new Coord(origin);
			cursor.add(dir, 6);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 6);
			
			genFire(world, rand, theme, cursor);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir);
			start = new Coord(cursor);
			end = new Coord(cursor);
			end.add(dir, 6);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			cursor.add(Cardinal.getOrthogonal(dir)[0]);
			WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
			
			start = new Coord(end);
			end.add(Cardinal.UP, 2);
			end.add(Cardinal.reverse(dir));
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
			
			cursor = new Coord(end);
			start = new Coord(cursor);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, false);
			
			start = new Coord(cursor);
			start.add(Cardinal.DOWN);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, false);
			
			start = new Coord(cursor);
			start.add(Cardinal.reverse(dir));
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, false);
		}
		
		
		
		
		return false;
	}
	
	public static void genFire(World world, Random rand, ITheme theme, Coord origin){
		
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock stair = theme.getPrimaryStair();
		
		
		Coord cursor;
		Coord start;
		Coord end;
		
		cursor = new Coord(origin);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.netherrack);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.fire);
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);

			start = new Coord(origin);
			start.add(dir);
			start.add(orth[0]);
			end = new Coord(start);
			end.add(Cardinal.UP, 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, false);
			
			cursor = new Coord(origin);
			cursor.add(dir);
			WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, dir, false), true, false);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.setBlock(world, cursor, Blocks.iron_bars);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, dir, true), true, false);
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 6);
			cursor.add(dir, 3);
			
			for(Cardinal o : Cardinal.getOrthogonal(dir)){
				Coord c = new Coord(cursor);
				c.add(o, 2);
				WorldGenPrimitive.setBlock(world, rand, c, WorldGenPrimitive.blockOrientation(stair, dir, true), true, false);
				c.add(o);
				WorldGenPrimitive.setBlock(world, rand, c, WorldGenPrimitive.blockOrientation(stair, dir, true), true, false);
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP);
			cursor.add(dir, 2);
			
			if(!world.isAirBlock(cursor.getBlockPos())){
				continue;
			}
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			start.add(dir, 2);
			end = new Coord(start);
			start.add(orth[0], 2);
			end.add(orth[1], 2);
			WorldGenPrimitive.blockOrientation(stair, dir, true);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, false);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 3);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		end = new Coord(origin);
		end.add(Cardinal.UP, 7);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, false);

	}
	
	
	public int getSize(){
		return 10;
	}

}
