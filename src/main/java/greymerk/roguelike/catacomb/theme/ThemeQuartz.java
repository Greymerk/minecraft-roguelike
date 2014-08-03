package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeQuartz extends ThemeBase{

	public ThemeQuartz(){
	
		MetaBlock walls = new MetaBlock(Blocks.quartz_block);
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.quartz_block, 2);
		
		this.walls = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.quartz_block, 1);
		
		this.decor =  new BlockSet(SegmentWall, stair, pillar);

	}
}
