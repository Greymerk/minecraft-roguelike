package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeJungle extends ThemeBase{

	public ThemeJungle(){
	
		BlockRandomizer walls = new BlockRandomizer(new MetaBlock(Block.cobblestoneMossy.blockID, 2));
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 3);
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 5);
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 3), 20);
		
		MetaBlock stair = new MetaBlock(Block.stairsCobblestone.blockID);
		
		MetaBlock pillar = new MetaBlock(Block.wood.blockID, 3);
		
		
		
		this.walls = new BlockSet(walls, stair, pillar);
		this.decor = new BlockSet(new MetaBlock(Block.stoneBrick.blockID, 3), stair, pillar);

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.JUNGLE, Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.MOSSYARCH;
	}
}
