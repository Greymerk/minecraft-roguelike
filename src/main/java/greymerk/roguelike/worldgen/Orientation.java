package greymerk.roguelike.worldgen;

import net.minecraft.init.Blocks;

public class Orientation {

	public static void set(MetaBlock block, Cardinal dir){
		if(block.getBlockID() == Blocks.lit_pumpkin){
			switch(dir){
			case SOUTH: block.setMeta(0); return;
			case WEST: block.setMeta(1); return;
			case NORTH: block.setMeta(2); return;
			case EAST: block.setMeta(3); return;
			case UP: block.setMeta(4); return;
			case DOWN: block.setMeta(4); return;
			}
		}
	}
}
