package greymerk.roguelike;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Direction;
import net.minecraft.src.Facing;
import net.minecraft.src.World;

public class WorldGenPrimitive {

	public static final int UPSIDEDOWN = 4;
	
	public static final int WEST = 0;
	public static final int EAST = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;
	
	public static boolean setBlock(World world, int x, int y, int z, int blockID, int meta, int flags, boolean fillAir, boolean replaceSolid){
		
		if(world.blockHasTileEntity(x, y, z)){
			return false;
		}
		
		int currentBlock = world.getBlockId(x, y, z);
		
		if(currentBlock == Block.chest.blockID){
			return false;
		}
		
		if(currentBlock == Block.mobSpawner.blockID){
			return false;
		}
		
		boolean isAir = world.isAirBlock(x, y, z);
		
		if(!fillAir && isAir){
			return false;
		}
		
		if(!replaceSolid && !isAir){
			return false;
		}
		
		world.setBlock(x, y, z, blockID, meta, flags);
		return true;
		
	}
	
	public static boolean setBlock(World world, int x, int y, int z, int blockID){
		return setBlock(world, x, y, z, blockID, 0, 2, true, true);
	}
	
	public static void fillRectSolid(World world, int x1, int y1, int z1, int x2, int y2, int z2, int blockID, int meta, int flag, boolean fillAir, boolean replaceSolid){
		fillRectSolid(world, x1, y1, z1, x2, y2, z2, new SingleBlockFactory(blockID, meta, flag), fillAir, replaceSolid);
	}
	
	public static void fillRectSolid(World world, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks){
		fillRectSolid(world, x1, y1, z1, x2, y2, z2, blocks, true, true);
	}
	
	public static void fillRectSolid(World world, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		for(int x = x1; x <= x2; x++){
			for(int y = y1; y <= y2; y++){
				for(int z = z1; z <= z2; z++){
					blocks.setBlock(world, x, y, z, fillAir, replaceSolid);	
				}
			}
		}
	}
	
	public static void fillRectSolid(World world, int x1, int y1, int z1, int x2, int y2, int z2, int blockID){
		fillRectSolid(world, x1, y1, z1, x2, y2, z2, blockID, 0, 2, true, true);
	}
	
	public static List<Coord> getRectSolid(int x1, int y1, int z1, int x2, int y2, int z2){
		List<Coord> points = new LinkedList<Coord>();
		
		for(int x = x1; x <= x2; x++){
			for(int y = y1; y <= y2; y++){
				for(int z = z1; z <= z2; z++){
					points.add(new Coord(x, y, z));
				}
			}
		}	
		
		return points;
	}
	
	public static void fillRectHollow(World world, int x1, int y1, int z1, int x2, int y2, int z2, int blockID, int meta, int flag, boolean fillAir, boolean replaceSolid){
		fillRectHollow(world, x1, y1, z1, x2, y2, z2, new SingleBlockFactory(blockID, meta, flag), fillAir, replaceSolid);
	}
	
	public static void fillRectHollow(World world, int x1, int y1, int z1, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		for(int x = x1; x <= x2; x++){
			for(int y = y1; y <= y2; y++){
				for(int z = z1; z <= z2; z++){
					if(x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2){
						blocks.setBlock(world, x, y, z, fillAir, replaceSolid);
					} else {					
						setBlock(world, x, y, z, 0);
					}	
				}
			}
		}
	}
	
	public static void fillRectHollow(World world, int x1, int y1, int z1, int x2, int y2, int z2, int blockID){
		fillRectHollow(world, x1, y1, z1, x2, y2, z2, blockID, 0, 2, true, true);
	}
	
	
	public static List<Coord> getRectHollow(int x1, int y1, int z1, int x2, int y2, int z2){
		
		List<Coord> points = new LinkedList<Coord>();
		
		for(int x = x1; x <= x2; x++){
			for(int y = y1; y <= y2; y++){
				for(int z = z1; z <= z2; z++){
					if(x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2){
						points.add(new Coord(x, y, z));
					}
				}
			}
		}
		
		return points;
	}
	
	
	public static void spiralStairStep(World world, int inX, int inY, int inZ, MetaBlock stair, MetaBlock fill){
		
		final int UPSIDEDOWN = 4;
		
		// air
		fillRectSolid(world, inX - 1, inY, inZ - 1, inX + 1, inY, inZ + 1, 0, 0, 2, true, true);
		
		// core
		setBlock(world, inX, inY, inZ, fill.getBlockID(), fill.getMeta(), fill.getFlag(), true, true);
		
		switch (inY % 4){
		case 0: // north
			// stairs
			setBlock(world, inX, inY, inZ - 1, stair.getBlockID(), stair.getMeta() + WEST, stair.getFlag(), true, true);
			setBlock(world, inX + 1, inY, inZ - 1, stair.getBlockID(), stair.getMeta() + EAST + UPSIDEDOWN, stair.getFlag(), true, true);
			setBlock(world, inX + 1, inY, inZ, stair.getBlockID(), stair.getMeta() + SOUTH + UPSIDEDOWN, stair.getFlag(), true, true);
			return;
		case 1: // east
			setBlock(world, inX + 1, inY, inZ, stair.getBlockID(), stair.getMeta() + NORTH, stair.getFlag(), true, true);
			setBlock(world, inX + 1, inY, inZ + 1, stair.getBlockID(), stair.getMeta() + SOUTH + UPSIDEDOWN, stair.getFlag(), true, true);
			setBlock(world, inX, inY, inZ + 1, stair.getBlockID(), stair.getMeta() + WEST + UPSIDEDOWN, stair.getFlag(), true, true);
			return;
		case 2: // south
			setBlock(world, inX, inY, inZ + 1, stair.getBlockID(), stair.getMeta() + EAST, stair.getFlag(), true, true);
			setBlock(world, inX - 1, inY, inZ + 1, stair.getBlockID(), stair.getMeta() + WEST + UPSIDEDOWN, stair.getFlag(), true, true);
			setBlock(world, inX - 1, inY, inZ, stair.getBlockID(), stair.getMeta() + NORTH + UPSIDEDOWN, stair.getFlag(), true, true);
			return;
		case 3: // west
			setBlock(world, inX - 1, inY, inZ, stair.getBlockID(), stair.getMeta() + SOUTH, stair.getFlag(), true, true);
			setBlock(world, inX - 1, inY, inZ - 1, stair.getBlockID(), stair.getMeta() + NORTH + UPSIDEDOWN, stair.getFlag(), true, true);
			setBlock(world, inX, inY, inZ - 1, stair.getBlockID(), stair.getMeta() + EAST + UPSIDEDOWN, stair.getFlag(), true, true);
			return;
		default:
			return;
		}
	}
	
	public static void randomVines(World world, Random rand, int x1, int y1, int z1, int x2, int y2, int z2){

        for (int y = y1; y <= y2; y++){
        	for (int x = x1; x <= x2; x++){
        		for(int z = z1; z <= z2; z++){
        			for (int dir = 2; dir <= 5; ++dir){
	        			if(world.isAirBlock(x, y, z)){
	        				
	        				if(rand.nextBoolean()){
	        					continue;
	        				}
	        				
	            		    if (Block.vine.canPlaceBlockOnSide(world, x, y, z, dir))
	                        {
	                            world.setBlock(x, y, z, Block.vine.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[dir]], 2);
	                            break;
	                        }
	        			}
        			}
        		}
        	}
        }

	}
	
	private static class SingleBlockFactory implements IBlockFactory{

		private MetaBlock block;
		
		public SingleBlockFactory(int blockID, int meta, int flag){
			this.block = new MetaBlock(blockID, meta, flag);
		}
		
		@Override
		public void setBlock(World world, int x, int y, int z) {
			WorldGenPrimitive.setBlock(world, x, y, z, block.getBlockID(), block.getMeta(), block.getFlag(), true, true);
		}

		@Override
		public void setBlock(World world, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
			WorldGenPrimitive.setBlock(world, x, y, z, block.getBlockID(), block.getMeta(), block.getFlag(), fillAir, replaceSolid);
		}

		@Override
		public MetaBlock getMetaBlock() {
			return block;
		}		
	}
}

