package greymerk.roguelike.worldgen;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class WorldGenPrimitive {
	
	public static boolean setBlock(World world, int x, int y, int z, Block block, int meta, int flags, boolean fillAir, boolean replaceSolid){
		
		Block currentBlock = world.getBlock(x, y, z);
		
		if(currentBlock == Blocks.chest) return false;
		if(currentBlock == Blocks.trapped_chest) return false;
		if(currentBlock == Blocks.mob_spawner) return false;
		
		boolean isAir = world.isAirBlock(x, y, z);
		
		if(!fillAir && isAir) return false;
		if(!replaceSolid && !isAir)	return false;
		
		world.setBlock(x, y, z, block, meta, flags);
		return true;
		
	}
	
	public static boolean setBlock(World world, int x, int y, int z, Block block){
		return setBlock(world, x, y, z, block, 0, 2, true, true);
	}
	
	public static boolean setBlock(World world, Coord coord, Block block) {
		return setBlock(world, coord.getX(), coord.getY(), coord.getZ(), block);
	}
	
	public static boolean setBlock(World world, int x, int y, int z, MetaBlock block){
		return setBlock(world, x, y, z, block.getBlockID(), block.getMeta(), block.getFlag(), true, true);
	}
	
	public static boolean setBlock(World world, Random rand, int x, int y, int z, IBlockFactory block, boolean fillAir, boolean replaceSolid){
		return block.setBlock(world, rand, new Coord(x, y, z), fillAir, replaceSolid);
	}
	
	public static boolean setBlock(World world, Random rand, Coord coord, IBlockFactory blocks, boolean fillAir, boolean replaceSolid) {
		return blocks.setBlock(world, rand, coord, fillAir, replaceSolid);
	}
	
	public static void fillRectSolid(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		fillRectSolid(world, rand, new Coord(x1, y1, z1), new Coord(x2, y2, z2), blocks, fillAir, replaceSolid);
	}
	
	public static void fillRectSolid(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks){
		fillRectSolid(world, rand, new Coord(x1, y1, z1), new Coord(x2, y2, z2), blocks, true, true);
	}
	
	public static void fillRectSolid(World world, Random rand, Coord start, Coord end, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		Coord c1 = new Coord(start);
		Coord c2 = new Coord(end);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					setBlock(world, rand, x, y, z, blocks, fillAir, replaceSolid);
				}
			}
		}
	}
	

	
	
	
	public static void fillRectHollow(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2, Block block, int meta, int flag, boolean fillAir, boolean replaceSolid){
		fillRectHollow(world, rand, x1, y1, z1, x2, y2, z2, new MetaBlock(block, meta, flag), fillAir, replaceSolid);
	}
	
	public static void fillRectHollow(World world, Random rand, Coord c1, Coord c2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		Coord first = new Coord(c1);
		Coord second = new Coord(c2);
		Coord.correct(first, second);
		fillRectHollow(world, rand, first.getX(), first.getY(), first.getZ(), second.getX(), second.getY(), second.getZ(), blocks, fillAir, replaceSolid);
	}
	
	public static void fillRectHollow(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					if(x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2){
						setBlock(world, rand, x, y, z, blocks, fillAir, replaceSolid);
					} else {					
						setBlock(world, x, y, z, Blocks.air);
					}	
				}
			}
			
		}
	}
	

	public static List<Coord> getRectSolid(Coord start, Coord end){
		return getRectSolid(start.getX(), start.getY(), start.getZ(), end.getX(), end.getY(), end.getZ());
	}
	
	public static List<Coord> getRectSolid(int x1, int y1, int z1, int x2, int y2, int z2){
		
		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		
		Coord.correct(c1, c2);
		
		List<Coord> points = new LinkedList<Coord>();
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					points.add(new Coord(x, y, z));
				}
			}
		}	
		
		return points;
	}
	
	public static List<Coord> getRectHollow(Coord start, Coord end){
		return getRectHollow(start.getX(), start.getY(), start.getZ(), end.getX(), end.getY(), end.getZ());
	}
	
	public static List<Coord> getRectHollow(int x1, int y1, int z1, int x2, int y2, int z2){

		
		List<Coord> points = new LinkedList<Coord>();
		
		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					if(x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2){
						points.add(new Coord(x, y, z));
					}
				}
			}
		}
		
		return points;
	}
	
	public static void fillPyramidSolid(World world, Random rand, Coord base, int height, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		if(height == 0){
			setBlock(world, rand, base, blocks, fillAir, replaceSolid);
			return;
		}
		
		Coord start;
		Coord end;
		
		start = new Coord(base);
		end = new Coord(base);
		start.add(Cardinal.NORTH, height);
		start.add(Cardinal.WEST, height);
		end.add(Cardinal.SOUTH, height);
		end.add(Cardinal.EAST, height);
		
		fillRectSolid(world, rand, start, end, blocks, fillAir, replaceSolid);
		
		base.add(Cardinal.UP);
		
		fillPyramidSolid(world, rand, base, (height - 1), blocks, fillAir, replaceSolid);
		
	}
	

	public static void spiralStairStep(World world, Random rand, Coord origin, MetaBlock stair, IBlockFactory fill){
		
		MetaBlock air = new MetaBlock(Blocks.air);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(new Coord(-1, 0, -1));
		end = new Coord(origin);
		end.add(new Coord(1, 0, 1));
		
		// air
		fillRectSolid(world, rand, start, end, air, true, true);
		
		// core
		setBlock(world, rand, origin, fill, true, true);
		
		Cardinal dir = Cardinal.directions[origin.getY() % 4];
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		cursor = new Coord(origin);
		cursor.add(dir);
		blockOrientation(stair, orth[0], false).setBlock(world, cursor);
		cursor.add(orth[1]);
		blockOrientation(stair, orth[1], true).setBlock(world, cursor);
		cursor.add(Cardinal.reverse(dir));
		blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
		
		
	}
	
	public static void randomVines(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2){

		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
        			for (int dir = 2; dir <= 5; ++dir){
	        			if(world.isAirBlock(x, y, z)){
	        				
	        				if(rand.nextBoolean()){
	        					continue;
	        				}
	        				
	            		    if (Blocks.vine.canPlaceBlockOnSide(world, x, y, z, dir))
	                        {
	            		    	setBlock(world, x, y, z, Blocks.vine, 1 << Direction.facingToDirection[Facing.oppositeSide[dir]], 2, true, true);
	                            break;
	                        }
	        			}
        			}
        		}
        	}
        }

	}

	public static int blockOrientation(Cardinal dir, Boolean upsideDown){
		return Cardinal.getBlockMeta(dir) + (upsideDown ? 4 : 0);
	}
	
	public static MetaBlock blockOrientation(MetaBlock block, Cardinal dir, Boolean upsideDown){
		block.setMeta(blockOrientation(dir, upsideDown));
		return block;
	}
	
	public static void fillDown(World world, Random rand, Coord origin, IBlockFactory blocks){

		Coord cursor = new Coord(origin);
		
		while(!world.getBlock(cursor.getX(), cursor.getY(), cursor.getZ()).getMaterial().isOpaque() && cursor.getY() > 1){
			blocks.setBlock(world, rand, cursor);
			cursor.add(Cardinal.DOWN);
		}
	}
}

