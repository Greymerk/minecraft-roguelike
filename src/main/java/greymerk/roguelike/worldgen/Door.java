package greymerk.roguelike.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public enum Door {

	IRON, WOOD;
	
	public static void generate(World world, int x, int y, int z, Cardinal dir, Door type){
		generate(world, x, y, z, dir, type, false);
	}
	
	public static void generate(World world, int x, int y, int z, Cardinal dir, Door type, boolean open){
		
		MetaBlock doorBase = new MetaBlock(getBlockId(type), getMeta(false, dir, open, false));
		doorBase.setBlock(world, x, y, z);
		MetaBlock doorTop = new MetaBlock(getBlockId(type), getMeta(true, dir, open, false));
		doorTop.setBlock(world, x, y + 1, z);
	}
	
	private static Block getBlockId(Door type){
		if(type == Door.IRON) return Blocks.iron_door; 
		return Blocks.wooden_door;
	}
	
	private static int getMeta(boolean top, Cardinal dir){
		return getMeta(top, dir, false, false);
	}
	
	private static int getMeta(boolean top, Cardinal dir, boolean open, boolean hingeLeft){
		int meta = 0;

		if(top){
			
			if(hingeLeft) meta += 1;
			return meta + 8;
			
		} else {
			
			if(open) meta += 4;
			
			switch(dir){
			case WEST: return meta;
			case NORTH: return meta + 1;
			case EAST: return meta + 2;
			case SOUTH: return meta + 3;
			default: return meta;
			}
			
		}
		
	}

}
