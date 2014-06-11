package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ThemeBling extends ThemeBase{

	public ThemeBling(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.iron_block), 10);
		walls.addBlock(new MetaBlock(Blocks.gold_block), 3);
		walls.addBlock(new MetaBlock(Blocks.emerald_block), 10);
		walls.addBlock(new MetaBlock(Blocks.diamond_block), 20);
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.lapis_block);
		this.walls = new BlockSet(walls, stair, pillar);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
