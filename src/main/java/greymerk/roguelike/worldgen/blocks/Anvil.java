package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public enum Anvil {

	NEW_ANVIL, DAMAGED_ANVIL, VERY_DAMAGED_ANVIL;
	
	public static MetaBlock get(Anvil damage, Cardinal dir){
		
		MetaBlock anvil = new MetaBlock(Blocks.anvil);
		
		int d;
		switch(dir){
		case NORTH: d = 0; break;
		case EAST: d = 1; break;
		case SOUTH: d = 2; break;
		case WEST: d = 3; break;
		default: d = 0;
		}
		
		switch(damage){
		case NEW_ANVIL: anvil.setMeta(d); break;
		case DAMAGED_ANVIL: anvil.setMeta(d + 4); break;
		case VERY_DAMAGED_ANVIL: anvil.setMeta(d + 8); break;
		default: anvil.setMeta(d);
		}
		
		return anvil;
	}
	
}
