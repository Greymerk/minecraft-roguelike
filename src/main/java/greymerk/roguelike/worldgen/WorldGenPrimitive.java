package greymerk.roguelike.worldgen;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenPrimitive {
	
	public static boolean setBlock(World world, Coord pos, IBlockState block, int flags, boolean fillAir, boolean replaceSolid){
		
		MetaBlock currentBlock = WorldGenPrimitive.getBlock(world, pos);
		
		if(currentBlock.getBlock() == Blocks.chest) return false;
		if(currentBlock.getBlock() == Blocks.trapped_chest) return false;
		if(currentBlock.getBlock() == Blocks.mob_spawner) return false;
		
		boolean isAir = world.isAirBlock(pos);
		
		if(!fillAir && isAir) return false;
		if(!replaceSolid && !isAir)	return false;
		
		world.setBlockState(pos, block, flags);
		return true;
		
	}
	
	public static boolean setBlock(World world, Coord pos, MetaBlock block){
		return block.setBlock(world, pos);
	}
	
	public static boolean setBlock(World world, Coord pos, Block block){
		return setBlock(world, pos, new MetaBlock(block));
	}
	
	public static boolean setBlock(World world, Random rand, Coord coord, IBlockFactory blocks, boolean fillAir, boolean replaceSolid) {
		return blocks.setBlock(world, rand, coord, fillAir, replaceSolid);
	}
	
	public static void fillRectSolid(World world, Random rand, Coord start, Coord end, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		Coord c1 = new Coord(start);
		Coord c2 = new Coord(end);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					setBlock(world, rand, new Coord(x, y, z), blocks, fillAir, replaceSolid);
				}
			}
		}
	}
	
	public static void fillRectHollow(World world, Random rand, Coord start, Coord end, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		Coord c1 = new Coord(start);
		Coord c2 = new Coord(end);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					if(x == c1.getX() || x == c2.getX() || y == c1.getY() || y == c2.getY() || z == c1.getZ() || z == c2.getZ()){
						setBlock(world, rand, new Coord(x, y, z), blocks, fillAir, replaceSolid);
					} else {					
						setBlock(world, new Coord(x, y, z), new MetaBlock(Blocks.air));
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
		
		MetaBlock vine = new MetaBlock(Blocks.vine);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
        			for (Cardinal dir : Cardinal.directions){
        				Coord pos = new Coord(x, y, z);
        				
	        			if(world.isAirBlock(pos)){
	        				
	        				if(rand.nextBoolean()){
	        					continue;
	        				}
	        				
	            		    if (Blocks.vine.canPlaceBlockOnSide(world, new BlockPos(x, y, z), Cardinal.getFacing(dir)));
	                        {
	                        	
	            		    	setBlock(world, rand, pos, vine, true, true);
	                            break;
	                        }
	        			}
        			}
        		}
        	}
        }

	}
	
	public static MetaBlock blockOrientation(MetaBlock block, Cardinal dir, Boolean upsideDown){
		IBlockState stair = block.getBlock().getDefaultState();
		stair = stair.withProperty(BlockStairs.FACING, Cardinal.getFacing(dir));
		stair = stair.withProperty(BlockStairs.HALF, upsideDown ? EnumHalf.TOP : EnumHalf.BOTTOM);
		block.setState(stair);
		return block;
	}
	
	public static void fillDown(World world, Random rand, Coord origin, IBlockFactory blocks){

		Coord cursor = new Coord(origin);
		
		while(!WorldGenPrimitive.getBlock(world, cursor).getBlock().getMaterial().isOpaque() && cursor.getY() > 1){
			blocks.setBlock(world, rand, cursor);
			cursor.add(Cardinal.DOWN);
		}
	}
	
	public static MetaBlock getBlock(World world, Coord pos){
		return new MetaBlock(world.getBlockState(pos));
	}
	
	public static TileEntity getTileEntity(World world, Coord pos){
		return world.getTileEntity(pos);
	}
	
	public static void setBlock(World world, int x, int y, int z, Block block){
		new MetaBlock(block).setBlock(world, new Coord(x, y, z));
	}
	
	public static void setBlock(World world, int x, int y, int z, MetaBlock block){
		block.setBlock(world, new Coord(x, y, z));
	}
	
	public static void fillRectSolid(World world, Random rand, int x, int y, int z, int x2, int y2, int z2, IBlockFactory blocks){
		fillRectSolid(world, rand, new Coord(x, y, z), new Coord(x2, y2, z2), blocks, true, true);
	}
	
	public static void fillRectSolid(World world, Random rand, int x, int y, int z, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		fillRectSolid(world, rand, new Coord(x, y, z), new Coord(x2, y2, z2), blocks, fillAir, replaceSolid);
	}
	
	public static void setBlock(World world, Random rand, int x, int y, int z, IBlockFactory block, boolean fillAir, boolean replaceSolid){
		setBlock(world, rand, new Coord(x, y, z), block, true, true);
	}
	
	public static void fillRectHollow(World world, Random rand, int x, int y, int z, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		fillRectHollow(world, rand, new Coord(x, y, z), new Coord(x2, y2, z2), blocks, fillAir, replaceSolid);
	}
}

