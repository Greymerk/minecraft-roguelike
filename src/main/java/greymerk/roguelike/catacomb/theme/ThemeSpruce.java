package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

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
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
