package greymerk.roguelike.worldgen;

import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.block.Block;

public class MetaStair extends MetaBlock implements IStair{

	public MetaStair(Block block) {
		super(block);
	}

	public MetaStair(MetaBlock block){
		super(block);
	}
	
	public MetaStair(StairType type){
		super(StairType.getBlock(type));
	}
	
	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown){
		this.setMeta(getBlockMeta(dir) + (upsideDown ? 4 : 0));
		return this;
	}
	
	public static int getBlockMeta(Cardinal dir){
		switch(dir){
		case NORTH: return 2;
		case EAST: return 1;
		case WEST: return 0;
		case SOUTH: return 3;
		default: return 0;
		}
	}
}
