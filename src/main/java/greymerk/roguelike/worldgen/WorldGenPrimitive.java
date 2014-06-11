package greymerk.roguelike.worldgen;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class WorldGenPrimitive {
	
	public static boolean setBlock(World world, int x, int y, int z, Block block, int meta, int flags, boolean fillAir, boolean replaceSolid){
		
		Block currentBlock = world.getBlock(x, y, z);
		
		if(world.getTileEntity(x, y, z) != null) return false;
		if(currentBlock == Blocks.chest) return false;
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
	
	public static void setBlock(World world, Random rand, int x, int y, int z, IBlockFactory block, boolean fillAir, boolean replaceSolid){
		block.setBlock(world, rand, x, y, z, fillAir, replaceSolid);
	}
	
	public static void setBlock(World world, Random rand, Coord coord, IBlockFactory blocks, boolean fillAir, boolean replaceSolid) {
		blocks.setBlock(world, rand, coord.getX(), coord.getY(), coord.getZ(), fillAir, replaceSolid);
	}
	
	public static void fillRectSolid(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks){
		fillRectSolid(world, rand, x1, y1, z1, x2, y2, z2, blocks, true, true);
	}
		
	public static void fillRectSolid(World world, Random rand, Coord c1, Coord c2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		Coord first = new Coord(c1);
		Coord second = new Coord(c2);
		Coord.correct(first, second);
		fillRectSolid(world, rand, first.getX(), first.getY(), first.getZ(), second.getX(), second.getY(), second.getZ(), blocks, fillAir, replaceSolid);
	}
	
	
	public static void fillRectSolid(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					blocks.setBlock(world, rand, x, y, z, fillAir, replaceSolid);	
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
		fillRectHollow(world, rand, first.getX(), first.getY(), first.getZ(), second.getX(), second.getY(), second.getZ(), blocks, true, true);
	}
	
	public static void fillRectHollow(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					if(x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2){
						blocks.setBlock(world, rand, x, y, z, fillAir, replaceSolid);
					} else {					
						setBlock(world, x, y, z, Blocks.air);
					}	
				}
			}
		}
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
	
	
	

	public static void spiralStairStep(World world, Random rand, int inX, int inY, int inZ, MetaBlock stair, IBlockFactory fill){
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// air
		fillRectSolid(world, rand, inX - 1, inY, inZ - 1, inX + 1, inY, inZ + 1, air, true, true);
		
		// core
		setBlock(world, rand, inX, inY, inZ, fill, true, true);
		
		switch (inY % 4){
		case 0: // north
			// stairs
			setBlock(world, inX, inY, inZ - 1, stair.getBlockID(), blockOrientation(Cardinal.WEST, false), stair.getFlag(), true, true);
			setBlock(world, inX + 1, inY, inZ - 1, stair.getBlockID(), blockOrientation(Cardinal.EAST, true), stair.getFlag(), true, true);
			setBlock(world, inX + 1, inY, inZ, stair.getBlockID(), blockOrientation(Cardinal.SOUTH, true), stair.getFlag(), true, true);
			return;
		case 1: // east
			setBlock(world, inX + 1, inY, inZ, stair.getBlockID(), blockOrientation(Cardinal.NORTH, false), stair.getFlag(), true, true);
			setBlock(world, inX + 1, inY, inZ + 1, stair.getBlockID(), blockOrientation(Cardinal.SOUTH, true), stair.getFlag(), true, true);
			setBlock(world, inX, inY, inZ + 1, stair.getBlockID(), blockOrientation(Cardinal.WEST, true), stair.getFlag(), true, true);
			return;
		case 2: // south
			setBlock(world, inX, inY, inZ + 1, stair.getBlockID(), blockOrientation(Cardinal.EAST, false), stair.getFlag(), true, true);
			setBlock(world, inX - 1, inY, inZ + 1, stair.getBlockID(), blockOrientation(Cardinal.WEST, true), stair.getFlag(), true, true);
			setBlock(world, inX - 1, inY, inZ, stair.getBlockID(), blockOrientation(Cardinal.NORTH, true), stair.getFlag(), true, true);
			return;
		case 3: // west
			setBlock(world, inX - 1, inY, inZ, stair.getBlockID(), blockOrientation(Cardinal.SOUTH, false), stair.getFlag(), true, true);
			setBlock(world, inX - 1, inY, inZ - 1, stair.getBlockID(), blockOrientation(Cardinal.NORTH, true), stair.getFlag(), true, true);
			setBlock(world, inX, inY, inZ - 1, stair.getBlockID(), blockOrientation(Cardinal.EAST, true), stair.getFlag(), true, true);
			return;
		default:
			return;
		}
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
	
	public static void fillDown(World world, Random rand, int x, int y, int z, IBlockFactory blocks){
		while(!world.getBlock(x, y, z).getMaterial().isOpaque() && y > 1){
			blocks.setBlock(world, rand, x, y, z);
			--y;
		}
	}

	public static void skull(World world, Random rand, int x, int y, int z, Cardinal dir){
		
		if(rand.nextBoolean()){
			return;
		}
		
		MetaBlock skull = new MetaBlock(Blocks.skull, 1);
		
		if(!skull.setBlock(world, x, y, z)) return;
		
		TileEntitySkull skullEntity;
		
		try{
			skullEntity = (TileEntitySkull) world.getTileEntity(x, y, z);
		} catch (Exception e){
			return;
		}
		
		if(rand.nextInt(10) == 0){
			// TODO: Make sure this works for skulls
			skullEntity.func_145905_a(1, "");
		}
		
		switch(dir){
		case SOUTH: skullEntity.func_145903_a(8);
		break;
		case NORTH: skullEntity.func_145903_a(0);
		break;
		case WEST: skullEntity.func_145903_a(12);
		break;
		case EAST: skullEntity.func_145903_a(4);
		break;
		}
		
	}

}

