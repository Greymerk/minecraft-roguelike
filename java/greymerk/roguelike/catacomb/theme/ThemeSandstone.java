package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
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
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
