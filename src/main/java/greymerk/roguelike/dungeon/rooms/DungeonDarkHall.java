package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonDarkHall extends DungeonBase{

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory outerWall = theme.getPrimaryWall();
		IBlockFactory wall = theme.getSecondaryWall();
		IBlockFactory pillar = theme.getSecondaryPillar();
		MetaBlock stair = theme.getSecondaryStair();
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
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, outerWall, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);

		start.add(Cardinal.NORTH, 4);
		start.add(Cardinal.WEST, 4);
		end.add(Cardinal.SOUTH, 4);
		end.add(Cardinal.EAST, 4);
		start.add(Cardinal.UP, 6);
		end.add(Cardinal.UP, 9);

		WorldGenPrimitive.fillRectHollow(world, rand, start, end, outerWall, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.NORTH, 6);
		start.add(Cardinal.WEST, 6);
		end.add(Cardinal.SOUTH, 6);
		end.add(Cardinal.EAST, 6);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryFloor(), false, true);
		
		for (Cardinal dir : entrances){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(orth[0]);
			end = new Coord(origin);
			end.add(orth[1]);
			end.add(dir, 7);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryFloor(), false, true);
		}
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(origin);
			start.add(dir, 6);
			start.add(orth[0], 6);
			end = new Coord(start);
			end.add(Cardinal.UP, 5);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
			
			start = new Coord(origin);
			start.add(dir, 6);
			start.add(Cardinal.UP, 6);
			end = new Coord(start);
			start.add(orth[0], 6);
			end.add(orth[1], 6);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(Cardinal.UP, 6);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			start.add(Cardinal.UP, 2);
			end.add(Cardinal.UP, 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(Cardinal.UP, 7);
			pillar.setBlock(world, rand, start);
			start.add(Cardinal.UP);
			end = new Coord(start);
			end.add(Cardinal.reverse(dir), 3);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			
			if(Arrays.asList(entrances).contains(dir)){
				start = new Coord(origin);
				start.add(dir, 7);
				start.add(Cardinal.UP, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				start.add(orth[0], 2);
				end.add(orth[1], 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
				
				cursor = new Coord(origin);
				cursor.add(dir, 7);
				cursor.add(Cardinal.UP, 2);
				air.setBlock(world, cursor);
				
				for (Cardinal o : orth){
					cursor = new Coord(origin);
					cursor.add(dir, 7);
					cursor.add(Cardinal.UP, 2);
					cursor.add(o);
					WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
					
					cursor = new Coord(origin);
					cursor.add(dir, 6);
					cursor.add(o, 3);
					pillar(world, rand, settings, Cardinal.reverse(o), cursor);
					
					cursor = new Coord(origin);
					cursor.add(dir, 7);
					cursor.add(o, 2);
					pillar.setBlock(world, rand, cursor);
					cursor.add(Cardinal.UP);
					pillar.setBlock(world, rand, cursor);
				}
			} else {
				cursor = new Coord(origin);
				cursor.add(dir, 6);
				pillar(world, rand, settings, Cardinal.reverse(dir), cursor);
			}
			
			start = new Coord(origin);
			start.add(dir, 6);
			start.add(Cardinal.UP, 6);
			end = new Coord(start);
			end.add(Cardinal.reverse(dir), 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			
			for (Cardinal o : orth){
				cursor = new Coord(origin);
				cursor.add(dir, 6);
				cursor.add(o, 3);
				pillar(world, rand, settings, Cardinal.reverse(dir), cursor);
				start = new Coord(cursor);
				start.add(Cardinal.UP, 6);
				end = new Coord(start);
				end.add(Cardinal.reverse(dir), 6);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			}
		}
		
		return false;
	}

	private void pillar(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();

		IBlockFactory wall = theme.getSecondaryWall();
		IBlockFactory pillar = theme.getSecondaryPillar();
		MetaBlock stair = theme.getSecondaryStair();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(start);
		end.add(Cardinal.UP, 5);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 3);
		cursor.add(dir);
		WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
		cursor.add(dir);
		WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
		cursor.add(dir);
		if(world.isAirBlock(cursor.getBlockPos())){
			WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);	
		} else {
			wall.setBlock(world, rand, cursor);
		}
		
	}
	
	@Override
	public int getSize() {
		return 9;
	}

	
	
}
