package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum StairType {

	COBBLE, STONEBRICK, BRICK, SANDSTONE, RED_SANDSTONE, QUARTZ, NETHERBRICK, OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;
	
	public static Block getBlock(StairType type){
		switch(type){
		case COBBLE: return Blocks.stone_stairs;
		case STONEBRICK: return Blocks.stone_brick_stairs;
		case BRICK: return Blocks.brick_stairs;
		case SANDSTONE: return Blocks.sandstone_stairs;
		case RED_SANDSTONE: return Block.getBlockById(180);
		case QUARTZ: return Blocks.quartz_stairs;
		case NETHERBRICK: return Blocks.nether_brick_stairs;
		case OAK: return Blocks.oak_stairs;
		case SPRUCE: return Blocks.spruce_stairs;
		case BIRCH: return Blocks.birch_stairs;
		case JUNGLE: return Blocks.jungle_stairs;
		case ACACIA: return Blocks.acacia_stairs;
		case DARKOAK: return Blocks.dark_oak_stairs;
		default: return Blocks.stone_stairs;
		}
	}
}
