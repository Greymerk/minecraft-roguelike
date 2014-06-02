package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeBling extends ThemeBase{

	public ThemeBling(Random rand){
	
		BlockRandomizer walls = new BlockRandomizer(rand, new MetaBlock(Block.blockIron.blockID));
		walls.addBlock(new MetaBlock(Block.blockGold.blockID), 3);
		walls.addBlock(new MetaBlock(Block.blockEmerald.blockID), 10);
		walls.addBlock(new MetaBlock(Block.blockDiamond.blockID), 20);

		
		BlockRandomizer bridge = new BlockRandomizer(rand, new MetaBlock(Block.blockIron.blockID));
		bridge.addBlock(new MetaBlock(0), 3);
		
		MetaBlock stair = new MetaBlock(Block.stairsNetherQuartz.blockID);
		MetaBlock pillar = new MetaBlock(Block.blockLapis.blockID);
		this.walls = new BlockSet(walls, bridge, stair, pillar);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
