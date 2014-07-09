package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeSpruce extends ThemeBase{

	public ThemeSpruce(){
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 0), 20);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 2), 10);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 5);
		walls.addBlock(new MetaBlock(Blocks.gravel), 1);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		
		this.walls = new BlockSet(walls, stair, walls);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.planks, 1);
		MetaBlock SegmentStair = new MetaBlock(Blocks.spruce_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.log, 1);
		this.decor =  new BlockSet(SegmentWall, SegmentStair, pillar);
		
		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 1));
		
		this.arch = Segment.ARCH;
	}
}
