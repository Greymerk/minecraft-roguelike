package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockPlanks;
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
		default: return Log.getLog(OAK);
		}
	}
	
	public static MetaBlock getPlank(Wood type){
		
		MetaBlock plank = new MetaBlock(Blocks.planks);
		
		switch(type){
		case OAK: plank.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.OAK); break;
		case SPRUCE: plank.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE); break;
		case BIRCH: plank.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.BIRCH); break;
		case JUNGLE: plank.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.JUNGLE); break;
		case ACACIA: plank.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.ACACIA); break;
		case DARKOAK: plank.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.DARK_OAK); break;
		}
		
		return plank;
	}
}
