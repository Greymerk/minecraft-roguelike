package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

public enum Leaves {
	
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;
	
	public static MetaBlock get(Leaves type, boolean decay){
		
		Block base = getBlockId(type);
		
		MetaBlock leaf = new MetaBlock(base);
		
		// Original minecraft leaves use a different variant property than
		// newer leaves like acacia and dark oak.
		if(base == Blocks.leaves){
			leaf.withProperty(BlockOldLeaf.VARIANT, getType(type));	
		} else {
			leaf.withProperty(BlockNewLeaf.VARIANT, getType(type));
		}
		
		leaf.withProperty(BlockLeaves.DECAYABLE, decay);
		
		return leaf;
		
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
	
	private static BlockPlanks.EnumType getType(Leaves type){
		
		switch(type){
		case OAK: return BlockPlanks.EnumType.OAK;
		case SPRUCE: return BlockPlanks.EnumType.SPRUCE;
		case BIRCH: return BlockPlanks.EnumType.BIRCH;
		case JUNGLE: return BlockPlanks.EnumType.JUNGLE;
		case ACACIA: return BlockPlanks.EnumType.ACACIA;
		case DARKOAK: return BlockPlanks.EnumType.DARK_OAK;
		default: return BlockPlanks.EnumType.OAK;
		}		
	}
}
