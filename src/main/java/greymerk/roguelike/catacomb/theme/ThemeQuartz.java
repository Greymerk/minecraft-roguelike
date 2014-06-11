package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.init.Blocks;

public class ThemeQuartz extends ThemeBase{

	public ThemeQuartz(){
	
		MetaBlock walls = new MetaBlock(Blocks.quartz_block);
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.quartz_block, 2);
		
		this.walls = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.quartz_block, 1);
		
		this.decor =  new BlockSet(SegmentWall, stair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
