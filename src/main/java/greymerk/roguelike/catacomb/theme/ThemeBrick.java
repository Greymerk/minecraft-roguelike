package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeBrick extends ThemeBase{

	public ThemeBrick(){
	
		MetaBlock walls = new MetaBlock(Blocks.brick_block);
		
		MetaBlock stair = new MetaBlock(Blocks.brick_stairs);
		MetaBlock pillar = Log.getLog(Log.SPRUCE);
		
		this.walls = new BlockSet(walls, stair, walls);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.planks, 1);
		MetaBlock SegmentStair = new MetaBlock(Blocks.brick_stairs);
		
		this.decor =  new BlockSet(SegmentWall, SegmentStair, pillar);
		
		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 3));
		this.segments.add(new WeightedChoice<Segment>((Segment.FLOWERS), 4));
		this.segments.add(new WeightedChoice<Segment>((Segment.BOOKS), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.WHEAT), 2));
		this.segments.add(new WeightedChoice<Segment>((Segment.FIREPLACE), 2));
		
		this.arch = Segment.ARCH;
	}
}
