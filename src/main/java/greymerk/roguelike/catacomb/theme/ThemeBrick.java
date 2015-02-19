package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

public class ThemeBrick extends ThemeBase{

	public ThemeBrick(){
	
		MetaBlock walls = new MetaBlock(Blocks.brick_block);
		
		MetaBlock stair = new MetaBlock(Blocks.brick_stairs);
		MetaBlock pillar = Log.getLog(Log.SPRUCE);
		
		this.primary = new BlockSet(walls, stair, walls);
		
		MetaBlock segmentWall = new MetaBlock(Blocks.planks);
		segmentWall.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE);
		MetaBlock SegmentStair = new MetaBlock(Blocks.brick_stairs);
		
		this.secondary =  new BlockSet(segmentWall, SegmentStair, pillar);

	}
}
