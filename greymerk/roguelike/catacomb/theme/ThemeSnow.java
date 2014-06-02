package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeSnow extends ThemeBase{

	public ThemeSnow(Random rand){
	
		BlockRandomizer walls = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID));
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 30);
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 30);
		walls.addBlock(new MetaBlock(Block.ice.blockID), 20);
		walls.addBlock(new MetaBlock(Block.blockSnow.blockID), 20);
		
		BlockRandomizer bridge = new BlockRandomizer(rand, new MetaBlock(Block.blockSnow.blockID));
		bridge.addBlock(new MetaBlock(0), 3);
		walls.addBlock(new MetaBlock(Block.ice.blockID), 3);
		
		MetaBlock stair = new MetaBlock(Block.stairsStoneBrick.blockID);
		MetaBlock pillar = new MetaBlock(Block.wood.blockID, 1);
		
		this.walls = new BlockSet(walls, bridge, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Block.blockSnow.blockID);
		MetaBlock SegmentStair = new MetaBlock(Block.stairsCobblestone.blockID);
		
		this.decor =  new BlockSet(SegmentWall, SegmentWall, SegmentStair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.FIRE, Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
