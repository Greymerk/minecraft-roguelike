package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public enum Crops {

	WHEAT, CARROTS, NETHERWART, MELON, PUMPKIN, POTATOES;
	
	public static MetaBlock get(Crops type){
		switch(type){
		case WHEAT: return new MetaBlock(Blocks.wheat);
		case CARROTS: return new MetaBlock(Blocks.carrots);
		case NETHERWART: return new MetaBlock(Blocks.nether_wart);
		case MELON: return new MetaBlock(Blocks.melon_stem);
		case PUMPKIN: return new MetaBlock(Blocks.pumpkin_stem);
		case POTATOES: return new MetaBlock(Blocks.potatoes);
		default: return new MetaBlock(Blocks.wheat);
		}
	}
	
	public static MetaBlock getCocao(Cardinal dir){
		MetaBlock cocao = new MetaBlock(Blocks.cocoa, dirMeta(Cardinal.reverse(dir)) + 12);
		return cocao;
	}
	
	public static MetaBlock getPumpkin(Cardinal dir, boolean lit){
		MetaBlock pumpkin = new MetaBlock(lit ? Blocks.lit_pumpkin : Blocks.pumpkin, dirMeta(Cardinal.reverse(dir)));
		return pumpkin;
	}
	
	public static int dirMeta(Cardinal dir){
		switch(dir){
		case NORTH: return 0;
		case EAST: return 1;
		case SOUTH: return 2;
		case WEST: return 3;
		default: return 0;
		}
	}
	
	
}
