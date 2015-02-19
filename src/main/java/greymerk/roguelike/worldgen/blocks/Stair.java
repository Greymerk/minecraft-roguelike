package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import net.minecraft.block.Block;

public class Stair{

	public static MetaBlock get(Block type, Cardinal dir, boolean upsideDown){
		MetaBlock stair = new MetaBlock(type);
		WorldGenPrimitive.blockOrientation(stair, dir, upsideDown);
		return stair;
	}
	
}
