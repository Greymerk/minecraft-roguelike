package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public enum Quartz {

	SMOOTH, CHISELED, PILLAR;
	
	public static MetaBlock get(Quartz type){
		switch(type){
		case CHISELED: return new MetaBlock(Blocks.quartz_block, 1);
		case PILLAR: return new MetaBlock(Blocks.quartz_block, 2);
		case SMOOTH:
		default: return new MetaBlock(Blocks.quartz_block);
		}
	}
	
	public static MetaBlock getPillar(Cardinal dir){
		MetaBlock block = get(PILLAR);
		
		switch(dir){
		case UP:
		case DOWN: block.setMeta(2); break;
		case NORTH:
		case SOUTH: block.setMeta(3); break;
		case EAST:
		case WEST: block.setMeta(4); break;
		default: block.setMeta(2); break;
		}
		
		return block;
	}
}
