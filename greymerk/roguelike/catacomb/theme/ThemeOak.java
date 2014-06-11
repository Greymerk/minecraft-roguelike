package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ThemeOak extends ThemeBase{

	public ThemeOak(){
	
		BlockJumble rubble = new BlockJumble();
		rubble.addBlock(new MetaBlock(Blocks.stonebrick, 1));
		rubble.addBlock(new MetaBlock(Blocks.cobblestone));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 20);
		walls.addBlock(rubble, 10);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		MetaBlock pillar = Log.getLog(Log.OAK);
		
		this.walls = new BlockSet(walls, stair, walls);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.planks);
		MetaBlock SegmentStair = new MetaBlock(Blocks.oak_stairs);
		
		this.decor =  new BlockSet(SegmentWall, SegmentStair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.FIRE, Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
