package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockQuartz;
import net.minecraft.init.Blocks;

public enum Quartz {

	SMOOTH, CHISELED, PILLAR;
	
	public static MetaBlock get(Quartz type){
		MetaBlock block;
		switch(type){
		case CHISELED:
			block = new MetaBlock(Blocks.quartz_block);
			block.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.CHISELED);
		case PILLAR:
			block = new MetaBlock(Blocks.quartz_block);
			block.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_Y);
		case SMOOTH:
		default: return new MetaBlock(Blocks.quartz_block);
		}
	}
	
	public static MetaBlock getPillar(Cardinal dir){
		MetaBlock block = new MetaBlock(Blocks.quartz_block);
		switch(dir){
		case EAST:
		case WEST:
			block.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_X); break;
		case NORTH:
		case SOUTH:
			block.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_Z); break;
		case UP:
		case DOWN:
		default: 
			block.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_Y); break;
		}
		
		return block;
	}
	
}
