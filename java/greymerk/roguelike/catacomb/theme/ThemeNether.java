package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ThemeNether extends ThemeBase{

	public ThemeNether(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.nether_brick), 200);
		walls.addBlock(new MetaBlock(Blocks.netherrack), 20);
		walls.addBlock(new MetaBlock(Blocks.quartz_ore), 20);
		walls.addBlock(new MetaBlock(Blocks.soul_sand), 15);
		walls.addBlock(new MetaBlock(Blocks.coal_block), 5);

		MetaBlock stair = new MetaBlock(Blocks.nether_brick_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.obsidian);
		
		this.walls = new BlockSet(walls, stair, pillar);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.INSET, Segment.NETHERSTRIPE, Segment.NETHERWART, Segment.NETHERLAVA));
		
		this.arch = Segment.NETHERARCH;
	}
}
