package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum Leaves {
	
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;
	
	public static MetaBlock get(Leaves type, boolean decay){
		
		int meta = getType(type);
		
		if(decay){
			meta += 4;
		}
		
		
		return new MetaBlock(getBlockId(type), meta);		
	}
	
	public static Block getBlockId(Leaves type){
		switch(type){
		case OAK: return Blocks.leaves;
		case SPRUCE: return Blocks.leaves;
		case BIRCH: return Blocks.leaves;
		case JUNGLE: return Blocks.leaves;
		case ACACIA: return Blocks.leaves2;
		case DARKOAK: return Blocks.leaves2;
		default: return Blocks.log;
		}
	}
	
	private static int getType(Leaves type){
		
		switch(type){
		case OAK: return 0;
		case SPRUCE: return 1;
		case BIRCH: return 2;
		case JUNGLE: return 3;
		case ACACIA: return 0;
		case DARKOAK: return 1;
		default: return 0;
		}		
	}
}
