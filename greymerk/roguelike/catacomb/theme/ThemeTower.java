package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeTower extends ThemeBase{

	public ThemeTower(Random rand){
	
		BlockJumble stone = new BlockJumble(rand, new MetaBlock(Block.stoneBrick.blockID));
		stone.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1));
		stone.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2));
		
		BlockRandomizer walls = new BlockRandomizer(rand, stone);
		walls.addBlock(new MetaBlock(Block.cobblestone.blockID, 1), 5);
		walls.addBlock(new MetaBlock(Block.gravel.blockID, 1), 15);
		walls.addBlock(new MetaBlock(0), 20);
		
		MetaBlock stair = new MetaBlock(Block.stairsStoneBrick.blockID);
		MetaBlock pillar = Log.getLog(Log.OAK);
		
		this.walls = new BlockSet(walls, walls, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Block.planks.blockID);
		MetaBlock SegmentStair = new MetaBlock(Block.stairsWoodOak.blockID);
		
		this.decor =  new BlockSet(SegmentWall, SegmentWall, SegmentStair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.FIRE, Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
