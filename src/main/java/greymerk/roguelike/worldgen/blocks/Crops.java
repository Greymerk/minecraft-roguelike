package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockCocoa;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

public enum Crops {

	WHEAT, CARROTS, NETHERWART, MELON, PUMPKIN;
	
	public static MetaBlock get(Crops type){
		switch(type){
		case WHEAT: return new MetaBlock(Blocks.wheat);
		case CARROTS: return new MetaBlock(Blocks.carrots);
		case NETHERWART: return new MetaBlock(Blocks.nether_wart);
		case MELON: return new MetaBlock(Blocks.melon_stem);
		case PUMPKIN: return new MetaBlock(Blocks.pumpkin_stem);
		default: return new MetaBlock(Blocks.wheat);
		}
	}
	
	public static MetaBlock getCocao(Cardinal dir){
		
		MetaBlock cocao = new MetaBlock(Blocks.cocoa);
		switch(dir){
		case NORTH: cocao.withProperty(BlockCocoa.FACING, EnumFacing.NORTH); break;
		case EAST: cocao.withProperty(BlockCocoa.FACING, EnumFacing.EAST); break;
		case WEST: cocao.withProperty(BlockCocoa.FACING, EnumFacing.WEST); break;
		case SOUTH: cocao.withProperty(BlockCocoa.FACING, EnumFacing.SOUTH); break;
		default:
		}
		
		return cocao;
		
	}
	
}
