package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public enum Wood {
	
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;

	public static MetaBlock get(WoodBlock block){
		return get(OAK, block);
	}
	
	public static MetaBlock get(Wood type, WoodBlock block){
		switch(block){
		case LOG: return Log.getLog(type);
		case PLANK: return getPlank(type);
		case FENCE: return getFence(type);
		default: return Log.getLog(OAK);
		}
	}
	
	public static MetaBlock getPlank(Wood type){
		
		MetaBlock plank = new MetaBlock(Blocks.planks);
		
		switch(type){
		case OAK: plank.setMeta(0); break;
		case SPRUCE: plank.setMeta(1); break;
		case BIRCH: plank.setMeta(2); break;
		case JUNGLE: plank.setMeta(3); break;
		case ACACIA: plank.setMeta(4); break;
		case DARKOAK: plank.setMeta(5); break;
		}
		
		return plank;
	}
	
	public static MetaBlock getFence(Wood type){
		return new MetaBlock(Blocks.fence);
	}
	
	public static MetaBlock getSapling(Wood type){
		MetaBlock sapling = new MetaBlock(Blocks.sapling);
		
		switch(type){
		case OAK: sapling.setMeta(0); break; 
		case SPRUCE: sapling.setMeta(1); break;
		case BIRCH: sapling.setMeta(2); break;
		case JUNGLE: sapling.setMeta(3); break;
		case ACACIA: sapling.setMeta(4); break;
		case DARKOAK: sapling.setMeta(5); break;
		default:
		}
		
		return sapling;
	}
	
	// used to copy the wood type from one block from another.
	public static MetaBlock applyType(MetaBlock source, MetaBlock destination){
		int meta = source.getMeta() + (source.getBlock().equals(Blocks.log2) ? 5 : 0);
		destination.setMeta(meta);
		return destination;
	}
}
