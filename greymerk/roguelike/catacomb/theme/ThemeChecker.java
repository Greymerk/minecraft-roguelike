package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeChecker extends ThemeBase{

	public ThemeChecker(){
	
		MetaBlock one = new MetaBlock(Block.obsidian.blockID);
		MetaBlock two = new MetaBlock(Block.blockNetherQuartz.blockID);
		
		IBlockFactory checks = new BlockFactoryCheckers(one, two);
		
		MetaBlock stair = new MetaBlock(Block.stairsNetherQuartz.blockID);
		
		this.walls = new BlockSet(checks, stair, checks);
		
		MetaBlock SegmentWall = new MetaBlock(Block.blockNetherQuartz.blockID, 1);
		
		this.decor = walls;
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
