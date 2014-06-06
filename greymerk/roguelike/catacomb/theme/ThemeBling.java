package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeBling extends ThemeBase{

	public ThemeBling(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Block.blockIron.blockID), 10);
		walls.addBlock(new MetaBlock(Block.blockGold.blockID), 3);
		walls.addBlock(new MetaBlock(Block.blockEmerald.blockID), 10);
		walls.addBlock(new MetaBlock(Block.blockDiamond.blockID), 20);
		
		MetaBlock stair = new MetaBlock(Block.stairsNetherQuartz.blockID);
		MetaBlock pillar = new MetaBlock(Block.blockLapis.blockID);
		this.walls = new BlockSet(walls, stair, pillar);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
