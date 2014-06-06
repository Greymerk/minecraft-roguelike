package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeSandstone extends ThemeBase{

	public ThemeSandstone(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom(new MetaBlock(Block.sandStone.blockID));
		walls.addBlock(new MetaBlock(Block.sand.blockID), 30);
		
		MetaBlock stair = new MetaBlock(Block.stairsSandStone.blockID);
		
		MetaBlock pillar = new MetaBlock(Block.sandStone.blockID, 2);
		
		this.walls = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Block.sandStone.blockID, 1);
		
		this.decor =  new BlockSet(SegmentWall, stair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
