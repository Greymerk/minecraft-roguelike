package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.Block;

public class Stair{

	public static MetaBlock get(Block type, Cardinal dir, boolean upsideDown){
		MetaBlock stair = new MetaBlock(type);
		WorldEditor.blockOrientation(stair, dir, upsideDown);
		return stair;
	}
	
}
