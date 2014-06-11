package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class SegmentFire extends SegmentBase {

	private int stairType;
	
	@Override
	protected void genWall(Cardinal wallDirection) {
		
		MetaBlock stair = new MetaBlock(Blocks.brick_stairs);

		// TODO: Redo brick segment
		
		
	}
}
