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
		this.setMeta(Cardinal.getBlockMeta(dir) + (upsideDown ? 4 : 0));
		return this;
	}

}
