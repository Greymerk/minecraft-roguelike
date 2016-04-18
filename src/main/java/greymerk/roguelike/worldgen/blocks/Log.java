package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Log {

	public static MetaBlock getLog(Wood type, Cardinal dir){
		
		MetaBlock log = new MetaBlock(getBlockId(type), getMeta(type, dir));
		return log;
		
	}
	
	public static MetaBlock get(Wood type){
		return getLog(type, Cardinal.UP);
	}
	
	public static MetaBlock get(Wood type, Cardinal dir){
		return new MetaBlock(getBlockId(type), getMeta(type, dir));
	}
	
	public static Block getBlockId(Wood type){
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
	
	public static int getMeta(Wood type, Cardinal dir){
		
		int orientation;

		switch(dir){
		case NORTH:
		case SOUTH: orientation = 8; break;
		case EAST:
		case WEST: orientation = 4; break;
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
