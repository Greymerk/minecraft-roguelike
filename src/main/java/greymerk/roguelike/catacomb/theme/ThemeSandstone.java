package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeSandstone extends ThemeBase{

	public ThemeSandstone(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.sandstone), 100);
		walls.addBlock(new MetaBlock(Blocks.sand), 5);
		
		MetaBlock stair = new MetaBlock(Blocks.sandstone_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.sandstone, 2);
		
		this.walls = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.sandstone, 1);
		
		this.decor =  new BlockSet(SegmentWall, stair, pillar);
		
		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 1));
		
		this.arch = Segment.ARCH;
	}
}
