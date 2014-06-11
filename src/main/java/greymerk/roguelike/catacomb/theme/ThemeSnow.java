package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.init.Blocks;

public class ThemeSnow extends ThemeBase{

	public ThemeSnow(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 100);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 1), 5);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 2), 5);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		MetaBlock pillar = Log.getLog(Log.SPRUCE);
		
		this.walls = new BlockSet(walls, stair, walls);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.snow);
		MetaBlock SegmentStair = new MetaBlock(Blocks.stone_stairs);
		
		this.decor =  new BlockSet(SegmentWall, SegmentStair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.FIRE, Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
