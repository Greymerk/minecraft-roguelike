package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

public class Log {

	public static MetaBlock get(Wood type, Cardinal dir){
		
		MetaBlock log = new MetaBlock(getBlockId(type));
		setType(log, type);

		if(dir == null){
			log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE);
			return log;
		}
		
		switch(dir){
		case UP:
		case DOWN: log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y); break;
		case EAST:
		case WEST: log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X); break;
		case NORTH:
		case SOUTH: log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Z); break;
		default: log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE); break;
		}
		
		return log;
		
	}
	
	public static MetaBlock getLog(Wood type){
		return get(type, Cardinal.UP);
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
	
	public static void setType(MetaBlock log, Wood type){
		switch(type){
		case OAK: log.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK); return;
		case SPRUCE: log.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE); return;
		case BIRCH: log.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH); return;
		case JUNGLE: log.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE); return;
		case ACACIA: log.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA); return;
		case DARKOAK: log.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK); return;
		default: log.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK); return;
		}
	}
}
