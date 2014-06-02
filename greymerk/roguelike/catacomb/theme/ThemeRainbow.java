package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeRainbow extends ThemeBase{

	public ThemeRainbow(Random rand){
	
		BlockStripes rainbow = new BlockStripes(rand, new MetaBlock(Block.stainedClay.blockID));
		for(int i = 1; i < 16; ++i){
			rainbow.addBlock(new MetaBlock(Block.stainedClay.blockID, i));
		}
		
		MetaBlock stair = new MetaBlock(Block.stairsNetherQuartz.blockID);
		
		this.walls = new BlockSet(rainbow, rainbow, stair, rainbow);
		
		MetaBlock SegmentWall = new MetaBlock(Block.blockNetherQuartz.blockID, 1);
		
		this.decor = walls;
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
