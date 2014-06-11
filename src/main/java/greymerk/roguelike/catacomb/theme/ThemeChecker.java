package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ThemeChecker extends ThemeBase{

	public ThemeChecker(){
	
		MetaBlock one = new MetaBlock(Blocks.obsidian);
		MetaBlock two = new MetaBlock(Blocks.quartz_block);
		
		IBlockFactory checks = new BlockFactoryCheckers(one, two);
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		
		this.walls = new BlockSet(checks, stair, checks);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.quartz_block, 1);
		
		this.decor = walls;
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
