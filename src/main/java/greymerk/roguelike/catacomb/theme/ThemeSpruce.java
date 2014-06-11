package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.init.Blocks;

public class ThemeSpruce extends ThemeBase{

	public ThemeSpruce(){
	
		BlockJumble rubble = new BlockJumble();
		rubble.addBlock(new MetaBlock(Blocks.stonebrick, 2));
		rubble.addBlock(new MetaBlock(Blocks.cobblestone));
		rubble.addBlock(new MetaBlock(Blocks.gravel));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 100);
		walls.addBlock(rubble, 10);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		
		this.walls = new BlockSet(walls, stair, walls);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.planks, 1);
		MetaBlock SegmentStair = new MetaBlock(Blocks.spruce_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.log, 1);
		this.decor =  new BlockSet(SegmentWall, SegmentStair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
