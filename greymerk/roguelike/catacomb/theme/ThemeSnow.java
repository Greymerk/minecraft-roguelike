package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeSnow extends ThemeBase{

	public ThemeSnow(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID), 100);
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 5);
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 5);
		
		MetaBlock stair = new MetaBlock(Block.stairsStoneBrick.blockID);
		MetaBlock pillar = Log.getLog(Log.SPRUCE);
		
		this.walls = new BlockSet(walls, stair, walls);
		
		MetaBlock SegmentWall = new MetaBlock(Block.blockSnow.blockID);
		MetaBlock SegmentStair = new MetaBlock(Block.stairsCobblestone.blockID);
		
		this.decor =  new BlockSet(SegmentWall, SegmentStair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.FIRE, Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
