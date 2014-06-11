package greymerk.roguelike.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum Log {

	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;
	
	public static MetaBlock getLog(Log type, Cardinal dir){
		return new MetaBlock(getBlockId(type), getMeta(type, dir));
	}
	
	public static MetaBlock getLog(Log type){
		return getLog(type, Cardinal.UP);
	}
	
	public static Block getBlockId(Log type){
		switch(type){
		case OAK: return Blocks.log;
		case SPRUCE: return Blocks.log;
		case BIRCH: return Blocks.log;
		case JUNGLE: return Blocks.log;
		case ACACIA: return Blocks.log2;
		case DARKOAK: return Blocks.log2;
		default: return Blocks.log;
		}
	}
	
	public static int getMeta(Log type, Cardinal dir){
		
		int orientation;

		switch(dir){
		case NORTH:
		case SOUTH: orientation = 8;
		case EAST:
		case WEST: orientation = 4;
		default: orientation = 0;
		}
		
		switch(type){
		case OAK: return orientation;
		case SPRUCE: return orientation + 1;
		case BIRCH: return orientation + 2;
		case JUNGLE: return orientation + 3;
		case ACACIA: return orientation;
		case DARKOAK: return orientation + 1;
		default: return 0;
		}
		
	}
	
	
}
