package greymerk.roguelike.catacomb.tower;

import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EthoTower implements ITower {

	@Override
	public void generate(World world, Random rand, ITheme theme, int x, int y, int z) {
		
		IBlockFactory primary = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getSecondaryPillar();
		
		MetaBlock stair = theme.getSecondaryStair();
		
		Coord floor = Tower.getBaseCoord(world, x, y, z);
		
		Coord start = new Coord(floor);
		Coord end = new Coord(start);
		Coord cursor;
		
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.WEST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.EAST, 3);
		end.add(Cardinal.UP, 4);
		
		//WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		start.add(Cardinal.NORTH);
		start.add(Cardinal.WEST);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.EAST);
		end.add(Cardinal.UP);
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, primary, true, true);

		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(floor);
			start.add(dir, 3);
			start.add(orth[0], 3);
			end = new Coord(start);
			end.add(Cardinal.UP, 6);
				
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
			
			for(Cardinal o : orth){
				start = new Coord(floor);
				start.add(dir, 5);
				start.add(o, 4);
				end = new Coord(start);
				end.add(Cardinal.UP, 4);
				start.add(Cardinal.DOWN, 10);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				
				end.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, end);
				
				end.add(Cardinal.reverse(dir));
				end.add(Cardinal.reverse(o));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, end);
				end.add(Cardinal.reverse(o));
				start = new Coord(end);
				start.add(Cardinal.reverse(o), 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, dir, false), true, true);
				
				end.add(Cardinal.reverse(dir));
				end.add(Cardinal.UP);
				start.add(Cardinal.reverse(dir));
				start.add(Cardinal.UP);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, dir, false), true, true);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, end);
				
				start = new Coord(floor);
				start.add(dir, 3);
				start.add(Cardinal.UP, 4);
				end = new Coord(start);
				end.add(o, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true), true, true);
				start.add(Cardinal.reverse(dir));
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(o, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true), true, true);
				start.add(Cardinal.UP);
				end.add(Cardinal.UP);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				cursor = new Coord(end);
				start = new Coord(end);
				start.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				cursor.add(Cardinal.reverse(o));
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, cursor);
				cursor.add(Cardinal.UP, 2);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(Cardinal.reverse(o), 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, dir, false), true, true);
				cursor = new Coord(end);
				cursor.add(Cardinal.reverse(dir));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				cursor.add(o);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(o));
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
				
				
				
			}
			

		}
		
		Cardinal front = Cardinal.NORTH;
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(floor);
			cursor.add(dir, 6);
			if(world.isAirBlock(cursor.getBlockPos())){
				front = dir;
				break;
			}
		}
		
		for(Cardinal dir : Cardinal.directions){
			
			if(dir == front){

				for(Cardinal o : Cardinal.getOrthogonal(dir)){
					cursor = new Coord(floor);
					cursor.add(dir, 5);
					cursor.add(o, 2);
					WorldGenPrimitive.setBlock(world, rand, cursor, primary, true, true);
					cursor.add(o);
					WorldGenPrimitive.blockOrientation(stair, o, false).setBlock(world, cursor);
					cursor.add(dir);
					WorldGenPrimitive.blockOrientation(stair, o, false).setBlock(world, cursor);
					cursor.add(Cardinal.reverse(o));
					WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
					cursor.add(Cardinal.reverse(dir));
					cursor.add(Cardinal.UP);
					WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, cursor);
					cursor.add(Cardinal.UP);
					WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
					cursor.add(o);
					WorldGenPrimitive.blockOrientation(stair, o, false).setBlock(world, cursor);
					cursor.add(Cardinal.reverse(o));
					cursor.add(Cardinal.UP);
					WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, cursor);
					cursor.add(Cardinal.reverse(o));
					WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
					cursor.add(Cardinal.reverse(o));
					cursor.add(Cardinal.UP);
					WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
					cursor.add(o);
					WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
					cursor.add(o);
					WorldGenPrimitive.blockOrientation(stair, o, false).setBlock(world, cursor);
				}
				
				// carve doorway
				Cardinal[] orth = Cardinal.getOrthogonal(dir);
				cursor = new Coord(floor);
				cursor.add(dir, 4);
				start = new Coord(cursor);
				end = new Coord(start);
				start.add(orth[0]);
				end.add(Cardinal.UP, 2);
				end.add(orth[1]);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.air), true, true);
				
				cursor = new Coord(floor);
				cursor.add(dir, 6);
				cursor.add(Cardinal.DOWN);
				step(world, rand, theme, dir, cursor);
				
				continue;
			}
			
			
			for(Cardinal o : Cardinal.getOrthogonal(dir)){
				start = new Coord(floor);
				start.add(Cardinal.UP, 4);
				start.add(dir, 5);
				end = new Coord(start);
				start.add(o, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, dir, false), true, true);
				start.add(o);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, start);
				start.add(Cardinal.DOWN);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, start);
			}
			
		}
		
		
		for(int i = floor.getY() - 1; i >= 50; --i){
			WorldGenPrimitive.spiralStairStep(world, rand, new Coord(x, i, z), stair, theme.getPrimaryPillar());
		}
	}
	
	private void step(World world, Random rand, ITheme theme, Cardinal dir, Coord origin){
		
		if(WorldGenPrimitive.getBlock(world, origin).getBlock().isOpaqueCube()) return;
		
		Coord start;
		Coord end;
		
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory blocks = theme.getPrimaryWall();
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0]);
		end.add(orth[1]);
		end = new Coord(end.getX(), 60, end.getZ());
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldGenPrimitive.blockOrientation(stair, dir, false);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, true);
		
		origin.add(Cardinal.DOWN);
		origin.add(dir);
		step(world, rand, theme, dir, origin);
	}

}
