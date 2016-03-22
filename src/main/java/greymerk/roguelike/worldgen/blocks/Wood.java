package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
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
		case OAK: plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK); break;
		case SPRUCE: plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE); break;
		case BIRCH: plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH); break;
		case JUNGLE: plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE); break;
		case ACACIA: plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA); break;
		case DARKOAK: plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK); break;
		}
		
		return plank;
	}
	
	public static MetaBlock getFence(Wood type){
		
		MetaBlock fence;
		
		switch(type){
		case OAK: fence = new MetaBlock(Blocks.oak_fence); break;
		case SPRUCE: fence = new MetaBlock(Blocks.spruce_fence); break;
		case BIRCH: fence = new MetaBlock(Blocks.birch_fence); break;
		case JUNGLE: fence = new MetaBlock(Blocks.jungle_fence); break;
		case ACACIA: fence = new MetaBlock(Blocks.acacia_fence); break;
		case DARKOAK: fence = new MetaBlock(Blocks.dark_oak_fence); break;
		default: fence = new MetaBlock(Blocks.oak_fence); break;
		}
		
		return fence;
	}
	
	public static MetaBlock getSapling(Wood type){
		MetaBlock sapling = new MetaBlock(Blocks.sapling);
		
		switch(type){
		case OAK: sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.OAK); break; 
		case SPRUCE: sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.SPRUCE); break;
		case BIRCH: sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.BIRCH); break;
		case JUNGLE: sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.JUNGLE); break;
		case ACACIA: sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.ACACIA); break;
		case DARKOAK: sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.DARK_OAK); break;
		default:
		}
		
		return sapling;
	}

}
