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

public class ThemeSpruce extends ThemeBase{

	public ThemeSpruce(){
	
		BlockJumble rubble = new BlockJumble(new MetaBlock(Block.stoneBrick.blockID, 2));
		rubble.addBlock(new MetaBlock(Block.cobblestone.blockID));
		rubble.addBlock(new MetaBlock(Block.gravel.blockID));
		
		BlockRandomizer walls = new BlockRandomizer(new MetaBlock(Block.stoneBrick.blockID));
		walls.addBlock(rubble, 3);
		
		MetaBlock stair = new MetaBlock(Block.stairsStoneBrick.blockID);
		
		this.walls = new BlockSet(walls, stair, walls);
		
		MetaBlock SegmentWall = new MetaBlock(Block.planks.blockID, 1);
		MetaBlock SegmentStair = new MetaBlock(Block.stairsWoodSpruce.blockID);
		
		MetaBlock pillar = new MetaBlock(Block.wood.blockID, 1);
		this.decor =  new BlockSet(SegmentWall, SegmentStair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
