package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeRainbow extends ThemeBase{

	public ThemeRainbow(){
	
		BlockStripes rainbow = new BlockStripes();
		for(int i = 1; i < 16; ++i){
			rainbow.addBlock(new MetaBlock(Block.stainedClay.blockID, i));
		}
		
		MetaBlock stair = new MetaBlock(Block.stairsNetherQuartz.blockID);
		
		BlockFactoryCheckers pillar = new BlockFactoryCheckers(new MetaBlock(Block.obsidian.blockID), new MetaBlock(Block.blockNetherQuartz.blockID)); 
		
		this.walls = new BlockSet(rainbow, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Block.blockNetherQuartz.blockID, 1);
		
		this.decor = new BlockSet(new MetaBlock(Block.glowStone.blockID), stair, new MetaBlock(Block.blockEmerald.blockID));;
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
