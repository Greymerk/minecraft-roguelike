package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public enum Trapdoor {

	OAK, IRON;
	
	public static MetaBlock get(Trapdoor type, Cardinal dir, boolean bottom, boolean open){
		
		MetaBlock block = new MetaBlock(Blocks.trapdoor);
		
		int facing;
		switch(dir){
		case SOUTH: facing = 0; break;
		case NORTH: facing = 1; break;
		case EAST: facing = 2; break;
		case WEST: facing = 3; break;
		default: facing = 0; break;
		}
		
		int half = bottom ? 8 : 0;
		int o = open ? 4 : 0;
		
		block.setMeta(facing + half + o);
		
		return block;
		
	}
	
}
