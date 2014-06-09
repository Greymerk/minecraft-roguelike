package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeNether extends ThemeBase{

	public ThemeNether(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Block.netherBrick.blockID), 200);
		walls.addBlock(new MetaBlock(Block.netherrack.blockID), 20);
		walls.addBlock(new MetaBlock(Block.oreNetherQuartz.blockID), 20);
		walls.addBlock(new MetaBlock(Block.slowSand.blockID), 15);
		walls.addBlock(new MetaBlock(Block.coalBlock.blockID), 5);

		MetaBlock stair = new MetaBlock(Block.stairsNetherBrick.blockID);
		
		MetaBlock pillar = new MetaBlock(Block.obsidian.blockID);
		
		this.walls = new BlockSet(walls, stair, pillar);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.INSET, Segment.NETHERSTRIPE, Segment.NETHERWART, Segment.NETHERLAVA));
		
		this.arch = Segment.NETHERARCH;
	}
}
