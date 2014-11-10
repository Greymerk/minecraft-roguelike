package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeBrick extends ThemeBase{

	public ThemeBrick(){
	
		MetaBlock walls = new MetaBlock(Blocks.brick_block);
		
		MetaBlock stair = new MetaBlock(Blocks.brick_stairs);
		MetaBlock pillar = Log.getLog(Log.SPRUCE);
		
		this.primary = new BlockSet(walls, stair, walls);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.planks, 1);
		MetaBlock SegmentStair = new MetaBlock(Blocks.brick_stairs);
		
		this.secondary =  new BlockSet(SegmentWall, SegmentStair, pillar);

	}
}
