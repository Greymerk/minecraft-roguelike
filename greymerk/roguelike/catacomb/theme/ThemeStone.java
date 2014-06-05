package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeStone extends ThemeBase{

	public ThemeStone(){
	
		BlockRandomizer walls = new BlockRandomizer(new MetaBlock(Block.stoneBrick.blockID, 2));
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 3);
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID), 6);
		walls.addBlock(new MetaBlock(Block.cobblestone.blockID), 10);
		walls.addBlock(new MetaBlock(Block.gravel.blockID), 20);
		
		MetaBlock stair = new MetaBlock(Block.stairsStoneBrick.blockID);
		
		this.walls = new BlockSet(walls, stair, walls);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
